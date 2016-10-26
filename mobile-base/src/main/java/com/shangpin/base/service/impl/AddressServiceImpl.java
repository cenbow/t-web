package com.shangpin.base.service.impl;

import com.shangpin.base.service.AddressService;
import com.shangpin.base.service.CustomerService;
import com.shangpin.base.utils.BaseDataUtil;
import com.shangpin.base.utils.GlobalConstants;
import com.shangpin.base.vo.ConsigneeAddress;
import com.shangpin.utils.HttpClientUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {

    // 查询收货地址请求的URL
    private StringBuilder findAddressListURL = new StringBuilder(GlobalConstants.USER_SERVICE_ADDR).append("list");
    // 删除收货地址请求的URL
    private StringBuilder deleteAddressURL = new StringBuilder(GlobalConstants.USER_SERVICE_ADDR).append("del");
    // 获取设定根据地址id获取收货地址信息的URL
    private StringBuilder findAddressByAddressIdURL = new StringBuilder(GlobalConstants.USER_SERVICE_ADDR).append("get/");
    // 获取省级行政区信息URL
    private StringBuilder findProvinceListURL = new StringBuilder(GlobalConstants.USER_SERVICE_ADDR).append("cascade/");
    // 编辑收货地址请求的URL
    private StringBuilder editAddressURL = new StringBuilder(GlobalConstants.USER_SERVICE_ADDR).append("edit");
    // 新增收货地址请求的URL
    private StringBuilder addConsigneeAddressURL = new StringBuilder(GlobalConstants.USER_SERVICE_ADDR).append("add");

    private String accessAddress = new StringBuilder(GlobalConstants.BASE_URL_ORDER).append("ShopCart/MainDeliverAddress").toString();

    // 获取设定默认收货地址的URL
    private StringBuilder modifyDefaultConsigneeAddressURL = new StringBuilder(GlobalConstants.BASE_URL_SHANGPIN_AOLAI).append("setDefaultConsigneeAddress");
    // 查询收货地址请求的URL
    private StringBuilder getConsigneeAddressURL = new StringBuilder(GlobalConstants.BASE_URL_SHANGPIN_AOLAI).append("getconsigneeaddress");

    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public String findAddressList(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("userid", userId);
        params.put("version", GlobalConstants.MOBILE_VERSION);
        return HttpClientUtil.doGet(findAddressListURL.toString(), params);
    }

    @Override
    public String deleteAddress(String userId, String addrId) {
        Map<String, String> params = new HashMap<>();
        params.put("userid", userId);
        params.put("addrid", addrId);
        params.put("version", GlobalConstants.MOBILE_VERSION);
        return HttpClientUtil.doGet(deleteAddressURL.toString(), params);
    }

    @Override
    public String findAddressByAddressId(String userId, String addrId) {
        Map<String, String> params = new HashMap<>();
        params.put("userid", userId);
        params.put("version",GlobalConstants.MOBILE_VERSION);
        return HttpClientUtil.doGet(findAddressByAddressIdURL.toString()+addrId, params);
    }

    @Override
    public String findAddrNewList(String pid) {
        return BaseDataUtil.findData(GlobalConstants.CACHE_BASE, "cascade/"+pid, new HashMap<String, String>(), findProvinceListURL.toString()+pid);
    }

    /**
     * 编辑收货地址
     */
    @Override
    public String modifyConsigneeAddress(ConsigneeAddress consigneeAddressVO) {
        Map<String, String> params = new HashMap<>();
        params.put("userid", consigneeAddressVO.getUserId());
        params.put("addrid", consigneeAddressVO.getAddrId());
        params.put("consigneename", consigneeAddressVO.getConsigneeName());
        params.put("province", consigneeAddressVO.getProvince());
        params.put("city", consigneeAddressVO.getCity());
        params.put("area", consigneeAddressVO.getArea());
        params.put("address", consigneeAddressVO.getAddress());
        params.put("town", consigneeAddressVO.getTown());
        params.put("postcode", consigneeAddressVO.getPostcode());
        params.put("tel", consigneeAddressVO.getTel());
        params.put("cardID", consigneeAddressVO.getCardID());
        params.put("setd", consigneeAddressVO.getSetd());
        params.put("version", GlobalConstants.MOBILE_VERSION);
        return HttpClientUtil.doPost(editAddressURL.toString(), params);
    }


    /**
     * 设定默认收货地址（尚品、奥莱完全相同）
     *
     * @author liujie
     */
    @Override
    public String modifyDefaultConsigneeAddress(String userId, String addrId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("addrid", addrId);
        params.put("userid", userId);
        String result = HttpClientUtil.doGet(modifyDefaultConsigneeAddressURL.toString(), params);
        return result;
    }


    /**
     * 新增收货地址
     */
    @Override
    public String addConsigneeAddress(ConsigneeAddress consigneeAddressVO) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userid", consigneeAddressVO.getUserId());
        params.put("consigneename", consigneeAddressVO.getConsigneeName());
        params.put("province", consigneeAddressVO.getProvince());
        params.put("city", consigneeAddressVO.getCity());
        params.put("area", consigneeAddressVO.getArea());
        params.put("town", consigneeAddressVO.getTown());
        params.put("address", consigneeAddressVO.getAddress());
        params.put("postcode", consigneeAddressVO.getPostcode());
        params.put("tel", consigneeAddressVO.getTel());
        params.put("cardID", consigneeAddressVO.getCardID());
        params.put("setd", consigneeAddressVO.getSetd());
        params.put("version",GlobalConstants.MOBILE_VERSION);
        return HttpClientUtil.doPost(addConsigneeAddressURL.toString(), params);
    }

    /**
     * 查询收货地址
     */
    @Override
    public String findConsigneeAddress(String userId,String isInvoice) {
    	Map<String, String> params = new HashMap<>();
    	params.put("userid", userId);
    	params.put("isinvoice", isInvoice);
    	String result = HttpClientUtil.doGet(getConsigneeAddressURL.toString(), params);
    	return result;
    }

    @Override
    public String accessAddress(String s) {

        Map<String, String> params = new HashMap<>();
        params.put("jsonMsg", s);
        String result = HttpClientUtil.doGet(accessAddress.toString(), params);
        return result;
    }

}
