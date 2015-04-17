package com.glomozda.machinerepair.service.machine;

import java.util.List;

import com.glomozda.machinerepair.domain.machine.*;
import com.glomozda.machinerepair.repository.machine.MachineRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineService {
   
	@Autowired
	private MachineRepository machineRepository;

	@Transactional
	public List<Machine> getAll() {
		return machineRepository.getAll();
	}
	
	@Transactional
	public List<Machine> getAll(Long start, Long length) {
		return machineRepository.getAll(start, length);
	}
	
	@Transactional
	public List<Machine> getAllWithFetching() {
		return machineRepository.getAllWithFetching();
	}
	
	@Transactional
	public List<Machine> getAllWithFetching(Long start, Long length) {
		return machineRepository.getAllWithFetching(start, length);
	}
	
	@Transactional
	public Machine getMachineForSerialNumber(String machineSerialNumber) {
		return machineRepository.getMachineForSerialNumber(machineSerialNumber);
	}
	
	@Transactional
	public Machine getMachineForSerialNumberWithFetching(String machineSerialNumber) {
		return machineRepository.getMachineForSerialNumberWithFetching(machineSerialNumber);
	}

	@Transactional
	public void add(Machine m, Long machineServiceableId) {
		machineRepository.add(m, machineServiceableId);
	}
	
	@Transactional	
	public Integer incrementTimesRepairedById(Long machineId) {
		return machineRepository.incrementTimesRepairedById(machineId);	
	}
	
	@Transactional
	public Long getMachineCount() {
		return machineRepository.getMachineCount();
	}
}
