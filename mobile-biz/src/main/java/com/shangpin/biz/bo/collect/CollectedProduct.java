package com.shangpin.biz.bo.collect;

import com.shangpin.biz.bo.Product;
import com.shangpin.biz.bo.ProductSreach;
import com.shangpin.biz.bo.SearchProduct;

import java.io.Serializable;

/** 
 * ClassName:CollectedProduct <br/> 
 * 收藏商品删除时需要id字段<br/>
 * Date:     2016年8月12日 <br/> 
 * @author
 * @since    JDK 7
 */
public class CollectedProduct extends Product implements Serializable {

	private static final long serialVersionUID = 6490304198016171698L;

	private String id;

	private String type;

	private String isShelve;

	public CollectedProduct() {
	}

	public CollectedProduct(String id, String type, String isShelve, Product product) {
		super();
		this.setProductName(product.getProductName());
		this.setBrandCnName(product.getBrandCnName());
		this.setBrandNameCN(product.getBrandNameCN());
		this.setBrandNameEN(product.getBrandNameEN());
		this.setBrandEnName(product.getBrandEnName());
		this.setProductId(product.getProductId());
		this.setTaxPromotionPrice(product.getTaxPromotionPrice());
		this.setTaxSellPrice(product.getTaxSellPrice());
		this.setIsPromotion(product.getIsPromotion());
		this.setTaxStandardPrice(product.getTaxStandardPrice());
		this.setPic(product.getPic());
		this.setProductId(product.getProductId());
		this.setCountryPic("");// 占时去掉国旗标
		this.setPriceColor(product.getPriceColor());
		this.setDescColor(product.getDescColor());
		this.setExpressLogo(product.getExpressLogo());
		this.setPriceTitle(product.getPriceTitle());
		this.setPriceDesc(product.getPriceDesc());
		this.id = id;
		this.type = type;
		this.isShelve = isShelve;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsShelve() {
		return isShelve;
	}

	public void setIsShelve(String isShelve) {
		this.isShelve = isShelve;
	}
}
