package com.workflow.demo.logic.helper;

import com.workflow.demo.custome.state.ProcessEvent;
import com.workflow.demo.custome.state.ProcessState;
import com.workflow.demo.custome.state.Processor;
import com.workflow.demo.logic.service.processor.ActivationProcessor;
import com.workflow.demo.logic.service.processor.ApproveProcessor;
import com.workflow.demo.logic.service.processor.SubmitProcessor;

/**  
 * ADDED      -  submit    -> submitProcessor()     -> submitCompleted      -> IN_CHECK
 * IN_CHECK   -  approve   -> approveProcessor()    -> approveCompleted     -> APPROVED
 * APPROVED   -  activate  -> activationProcessor() -> activationCompleted  -> ACTIVE 
 */

public enum EmployeeEvent implements ProcessEvent {
	 SUBMIT {
	        @Override
	        public Class<? extends Processor> nextStepProcessor(ProcessEvent event) {
	                return SubmitProcessor.class;
	        }
	        @Override
	        public ProcessState nextState(ProcessEvent event) {
	                return EmployeeState.ADDED;
	        }

	    },
	 SUBMIT_COMPLETED {
	        @Override
	        public Class<? extends Processor> nextStepProcessor(ProcessEvent event) {
	                return null;
	        }

	        @Override
	        public ProcessState nextState(ProcessEvent event) {
	                return EmployeeState.IN_CHECK;
	        }

	    },
	 APPROVE {
	        @Override
	        public Class<? extends Processor> nextStepProcessor(ProcessEvent event) {
	                return ApproveProcessor.class;
	        }

	        @Override
	        public ProcessState nextState(ProcessEvent event) {
	                return EmployeeState.IN_CHECK;
	        }

	    },
	 APPROVE_COMPLETED {
	    	@Override
	        public Class<? extends Processor> nextStepProcessor(ProcessEvent event) {
	                return null;
	        }

	        @Override
	        public ProcessState nextState(ProcessEvent event) {
	                return EmployeeState.APPROVED;
	        }
	    },
	 ACTIVATE {
	    	@Override
	        public Class<? extends Processor> nextStepProcessor(ProcessEvent event) {
	                return ActivationProcessor.class;
	        }

	        @Override
	        public ProcessState nextState(ProcessEvent event) {
	                return EmployeeState.APPROVED;
	        }
	    },
	 ACTIVATION_COMPLETED {
	    	 @Override
	         public Class<? extends Processor> nextStepProcessor(ProcessEvent event) {
	                return null ;
	         }
	
	         @Override
	         public ProcessState nextState(ProcessEvent event) {
	                return EmployeeState.ACTIVE;
	         }
	   };
}
