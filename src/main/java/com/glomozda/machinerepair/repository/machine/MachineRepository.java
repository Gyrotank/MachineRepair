package com.glomozda.machinerepair.repository.machine;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;

@Repository
public class MachineRepository {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public List<Machine> getAll() {
		List<Machine> result = em.createNamedQuery(
				"Machine.findAll", Machine.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<Machine> getAll(Long start, Long length) {
		List<Machine> result = em.createNamedQuery(
				"Machine.findAll", Machine.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Transactional
	public List<Machine> getAllWithFetching() {
		List<Machine> result = em.createNamedQuery(
				"Machine.findAllWithFetching", Machine.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<Machine> getAllWithFetching(Long start, Long length) {
		List<Machine> result = em.createNamedQuery(
				"Machine.findAllWithFetching", Machine.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Transactional
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
	
	@Transactional
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

	@Transactional
	public Boolean add(Machine m, Long machineServiceableId) {
		MachineServiceable machineServiceable =
				em.getReference(MachineServiceable.class, machineServiceableId);

		Machine newMachine = new Machine();
		newMachine.setMachineSerialNumber(m.getMachineSerialNumber());
		newMachine.setMachineYear(m.getMachineYear());
		newMachine.setMachineTimesRepaired(m.getMachineTimesRepaired());
		newMachine.setMachineServiceable(machineServiceable);
		em.persist(newMachine);
		if (em.contains(newMachine)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional	
	public Integer incrementTimesRepairedById(Long machineId) {
		Query query = em.createNamedQuery("Machine.incrementTimesRepairedById");
		query.setParameter("id", machineId);		
		int updateCount = query.executeUpdate();
		return updateCount;		
	}
	
	@Transactional
	public Long getMachineCount() {
		return em.createNamedQuery("Machine.countAll", Long.class).getSingleResult();
	}

}
