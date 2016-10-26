package com.shangpin.biz.bo.user;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/** 
 * 用户权益信息的数据domain<br/>
 * @date    2016年8月18日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
public class UserLevelInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1611696618529670045L;
	//近12个月消费金额
	String consumedAmount;
	//去下一级别还需消费金额
    String toNextLevelAmount;
    //会员结束时间 yyyy-MM-dd
    String memberEndTime;
    //-1 没找到等级历史； 0 沿用会员等级信息 1 定时更新 2 299购买会员 3 购买礼品卡后升级会员 4 试用会员
    String upgradeWay;
    
    public boolean isTryVip(){
    	return "4".equalsIgnoreCase(upgradeWay);
    }
    
    
}
