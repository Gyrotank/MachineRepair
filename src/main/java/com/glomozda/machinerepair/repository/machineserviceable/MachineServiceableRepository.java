package com.glomozda.machinerepair.repository.machineserviceable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;

@Repository
public class MachineServiceableRepository {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public List<MachineServiceable> getAll() {
		List<MachineServiceable> result = em.createNamedQuery("MachineServiceable.findAll",
				MachineServiceable.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<MachineServiceable> getAll(Long start, Long length) {
		List<MachineServiceable> result = em.createNamedQuery("MachineServiceable.findAll",
				MachineServiceable.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Transactional
	public List<MachineServiceable> getAllOrderByName() {
		List<MachineServiceable> result = em.createNamedQuery
				("MachineServiceable.findAllOrderByName",
				MachineServiceable.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<MachineServiceable> getAllOrderByTrademark() {
		List<MachineServiceable> result = em.createNamedQuery
				("MachineServiceable.findAllOrderByTrademark",
				MachineServiceable.class).getResultList();
		return result;
	}
	
	@Transactional
	public MachineServiceable getMachineServiceableById(Long machineServiceableId) {
		MachineServiceable result = null;	  
		TypedQuery<MachineServiceable> query = em.createNamedQuery(
				"MachineServiceable.findMachineServiceableById", MachineServiceable.class);
		query.setParameter("id", machineServiceableId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public Long getMachineServiceableCount() {
		return em.createNamedQuery("MachineServiceable.countAll", Long.class).getSingleResult();
	}
	
	@Transactional
	public void add(MachineServiceable ms) {
		em.persist(ms);
	}

}
