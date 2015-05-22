package com.glomozda.machinerepair.service;

import java.util.List;

public interface EntityService {
	
	@SuppressWarnings("rawtypes")
	public List getAllEntities();
	
	@SuppressWarnings("rawtypes")
	public List getAllEntities(Long start, Long length);
	
	public Long getCountEntities();

}
