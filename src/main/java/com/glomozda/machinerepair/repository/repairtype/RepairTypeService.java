package com.glomozda.machinerepair.repository.repairtype;

import java.util.List;

import com.glomozda.machinerepair.domain.repairtype.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RepairTypeService {
   
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public List<RepairType> getAll() {
		List<RepairType> result = em.createNamedQuery("RepairType.findAll", RepairType.class)
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
	public void add(RepairType rt) {
		em.persist(rt);
	}
}
