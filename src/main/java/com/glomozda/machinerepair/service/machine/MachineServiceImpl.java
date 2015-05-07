package com.glomozda.machinerepair.service.machine;

import java.util.List;

import com.glomozda.machinerepair.domain.machine.*;

import org.springframework.stereotype.Service;

@Service
public class MachineServiceImpl extends MachineService {
   
	@Override
	public List<Machine> getAll() {
		return machineRepository.getAll();
	}
	
	@Override
	public List<Machine> getAll(Long start, Long length) {
		return machineRepository.getAll(start, length);
	}
	
	@Override
	public List<Machine> getAllWithFetching() {
		return machineRepository.getAllWithFetching();
	}
	
	@Override
	public List<Machine> getAllWithFetching(Long start, Long length) {
		return machineRepository.getAllWithFetching(start, length);
	}
	
	@Override
	public Machine getMachineForSerialNumber(String machineSerialNumber) {
		return machineRepository.getMachineForSerialNumber(machineSerialNumber);
	}
	
	@Override
	public Machine getMachineForSerialNumberWithFetching(String machineSerialNumber) {
		return machineRepository.getMachineForSerialNumberWithFetching(machineSerialNumber);
	}
	
	@Override
	public Machine getMachineByIdWithFetching(Long machineId) {
		return machineRepository.getMachineByIdWithFetching(machineId);
	}

	@Override
	public Boolean add(Machine m, Long machineServiceableId) {
		return machineRepository.add(m, machineServiceableId);
	}
	
	@Override
	public Integer incrementTimesRepairedById(Long machineId) {
		return machineRepository.incrementTimesRepairedById(machineId);	
	}
	
	@Override
	public Long getMachineCount() {
		return machineRepository.getMachineCount();
	}
	
	@Override
	public Machine getMachineById(Long machineId) {
		return machineRepository.getMachineById(machineId);
	}
	
	@Override
	public Integer updateMachineById(Long machineId, Machine machine) {
		return machineRepository.updateMachineById(machineId, machine);
	}
}
