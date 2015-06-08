package com.glomozda.machinerepair;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.glomozda.machinerepair.controller.AllControllerTests;
import com.glomozda.machinerepair.service.AllServiceTests;

@RunWith(Suite.class)
@SuiteClasses({ AllControllerTests.class,
				AllServiceTests.class
			})

public class AllTests {

}