package com.shangpin.biz.bo;

import com.shangpin.biz.bo.product.ProductPrice;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * @ClassName: ProductFirstProps
 * @Description:商品详情sku属性实体类（支持sku不同价格体系）
 * @author wangfeng
 * @date 2015年3月11日
 * @version 1.0
 */

@Setter
@Getter
public class ProductSecondProps implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4106633560680239069L;

  
    
    private String sku;//sku编号
    private String secondProp;//第二属性值
    private String isSupportDiscount;//是否支持会员权益 1支持，0不支持
    private String diamondPrice;//钻石会员价
    private String platinumPrice;//白金价
    private String goldPrice;//黄金价
    private String promotionPrice;// 促销价
    private String limitedPrice;//普通会员价
    private String marketPrice;//市场价
    private String isExchange;//是否支持退换换1支持，0不支持
    private String isPromotion;//是否促销 0 不1 促销
    private String count;//库存
    private List<PriceTag> priceTag;//针对海外商品添加的有关价格的信息
    private String shopPrice;
    private List<Property> sizeAbout;//尺码参考
    
    
    private String vipPrice;
	private String taxPromotionPrice; //新的促销状态
	private String taxStandardPrice; //新的尚品价
	private String operatingFlag; //运营标	

    private ProductPrice productPrice;

}
