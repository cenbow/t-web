package com.shangpin.biz.bo.base;

import java.io.Serializable;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.shangpin.biz.bo.Product;
import com.shangpin.biz.bo.RunProduct;
import com.shangpin.utils.JsonUtil;

public class ResultBaseWy<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private T result;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
