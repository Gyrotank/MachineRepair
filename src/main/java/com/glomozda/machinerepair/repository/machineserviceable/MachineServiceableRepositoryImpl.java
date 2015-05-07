package com.glomozda.machinerepair.repository.machineserviceable;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;

@Repository
public class MachineServiceableRepositoryImpl extends MachineServiceableRepository {
	
	@Override
	public List<MachineServiceable> getAll() {
		List<MachineServiceable> result = em.createNamedQuery("MachineServiceable.findAll",
				MachineServiceable.class).getResultList();
		return result;
	}
	
	@Override
	public List<MachineServiceable> getAll(Long start, Long length) {
		List<MachineServiceable> result = em.createNamedQuery("MachineServiceable.findAll",
				MachineServiceable.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Override
	public List<MachineServiceable> getAllOrderByName() {
		List<MachineServiceable> result = em.createNamedQuery
				("MachineServiceable.findAllOrderByName",
				MachineServiceable.class).getResultList();
		return result;
	}
	
	@Override
	public List<MachineServiceable> getAllOrderByTrademark() {
		List<MachineServiceable> result = em.createNamedQuery
				("MachineServiceable.findAllOrderByTrademark",
				MachineServiceable.class).getResultList();
		return result;
	}
	
	@Override
	public MachineServiceable getMachineServiceableById(Long machineServiceableId) {
		return em.find(MachineServiceable.class, machineServiceableId);
	}
	
	@Override
	public Long getMachineServiceableCount() {
		return em.createNamedQuery("MachineServiceable.countAll", Long.class).getSingleResult();
	}
	
	@Override
	@Transactional
	public Boolean add(MachineServiceable ms) {
		em.persist(ms);
		if (em.contains(ms)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
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
	
	@Override
	@Transactional
	public Integer setMachineServiceableAvailableById(
			Long machineServiceableId, Byte available) {
		Query query = em.createNamedQuery("MachineServiceable.setMachineServiceableAvailableById");
		query.setParameter("id", machineServiceableId);
		query.setParameter("available", available);
		int updateCount = query.executeUpdate();
		return updateCount;		
	}
	
	@Override
	public List<MachineServiceable> getAllAvailableOrderByTrademark() {
		List<MachineServiceable> result = em.createNamedQuery
				("MachineServiceable.findAllAvailableOrderByTrademark",
				MachineServiceable.class).getResultList();
		return result;
	}
}
