package com.glomozda.machinerepair.service.machineserviceable;

import java.util.List;

import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.repository.machineserviceable.MachineServiceableRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineServiceableService {
   
	@Autowired
	private MachineServiceableRepository machineServiceableRepository;

	@Transactional
	public List<MachineServiceable> getAll() {
		return machineServiceableRepository.getAll();
	}
	
	@Transactional
	public List<MachineServiceable> getAll(Long start, Long length) {
		return machineServiceableRepository.getAll(start, length);
	}
	
	@Transactional
	public List<MachineServiceable> getAllOrderByName() {
		return machineServiceableRepository.getAllOrderByName();
	}
	
	@Transactional
	public List<MachineServiceable> getAllOrderByTrademark() {
		return machineServiceableRepository.getAllOrderByTrademark();
	}
	
	@Transactional
	public MachineServiceable getMachineServiceableById(Long machineServiceableId) {
		return machineServiceableRepository.getMachineServiceableById(machineServiceableId);
	}
	
	@Transactional
	public Long getMachineServiceableCount() {
		return machineServiceableRepository.getMachineServiceableCount();
	}
	
	@Transactional
	public Boolean add(MachineServiceable ms) {
		return machineServiceableRepository.add(ms);
	}
	
	@Transactional
	public Integer updateMachineServiceableById(Long machineServiceableId,
			MachineServiceable machineServiceable) {
		return machineServiceableRepository.updateMachineServiceableById(machineServiceableId,
				machineServiceable);
	}
}
