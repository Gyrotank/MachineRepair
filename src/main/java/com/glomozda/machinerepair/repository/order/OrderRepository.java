package com.glomozda.machinerepair.repository.order;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.domain.repairtype.RepairType;

@Repository
public class OrderRepository {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public List<Order> getAll() {
		List<Order> result = em.createNamedQuery(
				"Order.findAll", Order.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<Order> getAll(Long start, Long length) {
		List<Order> result = em.createNamedQuery(
				"Order.findAll", Order.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Transactional
	public List<Order> getAllWithFetching() {
		List<Order> result = em.createNamedQuery("Order.findAllWithFetching",
				Order.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<Order> getAllWithFetching(Long start, Long length) {
		List<Order> result = em.createNamedQuery("Order.findAllWithFetching",
				Order.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Transactional
	public Order getOrderById(Long orderId) {
		Order result = null;
		TypedQuery<Order> query = em.createNamedQuery("Order.findOrderById", Order.class);
		query.setParameter("id", orderId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public Order getOrderByIdWithFetching(Long orderId) {
		Order result = null;
		TypedQuery<Order> query = em.createNamedQuery("Order.findOrderByIdWithFetching",
				Order.class);
		query.setParameter("id", orderId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public List<Order> getOrdersForStatus(String status) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createNamedQuery("Order.findOrdersByStatus", Order.class);
		query.setParameter("status", status);	  
		try {
			result = query.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	public Long getCountOrdersForStatus(String status) {
		return em.createNamedQuery("Order.countOrdersByStatus", Long.class)
				.setParameter("status", status)
				.getSingleResult();
	}	
	
	@Transactional
	public List<Order> getOrdersForStatusWithFetching(String status) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createNamedQuery("Order.findOrdersByStatusWithFetching",
				Order.class);
		query.setParameter("status", status);	  
		try {
			result = query.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public List<Order> getOrdersForStatusWithFetching(String status, Long start, Long length) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createNamedQuery("Order.findOrdersByStatusWithFetching",
				Order.class);
		query.setParameter("status", status);	  
		try {
			result = query
					.setFirstResult(start.intValue())
					.setMaxResults(length.intValue())
					.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public List<Order> getAllForClientId(Long clientId) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createNamedQuery("Order.findOrdersByClientId", Order.class);
		query.setParameter("id", clientId);	  
		try {
			result = query.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public Long getCountAllForClientId(Long clientId) {
		return em.createNamedQuery("Order.countOrdersByClientId", Long.class)
				.setParameter("id", clientId)				
				.getSingleResult();
	}
	
	@Transactional
	public List<Order> getOrdersForClientIdAndStatusWithFetching(Long clientId, String status) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createNamedQuery(
				"Order.findOrdersByClientIdAndStatusWithFetching", Order.class);
		query.setParameter("id", clientId);
		query.setParameter("status", status);
		try {
			result = query.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public List<Order> getOrdersForClientIdAndStatusWithFetching(Long clientId, String status,
			Long start, Long length) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createNamedQuery(
				"Order.findOrdersByClientIdAndStatusWithFetching", Order.class);
		query.setParameter("id", clientId);
		query.setParameter("status", status);
		try {
			result = query
					.setFirstResult(start.intValue())
					.setMaxResults(length.intValue())
					.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public List<Order> getOrdersByClientIdAndMachineSNAndNotFinished(Long clientId,
			String serialNumber) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createNamedQuery(
				"Order.findOrderByClientIdAndMachineSNAndNotFinished",
				Order.class);
		query.setParameter("id", clientId);
		query.setParameter("sn", serialNumber);
		try {
			result = query.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public List<Order> getCurrentOrdersForClientIdWithFetching(Long clientId) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createNamedQuery(
				"Order.findCurrentOrdersByClientIdWithFetching", Order.class);
		query.setParameter("id", clientId);
		try {
			result = query					
					.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public List<Order> getCurrentOrdersForClientIdWithFetching(Long clientId,
			Long start, Long length) {
		List<Order> result = null;
		TypedQuery<Order> query = em.createNamedQuery(
				"Order.findCurrentOrdersByClientIdWithFetching", Order.class);
		query.setParameter("id", clientId);
		try {
			result = query
					.setFirstResult(start.intValue())
					.setMaxResults(length.intValue())
					.getResultList();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public Long getOrderCount() {
		return em.createNamedQuery("Order.countAll", Long.class).getSingleResult();
	}
	
	@Transactional
	public Long getCountOrdersForClientIdAndStatus(Long clientId, String status) {
		return em.createNamedQuery("Order.countOrdersByClientIdAndStatus", Long.class)
				.setParameter("id", clientId)
				.setParameter("status", status)
				.getSingleResult();
	}
	
	@Transactional
	public Long getCountCurrentOrderForClientId(Long clientId) {
		return em.createNamedQuery("Order.countCurrentOrdersByClientId", Long.class)
				.setParameter("id", clientId)
				.getSingleResult();
	}

	@Transactional
	public Boolean add(Order o, Long clientId, Long repairTypeId, Long machineId) {

		Client client = em.getReference(Client.class, clientId);
		RepairType repairType = em.getReference(RepairType.class, repairTypeId);
		Machine machine = em.getReference(Machine.class, machineId);

		Order newOrder = new Order();
		newOrder.setClient(client);
		newOrder.setRepairType(repairType);
		newOrder.setMachine(machine);
		newOrder.setStart(o.getStart());		
		newOrder.setStatus(o.getStatus());
		newOrder.setManager(o.getManager());
		
		em.persist(newOrder);
		
		if (em.contains(newOrder)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional
	public Integer confirmOrderById(Long orderId, String manager) {
		Query query = em.createNamedQuery("Order.confirmOrderById");
		int updateCount = query
							.setParameter("id", orderId)
							.setParameter("manager", manager)
							.executeUpdate();
		return updateCount;
	}
	
	@Transactional
	public Integer setOrderStatusById(Long orderId, String status) {
		Query query = em.createNamedQuery("Order.setOrderStatusById");
		query.setParameter("id", orderId);
		query.setParameter("status", status);
		int updateCount = query.executeUpdate();
		return updateCount;		
	}
	
	@Transactional
	public Integer cancelOrderById(Long orderId) {
		Query query = em.createNamedQuery("Order.cancelOrderById");
		query.setParameter("id", orderId);		
		int deletedCount = query.executeUpdate();
		return deletedCount;
	}

}
