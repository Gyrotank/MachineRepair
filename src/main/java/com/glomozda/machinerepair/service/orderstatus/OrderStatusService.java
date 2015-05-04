package com.glomozda.machinerepair.service.orderstatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.orderstatus.OrderStatus;
import com.glomozda.machinerepair.repository.orderstatus.OrderStatusRepository;

@Service
public class OrderStatusService {
	
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	
	@Transactional
	public List<OrderStatus> getAll() {
		return orderStatusRepository.getAll();
	}
	
	@Transactional
	public OrderStatus getOrderStatusById(Long orderStatusId) {
		return orderStatusRepository.getOrderStatusById(orderStatusId);
	}
	
	@Transactional
	public OrderStatus getOrderStatusByName(String orderStatusName) {
		return orderStatusRepository.getOrderStatusByName(orderStatusName);
	}
	
	@Transactional
	public Boolean add(OrderStatus os) {
		return orderStatusRepository.add(os);
	}
}
