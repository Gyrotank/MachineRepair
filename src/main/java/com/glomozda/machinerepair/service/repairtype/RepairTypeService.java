package com.glomozda.machinerepair.service.repairtype;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glomozda.machinerepair.domain.repairtype.RepairType;
import com.glomozda.machinerepair.repository.repairtype.RepairTypeRepository;
import com.glomozda.machinerepair.service.EntityService;

@Service
public abstract class RepairTypeService implements EntityService {

	@Autowired
	protected RepairTypeRepository repairTypeRepository;

	public abstract List<RepairType> getAllAvailable();

	public abstract int setRepairTypeAvailableById(Long repairTypeId, Byte available);

	public abstract Integer updateRepairTypeById(Long repairTypeId, RepairType repairType);

	public abstract RepairType getRepairTypeById(Long repairTypeId);

	public abstract Boolean add(RepairType rt);

	public abstract Long getRepairTypeCount();

	public abstract RepairType getRepairTypeForName(String repairTypeName);

	public abstract List<RepairType> getAll(Long start, Long length);

	public abstract List<RepairType> getAll();

	public RepairTypeService() {
		super();
	}

	public abstract Map<Long, String> getIdsAndNamesOfAvailable();

	public abstract Map<Long, String> getIdsAndNamesRuOfAvailable();

}