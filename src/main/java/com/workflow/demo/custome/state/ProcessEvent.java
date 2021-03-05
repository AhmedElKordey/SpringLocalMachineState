package com.workflow.demo.custome.state;

/**
 * 
 * event enum will implement this marker interface
 * @author ahmed_talaat
 *
 */
public interface ProcessEvent {
	public abstract Class<? extends Processor> nextStepProcessor(ProcessEvent event);   
    public abstract ProcessState nextState(ProcessEvent event);
}
