package com.workflow.demo.logic.service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.workflow.demo.logic.helper.EmployeeState;

@Service
public class EmployeeDbService {
	private final ConcurrentHashMap<UUID, EmployeeState> states = new ConcurrentHashMap<>();

	public ConcurrentHashMap<UUID, EmployeeState> getStates() {
		return states;
	}
}
