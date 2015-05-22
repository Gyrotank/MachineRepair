package com.glomozda.machinerepair.service.repairtype;

import java.util.List;

import javax.persistence.PersistenceException;

import com.glomozda.machinerepair.domain.repairtype.*;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

@Service
public class RepairTypeServiceImpl extends RepairTypeService {
   
	@Override
	public List<RepairType> getAll() {
		return repairTypeRepository.getAll();
	}
	
	@Override
	public List<RepairType> getAll(Long start, Long length) {
		return repairTypeRepository.getAll(start, length);
	}
	
	@Override
	public RepairType getRepairTypeForName(String repairTypeName) {
		return repairTypeRepository.getRepairTypeForName(repairTypeName);
	}
	
	@Override
	public Long getRepairTypeCount() {
		return repairTypeRepository.getRepairTypeCount();
	}

	@Override
	public Boolean add(RepairType rt) {
		
		Boolean result = false;

		try {
			result = repairTypeRepository.add(rt);
		} catch (PersistenceException e) {
			Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		        return false;
		    }
		    throw (e);
		}
		
		return result;		
	}
	
	@Override
	public RepairType getRepairTypeById(Long repairTypeId) {
		return repairTypeRepository.getRepairTypeById(repairTypeId);
	}
	
	@Override
	public Integer updateRepairTypeById(Long repairTypeId, RepairType repairType) {
		return repairTypeRepository.updateRepairTypeById(repairTypeId, repairType);
	}
	
	@Override
	public int setRepairTypeAvailableById(Long repairTypeId, Byte available) {
		return repairTypeRepository.setRepairTypeAvailableById(repairTypeId, available);
	}
	
	@Override
	public List<RepairType> getAllAvailable() {
		return repairTypeRepository.getAllAvailable();
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
		return getRepairTypeCount();
	}
}
