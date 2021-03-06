package com.workflow.demo.logic.dto;

import java.util.UUID;

import com.workflow.demo.custome.state.ProcessData;
import com.workflow.demo.custome.state.ProcessEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class EmployeeData implements ProcessData {
	private EmployeeBasicInfo employeeBasicInfo;
	private ProcessEvent event;
	private UUID employeeId;

	@Override
	public ProcessEvent getEvent() {
		return this.event;
	}
}
