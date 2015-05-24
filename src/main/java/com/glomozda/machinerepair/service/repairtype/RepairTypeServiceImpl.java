package com.glomozda.machinerepair.service.repairtype;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	
	@Override
	public Map<Long, String> getIdsAndNamesOfAvailable() {
		List<Object[]> idsAndNamesList = repairTypeRepository.getIdsAndNamesOfAvailable();
		
		Map<Long, String> idsAndNamesMap = 
				new LinkedHashMap<Long, String>(idsAndNamesList.size());
		for (Object[] idAndName : idsAndNamesList)
			idsAndNamesMap.put((Long)idAndName[0], (String)idAndName[1]);
		
		return idsAndNamesMap;
	}
	
	@Override
	public Map<Long, String> getIdsAndNamesRuOfAvailable() {
		List<Object[]> idsAndNamesList = repairTypeRepository.getIdsAndNamesRuOfAvailable();
		
		Map<Long, String> idsAndNamesMap = 
				new LinkedHashMap<Long, String>(idsAndNamesList.size());
		for (Object[] idAndName : idsAndNamesList)
			idsAndNamesMap.put((Long)idAndName[0], (String)idAndName[1]);
		
		return idsAndNamesMap;
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
