package com.shangpin.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 省市区基类
 * 
 * @author zhanghongwei
 */
@Getter
@Setter
public class Region implements Serializable{
    private static final long serialVersionUID = 1L;
    /** 省市区id */
    private String id;
    /** 省市区对象的上级ID */
    private String parentid;
    /** 省市区名称 */
    private String name;

}
