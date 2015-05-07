package com.glomozda.machinerepair.service.order;

import java.util.List;

import com.glomozda.machinerepair.domain.order.*;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends OrderService {
	
	@Override
	public List<Order> getAll() {
		return orderRepository.getAll();
	}
	
	@Override
	public List<Order> getAll(Long start, Long length) {
		return orderRepository.getAll(start, length);
	}
	
	@Override
	public List<Order> getAllWithFetching() {
		return orderRepository.getAllWithFetching();
	}
	
	@Override
	public List<Order> getAllWithFetching(Long start, Long length) {
		return orderRepository.getAllWithFetching(start, length);
	}
	
	@Override
	public Order getOrderById(Long orderId) {
		return orderRepository.getOrderById(orderId);
	}
	
	@Override
	public Order getOrderByIdWithFetching(Long orderId) {
		return orderRepository.getOrderByIdWithFetching(orderId);
	}
	
	@Override
	public List<Order> getOrdersForStatus(String status) {
		return orderRepository.getOrdersForStatus(status);
	}
	
	@Override
	public Long getCountOrdersForStatus(String status) {
		return orderRepository.getCountOrdersForStatus(status);
	}	
	
	@Override
	public List<Order> getOrdersForStatusWithFetching(String status) {
		return orderRepository.getOrdersForStatusWithFetching(status);
	}
	
	@Override
	public List<Order> getOrdersForStatusWithFetching(String status, Long start, Long length) {
		return orderRepository.getOrdersForStatusWithFetching(status, start, length);
	}
	
	@Override
	public List<Order> getAllForClientId(Long clientId) {
		return orderRepository.getAllForClientId(clientId);
	}
	
	@Override
	public Long getCountAllForClientId(Long clientId) {
		return orderRepository.getCountAllForClientId(clientId);
	}
	
	@Override
	public List<Order> getOrdersForClientIdAndStatusWithFetching(Long clientId, String status) {
		return orderRepository.getOrdersForClientIdAndStatusWithFetching(clientId, status);
	}
	
	@Override
	public List<Order> getOrdersForClientIdAndStatusWithFetching(Long clientId, String status,
			Long start, Long length) {
		return orderRepository
				.getOrdersForClientIdAndStatusWithFetching(clientId, status, start, length);
	}
	
	@Override
	public List<Order> getOrdersByClientIdAndMachineSNAndNotFinished(Long clientId,
			String serialNumber) {
		return orderRepository
				.getOrdersByClientIdAndMachineSNAndNotFinished(clientId, serialNumber);
	}
	
	@Override
	public List<Order> getCurrentOrdersForClientIdWithFetching(Long clientId) {
		return orderRepository.getCurrentOrdersForClientIdWithFetching(clientId);
	}
	
	@Override
	public List<Order> getCurrentOrdersForClientIdWithFetching(Long clientId,
			Long start, Long length) {
		return orderRepository.getCurrentOrdersForClientIdWithFetching(clientId, start, length);
	}
	
	@Override
	public Long getOrderCount() {
		return orderRepository.getOrderCount();
	}
	
	@Override
	public Long getCountOrdersForClientIdAndStatus(Long clientId, String status) {
		return orderRepository.getCountOrdersForClientIdAndStatus(clientId, status);
	}
	
	@Override
	public Long getCountCurrentOrderForClientId(Long clientId) {
		return orderRepository.getCountCurrentOrderForClientId(clientId);
	}

	@Override
	public Boolean add(Order o, Long clientId,
			Long repairTypeId, Long machineId, Long orderStatusId) {
		return orderRepository.add(o, clientId, repairTypeId, machineId, orderStatusId);
	}
	
	@Override
	public Integer confirmOrderById(Long orderId, String manager, Long orderStatusId) {
		return orderRepository.confirmOrderById(orderId, manager, orderStatusId);
	}
	
	@Override
	public Integer setOrderStatusById(Long orderId, Long orderStatusId) {
		return orderRepository.setOrderStatusById(orderId, orderStatusId);
	}
	
	@Override
	public Integer cancelOrderById(Long orderId) {
		return orderRepository.cancelOrderById(orderId);
	}
	
	@Override
	public Integer updateOrderById(Long orderId, Order order) {
		return orderRepository.updateOrderById(orderId, order);
	}
}
