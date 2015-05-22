package com.glomozda.machinerepair.repository.machine;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;

@Repository
public class MachineRepositoryImpl extends MachineRepository {
	
	@Override
	public List<Machine> getAll() {
		List<Machine> result = em.createNamedQuery(
				"Machine.findAll", Machine.class).getResultList();
		return result;
	}
	
	@Override
	public List<Machine> getAll(Long start, Long length) {
		List<Machine> result = em.createNamedQuery(
				"Machine.findAll", Machine.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Override
	public List<Machine> getAllWithFetching() {
		List<Machine> result = em.createNamedQuery(
				"Machine.findAllWithFetching", Machine.class).getResultList();
		return result;
	}
	
	@Override
	public List<Machine> getAllWithFetching(Long start, Long length) {
		List<Machine> result = em.createNamedQuery(
				"Machine.findAllWithFetching", Machine.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Override
	public Machine getMachineForSerialNumber(String machineSerialNumber) {
		Machine result = null;
		TypedQuery<Machine> query = em.createNamedQuery(
				"Machine.findMachineBySerialNumber", Machine.class);
		query.setParameter("msn", machineSerialNumber);
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Override
	public Machine getMachineForSerialNumberWithFetching(String machineSerialNumber) {
		Machine result = null;
		TypedQuery<Machine> query = em.createNamedQuery(
				"Machine.findMachineBySerialNumberWithFetching", Machine.class);
		query.setParameter("msn", machineSerialNumber);
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Override
	public Machine getMachineByIdWithFetching(Long machineId) {
		Machine result = null;
		TypedQuery<Machine> query = em.createNamedQuery(
				"Machine.findMachineByIdWithFetching", Machine.class);
		query.setParameter("id", machineId);
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Override
	@Transactional
	public Boolean add(Machine m, Long machineServiceableId) 
			throws PersistenceException {
		MachineServiceable machineServiceable =
				em.getReference(MachineServiceable.class, machineServiceableId);

		Machine newMachine = new Machine();
		newMachine.setMachineSerialNumber(m.getMachineSerialNumber());
		newMachine.setMachineYear(m.getMachineYear());
		newMachine.setMachineTimesRepaired(m.getMachineTimesRepaired());
		newMachine.setMachineServiceable(machineServiceable);
		
		if (em.contains(newMachine)) {
			return false;
		}
		
		em.persist(newMachine);
		
		if (em.contains(newMachine)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	@Transactional	
	public Integer incrementTimesRepairedById(Long machineId) {
		Query query = em.createNamedQuery("Machine.incrementTimesRepairedById");
		query.setParameter("id", machineId);		
		int updateCount = query.executeUpdate();
		return updateCount;		
	}
	
	@Override
	public Long getMachineCount() {
		return em.createNamedQuery("Machine.countAll", Long.class).getSingleResult();
	}
	
	@Override
	public Machine getMachineById(Long machineId) {
		return em.find(Machine.class, machineId);
	}
	
	@Override
	@Transactional
	public Integer updateMachineById(Long machineId, Machine machine) {
		Query query = em.createNamedQuery("Machine.updateMachineById");
		query.setParameter("id", machineId);
		query.setParameter("msn", machine.getMachineSerialNumber());
		query.setParameter("year", machine.getMachineYear());
		query.setParameter("times_repaired", machine.getMachineTimesRepaired());
		int updateCount = query.executeUpdate();
		return updateCount;
	}
}
