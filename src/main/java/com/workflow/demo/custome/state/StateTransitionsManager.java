package com.workflow.demo.custome.state;

/**
 * 
 * Events handler
 * @author ahmed_talaat
 *
 */
public interface StateTransitionsManager {
	public ProcessData processEvent(ProcessData data) throws ProcessException;
}
