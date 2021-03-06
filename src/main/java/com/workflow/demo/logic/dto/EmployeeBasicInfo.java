package com.workflow.demo.logic.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeBasicInfo implements Serializable{

	private static final long serialVersionUID = -8755886160474237319L;
	
	private String name;
	private String age;
	private String grade;
	private String currentPosition;
	private String currentSallary;

}
