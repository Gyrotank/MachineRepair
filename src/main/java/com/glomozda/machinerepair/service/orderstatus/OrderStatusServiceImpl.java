package com.glomozda.machinerepair.service.orderstatus;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	public Map<Long, String> getIdsAndNames() {		
		List<Object[]> idsAndNamesList = orderStatusRepository.getIdsAndNames();
				
				Map<Long, String> idsAndNamesMap = 
						new LinkedHashMap<Long, String>(idsAndNamesList.size());
				for (Object[] idAndName : idsAndNamesList)
					idsAndNamesMap.put((Long)idAndName[0], (String)idAndName[1]);
				
		return idsAndNamesMap;
	}
	
	@Override
	public Map<Long, String> getIdsAndNamesRu() {		
		List<Object[]> idsAndNamesList = orderStatusRepository.getIdsAndNamesRu();
				
				Map<Long, String> idsAndNamesMap = 
						new LinkedHashMap<Long, String>(idsAndNamesList.size());
				for (Object[] idAndName : idsAndNamesList)
					idsAndNamesMap.put((Long)idAndName[0], (String)idAndName[1]);
				
		return idsAndNamesMap;
	}
	
	@Override
	public Boolean add(OrderStatus os) {
		return orderStatusRepository.add(os);
	}
}
