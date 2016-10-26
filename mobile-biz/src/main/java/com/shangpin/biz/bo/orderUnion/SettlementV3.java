package com.shangpin.biz.bo.orderUnion;

import com.shangpin.biz.bo.Receive;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @auther liushaoqing
 * @date 2016/8/2
 */
@ToString
public class SettlementV3 implements Serializable {


    private static final long serialVersionUID = 8886996507084210744L;

    public static final String COD_NOT = "0";//不支持货到付款

    public static final String COD_IS = "1"; //支持货到付款

    public static final String Pay_Inner = "1";//国内

    public static final String Pay_Outer = "2"; //国外

    public static final String Pay_Union = "3"; //分账

    /**
     * isLoad : 是否加载。1是，0否
     * valid : 是否可选地址。1是，0否
     * prompt : 当valid为0是显示：电子卡订单不需要收获地址
     * currentAddress : {"id":"88050","name":"fffzzz","province":"1","provName":"北京","city":"1","cityName":"北京市","area":"2","areaName":"东城区","town":"0","townName":"","addr":"fdsdaf fasfasfas","zip":"111111","tel":"13666666666","cod":"此地址是否支持货到付款1","cardID":"身份证号"}
     */

    private ReceivedBean received ;
    /**
     * isLoad : 是否加载。1是，0否
     * title : 配送及商品信息
     * desc : 从北京发货
     * freight : 运费合计：￥10
     * detailDesc : EIP会员免运费
     * list : [{"title":"描述发货地发货时间","detail":[{"productNo":"32093","name":"保暖内衣","skuNo":"32983298","price":"1000","brandNameCN":"路易威登","brandNameEN":"LV","pic":"http://pic12.m.shangpin.com/1.jpg","quantity":"2","priceTitle":"到手价","priceDesc":"含税费","countryDesc":"商品标识:欧洲直发,文字版","isDirectDeliver":"是否直发","attribute":[{"name":"颜色","value":"红色"},{"name":"尺码","value":"XL"}],"isShowFreight":"是否显示运费。1是，0否","freight":"￥129","freightDesc":"运费描述"}],"isShowFreight":"是否显示运费。1是，0否","freight":"￥129","freightDesc":"运费描述"}]
     */

    private ProductBean product;
    /**
     * isLoad : 是否加载。1是，0否
     * title : 优惠券/现金券
     * couponNo : 534623803849
     * desc : 以优惠100元
     * count : 可用优惠券数量：3
     */

    private CouponBean coupon;
    /**
     * isLoad : 是否加载。1是，0否
     * valid : 1。全部是海外商品时不能开发票，为0
     * buttonStatus : 是否使用。0关闭，1打开
     * title : 是否开发票
     * desc : 不开发票
     * prompt : 提示: 全球购商品不提供发票
     * lastInvoice : {"invoiceType":"1个人,2公司","invoiceTitle":"公司时写公司名，个人时写个人","invoiceContent":"箱包","contentList":["商品全称","香波","化妆品","鞋帽"],"invoiceEmail":"发票email","invoiceTel":"发票手机号","invoiceDesc":"发票的描述"}
     */

    private InvoiceBean invoice;
    /**
     * isLoad : 是否加载。1是，0否
     * valid : 1
     * buttonStatus : 是否使用。0关闭，1打开
     * title : 礼品卡
     * desc : 礼品卡余额1450元
     * prompt : 提示:海外购不能使用礼品卡支付
     * balance : 余额:1450
     * canUseAmount : 能用金额350
     */

