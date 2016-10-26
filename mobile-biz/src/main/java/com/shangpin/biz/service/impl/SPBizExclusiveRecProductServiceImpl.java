package com.shangpin.biz.service.impl;

import com.shangpin.base.service.CommonService;
import com.shangpin.base.service.RecRuleService;
import com.shangpin.biz.bo.Product;
import com.shangpin.biz.bo.RecNewProductFor;
import com.shangpin.biz.bo.RecProductFor;
import com.shangpin.biz.bo.RecResultProduct;
import com.shangpin.biz.service.SPBizExclusiveRecProductService;
import com.shangpin.biz.service.SPBizRecommendService;
import com.shangpin.biz.service.abstraction.AbstractBizRecommendService;
import com.shangpin.biz.utils.SearchUtil;
import com.shangpin.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SPBizExclusiveRecProductServiceImpl extends  AbstractBizRecommendService implements SPBizExclusiveRecProductService{

    public static final Logger logger = LoggerFactory.getLogger(SPBizExclusiveRecProductServiceImpl.class);

	@Autowired
	CommonService commonService;

    @Autowired
    SPBizRecommendService spBizRecommendService;

    @Autowired
    private RecRuleService recRuleService;

	@Override
	public RecProductFor queryRecProduct(String type, String userId,
			String vuid, String coord, String ip, String pageIndex,
			String pageSize)  {
		RecProductFor recProductFor = null;
		try {
			recProductFor = doBaseRecProduct( type, userId,  vuid, coord,  ip, pageIndex,  pageSize);
            List<Product> products =new ArrayList<>();
            if(recProductFor!=null){
                for (Product product : recProductFor.getList()) {
                	/*if("1".equals(product.getIsPromotion())){
                		product.setTaxSellPrice(product.getTaxPromotionPrice());
                	}else{
                		product.setTaxSellPrice(product.getTaxStandardPrice());
                	}*/
                    Product promionProduct = SearchUtil.getPromionProduct(product, null, null, null);
                    products.add(promionProduct);
                }
                recProductFor.setList(products);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recProductFor;
	}

	@Override
	public RecNewProductFor recProductFromRecomannder(String userId,String uvId, String pageIndex, String pageSize) {
        try {
            //复用app的逻辑,所以这块版本号用最新的
            return newRecommander(userId,uvId,pageIndex,pageSize,"2.9.16");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public RecNewProductFor newRecommander(String userId, String uvId, String pageIndex, String pagesize,String version) throws Exception{

        //start 查询条数
        String offset="0";//调用搜索的推荐开始位置
        int limit=Integer.parseInt(pageIndex);
        if(limit>1){
            limit= limit-1;
            int start=limit*Integer.parseInt(pagesize);
            offset=String.valueOf(start);
        }
        String data="";
        data =recRuleService.doRecRuleCookie(userId, uvId,offset,pagesize);
        List<String> product=getproductList(data);
        List<Product> list =new ArrayList<Product>();
        if(null!=product&&product.size()>0){
            list=spBizRecommendService.products(product,userId, version);
        }
        //推荐结果Id反序列化
        RecResultProduct recproduct = JsonUtil.fromJson(data, RecResultProduct.class);
        RecNewProductFor rec =new RecNewProductFor();
        if (list != null&&list.size()>0) {
            rec.setSystime(String.valueOf(System.currentTimeMillis()));
            rec.setRecommendNum(String.valueOf(list.size()));
            if(null!=recproduct){
                rec.setRecid(recproduct.getRecid());
            }
            rec.setList(list);
        }else{
            rec.setSystime(String.valueOf(System.currentTimeMillis()));
            rec.setRecommendNum(String.valueOf(list.size()));
            if(null!=recproduct){
                rec.setRecid(recproduct.getRecid());
            }
            rec.setList(new ArrayList<Product>());
        }
        return rec;
    }

    /**
     * 解析获取产品编码集合
     * @param data
     * @return
     */
    public List<String> getproductList(String data){
        List<String> product =new ArrayList<String>();
        RecResultProduct recproduct = JsonUtil.fromJson(data, RecResultProduct.class);
        if (null != recproduct) {
            String args[]=recproduct.getResults();
            if(null!=args&&args.length>0){
                for (int i = 0; i < args.length; i++) {
                    product.add(args[i]);
                }
            }
        }
        return product;
    }

}
