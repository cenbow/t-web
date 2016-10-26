package com.shangpin.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Deprecated
public class User_old implements Serializable {

	private static final long serialVersionUID = 4305374828766081854L;

	public String userid;
	public String gender;//性别0女 1男
	public String name;
	public String mobile;
	public String email;
	public String regTime;//注册时间
	public String regOrigin;
	public String invitefriendcode;
	public String cartcount;
	public String nopaycount;
	public String level;//等级：汉字
	public String lv;//等级：数字
	public String priceindex;
	public String sessionid;
	
	//2.9.7版本新添加属性开始
	private String bindPhone="";//0未绑定手机号   1绑定了手机号 
	private String bindGiftPassword="";//0:未设置礼品卡密码 1：绑定了礼品卡密码
	private String icon="";//头像url
	private String nickName="";//昵称
    private String birthday="";//生日 
    private List<AttributeArr> userRight=new ArrayList<AttributeArr>();//用户会员权益
    private String bindBirthday="";//是否绑定了生日  0未绑定   1绑定了 
	//2.9.7版本新添加属性结束

}
