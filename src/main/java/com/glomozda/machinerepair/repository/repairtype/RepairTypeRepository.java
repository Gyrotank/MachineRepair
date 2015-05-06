package com.glomozda.machinerepair.repository.repairtype;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.repairtype.RepairType;

@Repository
public class RepairTypeRepository {
	
	@PersistenceContext
	private EntityManager em;

	public List<RepairType> getAll() {
		List<RepairType> result = em.createNamedQuery("RepairType.findAll", RepairType.class)
				.getResultList();
		return result;
	}
	
	public List<RepairType> getAll(Long start, Long length) {
		List<RepairType> result = em.createNamedQuery("RepairType.findAll", RepairType.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	public RepairType getRepairTypeForName(String repairTypeName) {
		RepairType result = null;
		TypedQuery<RepairType> query = em.createNamedQuery("RepairType.findRepairTypeByName",
				RepairType.class);
		query.setParameter("rtn", repairTypeName);
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	public Long getRepairTypeCount() {
		return em.createNamedQuery("RepairType.countAll", Long.class).getSingleResult();
	}

	@Transactional
	public Boolean add(RepairType rt) {
		em.persist(rt);
		if (em.contains(rt)) {
			return true;
		} else {
			return false;
		}
	}
	
	public RepairType getRepairTypeById(Long repairTypeId) {
		return em.find(RepairType.class, repairTypeId);
	}
	
	@Transactional
	public Integer updateRepairTypeById(Long repairTypeId, RepairType repairType) {
		Query query = em.createNamedQuery("RepairType.updateRepairTypeById");
		query.setParameter("id", repairTypeId);
		query.setParameter("name", repairType.getRepairTypeName());
		query.setParameter("name_ru", repairType.getRepairTypeNameRu());
		query.setParameter("price", repairType.getRepairTypePrice());
		query.setParameter("duration", repairType.getRepairTypeDuration());
		int updateCount = query.executeUpdate();
		return updateCount;
	}
	
	@Transactional
	public int setRepairTypeAvailableById(Long repairTypeId, Byte available) {
		Query query = em.createNamedQuery("RepairType.setRepairTypeAvailableById");
		query.setParameter("id", repairTypeId);
		query.setParameter("available", available);
		int updateCount = query.executeUpdate();
		return updateCount;
	}
	
	public List<RepairType> getAllAvailable() {
		List<RepairType> result = em.createNamedQuery
				("RepairType.findAllAvailable",
					RepairType.class).getResultList();
		return result;
	}
}
