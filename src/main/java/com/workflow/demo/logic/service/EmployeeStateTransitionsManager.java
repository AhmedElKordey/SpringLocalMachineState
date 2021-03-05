package com.workflow.demo.logic.service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.workflow.demo.custome.state.AbstractStateTransitionsManager;
import com.workflow.demo.custome.state.ProcessData;
import com.workflow.demo.custome.state.ProcessEvent;
import com.workflow.demo.custome.state.ProcessException;
import com.workflow.demo.logic.dto.EmployeeData;
import com.workflow.demo.logic.helper.EmployeeEvent;
import com.workflow.demo.logic.helper.EmployeeState;

import lombok.extern.java.Log;

@Log
@Service
public class EmployeeStateTransitionsManager extends AbstractStateTransitionsManager {
	@Autowired
	private ApplicationContext context;
	@Autowired
	private EmployeeDbService dbService;

	@Override
	protected ProcessData processStateTransition(ProcessData sdata) throws ProcessException {

		EmployeeData data = (EmployeeData) sdata;

		if (data.getEvent() != null) {
			try {
				log.info("Pre-event: " + data.getEvent().toString());
				data = (EmployeeData) this.context.getBean(data.getEvent().nextStepProcessor(data.getEvent()))
						.process(data);
				log.info("Post-event: " + data.getEvent().toString());
				dbService.getStates().put(data.getEmployeeId(),
						(EmployeeState) data.getEvent().nextState(data.getEvent()));
				log.info("Final state: " + dbService.getStates().get(data.getEmployeeId()).name());
				log.info("??*************************************");

			} catch (ProcessException e) {
				log.info("Post-event: " + ((EmployeeEvent) data.getEvent()).name());
				dbService.getStates().put(data.getEmployeeId(),
						(EmployeeState) data.getEvent().nextState(data.getEvent()));
				log.info("Final state: " + dbService.getStates().get(data.getEmployeeId()).name());
				log.info("??*************************************");
				throw new ProcessException(((EmployeeEvent) data.getEvent()).name(), e);
			}
		}
		return data;
	}

	@Override
	protected ProcessData initializeState(ProcessData sdata) throws ProcessException {

		EmployeeData data = (EmployeeData) sdata;

		if (data.getEmployeeId() != null) {
			return checkStateForReturningEmployees(data);
		}

		UUID employeeId = UUID.randomUUID();
		data.setEmployeeId(employeeId);
		dbService.getStates().put(employeeId, EmployeeState.ADDED);

		log.info("Initial state: " + dbService.getStates().get(data.getEmployeeId()).name());
		return data;
	}

	private EmployeeData checkStateForReturningEmployees(EmployeeData data) throws ProcessException {
		if (data.getEmployeeId() != null) {
			if (this.dbService.getStates().get(data.getEmployeeId()) == null) {
				throw new ProcessException("No state exists for Employee Id = " + data.getEmployeeId());
			}
			if (!checkEventWithState((EmployeeEvent) data.getEvent(),
					this.dbService.getStates().get(data.getEmployeeId()))) {
				throw new ProcessException(
						"curent state not match with required action for Employee Id = " + data.getEmployeeId());
			} else if (this.dbService.getStates().get(data.getEmployeeId()) == EmployeeState.ACTIVE) {
				throw new ProcessException("Employee Task Comlpeted for Employee Id = " + data.getEmployeeId());
			} else {
				log.info("Initial state: " + dbService.getStates().get(data.getEmployeeId()).name());
			}
		}
		return data;
	}

	private boolean checkEventWithState(EmployeeEvent employeeEvent, EmployeeState employeeState) {
		return (employeeEvent.equals(EmployeeEvent.SUBMIT) && employeeState.equals(EmployeeState.ADDED))
				|| (employeeEvent.equals(EmployeeEvent.APPROVE) && employeeState.equals(EmployeeState.IN_CHECK))
				|| (employeeEvent.equals(EmployeeEvent.ACTIVATE) && employeeState.equals(EmployeeState.APPROVED));
	}

	public ConcurrentHashMap<UUID, EmployeeState> getStates() {
		return dbService.getStates();
	}

}
