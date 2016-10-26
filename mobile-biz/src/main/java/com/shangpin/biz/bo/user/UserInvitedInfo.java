package com.shangpin.biz.bo.user;

import lombok.Getter;
import lombok.Setter;

/**
 *  2016/9/26.
 *  获取用户邀请码信息接口返回实体
 */
@Getter
@Setter
public class UserInvitedInfo {

    String serialNo;// "LA9PQJUT",
    String userId;// "0056622aa7fcf1b9955c4c95fbaeddaa",
    String dateCreate;// 1471860086197,
    String serialNoGuid;// "66580741d6a8ca6e178f8c31e201ea25",
    String inviteCount;// 1000,限制邀请的数量
    String userType;// 1,
    String invitedCount;// 0 已经邀请的数量
}