    private GiftCardBean giftCard;
    /**
     * isLoad : 是否加载。1是，0否
     * price : [{"type":"1商品金额(海外商品+关税)2满减（满减优惠）3优惠4礼品卡5运费6运费减免7应付金额）","amount":"2000","title":"商品金额"}]
     * totalSettlement : 结算数量
     * totalAmount : 需支付现金金额12499
     */
    private PriceShowV3 priceShow;
    /**
     * received : {"isLoad":"是否加载。1是，0否","valid":"是否可选地址。1是，0否","prompt":"当valid为0是显示：电子卡订单不需要收获地址","currentAddress":{"id":"88050","name":"fffzzz","province":"1","provName":"北京","city":"1","cityName":"北京市","area":"2","areaName":"东城区","town":"0","townName":"","addr":"fdsdaf fasfasfas","zip":"111111","tel":"13666666666","cod":"此地址是否支持货到付款1","cardID":"身份证号"}}
     * product : {"isLoad":"是否加载。1是，0否","title":"配送及商品信息","desc":"从北京发货","freight":"运费合计：￥10","detailDesc":"EIP会员免运费","list":[{"title":"描述发货地发货时间","detail":[{"productNo":"32093","name":"保暖内衣","skuNo":"32983298","price":"1000","brandNameCN":"路易威登","brandNameEN":"LV","pic":"http://pic12.m.shangpin.com/1.jpg","quantity":"2","priceTitle":"到手价","priceDesc":"含税费","countryDesc":"商品标识:欧洲直发,文字版","isDirectDeliver":"是否直发","attribute":[{"name":"颜色","value":"红色"},{"name":"尺码","value":"XL"}],"isShowFreight":"是否显示运费。1是，0否","freight":"￥129","freightDesc":"运费描述"}],"isShowFreight":"是否显示运费。1是，0否","freight":"￥129","freightDesc":"运费描述"}]}
     * coupon : {"isLoad":"是否加载。1是，0否","title":"优惠券/现金券","couponNo":"534623803849","desc":"以优惠100元","count":"可用优惠券数量：3"}
     * invoice : {"isLoad":"是否加载。1是，0否","valid":"1。全部是海外商品时不能开发票，为0","buttonStatus":"是否使用。0关闭，1打开","title":"是否开发票","desc":"不开发票","prompt":"提示: 全球购商品不提供发票","lastInvoice":{"invoiceType":"1个人,2公司","invoiceTitle":"公司时写公司名，个人时写个人","invoiceContent":"箱包","contentList":["商品全称","香波","化妆品","鞋帽"],"invoiceEmail":"发票email","invoiceTel":"发票手机号","invoiceDesc":"发票的描述"}}
     * giftCard : {"isLoad":"是否加载。1是，0否","valid":"1","buttonStatus":"是否使用。0关闭，1打开","title":"礼品卡","desc":"礼品卡余额1450元","prompt":"提示:海外购不能使用礼品卡支付","balance":"余额:1450","canUseAmount":"能用金额350"}
     * priceShow : {"isLoad":"是否加载。1是，0否","price":[{"type":"1商品金额(海外商品+关税)2满减（满减优惠）3优惠4礼品卡5运费6运费减免7应付金额）","amount":"2000","title":"商品金额"}],"totalSettlement":"结算数量","totalAmount":"需支付现金金额12499"}
     * isProductCod : 商品是否支持货到付款。0:否，1是。
     * isNeedCardId; 是否包含国外商品
     * payCategory : 1:国内，2：国外，3：支付宝分帐
     * buyIds : ["220234230013","22023423001","3220234230013"]
     * listSkuDetail : [{"skuNo":"2342342342","wareHourseNo":"2342342342","quantity":5}]
     */

    private String isNeedCardId;
    private String isProductCod;
    private String payCategory;
    private List<String> buyIds;
    private String internalFreight;
	private String prompt; 	//埋点
    private String productIds;    /**
     * skuNo : 2342342342
     * wareHourseNo : 2342342342
     * quantity : 5
     */

    private List<ListSkuDetailBeanV3> listSkuDetail;

    public ReceivedBean getReceived() {
        return received;
    }


    public void setReceived(ReceivedBean received) {
        this.received = received;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public CouponBean getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponBean coupon) {
        this.coupon = coupon;
    }

