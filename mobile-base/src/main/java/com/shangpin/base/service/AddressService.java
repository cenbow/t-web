package com.shangpin.base.service;

import com.shangpin.base.vo.ConsigneeAddress;

import java.util.Map;

/**
 * 用户管理相关接口的Service
 * 
 * @author sunweiwei
 * 
 */
public interface AddressService {
	/**
	 * 根据用户id查询收货地址列表
	 * @param userId     用户的唯一标识
	 * @return	String
	 */
	public String findAddressList(String userId);

	/**
	 * 删除收货地址
	 *
	 * @param userId   用户的唯一标识
	 * @param addrId   用户收货地址的唯一标识
	 * @return String
	 */
	public String deleteAddress(String userId, String addrId);

	/**
	 * 获取省级行政区信息
	 * @param pid 父id
	 */
	public String findAddrNewList(String pid);


	/**
	 * 设定默认收货地址（尚品、奥莱完全相同）
	 *
	 * @param userId
	 *            用户的唯一标识
	 *
	 * @parm addrId 用户收货地址的唯一标识
	 *
	 * @return
	 * @author liujie
	 */
	public String modifyDefaultConsigneeAddress(String userId, String addrId);

	/**
	 * 根据地址id获取收货地址信息
	 * 
	 * @param userId 用户的唯一标识
	 * @parm addrid 用户收货地址的唯一标识
	 * @return String
	 */
	public String findAddressByAddressId(String userId, String addrId);


	/**
	 * 新增收货地址（尚品、奥莱完全相同）
	 *
	 * @param consigneeAddressVO
	 *            请求参数
	 * @return
	 * @author cuibinqiang
	 */
	public String addConsigneeAddress(ConsigneeAddress consigneeAddressVO);



	/**
	 * 编辑收货地址
	 *
	 * @param consigneeAddressVO
	 *            请求参数
	 */
	public String modifyConsigneeAddress(ConsigneeAddress consigneeAddressVO);



	/**
	 * 查询收货地址（尚品、奥莱完全相同）
	 *
	 * @param userId
	 * @param isInvoice
	 *            0代表地址列表 1是发票地址列表
	 * @return
	 * @author zghw
	 */
	public String findConsigneeAddress(String userId, String isInvoice);

	/**
	 * 可获取地址V3
	 * @param s
	 * @return
	 * @date 2016-08-13
	 */
	String accessAddress(String s);

}
