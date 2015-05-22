package com.glomozda.machinerepair.repository.machine;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.machine.Machine;

@Repository
public abstract class MachineRepository {
	
	@PersistenceContext
	protected EntityManager em;

	public abstract List<Machine> getAll();
	
	public abstract List<Machine> getAll(Long start, Long length);
	
	public abstract List<Machine> getAllWithFetching();
	
	public abstract List<Machine> getAllWithFetching(Long start, Long length);
	
	public abstract Machine getMachineForSerialNumber(String machineSerialNumber);
	
	public abstract Machine getMachineForSerialNumberWithFetching(String machineSerialNumber);
	
	public abstract Machine getMachineByIdWithFetching(Long machineId);

	@Transactional
	public abstract Boolean add(Machine m, Long machineServiceableId) 
			throws PersistenceException;
	
	@Transactional	
	public abstract Integer incrementTimesRepairedById(Long machineId);
	
	public abstract Long getMachineCount();
	
	public abstract Machine getMachineById(Long machineId);
	
	@Transactional
	public abstract Integer updateMachineById(Long machineId, Machine machine);
}
