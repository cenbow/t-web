package com.shangpin.biz.service.impl;

import com.google.gson.reflect.TypeToken;
import com.shangpin.base.service.AddressService;
import com.shangpin.base.service.CustomerService;
import com.shangpin.base.vo.ConsigneeAddress;
import com.shangpin.biz.bo.Address;
import com.shangpin.biz.bo.Province;
import com.shangpin.biz.bo.Region;
import com.shangpin.biz.bo.addr.AddrNew;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.base.ResultObjList;
import com.shangpin.biz.bo.base.ResultObjMapList;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.service.SPBizAddressService;
import com.shangpin.utils.AESUtil;
import com.shangpin.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SPBizAddressServiceImpl  implements SPBizAddressService {

	private static final Logger logger = LoggerFactory.getLogger(SPBizAddressServiceImpl.class);
	@Autowired
	private AddressService addressService;
	@Override
	public List<Address> findAddressObj(String userId) {
		String json = addressService.findAddressList(userId);
		if (!StringUtils.isEmpty(json)) {
			ResultObjList<Address> result = JsonUtil.fromJson(json, new TypeToken<ResultObjList<Address>>() {});
			if (!StringUtils.isEmpty(result) && result.isSuccess()) {
				return result.getContent();
			}
		}
		return null;
	}

	@Override
	public List<Province> findProvinceListObj() {
		String json = addressService.findAddrNewList("1");
		if (!StringUtils.isEmpty(json)) {
			ResultObjList<AddrNew> obj = JsonUtil.fromJson(json, new TypeToken<ResultObjList<AddrNew>>() {});
			if (!StringUtils.isEmpty(obj) && obj.isSuccess()) {
				return convertProvince(obj.getList());
			}
		}
		return null;
	}

	@Override
	public Address findAddressByIdObj(String userId, String addrId) {
		String json = addressService.findAddressByAddressId(userId, addrId);
		if (!StringUtils.isEmpty(json)) {
			ResultObjOne<Address> obj = JsonUtil.fromJson(json, new TypeToken<ResultObjOne<Address>>() {
			});
			if (!StringUtils.isEmpty(obj) && obj.isSuccess()) {
				return obj.getObj();
			}
		}
		return null;
	}

	@Override
	public List<Region> findCityListObj(String provinceId) {
		String json = addressService.findAddrNewList(provinceId);
		if (!StringUtils.isEmpty(json)) {
			ResultObjList<AddrNew> obj = JsonUtil.fromJson(json, new TypeToken<ResultObjList<AddrNew>>() {});
			if (!StringUtils.isEmpty(obj) && obj.isSuccess()) {
				return convertRegion(obj.getList());
			}
		}
		return null;
	}


	/**
	 * 提交订单：新增收货地址
	 */
	@Override
	public List<Address> addAddr(Address address, String userId) {
		try {
			ConsigneeAddress ca = putContainer(address, userId);
			ResultObjList<Address> obj = beAddAddress(ca);
			if (!StringUtils.isEmpty(obj)) {
				return obj.getList();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/**
	 * 提交订单：新增收货地址
	 */
	@Override
	public ResultObjList<Address> addAddrObj(Address address, String userId) {
		try {
			ConsigneeAddress ca = putContainer(address, userId);
			ResultObjList<Address> obj = beAddAddress(ca);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public boolean isDeleteAddress(String addrId, String userId) {
		String json = addressService.deleteAddress(userId, addrId);
		if (!StringUtils.isEmpty(json)) {
			ResultBase obj = JsonUtil.fromJson(json, new TypeToken<ResultBase>() {});
			if (!StringUtils.isEmpty(obj)) {
				return obj.isSuccess();
			}
		}
		return false;
	}

	/**
	 * 设置参数 装箱
	 * 
	 * @param address
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private ConsigneeAddress putContainer(Address address, String userId) throws Exception {
		ConsigneeAddress ca = new ConsigneeAddress();
		ca.setUserId(userId);
		ca.setAddrId(address.getId());
		ca.setConsigneeName(address.getName());
		ca.setProvince(address.getProvince());
		ca.setCity(address.getCity());
		ca.setArea(address.getArea());
		ca.setTown(address.getTown());
		ca.setAddress(address.getAddr());
		ca.setPostcode(address.getPostcode());
		ca.setTel(address.getTel());
        ca.setCardID(address.getCardID());
//		if (!StringUtils.isEmpty(address.getCardID())) { // 身份证号加密
//			ca.setCardID(AESUtil.encrypt(address.getCardID(), AESUtil.IDCARD_KEY));
//		}

		ca.setSetd(null != address.getIsd() && "on".equals(address.getIsd()) ? "true" : "false");
		return ca;
	}


	@Override
	public Address findAddressByListObj(String userId, String addrId) {
		List<Address> list = findAddressObj(userId);
		if (list != null) {
			for (Address address : list) {
				if (addrId.equals(address.getId())) {
					return address;
				}
			}
		}
		return null;
	}


	@Override
	public ResultObjList<Address> updateAddrObj(Address address, String userId) {
		try {
			ConsigneeAddress ca = putContainer(address, userId);
			String json = addressService.modifyConsigneeAddress(ca);
			if (!StringUtils.isEmpty(json)) {
				return  JsonUtil.fromJson(json, new TypeToken<ResultObjList<Address>>() {});
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}


	public List<Province> convertProvince(List<AddrNew> addrNews){
		if(addrNews==null ||addrNews.isEmpty()){
			return null;
		}
		List<Province> result = new ArrayList<>(addrNews.size());
		for (AddrNew addrNew : addrNews) {
			Province province = new Province();
			province.setId(addrNew.getAreaId()==null?"":addrNew.getAreaId());
			province.setName(addrNew.getAreaChName()==null?"":addrNew.getAreaChName());
			province.setParentid(addrNew.getParentID()==null?"":addrNew.getParentID());
			result.add(province);
		}
		return result;
	}

	public List<Region> convertRegion(List<AddrNew> addrNews){
		if(addrNews==null ||addrNews.isEmpty()){
			return null;
		}
		List<Region> result = new ArrayList<>(addrNews.size());
		for (AddrNew addrNew : addrNews) {
			Region province = new Region();
			province.setId(addrNew.getAreaId()==null?"":addrNew.getAreaId());
			province.setName(addrNew.getAreaChName()==null?"":addrNew.getAreaChName());
			province.setParentid(addrNew.getParentID()==null?"":addrNew.getParentID());
			result.add(province);
		}
		return result;
	}


	public ResultObjList<Address> beAddAddress(ConsigneeAddress consigneeAddressVO) {
		String json = addressService.addConsigneeAddress(consigneeAddressVO);
		if (!StringUtils.isEmpty(json)) {
			ResultObjList<Address> result = JsonUtil.fromJson(json, new TypeToken<ResultObjList<Address>>() {
			});
			return result;
		}
		return null;
	}


}
