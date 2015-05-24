package com.glomozda.machinerepair.repository.orderstatus;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.orderstatus.OrderStatus;

@Repository
public abstract class OrderStatusRepository {

	@PersistenceContext
	protected EntityManager em;
	
	@Transactional
	public abstract Boolean add(OrderStatus os);

	public abstract OrderStatus getOrderStatusByName(String orderStatusName);

	public abstract OrderStatus getOrderStatusById(Long orderStatusId);

	public abstract List<OrderStatus> getAll();

	public abstract List<Object[]> getIdsAndNames();

	public abstract List<Object[]> getIdsAndNamesRu();

}