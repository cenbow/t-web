package com.shangpin.biz.service;

import com.shangpin.biz.bo.Address;
import com.shangpin.biz.bo.Province;
import com.shangpin.biz.bo.Region;
import com.shangpin.biz.bo.base.ResultObjList;
import com.shangpin.biz.bo.base.ResultObjMapList;

import java.util.List;

/**
 * 收货地址相关的Service
 * 
 * @author zhanghongwei
 *
 */
public interface SPBizAddressService  {

	/**
	 * 查询收货地址
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @author zhanghongwei
	 */
	public List<Address> findAddressObj(String userId);

	/**
	 * 获取省级行政区信息
	 * 
	 * @return
	 * @author zhanghongwei
	 */
	public List<Province> findProvinceListObj();

	/**
	 * 设定根据地址id获取收货地址信息
	 * 
	 * @param userId
	 *            用户的唯一标识
	 * 
	 * @parm addrid 用户收货地址的唯一标识
	 * 
	 * @return
	 * @author zhanghongwei
	 */
	public Address findAddressByIdObj(String userId, String addrId);
	/**
	 * 设定根据地址id获取收货地址信息
	 * 
	 * @param userId
	 *            用户的唯一标识
	 * 
	 * @parm addrid 用户收货地址的唯一标识
	 * 
	 * @return
	 * @author zhanghongwei
	 */
	public Address findAddressByListObj(String userId, String addrId);

	/**
	 * 根据省份id获得市列表
	 * 
	 * @param provinceId
	 *            省份ID
	 * @return
	 * @author zhanghongwei
	 */
	public List<Region> findCityListObj(String provinceId);




	/**
	 * 删除收货地址并返回是否成功添加成功
	 * 
	 * @param addrId
	 *            地址id
	 * @param userId
	 *            用户id
	 * @return
	 * @author zhanghongwei
	 */
	public boolean isDeleteAddress(String addrId, String userId);

	/**
	 * 提交订单：新增收货地址
	 * 
	 * @param address
	 *            地址封装对象
	 * @param userId
	 *            用户ID
	 * @return
	 *
	 * @author cuibinqiang
	 */
	public List<Address> addAddr(Address address, String userId);
	/**
	 * 提交订单：新增收货地址
	 * 
	 * @param address
	 *            地址封装对象
	 * @param userId
	 *            用户ID
	 * @return
	 *
	 * @author zghw
	 */
	public ResultObjList<Address> addAddrObj(Address address, String userId);

	/**
	 * 编辑收货地址
	 * @param address
	 * @param userId
	 * @return
	 * @author zghw
	 */
	public ResultObjList<Address> updateAddrObj(Address address, String userId);
}
