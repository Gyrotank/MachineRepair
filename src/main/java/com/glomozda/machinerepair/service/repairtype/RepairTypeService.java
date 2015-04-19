package com.glomozda.machinerepair.service.repairtype;

import java.util.List;

import com.glomozda.machinerepair.domain.repairtype.*;
import com.glomozda.machinerepair.repository.repairtype.RepairTypeRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairTypeService {
   
	@Autowired
	private RepairTypeRepository repairTypeRepository;

	@Transactional
	public List<RepairType> getAll() {
		return repairTypeRepository.getAll();
	}
	
	@Transactional
	public List<RepairType> getAll(Long start, Long length) {
		return repairTypeRepository.getAll(start, length);
	}
	
	@Transactional
	public RepairType getRepairTypeForName(String repairTypeName) {
		return repairTypeRepository.getRepairTypeForName(repairTypeName);
	}
	
	@Transactional
	public Long getRepairTypeCount() {
		return repairTypeRepository.getRepairTypeCount();
	}

	@Transactional
	public Boolean add(RepairType rt) {
		return repairTypeRepository.add(rt);
	}
}
