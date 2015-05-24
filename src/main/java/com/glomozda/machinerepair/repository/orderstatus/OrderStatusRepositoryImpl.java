package com.glomozda.machinerepair.repository.orderstatus;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.orderstatus.OrderStatus;

@Repository
public class OrderStatusRepositoryImpl extends OrderStatusRepository {
	
	@Override
	public List<OrderStatus> getAll() {
		return em.createNamedQuery(
				"OrderStatus.findAll", OrderStatus.class).getResultList();		
	}
	
	@Override
	public OrderStatus getOrderStatusById(Long orderStatusId) {
		return em.find(OrderStatus.class, orderStatusId);
	}
	
	@Override
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
	
	@Override
	public List<Object[]> getIdsAndNames() {
		return em.createNamedQuery(
				"OrderStatus.findIdsAndNames", Object[].class).getResultList();
	}
	
	@Override
	public List<Object[]> getIdsAndNamesRu() {
		return em.createNamedQuery(
				"OrderStatus.findIdsAndNamesRu", Object[].class).getResultList();
	}
	
	@Override
	@Transactional
	public Boolean add(OrderStatus os) {
		em.persist(os);
		if (em.contains(os)) {
			return true;
		} else {
			return false;
		}
	}	
}
