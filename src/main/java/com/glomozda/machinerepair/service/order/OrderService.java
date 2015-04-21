package com.glomozda.machinerepair.service.order;

import java.util.List;

import com.glomozda.machinerepair.domain.order.*;
import com.glomozda.machinerepair.repository.order.OrderRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	@Transactional
	public List<Order> getAll() {
		return orderRepository.getAll();
	}
	
	@Transactional
	public List<Order> getAll(Long start, Long length) {
		return orderRepository.getAll(start, length);
	}
	
	@Transactional
	public List<Order> getAllWithFetching() {
		return orderRepository.getAllWithFetching();
	}
	
	@Transactional
	public List<Order> getAllWithFetching(Long start, Long length) {
		return orderRepository.getAllWithFetching(start, length);
	}
	
	@Transactional
	public Order getOrderById(Long orderId) {
		return orderRepository.getOrderById(orderId);
	}
	
	@Transactional
	public Order getOrderByIdWithFetching(Long orderId) {
		return orderRepository.getOrderByIdWithFetching(orderId);
	}
	
	@Transactional
	public List<Order> getOrdersForStatus(String status) {
		return orderRepository.getOrdersForStatus(status);
	}
	
	public Long getCountOrdersForStatus(String status) {
		return orderRepository.getCountOrdersForStatus(status);
	}	
	
	@Transactional
	public List<Order> getOrdersForStatusWithFetching(String status) {
		return orderRepository.getOrdersForStatusWithFetching(status);
	}
	
	@Transactional
	public List<Order> getOrdersForStatusWithFetching(String status, Long start, Long length) {
		return orderRepository.getOrdersForStatusWithFetching(status, start, length);
	}
	
	@Transactional
	public List<Order> getAllForClientId(Long clientId) {
		return orderRepository.getAllForClientId(clientId);
	}
	
	@Transactional
	public Long getCountAllForClientId(Long clientId) {
		return orderRepository.getCountAllForClientId(clientId);
	}
	
	@Transactional
	public List<Order> getOrdersForClientIdAndStatusWithFetching(Long clientId, String status) {
		return orderRepository.getOrdersForClientIdAndStatusWithFetching(clientId, status);
	}
	
	@Transactional
	public List<Order> getOrdersForClientIdAndStatusWithFetching(Long clientId, String status,
			Long start, Long length) {
		return orderRepository
				.getOrdersForClientIdAndStatusWithFetching(clientId, status, start, length);
	}
	
	@Transactional
	public List<Order> getOrdersByClientIdAndMachineSNAndNotFinished(Long clientId,
			String serialNumber) {
		return orderRepository
				.getOrdersByClientIdAndMachineSNAndNotFinished(clientId, serialNumber);
	}
	
	@Transactional
	public List<Order> getCurrentOrdersForClientIdWithFetching(Long clientId,
			Long start, Long length) {
		return orderRepository.getCurrentOrdersForClientIdWithFetching(clientId, start, length);
	}
	
	@Transactional
	public Long getOrderCount() {
		return orderRepository.getOrderCount();
	}
	
	@Transactional
	public Long getCountOrdersForClientIdAndStatus(Long clientId, String status) {
		return orderRepository.getCountOrdersForClientIdAndStatus(clientId, status);
	}
	
	@Transactional
	public Long getCountCurrentOrderForClientId(Long clientId) {
		return orderRepository.getCountCurrentOrderForClientId(clientId);
	}

	@Transactional
	public Boolean add(Order o, Long clientId, Long repairTypeId, Long machineId) {
		return orderRepository.add(o, clientId, repairTypeId, machineId);
	}
	
	@Transactional
	public Integer confirmOrderById(Long orderId, String manager) {
		return orderRepository.confirmOrderById(orderId, manager);
	}
	
	@Transactional
	public Integer setOrderStatusById(Long orderId, String status) {
		return orderRepository.setOrderStatusById(orderId, status);
	}
	
	@Transactional
	public Integer cancelOrderById(Long orderId) {
		return orderRepository.cancelOrderById(orderId);
	}
}
