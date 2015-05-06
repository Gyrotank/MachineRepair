package com.glomozda.machinerepair.repository.machineserviceable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;

@Repository
public class MachineServiceableRepository {
	
	@PersistenceContext
	private EntityManager em;

	public List<MachineServiceable> getAll() {
		List<MachineServiceable> result = em.createNamedQuery("MachineServiceable.findAll",
				MachineServiceable.class).getResultList();
		return result;
	}
	
	public List<MachineServiceable> getAll(Long start, Long length) {
		List<MachineServiceable> result = em.createNamedQuery("MachineServiceable.findAll",
				MachineServiceable.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	public List<MachineServiceable> getAllOrderByName() {
		List<MachineServiceable> result = em.createNamedQuery
				("MachineServiceable.findAllOrderByName",
				MachineServiceable.class).getResultList();
		return result;
	}
	
	public List<MachineServiceable> getAllOrderByTrademark() {
		List<MachineServiceable> result = em.createNamedQuery
				("MachineServiceable.findAllOrderByTrademark",
				MachineServiceable.class).getResultList();
		return result;
	}
	
	public MachineServiceable getMachineServiceableById(Long machineServiceableId) {
		return em.find(MachineServiceable.class, machineServiceableId);
	}
	
	public Long getMachineServiceableCount() {
		return em.createNamedQuery("MachineServiceable.countAll", Long.class).getSingleResult();
	}
	
	@Transactional
	public Boolean add(MachineServiceable ms) {
		em.persist(ms);
		if (em.contains(ms)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional
	public Integer updateMachineServiceableById(Long machineServiceableId,
			MachineServiceable machineServiceable) {
		Query query = em.createNamedQuery("MachineServiceable.updateMachineServiceableById");
		query.setParameter("id", machineServiceableId);
		query.setParameter("name", machineServiceable.getMachineServiceableName());
		query.setParameter("trademark", machineServiceable.getMachineServiceableTrademark());
		query.setParameter("country", machineServiceable.getMachineServiceableCountry());
		query.setParameter("country_ru", machineServiceable.getMachineServiceableCountryRu());
		int updateCount = query.executeUpdate();
		return updateCount;
	}
	
	@Transactional
	public Integer setMachineServiceableAvailableById(
			Long machineServiceableId, Byte available) {
		Query query = em.createNamedQuery("MachineServiceable.setMachineServiceableAvailableById");
		query.setParameter("id", machineServiceableId);
		query.setParameter("available", available);
		int updateCount = query.executeUpdate();
		return updateCount;		
	}
	
	public List<MachineServiceable> getAllAvailableOrderByTrademark() {
		List<MachineServiceable> result = em.createNamedQuery
				("MachineServiceable.findAllAvailableOrderByTrademark",
				MachineServiceable.class).getResultList();
		return result;
	}
}
