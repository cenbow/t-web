package com.shangpin.biz.service.impl;

import com.google.common.reflect.TypeToken;
import com.shangpin.base.service.ShangPinService;
import com.shangpin.base.vo.ResultObjOne;
import com.shangpin.biz.bo.*;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.service.SPBizActivityService;
import com.shangpin.biz.service.SPBizBrandActivityService;
import com.shangpin.biz.service.SPBizSearchService;
import com.shangpin.biz.utils.Constants;
import com.shangpin.biz.utils.StringUtil;
import com.shangpin.utils.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SPBizActivityServiceImpl implements SPBizActivityService {

    @Autowired
    SPBizBrandActivityService aspBizBrandActivityService;
    @Autowired
    SPBizSearchService spBizSerchService;

    @Autowired
    ShangPinService shangPinService;

    public Map<String, Object> aActivityStartRemind(String phoneNum, String actityId, String time) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String json=shangPinService.activityStartRemind(phoneNum,actityId,time);
            ResultBase resultBase = JsonUtil.fromJson(json, ResultBase.class);
            if (Constants.SUCCESS.equals(resultBase.getCode())) {
                map.put("code", Constants.SUCCESS);
            } else {
                map.put("code", Constants.FAILE);
                String msg = resultBase.getMsg();
                if (StringUtil.isNotEmpty(msg)) {
                    map.put("mag", resultBase.getMsg());
                } else {
                    map.put("mag", "关注失败");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     * M站调用
     */
    @Override
    public ActivityIndex searchActivityIndex(String userId,String subjectNo, String start, String end, String tagId, String brandId, String price, String colorId, String size, String categoyId,
                                             String order, String gender, String userLv, String postArea, String imei) throws Exception {
        ActivityIndex activityIndex=getSearchActivityIndexWithoutSearch(userId, subjectNo);
        ActivityHead activity = activityIndex.getOperat().getActivity();
        SearchResult searchResult=spBizSerchService.queryActivityProductList(subjectNo, start, end, tagId, brandId, price, colorId, size, categoyId, order, gender, userLv, postArea,activity.getIsPre(),activity, imei, "2.9.16");
        activityIndex.setResult(searchResult);
        return activityIndex;
    }
    /**
     * M站调用
     */
    @Override
    public SearchResult searchActivityProduct(String subjectNO, String start, String end, String tagId, String brandNo, String price, String color, String size, String categoryNo, String order, String gender, String userLv, String postArea, String imei,ActivityHead activityHead) {
//		 SearchResult searchResult=spBizSerchService.queryActivityProductList(subjectNO, start, end, tagId, brandNo, price, color, size, categoryNo, order, gender, userLv, postArea,null,null, imei);
        SearchResult searchResult=spBizSerchService.queryActivityProductList(subjectNO, start, end, tagId, brandNo, price, color, size, categoryNo, order, gender, userLv, postArea,activityHead.getIsPre(),activityHead, imei, "2.9.16");
        return searchResult;
    }

    public ActivityIndex getSearchActivityIndexWithoutSearch(String userId,String subjectNo){
        ActivityIndex activityIndex=new ActivityIndex();
        activityIndex.setSysTime(String.valueOf(System.currentTimeMillis()));
        OperatHeader operatHeader=new OperatHeader();
        BrandActivityHead brandActivityHead = getBrandActivityHead(userId, subjectNo);
        operatHeader.setHead(brandActivityHead.getHead());
        operatHeader.setActivity(brandActivityHead.getActivity());
        operatHeader.setCoupon(aspBizBrandActivityService.couponInfoObj(userId, subjectNo, "1"));
        activityIndex.setOperat(operatHeader);
        return activityIndex;
    }
    /**
     * 是否预热
     *
     * @param preTime 预热时间
     * @param startTime 活动开始时间
     * @return
     */
    public String isPre(String preTime, String startTime) {
        String isPre = "0";
        if(preTime!=null&&!"".equals(preTime)&&startTime!=null&&!"".equals(startTime)){
            Long pre = Long.valueOf(preTime);
            Long start = Long.valueOf(startTime);
            Long now = System.currentTimeMillis();
            if (now > pre && now < start) {
                isPre = "1";
            }
        }
        return isPre;
    }

    public BrandActivityHead getBrandActivityHead(String userId,String subjectNo){
        BrandActivityHead brandActivityHead=new BrandActivityHead();
        brandActivityHead=aspBizBrandActivityService.headInfoObj(userId, subjectNo, "1");
        String isPre=isPre(brandActivityHead.getActivity().getDatepreTime(),brandActivityHead.getActivity().getStartTime());
        brandActivityHead.getActivity().setIsPre(isPre);
        //logger.info("BrandActivityHead"+JsonUtil.toJson(brandActivityHead));
        return brandActivityHead;
    }
    /**
     * M站调用
     */
    @Override
    public Map<String ,Object> getSubjectFloorInfo(String topicId,ActivityHead activityHead) {
        String data = aspBizBrandActivityService.subjectFloorInfo(topicId);
        if(StringUtils.isNotBlank(data)){
            ResultObjOne objOne = JsonUtil.fromJson(data, ResultObjOne.class);
            Map<String ,Object> map = new HashMap<>();
            if(objOne==null || !"0".equals(objOne.getCode())){
                return map;
            }
            String content = JsonUtil.toJson(objOne.getContent());
            JSONObject obj = JSONObject.fromObject(content);
            String isFloor = obj.getString("isFloor");
            boolean flag=false;
            if(isFloor==null||!"1".equals(isFloor)){
                map.put("isFloor", "0");//0不是楼层
                flag=true;
            }else{
                map.put("isFloor", "1");//1 是楼层
            }
            JSONObject headObj = obj.getJSONObject("headPic");
            String headPichtml = headObj.getString("html");
            if(StringUtils.isNotBlank(headPichtml)){
                //headPichtml = HtmlUtil.modifyHref(headPichtml, "1");
                Map<String ,String> headPic = new HashMap<>();
                headPic.put("html",headPichtml);
                headPic.put("css",headObj.getString("css"));
                headPic.put("js",headObj.getString("js"));
                map.put("headPic", headPic);
            }
            if(flag){
                return map;
            }
            JSONArray topicFloors = obj.getJSONArray("topicFloors");
            List<Map<String, Object>> floorList = new ArrayList<>();
            for (int i = 0; i < topicFloors.size(); i++) {
                Map<String, Object> floorMap = new HashMap<>();
                JSONObject floorObj = (JSONObject) topicFloors.get(i);
                String title = floorObj.getString("title");
                String pic = floorObj.getString("pic");
                if(StringUtils.isBlank(pic)){
                    String html = floorObj.getString("html");
                    String css = floorObj.getString("css");
                    String js = floorObj.getString("js");
                    floorMap.put("html", html);
                    floorMap.put("css", css);
                    floorMap.put("js", js);
                }else{
                    //floorMap.put("pic", PicCdnHash.getPicUrl(pic.trim(), "2"));//品牌图片 主站给的是图片编号
                    floorMap.put("pic", pic);
                }
                floorMap.put("title", title);

                String prodateStr = floorObj.getString("productList");
                List<ProductAtt> productAttrs = JsonUtil.fromJson(prodateStr, new TypeToken<List<ProductAtt>>(){}.getType());
                Map<String, String> productAttMap = new HashMap<>();
                List<String> spus = new ArrayList<>();
                for (ProductAtt productAtt : productAttrs) {
                    if(productAttMap.get(productAtt.getSpu())==null){//多sku只保留一个spu
                        spus.add(productAtt.getSpu().trim());
                        productAttMap.put(productAtt.getSpu(),productAtt.getOrder());
                    }
                }
                //调取搜索
                List<Product> productList = spBizSerchService.getProductListFromProductNo(spus, productAttMap,activityHead, null);
                floorMap.put("productList", productList);
                floorList.add(floorMap);
            }
            map.put("floorList", floorList);
            return map;
        }
        return null;
    }
    @Override
    public  Map<String, Object> activityStartRemind(String phoneNum, String actityId, String time) {
        return aActivityStartRemind(phoneNum,actityId,time);
    }



}
