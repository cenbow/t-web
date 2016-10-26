package com.shangpin.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 收货地址
 * @author zhanghongwei
 *
 */
@Getter
@Setter
public class Address implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -9019305467048914602L;
    /** id */
    private String id;
    /** 名称 */
    private String name;
    /** 省份ID */
    private String province;
    /** 省份名称 */
    private String provname;
    /** 市ID */
    private String city;// 城市id
    /** 市名 */
    private String cityname;
    /** 县(区)id */
    private String area;// 县（区）id
    /** 县(区)名称 */
    private String areaname;
    /** 城镇id */
    private String town;
    /** 城镇名称 */
    private String townname;
    /**详细地址*/
    private String addr;
    /** 邮编 */
    private String postcode;
    /** 是否是默认地址 1是默认 */
    private String isd;
    /** 电话 */
    private String tel;
    /**身份证号码*/
    private String cardID;
    /** */
    private String cod;
    

}
