package com.shangpin.biz.deprecated;

import com.google.common.reflect.TypeToken;
import com.shangpin.base.service.CommonService;
import com.shangpin.base.service.FreebieBaseService;
import com.shangpin.biz.bo.Freebie;
import com.shangpin.biz.bo.freeBie.FreebiePro;
import com.shangpin.biz.utils.Constants;
import com.shangpin.utils.AESUtil;
import com.shangpin.utils.JsonUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * #2016-04-29买赠分享页面，
 * 1.获取订单的可赠送商品,返回赠品分享数据<br/>
 * 2.对赠送品进行领取,记录领取人、领取记录，发送领取短信<br/>
 * 3.验证商品是否已经领取，<br/>
 * 4.获取所有的订单中可赠送的商品<br/>
 * @date:     2016年4月29日 <br/> 
 * @author 陈小峰
 * @since JDK7
 */
//@Service
public class FreebieService {
	private static final String SPLIT_CHAR = "`";
	private static final String freebieKey="key_shangpin_fbe";
	
	private static final Logger logger = LoggerFactory.getLogger(FreebieService.class);
	
	@Autowired
	private FreebieBaseService freebieBaseService;
	
	@Autowired
	private CommonService commonService;
	

	public Freebie getOrderFreeBie(String userId,String orderId){
		Freebie fb = this.fromOrderFreeBie(userId,orderId);
		return fb;
	}
	
	/**
	 * 加密分享url上面的参数 
	 * <br/>
	 * @param userId 用户id
	 * @param orderId 订单id
	 * @param skuId skuid 
	 * @return
	 */
	public static String encodeFreebieUrlParam(String userId,String orderId,String skuId, String sortNo){
		StringBuffer orgin= new StringBuffer();
		orgin.append(SPLIT_CHAR+userId);
		if(skuId!=null && !"".equals(skuId)){
			orgin.append(SPLIT_CHAR+skuId);
		}
		if(sortNo!=null && !"".equals(sortNo)){
			orgin.append(SPLIT_CHAR+sortNo);
		}
		try {
			return AESUtil.encrypt(orgin.toString(), freebieKey);
		} catch (Exception e) {
			logger.error("买赠参数加密失误，原始串：{}",orgin);
		}
		return "";
	}

    
	/**
	 * 调用主站 拼装数据
	 * @param userId
	 * @param orderId
	 * @return
	 */
	private Freebie fromOrderFreeBie(String userId, String orderId) {
		Freebie fb = null;
		String data = freebieBaseService.getOrderDeatil(userId,orderId);
		List<FreebiePro> freebieProList = this.getFreebieProList(data);
		if(freebieProList!=null && !freebieProList.isEmpty()){
			int size = freebieProList.size();
			fb = new Freebie();
			fb.setShareName(Constants.FREEBIE_520_SHARE_BUTTON_NAME);//app按钮的文字
			String isMorethan=null;//是否多于1件，0否:本地分享,1是多件，跳wapurl"
			if(size==1){
				isMorethan = "0";
				FreebiePro freebiePro = freebieProList.get(0);
				fb.setUrl(this.getShareUrl(userId, orderId, freebiePro.getSpu(),"001"));//分享的url
				fb.setDesc(Constants.FREEBIE_520_SHARE_DESC);//分享描述
				fb.setPic(Constants.FREEBIE_520_SHARE_PIC);//分享消息的图片
			}else{
				isMorethan = "1";
				StringBuffer url = new StringBuffer();
				url.append(commonService.getShangpinDomain());
				url.append(Constants.FREEBIE_520_SHARE_M_LIST_URL);
				fb.setWapurl(url.toString());//m站分享列表url
				fb.setWapTitle(Constants.FREEBIE_520_SHARE_M_TITLE);
			}
			fb.setIsMorethan(isMorethan);
		}
		return fb;
	}
	
	/**
	 * 获取加密后的url
	 * @param userId
	 * @param orderId
	 * @param spu
	 * @return
	 */
	public String getShareUrl(String userId,String orderId,String spu,String sortNo){
		StringBuffer url = new StringBuffer();
		url.append(commonService.getShangpinDomain());
		url.append("?p=").append(this.encodeFreebieUrlParam(userId, orderId, spu,sortNo));
		return url.toString();
	}
	
	/**
	 * 获取app的活动按钮 信息
	 * @param data
	 * @return
	 */
	private List<FreebiePro> getFreebieProList(String data){
		JSONObject jsonObj = JSONObject.fromObject(data);
		String code = jsonObj.getString("code");
		if(code!=null ||"0".equals(code)){
			JSONObject contentObj = jsonObj.getJSONObject("content");
			String productListStr = contentObj.getString("productList");
			List<FreebiePro> productList = JsonUtil.fromJson(productListStr, new TypeToken<List<FreebiePro>>(){}.getType());
			return productList;
		}
		return null;
	}
	
	
}
