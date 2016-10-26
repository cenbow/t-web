package com.shangpin.biz.service.impl;

import com.shangpin.biz.bo.Operation;
import com.shangpin.biz.service.SPBizOperationService;
import com.shangpin.biz.service.abstraction.AbstractBizOperationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SPBizOperationServiceImpl extends AbstractBizOperationService implements SPBizOperationService {

	@Override
	public List<Operation> doOperation(String type, String pageIndex, String pageSize) throws Exception {
		List<Operation> operations = doBaseOperation(type, pageIndex, pageSize);
		if (null == operations) {
			operations = new ArrayList<Operation>();
		}
		return operations;
	}

}
