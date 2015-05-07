package com.glomozda.machinerepair.repository.order;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.order.Order;

@Repository
public abstract class OrderRepository {

	@PersistenceContext
	protected EntityManager em;
	
	@Transactional
	public abstract Integer updateOrderById(Long orderId, Order order);
	
	@Transactional
	public abstract Integer cancelOrderById(Long orderId);
	
	@Transactional
	public abstract Integer setOrderStatusById(Long orderId, Long orderStatusId);
	
	@Transactional
	public abstract Integer confirmOrderById(Long orderId, String manager,
			Long orderStatusId);
	
	@Transactional
	public abstract Boolean add(Order o, Long clientId, Long repairTypeId,
			Long machineId, Long orderStatusId);

	public abstract Long getCountCurrentOrderForClientId(Long clientId);

	public abstract Long getCountOrdersForClientIdAndStatus(Long clientId,
			String status);

	public abstract Long getOrderCount();

	public abstract List<Order> getCurrentOrdersForClientIdWithFetching(
			Long clientId, Long start, Long length);

	public abstract List<Order> getCurrentOrdersForClientIdWithFetching(Long clientId);

	public abstract List<Order> getOrdersByClientIdAndMachineSNAndNotFinished(
			Long clientId, String serialNumber);

	public abstract List<Order> getOrdersForClientIdAndStatusWithFetching(
			Long clientId, String status, Long start, Long length);

	public abstract List<Order> getOrdersForClientIdAndStatusWithFetching(
			Long clientId, String status);

	public abstract Long getCountAllForClientId(Long clientId);

	public abstract List<Order> getAllForClientId(Long clientId);

	public abstract List<Order> getOrdersForStatusWithFetching(String status,
			Long start, Long length);

	public abstract List<Order> getOrdersForStatusWithFetching(String status);

	public abstract Long getCountOrdersForStatus(String status);

	public abstract List<Order> getOrdersForStatus(String status);

	public abstract Order getOrderByIdWithFetching(Long orderId);

	public abstract Order getOrderById(Long orderId);

	public abstract List<Order> getAllWithFetching(Long start, Long length);

	public abstract List<Order> getAllWithFetching();

	public abstract List<Order> getAll(Long start, Long length);

	public abstract List<Order> getAll();

}