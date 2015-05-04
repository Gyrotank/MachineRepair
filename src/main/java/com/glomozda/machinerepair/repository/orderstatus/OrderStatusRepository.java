package com.glomozda.machinerepair.repository.orderstatus;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.orderstatus.OrderStatus;

@Repository
public class OrderStatusRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public List<OrderStatus> getAll() {
		List<OrderStatus> result = em.createNamedQuery(
				"OrderStatus.findAll", OrderStatus.class).getResultList();
		return result;
	}
	
	@Transactional
	public OrderStatus getOrderStatusById(Long orderStatusId) {
		return em.find(OrderStatus.class, orderStatusId);
	}
	
	@Transactional
	public OrderStatus getOrderStatusByName(String orderStatusName) {
		OrderStatus result = null;
		TypedQuery<OrderStatus> query = em.createNamedQuery(
				"OrderStatus.findOrderStatusByName", OrderStatus.class);
		query.setParameter("name", orderStatusName);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}

	public Boolean add(OrderStatus os) {
		em.persist(os);
		if (em.contains(os)) {
			return true;
		} else {
			return false;
		}
	}	
}
