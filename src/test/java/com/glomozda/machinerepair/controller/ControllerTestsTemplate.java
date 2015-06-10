package com.glomozda.machinerepair.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.glomozda.machinerepair.service.SessionScopeInfoService;
import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.machine.MachineService;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.service.order.OrderService;
import com.glomozda.machinerepair.service.orderstatus.OrderStatusService;
import com.glomozda.machinerepair.service.repairtype.RepairTypeService;
import com.glomozda.machinerepair.service.user.UserService;
import com.glomozda.machinerepair.service.userauthorization.UserAuthorizationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/springContextControllerTest.xml"})
@WebAppConfiguration
public abstract class ControllerTestsTemplate {
	
	protected MockMvc mockMvc;
	
	@Autowired
	protected OrderService orderSvc;
	
	@Autowired
	protected OrderStatusService orderStatusSvc;
	
	@Autowired
	protected ClientService clientSvc;

	@Autowired
	protected RepairTypeService repairTypeSvc;

	@Autowired
	protected MachineService machineSvc;

	@Autowired
	protected MachineServiceableService machineServiceableSvc;

	@Autowired
	protected UserService userSvc;

	@Autowired
	protected UserAuthorizationService userAuthorizationSvc;
	
	@Autowired
	protected SessionScopeInfoService sessionScopeInfoSvc;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
 
    @Before
    public void setUp() {
    	Mockito.reset(orderSvc);
        Mockito.reset(orderStatusSvc);
        Mockito.reset(clientSvc);
        Mockito.reset(repairTypeSvc);
        Mockito.reset(machineSvc);
        Mockito.reset(machineServiceableSvc);
        Mockito.reset(userSvc);
        Mockito.reset(userAuthorizationSvc);
 
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}
