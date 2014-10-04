package com.glomozda.machinerepair.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.glomozda.machinerepair.repository.client.ClientDAOJDBCTest;
import com.glomozda.machinerepair.repository.machine.MachineDAOJDBCTest;
import com.glomozda.machinerepair.repository.machineserviceable.MachineServiceableDAOJDBCTest;
import com.glomozda.machinerepair.repository.order.OrderDAOJDBCTest;
import com.glomozda.machinerepair.repository.repairtype.RepairTypeDAOJDBCTest;
import com.glomozda.machinerepair.repository.user.UserDAOJDBCTest;
import com.glomozda.machinerepair.repository.userauthorization.UserAuthorizationDAOJDBCTest;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@RunWith(Suite.class)
@SuiteClasses({ MachineDAOJDBCTest.class,
				RepairTypeDAOJDBCTest.class,
				ClientDAOJDBCTest.class,
				UserDAOJDBCTest.class,
				UserAuthorizationDAOJDBCTest.class,
				OrderDAOJDBCTest.class,
				MachineServiceableDAOJDBCTest.class
			})

public class AllTests {

}
