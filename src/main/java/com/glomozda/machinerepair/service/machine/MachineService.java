package com.glomozda.machinerepair.service.machine;

import java.util.List;

import com.glomozda.machinerepair.domain.machine.*;
import com.glomozda.machinerepair.repository.machine.MachineRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineService {
   
	@Autowired
	private MachineRepository machineRepository;

	public List<Machine> getAll() {
		return machineRepository.getAll();
	}
	
	public List<Machine> getAll(Long start, Long length) {
		return machineRepository.getAll(start, length);
	}
	
	public List<Machine> getAllWithFetching() {
		return machineRepository.getAllWithFetching();
	}
	
	public List<Machine> getAllWithFetching(Long start, Long length) {
		return machineRepository.getAllWithFetching(start, length);
	}
	
	public Machine getMachineForSerialNumber(String machineSerialNumber) {
		return machineRepository.getMachineForSerialNumber(machineSerialNumber);
	}
	
	public Machine getMachineForSerialNumberWithFetching(String machineSerialNumber) {
		return machineRepository.getMachineForSerialNumberWithFetching(machineSerialNumber);
	}
	
	public Machine getMachineByIdWithFetching(Long machineId) {
		return machineRepository.getMachineByIdWithFetching(machineId);
	}

	public Boolean add(Machine m, Long machineServiceableId) {
		return machineRepository.add(m, machineServiceableId);
	}
	
	public Integer incrementTimesRepairedById(Long machineId) {
		return machineRepository.incrementTimesRepairedById(machineId);	
	}
	
	public Long getMachineCount() {
		return machineRepository.getMachineCount();
	}
	
	public Machine getMachineById(Long machineId) {
		return machineRepository.getMachineById(machineId);
	}
	
	public Integer updateMachineById(Long machineId, Machine machine) {
		return machineRepository.updateMachineById(machineId, machine);
	}
}
