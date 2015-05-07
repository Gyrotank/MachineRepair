package com.glomozda.machinerepair.service.orderstatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glomozda.machinerepair.domain.orderstatus.OrderStatus;
import com.glomozda.machinerepair.repository.orderstatus.OrderStatusRepository;

@Service
public abstract class OrderStatusService {

	@Autowired
	protected OrderStatusRepository orderStatusRepository;

	public abstract Boolean add(OrderStatus os);

	public abstract OrderStatus getOrderStatusByName(String orderStatusName);

	public abstract OrderStatus getOrderStatusById(Long orderStatusId);

	public abstract List<OrderStatus> getAll();

	public OrderStatusService() {
		super();
	}

}