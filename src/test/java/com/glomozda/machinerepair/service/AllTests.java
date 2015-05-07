package com.glomozda.machinerepair.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.glomozda.machinerepair.service.client.ClientDAOJDBCTest;
import com.glomozda.machinerepair.service.machine.MachineDAOJDBCTest;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableDAOJDBCTest;
import com.glomozda.machinerepair.service.order.OrderDAOJDBCTest;
import com.glomozda.machinerepair.service.orderstatus.OrderStatusDAOJDBCTest;
import com.glomozda.machinerepair.service.repairtype.RepairTypeDAOJDBCTest;
import com.glomozda.machinerepair.service.user.UserDAOJDBCTest;
import com.glomozda.machinerepair.service.userauthorization.UserAuthorizationDAOJDBCTest;

@RunWith(Suite.class)
@SuiteClasses({ MachineDAOJDBCTest.class,
				RepairTypeDAOJDBCTest.class,
				ClientDAOJDBCTest.class,
				UserDAOJDBCTest.class,
				UserAuthorizationDAOJDBCTest.class,
				OrderDAOJDBCTest.class,
				OrderStatusDAOJDBCTest.class,
				MachineServiceableDAOJDBCTest.class
			})

public class AllTests {

}
