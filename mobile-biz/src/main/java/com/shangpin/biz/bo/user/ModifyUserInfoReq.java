package com.shangpin.biz.bo.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 修改用户信息接口入参实体
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ModifyUserInfoReq implements Serializable{

    private static final long serialVersionUID = 1L;
    private String userId;//	用户ID	String	必须
    private String nickName;//	昵称	String	非必须
    private String gender;//	性别	String	非必须	0：女，1：男，2：保密
    private String birthday;	//生日	String	非必须	，生日格式为时间戳
    private String mobile;//	手机	String	非必须
    private String email;//	邮箱	String	非必须
    private String version;//	版本号	String	必须	例如 2.9.17
    private String plat;	//用户来源	int	非必须 0 未知 1 pc 2 M站 3 客户端

}
