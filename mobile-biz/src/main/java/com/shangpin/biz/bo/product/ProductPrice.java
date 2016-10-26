package com.shangpin.biz.bo.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by 文宇 on 2016/9/28.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPrice {
    private String color;//颜色
    private String priceDesc;//价格
    private String compareColor;//颜色
    private String compareDesc;//价格描述文字
    private String compareHasLine;//是否含有删除横线 0:无  1:有
}
