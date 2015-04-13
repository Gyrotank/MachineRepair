package com.glomozda.machinerepair.repository.machineserviceable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.repository.DAOTestsTemplate;
import com.glomozda.machinerepair.repository.machineserviceable.MachineServiceableService;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@ContextConfiguration(locations = "classpath:spring-context-test.xml")
@Transactional
public class MachineServiceableDAOJDBCTest extends DAOTestsTemplate{
    
	@Autowired
    private transient MachineServiceableService machineServiceableService;
    
    final MachineServiceable ms1 = new MachineServiceable("M-S-3", "TM-1", "UK");
    final MachineServiceable ms2 = new MachineServiceable("M-S-2", "ATM-2", "USA");
            
    @Before
    public void prepareDB(){
        jdbcTemplate.execute("TRUNCATE TABLE Machines_Serviceable");
        jdbcTemplate.execute("ALTER TABLE Machines_Serviceable "
        		+ "ALTER COLUMN machines_serviceable_id RESTART WITH 1");        
        
        machineServiceableService.add(ms1);
        machineServiceableService.add(ms2);        
    }
   
    @Test
    public void testGetAll() {
    	Assert.assertTrue(machineServiceableService.getAll().size() == 2);
    }
    
    @Test
    public void testGetAllWithLimits() {
    	Assert.assertTrue(machineServiceableService.getAll((long) 0, (long) 100).size() == 2);
    }
    
    @Test
    public void testGetAllOrderByName() {    	
    	Assert.assertTrue(machineServiceableService.getAllOrderByName().get(0)
    			.getMachineServiceableName().contentEquals("M-S-2"));
    }
    
    @Test
    public void testGetAllOrderByTrademark() {    	
    	Assert.assertTrue(machineServiceableService.getAllOrderByTrademark().get(0)
    			.getMachineServiceableTrademark().contentEquals("ATM-2"));
    }
    
    @Test
    public void testGetMachineServiceableById() {
    	final MachineServiceable actualResult = machineServiceableService
    			.getMachineServiceableById((long) 1);
    	Assert.assertEquals(ms1, actualResult);
    }
    
    @Test
    public void testGetMachineServiceableCount() {
    	Assert.assertTrue(machineServiceableService.getMachineServiceableCount() == 2);
    }
}
