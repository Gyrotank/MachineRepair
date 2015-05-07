package com.glomozda.machinerepair.repository.repairtype;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.repairtype.RepairType;

@Repository
public abstract class RepairTypeRepository {

	@PersistenceContext
	protected EntityManager em;

	public abstract List<RepairType> getAllAvailable();
	
	@Transactional
	public abstract int setRepairTypeAvailableById(Long repairTypeId, Byte available);
	
	@Transactional
	public abstract Integer updateRepairTypeById(Long repairTypeId, RepairType repairType);

	public abstract RepairType getRepairTypeById(Long repairTypeId);
	
	@Transactional
	public abstract Boolean add(RepairType rt);

	public abstract Long getRepairTypeCount();

	public abstract RepairType getRepairTypeForName(String repairTypeName);

	public abstract List<RepairType> getAll(Long start, Long length);

	public abstract List<RepairType> getAll();

}