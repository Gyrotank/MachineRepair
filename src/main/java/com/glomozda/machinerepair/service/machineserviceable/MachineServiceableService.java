package com.glomozda.machinerepair.service.machineserviceable;

import java.util.List;

import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.repository.machineserviceable.MachineServiceableRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineServiceableService {
   
	@Autowired
	private MachineServiceableRepository machineServiceableRepository;

	public List<MachineServiceable> getAll() {
		return machineServiceableRepository.getAll();
	}
	
	public List<MachineServiceable> getAll(Long start, Long length) {
		return machineServiceableRepository.getAll(start, length);
	}
	
	public List<MachineServiceable> getAllOrderByName() {
		return machineServiceableRepository.getAllOrderByName();
	}
	
	public List<MachineServiceable> getAllOrderByTrademark() {
		return machineServiceableRepository.getAllOrderByTrademark();
	}
	
	public MachineServiceable getMachineServiceableById(Long machineServiceableId) {
		return machineServiceableRepository.getMachineServiceableById(machineServiceableId);
	}
	
	public Long getMachineServiceableCount() {
		return machineServiceableRepository.getMachineServiceableCount();
	}
	
	public Boolean add(MachineServiceable ms) {
		return machineServiceableRepository.add(ms);
	}
	
	public Integer updateMachineServiceableById(Long machineServiceableId,
			MachineServiceable machineServiceable) {
		return machineServiceableRepository.updateMachineServiceableById(machineServiceableId,
				machineServiceable);
	}
	
	public Integer setMachineServiceableAvailableById(Long machineServiceableId,
			Byte available) {
		return machineServiceableRepository
				.setMachineServiceableAvailableById(machineServiceableId, available);
	}
	
	public List<MachineServiceable> getAllAvailableOrderByTrademark() {
		return machineServiceableRepository
				.getAllAvailableOrderByTrademark();
	}
}
