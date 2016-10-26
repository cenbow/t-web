package com.shangpin.biz.bo;

import java.io.Serializable;
import java.util.List;

import lombok.ToString;

/**
 * 子订单详情返回
 * @author zrs
 *
 */
@ToString
public class OrderDetailResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String systime;            
	private String mainOrderId;        
	private String canCancel;          
	private String canPay;             
	private String status;             
	private String statusName;         
	private String date;               
	private String expiryDate;         
	private String orderType;          
	private String orderTypeDesc;      
	private String isUseGiftCard;      
	private String giftCardBalance;    
	private String giftCardAmount;
	private String canUseGiftAmount;
	private String payAmount;          
	private String noticeInfo;         
	private PayType selectPayment;     
	private List<Pay> payment;            
	private List<DeliveryVo> delivery;           
	private String isProductCod;              
	private Receive receive;           
	private List<Receive> receiveList;        
	private Invoice invoice;           
	private List<Invoice> invoiceList;        
	private List<Order> orderList;          
	private List<PriceShow> priceShow;
	private PayInfo payInfo;
	private String payCategory;
    private String isCod;
	private String isusablegiftcardpay;
	private String internalAmount;
	private String orderTime;

	/**
	 * 通过消息实体判断是否是购买礼品卡
	 * @return
     */
	public String hasGiftCard(){

		String pic = "";
		if(this.getOrderList().size()>0){
			List<Order> orderList = this.getOrderList();
			if(orderList.size()>0){
				List<OrderDetail> detail = orderList.get(0).getDetail();
				if(detail!=null && detail.size()>0){
					String giftType = detail.get(0).getGiftType();
					//1电子卡2实物卡
					if("1".equals(giftType) || "2".equals(giftType)){
						pic = detail.get(0).getPic();
					}
				}
			}
		}
		return pic;
	}

	@ToString
	private static class PayInfo{
		private String payType;
		private String payTime;

		public String getPayType() {
			return payType;
		}

		public void setPayType(String payType) {
			this.payType = payType;
		}

		public String getPayTime() {
			return payTime;
		}

		public void setPayTime(String payTime) {
			this.payTime = payTime;
		}
	}

	public String getIsusablegiftcardpay() {
		return isusablegiftcardpay;
	}

	public void setIsusablegiftcardpay(String isusablegiftcardpay) {
		this.isusablegiftcardpay = isusablegiftcardpay;
	}

	public String getSystime() {
		return systime;
	}
	public void setSystime(String systime) {
		this.systime = systime;
	}
	public String getMainOrderId() {
		return mainOrderId;
	}
	public void setMainOrderId(String mainOrderId) {
		this.mainOrderId = mainOrderId;
	}
	public String getCanCancel() {
		return canCancel;
	}
	public void setCanCancel(String canCancel) {
		this.canCancel = canCancel;
	}
	public String getCanPay() {
		return canPay;
	}
	public void setCanPay(String canPay) {
		this.canPay = canPay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderTypeDesc() {
		return orderTypeDesc;
	}
	public void setOrderTypeDesc(String orderTypeDesc) {
		this.orderTypeDesc = orderTypeDesc;
	}
	public String getIsUseGiftCard() {
		return isUseGiftCard;
	}
	public void setIsUseGiftCard(String isUseGiftCard) {
		this.isUseGiftCard = isUseGiftCard;
	}
	public String getGiftCardBalance() {
		return giftCardBalance;
	}
	public void setGiftCardBalance(String giftCardBalance) {
		this.giftCardBalance = giftCardBalance;
	}
	public String getGiftCardAmount() {
		return giftCardAmount;
	}
	public void setGiftCardAmount(String giftCardAmount) {
		this.giftCardAmount = giftCardAmount;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getNoticeInfo() {
		return noticeInfo;
	}
	public void setNoticeInfo(String noticeInfo) {
		this.noticeInfo = noticeInfo;
	}
	public PayType getSelectPayment() {
		return selectPayment;
	}
	public void setSelectPayment(PayType selectPayment) {
		this.selectPayment = selectPayment;
	}
	public List<Pay> getPayment() {
		return payment;
	}
	public void setPayment(List<Pay> payment) {
		this.payment = payment;
	}
	public List<DeliveryVo> getDelivery() {
		return delivery;
	}
	public void setDelivery(List<DeliveryVo> delivery) {
		this.delivery = delivery;
	}
	public String getIsProductCod() {
		return isProductCod;
	}
	public void setIsProductCod(String isProductCod) {
		this.isProductCod = isProductCod;
	}
	public Receive getReceive() {
		return receive;
	}
	public void setReceive(Receive receive) {
		this.receive = receive;
	}
	public List<Receive> getReceiveList() {
		return receiveList;
	}
	public void setReceiveList(List<Receive> receiveList) {
		this.receiveList = receiveList;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}
	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}
	public List<Order> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	public List<PriceShow> getPriceShow() {
		return priceShow;
	}
	public void setPriceShow(List<PriceShow> priceShow) {
		this.priceShow = priceShow;
	}

	public String getInternalAmount() {
		return internalAmount;
	}

	public void setInternalAmount(String internalAmount) {
		this.internalAmount = internalAmount;
	}

	public String getCanUseGiftAmount() {
		return canUseGiftAmount;
	}

	public void setCanUseGiftAmount(String canUseGiftAmount) {
		this.canUseGiftAmount = canUseGiftAmount;
	}

	public PayInfo getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(PayInfo payInfo) {
		this.payInfo = payInfo;
	}

	public String getPayCategory() {
		return payCategory;
	}

	public void setPayCategory(String payCategory) {
		this.payCategory = payCategory;
	}

    public String getIsCod() {
        return isCod;
    }

    public void setIsCod(String isCod) {
        this.isCod = isCod;
    }

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
}
