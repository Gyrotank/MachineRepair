package com.glomozda.machinerepair.service.repairtype;

import java.util.List;

import com.glomozda.machinerepair.domain.repairtype.*;

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
		return repairTypeRepository.add(rt);
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
}
