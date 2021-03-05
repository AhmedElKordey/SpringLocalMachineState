package com.workflow.demo.logic.service.processor;

import org.springframework.stereotype.Service;

import com.workflow.demo.custome.state.ProcessData;
import com.workflow.demo.custome.state.ProcessException;
import com.workflow.demo.custome.state.Processor;
import com.workflow.demo.logic.dto.EmployeeData;
import com.workflow.demo.logic.helper.EmployeeEvent;

@Service
public class ApproveProcessor implements Processor {
	@Override
	public ProcessData process(ProcessData data) throws ProcessException {		
		((EmployeeData) data).setEvent(EmployeeEvent.APPROVE_COMPLETED);
		return data;
	}
}
