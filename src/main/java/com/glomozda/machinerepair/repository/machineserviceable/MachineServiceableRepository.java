package com.glomozda.machinerepair.repository.machineserviceable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;

@Repository
public abstract class MachineServiceableRepository {

	@PersistenceContext
	protected EntityManager em;

	public abstract List<MachineServiceable> getAllAvailableOrderByTrademark();
	
	@Transactional
	public abstract Integer setMachineServiceableAvailableById(Long machineServiceableId,
			Byte available);
	
	@Transactional
	public abstract Integer updateMachineServiceableById(Long machineServiceableId,
			MachineServiceable machineServiceable);
	
	@Transactional
	public abstract Boolean add(MachineServiceable ms) throws PersistenceException;

	public abstract Long getMachineServiceableCount();

	public abstract MachineServiceable getMachineServiceableById(Long machineServiceableId);

	public abstract List<MachineServiceable> getAllOrderByTrademark();

	public abstract List<MachineServiceable> getAllOrderByName();

	public abstract List<MachineServiceable> getAll(Long start, Long length);

	public abstract List<MachineServiceable> getAll();

	public abstract List<Object[]> getAllIdsAndNames();

}