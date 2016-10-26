package com.shangpin.biz.bo.orderUnion;

import java.io.Serializable;
import java.util.List;

import lombok.ToString;

/**
 * @auther liushaoqing
 * @date 2016/8/9
 */
@ToString
public class OrderSubmitParamV3 implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6867340460813615226L;

    /**
     * 订单来源来自M站
     */
    public static final int ORDER_FROM_M=19;
 	/**
     * userId : 42EE407359F049449AB8198071B08FC3
     * consigneeId : 100
     * ticketNo : 17073051
     * orderFrom : 1
     * orderType : 1
     * cartDetailIds : ["670722F4E104497691B6BD5C52CEBED4","72D8F9F21B074B47B08FCCF3CDDBE9E3"]
     * payTypeId : 1
     * payTypeChildId : 1
     * isUseGiftcardPay : 1
     * invoiceFlag : 1
     * invoiceInfo : {"invoiceType":0,"invoiceTitle":"发票抬头","invoiceContent":"发票内容","invoiceEmail":"sdfdf@163.com","invoiceTel":"13804543453"}
     * channelInfo : {"chanelNo":"001","chanelId":"lkt","channelType":0,"param":"dsdfdf-2332sd-sdfsde323|dsfsdfdf|dfsfsdfd232"}
     * listSkuDetail : [{"skuNo":"30004217001","warehouseNo":"A","quantity":2},{"skuNo":"30003997001","warehouseNo":"ITD01","quantity":2}]
     */

    private String userId;
    private int consigneeId;
    private String ticketNo;
    private int orderFrom;
    private int orderType;
    private int payTypeId;
    private int payTypeChildId;
    private int isUseGiftcardPay;
    private int invoiceFlag;
    /**
     * invoiceType : 0
     * invoiceTitle : 发票抬头
     * invoiceContent : 发票内容
     * invoiceEmail : sdfdf@163.com
     * invoiceTel : 13804543453
     */

    private InvoiceInfoBean invoiceInfo;
    /**
     * chanelNo : 001
     * chanelId : lkt
     * channelType : 0
     * param : dsdfdf-2332sd-sdfsde323|dsfsdfdf|dfsfsdfd232
     */

    private ChannelInfoBean channelInfo;
    private List<String> cartDetailIds;

    private String versionNo;
    /**
     * skuNo : 30004217001
     * warehouseNo : A
     * quantity : 2
     */

    private List<ListSkuDetailBeanV3> listSkuDetail;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(int consigneeId) {
        this.consigneeId = consigneeId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public int getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(int orderFrom) {
        this.orderFrom = orderFrom;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(int payTypeId) {
        this.payTypeId = payTypeId;
    }

    public int getPayTypeChildId() {
        return payTypeChildId;
    }

    public void setPayTypeChildId(int payTypeChildId) {
        this.payTypeChildId = payTypeChildId;
    }

    public int getIsUseGiftcardPay() {
        return isUseGiftcardPay;
    }

    public void setIsUseGiftcardPay(int isUseGiftcardPay) {
        this.isUseGiftcardPay = isUseGiftcardPay;
    }

    public int getInvoiceFlag() {
        return invoiceFlag;
    }

    public void setInvoiceFlag(int invoiceFlag) {
        this.invoiceFlag = invoiceFlag;
    }

    public InvoiceInfoBean getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(InvoiceInfoBean invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public ChannelInfoBean getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(ChannelInfoBean channelInfo) {
        this.channelInfo = channelInfo;
    }

    public List<String> getCartDetailIds() {
        return cartDetailIds;
    }

    public void setCartDetailIds(List<String> cartDetailIds) {
        this.cartDetailIds = cartDetailIds;
    }

    public List<ListSkuDetailBeanV3> getListSkuDetail() {
        return listSkuDetail;
    }

    public void setListSkuDetail(List<ListSkuDetailBeanV3> listSkuDetail) {
        this.listSkuDetail = listSkuDetail;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 提交订单校验
     */
    public boolean validate() {
        return true;
    }

    @ToString
    public static class InvoiceInfoBean {
        private int invoiceType;
        private String invoiceTitle;
        private String invoiceContent;
        private String invoiceEmail;
        private String invoiceTel;

        public int getInvoiceType() {
            return invoiceType;
        }

        public void setInvoiceType(int invoiceType) {
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
    }

    @ToString
    public static class ChannelInfoBean {

        private String chanelNo;
        private String chanelId;
        private int channelType;
        private String param;

        public String getChanelNo() {
            return chanelNo;
        }

        public void setChanelNo(String chanelNo) {
            this.chanelNo = chanelNo;
        }

        public String getChanelId() {
            return chanelId;
        }

        public void setChanelId(String chanelId) {
            this.chanelId = chanelId;
        }

        public int getChannelType() {
            return channelType;
        }

        public void setChannelType(int channelType) {
            this.channelType = channelType;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }
    }

}