    public InvoiceBean getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceBean invoice) {
        this.invoice = invoice;
    }

    public GiftCardBean getGiftCard() {
        return giftCard;
    }

    public void setGiftCard(GiftCardBean giftCard) {
        this.giftCard = giftCard;
    }

    public PriceShowV3 getPriceShow() {
        return priceShow;
    }

    public void setPriceShow(PriceShowV3 priceShow) {
        this.priceShow = priceShow;
    }

    public String getIsProductCod() {
        return isProductCod;
    }

    public void setIsProductCod(String isProductCod) {
        this.isProductCod = isProductCod;
    }

    public String getIsNeedCardId() {
        return isNeedCardId;
    }

    public void setIsNeedCardId(String isNeedCardId) {
        this.isNeedCardId = isNeedCardId;
    }

    public String getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(String payCategory) {
        this.payCategory = payCategory;
    }

    public List<String> getBuyIds() {
        return buyIds;
    }

    public void setBuyIds(List<String> buyIds) {
        this.buyIds = buyIds;
    }

    public List<ListSkuDetailBeanV3> getListSkuDetail() {
        return listSkuDetail;
    }

    public void setListSkuDetail(List<ListSkuDetailBeanV3> listSkuDetail) {
        this.listSkuDetail = listSkuDetail;
    }

    public String getInternalFreight() {
        return internalFreight;
    }

    public void setInternalFreight(String internalFreight) {
        this.internalFreight = internalFreight;
    }

	public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    public String getProductIds() {
        StringBuilder spus = new StringBuilder();
        if(product!= null && product.getList()!=null){
            for (ProductBean.ListBean listBean : product.getList()) {
                for (ProductBean.ListBean.DetailBean detailBean : listBean.getDetail()) {
                    spus.append(detailBean.getProductNo()+",");
                }
            }
            if(spus.length()>0){
                return spus.substring(0,spus.length()-1);
            }
        }
        return spus.toString();
    }
    @ToString
    public static class ReceivedBean implements Serializable{

        private static final long serialVersionUID = 8123996507084210744L;
        private String isLoad;
        private String valid;
        private String prompt;
        /**
         * id : 88050
         * name : fffzzz
         * province : 1
         * provName : 北京
         * city : 1
         * cityName : 北京市
         * area : 2
         * areaName : 东城区
         * town : 0
         * townName :
         * addr : fdsdaf fasfasfas
         * zip : 111111
         * tel : 13666666666
         * cod : 此地址是否支持货到付款1
         * cardID : 身份证号
         */

        private Receive currentAddress;

        public String getIsLoad() {
            return isLoad;
        }

        public void setIsLoad(String isLoad) {
            this.isLoad = isLoad;
        }

        public String getValid() {
            return valid;
        }

        public void setValid(String valid) {
            this.valid = valid;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public Receive getCurrentAddress() {
            return currentAddress;
        }

        public void setCurrentAddress(Receive currentAddress) {
            this.currentAddress = currentAddress;
        }

    }

    @ToString
    public static class ProductBean implements Serializable {

        private static final long serialVersionUID = 147917349127491274L;
        private String isLoad;
        private String title;
        private String desc;
        private String freight;
        private String detailDesc;
        /**
         * title : 描述发货地发货时间
         * detail : [{"productNo":"32093","name":"保暖内衣","skuNo":"32983298","price":"1000","brandNameCN":"路易威登","brandNameEN":"LV","pic":"http://pic12.m.shangpin.com/1.jpg","quantity":"2","priceTitle":"到手价","priceDesc":"含税费","countryDesc":"商品标识:欧洲直发,文字版","isDirectDeliver":"是否直发","attribute":[{"name":"颜色","value":"红色"},{"name":"尺码","value":"XL"}],"isShowFreight":"是否显示运费。1是，0否","freight":"￥129","freightDesc":"运费描述"}]
         * isShowFreight : 是否显示运费。1是，0否
         * freight : ￥129
         * freightDesc : 运费描述
         */

        private List<ListBean> list;

        public String getIsLoad() {
            return isLoad;
        }

        public void setIsLoad(String isLoad) {
            this.isLoad = isLoad;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getDetailDesc() {
            return detailDesc;
        }

        public void setDetailDesc(String detailDesc) {
            this.detailDesc = detailDesc;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        @ToString
        public static class ListBean implements Serializable{

            private static final long serialVersionUID = 3L;
            private String title;
            private String isShowFreight;
            private String freight;
            private String freightDesc;
            /**
             * productNo : 32093
             * name : 保暖内衣
             * skuNo : 32983298
             * price : 1000
             * brandNameCN : 路易威登
             * brandNameEN : LV
             * pic : http://pic12.m.shangpin.com/1.jpg
             * quantity : 2
             * priceTitle : 到手价
             * priceDesc : 含税费
             * countryDesc : 商品标识:欧洲直发,文字版
             * isDirectDeliver : 是否直发
             * attribute : [{"name":"颜色","value":"红色"},{"name":"尺码","value":"XL"}]
             * isShowFreight : 是否显示运费。1是，0否
             * freight : ￥129
             * freightDesc : 运费描述
             */

            private List<DetailBean> detail;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIsShowFreight() {
                return isShowFreight;
            }

            public void setIsShowFreight(String isShowFreight) {
                this.isShowFreight = isShowFreight;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getFreightDesc() {
                return freightDesc;
            }

            public void setFreightDesc(String freightDesc) {
                this.freightDesc = freightDesc;
            }

            public List<DetailBean> getDetail() {
                return detail;
            }

            public void setDetail(List<DetailBean> detail) {
                this.detail = detail;
            }

            @ToString
            public static class DetailBean implements Serializable{

                private static final long serialVersionUID = 4L;
                private String productNo;
                private String name;
                private String skuNo;
                private String price;
                private String brandNameCN;
                private String brandNameEN;
                private String pic;
                private String quantity;
                private String priceTitle;
                private String priceDesc;
                private String countryDesc;
                private String isDirectDeliver;
                private String isShowFreight;
                private String freight;
                private String freightDesc;
                /**
                 * name : 颜色
                 * value : 红色
                 */

                private List<AttributeBean> attribute;

                public String getProductNo() {
                    return productNo;
                }

                public void setProductNo(String productNo) {
                    this.productNo = productNo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getSkuNo() {
                    return skuNo;
                }

                public void setSkuNo(String skuNo) {
                    this.skuNo = skuNo;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getBrandNameCN() {
                    return brandNameCN;
                }

                public void setBrandNameCN(String brandNameCN) {
                    this.brandNameCN = brandNameCN;
                }

                public String getBrandNameEN() {
                    return brandNameEN;
                }

                public void setBrandNameEN(String brandNameEN) {
                    this.brandNameEN = brandNameEN;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getQuantity() {
                    return quantity;
                }

                public void setQuantity(String quantity) {
                    this.quantity = quantity;
                }

                public String getPriceTitle() {
                    return priceTitle;
                }

                public void setPriceTitle(String priceTitle) {
                    this.priceTitle = priceTitle;
                }

                public String getPriceDesc() {
                    return priceDesc;
                }

                public void setPriceDesc(String priceDesc) {
                    this.priceDesc = priceDesc;
                }

                public String getCountryDesc() {
                    return countryDesc;
                }

                public void setCountryDesc(String countryDesc) {
                    this.countryDesc = countryDesc;
                }

                public String getIsDirectDeliver() {
                    return isDirectDeliver;
                }

                public void setIsDirectDeliver(String isDirectDeliver) {
                    this.isDirectDeliver = isDirectDeliver;
                }

                public String getIsShowFreight() {
                    return isShowFreight;
                }

                public void setIsShowFreight(String isShowFreight) {
                    this.isShowFreight = isShowFreight;
                }

                public String getFreight() {
                    return freight;
                }

                public void setFreight(String freight) {
                    this.freight = freight;
                }

                public String getFreightDesc() {
                    return freightDesc;
                }

                public void setFreightDesc(String freightDesc) {
                    this.freightDesc = freightDesc;
                }

                public List<AttributeBean> getAttribute() {
                    return attribute;
                }

                public void setAttribute(List<AttributeBean> attribute) {
                    this.attribute = attribute;
                }

                @ToString
                public static class AttributeBean implements Serializable {

                    private static final long serialVersionUID = 888699650708L;

                    private String name;
                    private String value;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
                }
            }
        }
    }

    @ToString
    public static class CouponBean implements Serializable{

        private static final long serialVersionUID = 5555556507084210744L;

        private String isLoad;
        private String title;
        private String couponNo;
        private String type;
        private String desc;
        private String count;
        private String canUseAmount;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIsLoad() {
            return isLoad;
        }

        public void setIsLoad(String isLoad) {
            this.isLoad = isLoad;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCouponNo() {
            return couponNo;
        }

        public void setCouponNo(String couponNo) {
            this.couponNo = couponNo;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCanUseAmount() {
            return canUseAmount;
        }

        public void setCanUseAmount(String canUseAmount) {
            this.canUseAmount = canUseAmount;
        }

    }
    @ToString
    public static class InvoiceBean implements Serializable{

        private static final long serialVersionUID = 888693424554542435L;
        private String isLoad;
        private String valid;
        private String buttonStatus;
        private String title;
        private String desc;
        private String prompt;
        /**
         * invoiceType : 1个人,2公司
         * invoiceTitle : 公司时写公司名，个人时写个人
         * invoiceContent : 箱包
         * contentList : ["商品全称","香波","化妆品","鞋帽"]
         * invoiceEmail : 发票email
         * invoiceTel : 发票手机号
         * invoiceDesc : 发票的描述
         */

        private LastInvoiceBean lastInvoice;

        public String getIsLoad() {
            return isLoad;
        }

        public void setIsLoad(String isLoad) {
            this.isLoad = isLoad;
        }

        public String getValid() {
            return valid;
        }

        public void setValid(String valid) {
            this.valid = valid;
        }

        public String getButtonStatus() {
            return buttonStatus;
        }

        public void setButtonStatus(String buttonStatus) {
            this.buttonStatus = buttonStatus;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public LastInvoiceBean getLastInvoice() {
            return lastInvoice;
        }

        public void setLastInvoice(LastInvoiceBean lastInvoice) {
            this.lastInvoice = lastInvoice;
        }

        @ToString
        public static class LastInvoiceBean implements Serializable {

            private static final long serialVersionUID = 888699755489854L;

            private String invoiceType;
            private String invoiceTitle;
            private String invoiceContent;
            private String invoiceEmail;
            private String invoiceTel;
            private String invoiceDesc;
            private List<String> contentList;

            public String getInvoiceType() {
                return invoiceType;
            }

            public void setInvoiceType(String invoiceType) {
                this.invoiceType = invoiceType;
            }

            public String getInvoiceTitle() {
                return invoiceTitle;
            }

            public void setInvoiceTitle(String invoiceTitle) {
                this.invoiceTitle = invoiceTitle;
            }

            public String getInvoiceContent() {
                return invoiceContent;
            }

            public void setInvoiceContent(String invoiceContent) {
                this.invoiceContent = invoiceContent;
            }

            public String getInvoiceEmail() {
                return invoiceEmail;
            }

            public void setInvoiceEmail(String invoiceEmail) {
                this.invoiceEmail = invoiceEmail;
            }

            public String getInvoiceTel() {
                return invoiceTel;
            }

            public void setInvoiceTel(String invoiceTel) {
                this.invoiceTel = invoiceTel;
            }

            public String getInvoiceDesc() {
                return invoiceDesc;
            }

            public void setInvoiceDesc(String invoiceDesc) {
                this.invoiceDesc = invoiceDesc;
            }

            public List<String> getContentList() {
                return contentList;
            }

            public void setContentList(List<String> contentList) {
                this.contentList = contentList;
            }
        }
    }
    @ToString
    public static class GiftCardBean implements Serializable {

        private static final long serialVersionUID = 437923759237492375L;

        private String isLoad;
        private String valid;
        private String buttonStatus;
        private String title;
        private String desc;
        private String prompt;
        private String balance;
        private String canUseAmount;
        private String isShow;

        public String getIsLoad() {
            return isLoad;
        }

        public void setIsLoad(String isLoad) {
            this.isLoad = isLoad;
        }

        public String getValid() {
            return valid;
        }

        public void setValid(String valid) {
            this.valid = valid;
        }

        public String getButtonStatus() {
            return buttonStatus;
        }

        public void setButtonStatus(String buttonStatus) {
            this.buttonStatus = buttonStatus;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getCanUseAmount() {
            return canUseAmount;
        }

        public void setCanUseAmount(String canUseAmount) {
            this.canUseAmount = canUseAmount;
        }

        public String getIsShow() {
            return isShow;
        }

        public void setIsShow(String isShow) {
            this.isShow = isShow;
        }
    }



}
