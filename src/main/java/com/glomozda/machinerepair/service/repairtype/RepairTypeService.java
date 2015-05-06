package com.glomozda.machinerepair.service.repairtype;

import java.util.List;

import com.glomozda.machinerepair.domain.repairtype.*;
import com.glomozda.machinerepair.repository.repairtype.RepairTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairTypeService {
   
	@Autowired
	private RepairTypeRepository repairTypeRepository;

	public List<RepairType> getAll() {
		return repairTypeRepository.getAll();
	}
	
	public List<RepairType> getAll(Long start, Long length) {
		return repairTypeRepository.getAll(start, length);
	}
	
	public RepairType getRepairTypeForName(String repairTypeName) {
		return repairTypeRepository.getRepairTypeForName(repairTypeName);
	}
	
	public Long getRepairTypeCount() {
		return repairTypeRepository.getRepairTypeCount();
	}

	public Boolean add(RepairType rt) {
		return repairTypeRepository.add(rt);
	}
	
	public RepairType getRepairTypeById(Long repairTypeId) {
		return repairTypeRepository.getRepairTypeById(repairTypeId);
	}
	
	public Integer updateRepairTypeById(Long repairTypeId, RepairType repairType) {
		return repairTypeRepository.updateRepairTypeById(repairTypeId, repairType);
	}
	
	public int setRepairTypeAvailableById(Long repairTypeId, Byte available) {
		return repairTypeRepository.setRepairTypeAvailableById(repairTypeId, available);
	}
	
	public List<RepairType> getAllAvailable() {
		return repairTypeRepository.getAllAvailable();
	}
}
