package com.shangpin.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserCheck implements Serializable {

	private static final long serialVersionUID = 1L;
	private String isExist;//"是否账户存在： 0不存在 1 存在",
	private String hasPassword;//是否有密码： 0没有 1 有"
	private String canTrial30;//  1:可以试用 2: 试用过不可以 3: 金牌不可以
	private String userId;

	public static String CANTTRIAL30GOLDEN = "3";
	public static String CANTTRIAL30USED = "2";

	public boolean canTrial30(){
		return "1".equals(canTrial30);
	}

	public boolean isExist(){
		return "1".equals(isExist);
	}

}
