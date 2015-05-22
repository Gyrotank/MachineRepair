package com.glomozda.machinerepair.service.machineserviceable;

import java.util.List;

import javax.persistence.PersistenceException;

import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

@Service
public class MachineServiceableServiceImpl extends MachineServiceableService {
   
	@Override
	public List<MachineServiceable> getAll() {
		return machineServiceableRepository.getAll();
	}
	
	@Override
	public List<MachineServiceable> getAll(Long start, Long length) {
		return machineServiceableRepository.getAll(start, length);
	}
	
	@Override
	public List<MachineServiceable> getAllOrderByName() {
		return machineServiceableRepository.getAllOrderByName();
	}
	
	@Override
	public List<MachineServiceable> getAllOrderByTrademark() {
		return machineServiceableRepository.getAllOrderByTrademark();
	}
	
	@Override
	public MachineServiceable getMachineServiceableById(Long machineServiceableId) {
		return machineServiceableRepository.getMachineServiceableById(machineServiceableId);
	}
	
	@Override
	public Long getMachineServiceableCount() {
		return machineServiceableRepository.getMachineServiceableCount();
	}
	
	@Override
	public Boolean add(MachineServiceable ms) {
		Boolean result = false;
		
		try {
			result = machineServiceableRepository.add(ms);
		} catch (PersistenceException e) {
			Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		        return false;
		    }
		    throw(e);
		}
		
		return result;
	}
	
	@Override
	public Integer updateMachineServiceableById(Long machineServiceableId,
			MachineServiceable machineServiceable) {
		return machineServiceableRepository.updateMachineServiceableById(machineServiceableId,
				machineServiceable);
	}
	
	@Override
	public Integer setMachineServiceableAvailableById(Long machineServiceableId,
			Byte available) {
		return machineServiceableRepository
				.setMachineServiceableAvailableById(machineServiceableId, available);
	}
	
	@Override
	public List<MachineServiceable> getAllAvailableOrderByTrademark() {
		return machineServiceableRepository
				.getAllAvailableOrderByTrademark();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllEntities() {
		return getAll();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllEntities(Long start, Long length) {
		return getAll(start, length);
	}

	@Override
	public Long getCountEntities() {
		return getMachineServiceableCount();
	}
}
