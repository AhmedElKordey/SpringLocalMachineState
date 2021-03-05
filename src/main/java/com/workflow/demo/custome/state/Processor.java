package com.workflow.demo.custome.state;

public interface Processor {
	public ProcessData process(ProcessData data) throws ProcessException;
}
