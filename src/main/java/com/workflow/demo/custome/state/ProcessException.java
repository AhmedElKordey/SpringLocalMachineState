package com.workflow.demo.custome.state;

public class ProcessException extends Exception {
	
	private static final long serialVersionUID = 2327665853591097774L;

	public ProcessException(String message) {
		super(message);
	}

	public ProcessException(String message, Throwable e) {
		super(message, e);
	}
}
