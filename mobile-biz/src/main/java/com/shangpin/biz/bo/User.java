package com.shangpin.biz.bo;

import java.io.Serializable;
import java.util.List;

import com.shangpin.biz.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private static final String HASETRIAL = "1";
    private static final String NEVERTRIAL = "0";

	private String sessionId;
	private String userId;
	private String gender;
	private String name;
	private String email;
	private String level;
	private String lv;
	private String regOrigin;
	private String regTime;
	private String mobile;
	private String icon;
	private String nickName;
	private String trueName;

	private String bindBirthday;//1 是 0 否
	private String birthday;//1104681600000
	private String bindRelation;
	private String isEmailVerified;
	private String parentUserId;
	private String bindGiftPassword;
	private String consumedAmount;
	private String firstDiscountEnd;// 是否首单优惠完毕 0 未享受 1 已享受
	private String isTrial; //是否已经体验过会员试用 0 没有， 1已经试用过
	private String isTrial30; //30天会员是否试用 0: 未试用; 1:试用中; 2:已试用 ；默认0
	private String memberType;//1//外部用户 2//尚品员工
	private String inviteCode;

	private String mainId; //主id
	private List<String> userIds;//id集合
	private int cartCount;

	/**
	 * 用户是否可以修改邮箱
	 * @return
	 */
	public boolean canModifyEmail(){

		if (StringUtil.isNotEmpty(email) &&
				(email.endsWith("shangpinvip.com")
				|| email.endsWith("spcaibei.com"))
				|| email.endsWith("weixin.com") || email.startsWith("qq_")
				|| email.startsWith("alipay_")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是尚品网用户
	 */
	public boolean isShangUser(){
		return "2".equals(memberType);
	}

	public boolean hasTrial30(){
		return !"0".equals(isTrial30);
	}

	/**
	 * 能使用vip的条件
	 * Condition: 1) 会员等级为 普通会员;  2) 已完成首购; 3) 会员等级状态历史中从来没有出现过 金牌会员.
	 * @return
	 */
	public boolean canTryVip(){
		if( !isVip() && hasNotTryVip() && hasFirstBuy()){
			return true;
		}
		return false;
	}

    public boolean hasNotTryVip(){
        return "0".equals(isTrial);
    }

    public boolean hasFirstBuy(){
        return "1".equals(firstDiscountEnd);
    }

	/**
	 * 根据lv判断是否是vip（eip也是vip） 
	 * <br/>
	 * @return
	 */
	public boolean isVip(){
		return UserLevel.VIP.equals(lv)||UserLevel.EIP.equals(lv);
	}
	/**
	 * 判断是否eip 
	 * <br/>
	 * @see #isVip()
	 * @return
	 */
	public boolean isEip(){
		return UserLevel.EIP.equals(lv);		
	}
	
	/**
	 * 用户等级字段
	 * 普通：0001，vip:0005，eip:0006
	 * @date     2016年8月16日 <br/> 
	 * @author 陈小峰
	 * @since JDK7
	 */
	public static class UserLevel{
		/**
		 * 普通
		 */
		public static final String NORMAL="0001";
		/**
		 * vip
		 */
		public static final String VIP="0005";
		/**
		 * eip
		 */
		public static final String EIP="0006";
	}
	/**
	 * 更新等级为vip 
	 * <br/>
	 */
	public void updateLevelVip() {
		setLv(UserLevel.VIP);
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * 判断是否绑定手机
	 * <br/>
	 * @see #isBindPhone()
	 * @return 绑定了为true 否则为false
	 */
	public boolean isBindPhone(){//用户绑定了手机，但不一定是真绑定
		return (bindRelation!=null && bindRelation.startsWith("1"));
	}

	/**
	 * 判断是否真正绑定手机
	 * <br/>
	 * @see #isBindPhoneTrue()
	 * @return 真的绑定为true，否则为false
	 */
	public boolean isBindPhoneTrue(){//用户绑定真正绑定手机
		return (bindRelation!=null && bindRelation.startsWith("1") && bindRelation.length()==10 && "0".equals(bindRelation.substring(9,10)));
	}


}
