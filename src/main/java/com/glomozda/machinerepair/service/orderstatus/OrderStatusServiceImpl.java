package com.glomozda.machinerepair.service.orderstatus;

import java.util.List;

import org.springframework.stereotype.Service;

import com.glomozda.machinerepair.domain.orderstatus.OrderStatus;

@Service
public class OrderStatusServiceImpl extends OrderStatusService {
	
	@Override
	public List<OrderStatus> getAll() {
		return orderStatusRepository.getAll();
	}
	
	@Override
	public OrderStatus getOrderStatusById(Long orderStatusId) {
		return orderStatusRepository.getOrderStatusById(orderStatusId);
	}
	
	@Override
	public OrderStatus getOrderStatusByName(String orderStatusName) {
		return orderStatusRepository.getOrderStatusByName(orderStatusName);
	}
	
	@Override
	public Boolean add(OrderStatus os) {
		return orderStatusRepository.add(os);
	}
}
