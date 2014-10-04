package com.glomozda.machinerepair.repository.order;

import java.util.List;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.order.*;
import com.glomozda.machinerepair.domain.repairtype.RepairType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
   
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public List<Order> getAll() {
		List<Order> result = em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<Order> getAllWithFetching() {
		List<Order> result = em.createQuery("SELECT o FROM Order o"
				+ " LEFT JOIN FETCH o.client LEFT JOIN FETCH o.repairType"
				+ " LEFT JOIN FETCH o.machine as om LEFT JOIN FETCH om.machineServiceable"
				, Order.class).getResultList();
		return result;
	}
	
	@Transactional
	public Order getOrderByIdWithFetching(Integer orderId) {
		Order result = null;
		TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o"
				+ " LEFT JOIN FETCH o.client LEFT JOIN FETCH o.repairType"
				+ " LEFT JOIN FETCH o.machine as om LEFT JOIN FETCH om.machineServiceable"
				+ " WHERE o.orderId = :id", Order.class);
		query.setParameter("id", orderId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public List<Order> getOrdersForStatus(String status) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o"				
				+ " WHERE o.status = :status", Order.class);
		query.setParameter("status", status);	  
		try {
			result = query.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public List<Order> getOrdersForStatusWithFetching(String status) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o"
				+ " LEFT JOIN FETCH o.client LEFT JOIN FETCH o.repairType"
				+ " LEFT JOIN FETCH o.machine as om LEFT JOIN FETCH om.machineServiceable"				
				+ " WHERE o.status = :status", Order.class);
		query.setParameter("status", status);	  
		try {
			result = query.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public List<Order> getAllForClientId(Integer clientId) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o"
				+ " WHERE o.client.clientId = :id", Order.class);
		query.setParameter("id", clientId);	  
		try {
			result = query.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public List<Order> getOrdersForClientIdAndStatusWithFetching(Integer clientId, String status) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o"
				+ " LEFT JOIN FETCH o.client LEFT JOIN FETCH o.repairType"
				+ " LEFT JOIN FETCH o.machine as om LEFT JOIN FETCH om.machineServiceable"
				+ " WHERE o.client.clientId = :id AND o.status = :status", Order.class);
		query.setParameter("id", clientId);
		query.setParameter("status", status);
		try {
			result = query.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}

	@Transactional
	public void add(Order o, Integer clientId, Integer repairTypeId, Integer machineId) {

		Client client = em.getReference(Client.class, clientId);
		RepairType repairType = em.getReference(RepairType.class, repairTypeId);
		Machine machine = em.getReference(Machine.class, machineId);

		Order newOrder = new Order();
		newOrder.setClient(client);
		newOrder.setRepairType(repairType);
		newOrder.setMachine(machine);
		newOrder.setStart(o.getStart());
		newOrder.setStatus(o.getStatus());
		
		em.persist(newOrder);
	}
	
	@Transactional
	public Integer confirmOrderById(Integer orderId) {
		Query query = em.createQuery(
				"UPDATE Order o SET status = 'started' " +
				"WHERE o.orderId = :id AND o.status = 'pending'");
		int updateCount = query.setParameter("id", orderId).executeUpdate();
		return updateCount;
	}
	
	@Transactional
	public Integer setOrderStatusById(Integer orderId, String status) {
		Query query = em.createQuery(
				"UPDATE Order o SET status = :status " +
				"WHERE o.orderId = :id");
		query.setParameter("id", orderId);
		query.setParameter("status", status);
		int updateCount = query.executeUpdate();
		return updateCount;		
	}
	
	@Transactional
	public Integer cancelOrderById(Integer orderId) {
		Query query = em.createQuery(
				"DELETE FROM Order o " +
				"WHERE o.orderId = :id AND o.status = 'pending'");
		query.setParameter("id", orderId);		
		int deletedCount = query.executeUpdate();
		return deletedCount;
	}	
}
