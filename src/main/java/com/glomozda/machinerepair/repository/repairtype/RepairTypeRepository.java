package com.glomozda.machinerepair.repository.repairtype;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.repairtype.RepairType;

@Repository
public class RepairTypeRepository {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public List<RepairType> getAll() {
		List<RepairType> result = em.createNamedQuery("RepairType.findAll", RepairType.class)
				.getResultList();
		return result;
	}
	
	@Transactional
	public List<RepairType> getAll(Long start, Long length) {
		List<RepairType> result = em.createNamedQuery("RepairType.findAll", RepairType.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Transactional
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
	
	@Transactional
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

}
