package com.shangpin.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class UserIds implements Serializable {

	private static final long serialVersionUID = 1L;
	private String mainId; //主id
	private List<String> userIds;//id集合

}
