package com.shangpin.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: ProductBasic
 * @Description:商品详情基本实体类（支持sku不同价格体系）
 * @author wangfeng
 * @date 2015年3月11日
 * @version 1.0
 */
@Setter
@Getter
public class ProductBasic implements Serializable {

   
    /**
     * 
     */
    private static final long serialVersionUID = 812946510435627486L;
    
    
    private String productId;//商品编号
    private String productName;//商品名称
    private Brand brand;//品牌对象
    private String activityId;//活动编号
    private Collect collect;
    private String isPackage;//是否包邮 0不支持1支持
    private List<String> info;//详情基本信息
    private String recommend;//编辑推荐
    private String isAfterSale;//是否有售后保养 0没有 1有
    private String isSize;//是否有尺码 0没有 1有
    private String isBrandStyle;//是否有品牌风尚 0没有 1有
    private String commentCount;//评论数
    private String firstPropName;//第一属性名称
    private String secondPropName;//第二属性名称
    private String isSoldOut;//是否售罄 0 没有 1售罄
    private String[] allPics;// 全部要展示的图片
    private List<ProductFirstProps> firstProps;//sku第一属性对象
    private String prefix;//商品前缀
    private String suffix;//商品后缀
    private String advertWord;//广告语
    private String sales;//销量
    private String collections;//收藏数
    private List<AttributeInfo> attributeInfo;//属性对象
    private DefaultIndex defaultIndex;//默认选择sku索引
    
    
    /***  isShortSize 520活动临时所加参数，主站不传，自己封装 
     */
   // private String isShortSize;//是否断码 0:没有断码 ，1:断码

    
    
}