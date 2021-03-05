package com.workflow.demo.logic.helper;

import com.workflow.demo.custome.state.ProcessState;

/**  
 * ADDED      -  submit    -> submitProcessor()     -> submitCompleted      -> IN_CHECK
 * IN_CHECK   -  approve   -> approveProcessor()    -> denied               -> IN_CHECK
 * IN_CHECK   -  approve   -> approveProcessor()    -> approveCompleted     -> APPROVED
 * APPROVED   -  activate  -> activationProcessor() -> activationCompleted  -> COMPLETED 
 */

public enum EmployeeState implements ProcessState {
	ADDED,
	IN_CHECK,
	APPROVED,
	ACTIVE;
}
