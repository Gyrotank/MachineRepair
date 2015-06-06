package com.glomozda.machinerepair.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.machine.MachineService;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.service.order.OrderService;
import com.glomozda.machinerepair.service.orderstatus.OrderStatusService;
import com.glomozda.machinerepair.service.repairtype.RepairTypeService;
import com.glomozda.machinerepair.service.user.UserService;
import com.glomozda.machinerepair.service.userauthorization.UserAuthorizationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/springContextServiceTest.xml", 
		"/persistenceContextServiceTest.xml"})
public abstract class ServiceTestsTemplate {
	
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    
    @Autowired
    protected transient OrderService orderService;
	
	@Autowired
	protected transient OrderStatusService orderStatusService;
	
	@Autowired
	protected transient ClientService clientService;
	
	@Autowired
	protected transient RepairTypeService repairTypeService;
	
	@Autowired
	protected transient MachineService machineService;
	
	@Autowired
	protected transient MachineServiceableService machineServiceableService;
	
	@Autowired
	protected transient UserService userService;
	
	@Autowired
	protected transient UserAuthorizationService userAuthorizationService;
}
