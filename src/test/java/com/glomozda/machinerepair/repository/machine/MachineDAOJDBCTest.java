package com.glomozda.machinerepair.repository.machine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.repository.DAOTestsTemplate;
import com.glomozda.machinerepair.repository.machineserviceable.MachineServiceableService;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@ContextConfiguration(locations = "classpath:spring-context-test.xml")
@Transactional
public class MachineDAOJDBCTest extends DAOTestsTemplate{
    
	@Autowired
    private transient MachineServiceableService machineServiceableService;
    
    @Autowired
    private transient MachineService machineService;
    
    final Machine m1 = new Machine("SN1", 2010, 2);
    final Machine m2 = new Machine("SN2", 2013, 1);
            
    @Before
    public void prepareDB(){
        jdbcTemplate.execute("TRUNCATE TABLE Machines");
        jdbcTemplate.execute("ALTER TABLE Machines ALTER COLUMN machines_id RESTART WITH 1");
        jdbcTemplate.execute("TRUNCATE TABLE Machines_Serviceable");
        jdbcTemplate.execute("ALTER TABLE Machines_Serviceable "
        		+ "ALTER COLUMN machines_serviceable_id RESTART WITH 1");        
        
        machineServiceableService.add(new MachineServiceable("M-S-1", "TM-1", "UK"));
        machineServiceableService.add(new MachineServiceable("M-S-2", "TM-2", "USA"));
        
        machineService.add(m1, 1);
        machineService.add(m2, 2);
    }
   
    @Test
    public void testGetAll() {
    	Assert.assertTrue(machineService.getAll().size() == 2);
    }
        
    @Test
    public void testGetAllWithFetching() {    	
    	Assert.assertTrue(machineService.getAllWithFetching().get(0)
    			.getMachineServiceable().getMachineServiceableName().contentEquals("M-S-1"));
    }
    
    @Test
    public void testGetMachineForSerialNumber() {
    	final Machine actualResult = machineService.getMachineForSerialNumber("SN1");
    	Assert.assertEquals(m1, actualResult);
    }
    
    @Test
    public void testGetMachineForSerialNumberWithFetching() {    	
    	final Machine actualResult = machineService.getMachineForSerialNumberWithFetching("SN1");
        Assert.assertTrue(actualResult.getMachineServiceable()
        		.getMachineServiceableName().contentEquals("M-S-1"));
    }
    
    @Test
    @Ignore
    public void testIncrementTimesRepairedById() {    	
        machineService.incrementTimesRepairedById(machineService.
        		getMachineForSerialNumber("SN2").getMachineId());        
        Assert.assertEquals(2, machineService.getMachineForSerialNumber("SN2")
        		.getMachineTimesRepaired().intValue());
    }
}
