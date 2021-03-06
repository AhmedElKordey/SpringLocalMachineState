package com.workflow.demo.logic.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.workflow.demo.custome.state.ProcessException;
import com.workflow.demo.logic.dto.EmployeeBasicInfo;
import com.workflow.demo.logic.dto.EmployeeData;
import com.workflow.demo.logic.helper.EmployeeEvent;
import com.workflow.demo.logic.helper.EmployeeState;
import com.workflow.demo.logic.service.EmployeeStateTransitionsManager;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeStateTransitionsManager stateTrasitionsManager;

	@ApiOperation(value = "Retrieves a map of employees Id and there status .")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Response return successfully"),
			@ApiResponse(code = 404, message = "Service Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/allStatus")
	public String allStatus() {
		return castResponseToJson ( stateTrasitionsManager.getStates().isEmpty() ? "No Data Found"
				: stateTrasitionsManager.getStates().toString() );
	}

	@ApiOperation(value = "Change status for specific employee .")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Response return successfully"),
			@ApiResponse(code = 404, message = "Service Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	@PutMapping("changeStatus")
	public String changeStatus(@RequestParam UUID employeeId, @RequestParam EmployeeState status)
			throws ProcessException {
		EmployeeData employeeData = new EmployeeData();
		employeeData.setEmployeeId(employeeId);
		switch (status) {
		case IN_CHECK:
			employeeData.setEvent(EmployeeEvent.SUBMIT);
			break;
		case APPROVED:
			employeeData.setEvent(EmployeeEvent.APPROVE);
			break;
		case ACTIVE:
			employeeData.setEvent(EmployeeEvent.ACTIVATE);
			break;
		default:
			throw new ProcessException("Not valid status");
		}
		employeeData = (EmployeeData) stateTrasitionsManager.processEvent(employeeData);

		return castResponseToJson ( ((EmployeeEvent) employeeData.getEvent()).name() ) ;
	}

	@ApiOperation(value = "Add employee basic data and return auto generated id .")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Response return successfully"),
			@ApiResponse(code = 404, message = "Service Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/add")
	public String add(@RequestBody EmployeeBasicInfo employeeInfo) throws ProcessException {

		EmployeeData employeeData = new EmployeeData();
		employeeData.setEmployeeBasicInfo(employeeInfo);
		employeeData = (EmployeeData) stateTrasitionsManager.processEvent(employeeData);

		return castResponseToJson ( "EmployeeId  " + employeeData.getEmployeeId() ) ;
	}

	@ExceptionHandler(value = ProcessException.class)
	public String handleException(ProcessException e) {
		return e.getMessage();
	}
	
	private String castResponseToJson(String response) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.toJson(response);
	}
}
