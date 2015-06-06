package com.glomozda.machinerepair.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.glomozda.machinerepair.service.client.ClientServiceTest;
import com.glomozda.machinerepair.service.machine.MachineServiceTest;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableServiceTest;
import com.glomozda.machinerepair.service.order.OrderServiceTest;
import com.glomozda.machinerepair.service.orderstatus.OrderStatusServiceTest;
import com.glomozda.machinerepair.service.repairtype.RepairTypeServiceTest;
import com.glomozda.machinerepair.service.user.UserServiceTest;
import com.glomozda.machinerepair.service.userauthorization.UserAuthorizationServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ MachineServiceTest.class,
				RepairTypeServiceTest.class,
				ClientServiceTest.class,
				UserServiceTest.class,
				UserAuthorizationServiceTest.class,
				OrderServiceTest.class,
				OrderStatusServiceTest.class,
				MachineServiceableServiceTest.class
			})

public class AllServiceTests {

}
