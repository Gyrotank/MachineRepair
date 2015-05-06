package com.glomozda.machinerepair.service.order;

import java.util.List;

import com.glomozda.machinerepair.domain.order.*;
import com.glomozda.machinerepair.repository.order.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	public List<Order> getAll() {
		return orderRepository.getAll();
	}
	
	public List<Order> getAll(Long start, Long length) {
		return orderRepository.getAll(start, length);
	}
	
	public List<Order> getAllWithFetching() {
		return orderRepository.getAllWithFetching();
	}
	
	public List<Order> getAllWithFetching(Long start, Long length) {
		return orderRepository.getAllWithFetching(start, length);
	}
	
	public Order getOrderById(Long orderId) {
		return orderRepository.getOrderById(orderId);
	}
	
	public Order getOrderByIdWithFetching(Long orderId) {
		return orderRepository.getOrderByIdWithFetching(orderId);
	}
	
	public List<Order> getOrdersForStatus(String status) {
		return orderRepository.getOrdersForStatus(status);
	}
	
	public Long getCountOrdersForStatus(String status) {
		return orderRepository.getCountOrdersForStatus(status);
	}	
	
	public List<Order> getOrdersForStatusWithFetching(String status) {
		return orderRepository.getOrdersForStatusWithFetching(status);
	}
	
	public List<Order> getOrdersForStatusWithFetching(String status, Long start, Long length) {
		return orderRepository.getOrdersForStatusWithFetching(status, start, length);
	}
	
	public List<Order> getAllForClientId(Long clientId) {
		return orderRepository.getAllForClientId(clientId);
	}
	
	public Long getCountAllForClientId(Long clientId) {
		return orderRepository.getCountAllForClientId(clientId);
	}
	
	public List<Order> getOrdersForClientIdAndStatusWithFetching(Long clientId, String status) {
		return orderRepository.getOrdersForClientIdAndStatusWithFetching(clientId, status);
	}
	
	public List<Order> getOrdersForClientIdAndStatusWithFetching(Long clientId, String status,
			Long start, Long length) {
		return orderRepository
				.getOrdersForClientIdAndStatusWithFetching(clientId, status, start, length);
	}
	
	public List<Order> getOrdersByClientIdAndMachineSNAndNotFinished(Long clientId,
			String serialNumber) {
		return orderRepository
				.getOrdersByClientIdAndMachineSNAndNotFinished(clientId, serialNumber);
	}
	
	public List<Order> getCurrentOrdersForClientIdWithFetching(Long clientId) {
		return orderRepository.getCurrentOrdersForClientIdWithFetching(clientId);
	}
	
	public List<Order> getCurrentOrdersForClientIdWithFetching(Long clientId,
			Long start, Long length) {
		return orderRepository.getCurrentOrdersForClientIdWithFetching(clientId, start, length);
	}
	
	public Long getOrderCount() {
		return orderRepository.getOrderCount();
	}
	
	public Long getCountOrdersForClientIdAndStatus(Long clientId, String status) {
		return orderRepository.getCountOrdersForClientIdAndStatus(clientId, status);
	}
	
	public Long getCountCurrentOrderForClientId(Long clientId) {
		return orderRepository.getCountCurrentOrderForClientId(clientId);
	}

	public Boolean add(Order o, Long clientId,
			Long repairTypeId, Long machineId, Long orderStatusId) {
		return orderRepository.add(o, clientId, repairTypeId, machineId, orderStatusId);
	}
	
	public Integer confirmOrderById(Long orderId, String manager, Long orderStatusId) {
		return orderRepository.confirmOrderById(orderId, manager, orderStatusId);
	}
	
	public Integer setOrderStatusById(Long orderId, Long orderStatusId) {
		return orderRepository.setOrderStatusById(orderId, orderStatusId);
	}
	
	public Integer cancelOrderById(Long orderId) {
		return orderRepository.cancelOrderById(orderId);
	}
	
	public Integer updateOrderById(Long orderId, Order order) {
		return orderRepository.updateOrderById(orderId, order);
	}
}
