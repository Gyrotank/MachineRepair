package com.glomozda.machinerepair.service.machineserviceable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.service.ServiceTestsTemplate;

@Transactional
public class MachineServiceableServiceTest extends ServiceTestsTemplate{
    
    final MachineServiceable ms1 = new MachineServiceable("M-S-3", "TM-1", "UK", "��");
    final MachineServiceable ms2 = new MachineServiceable("M-S-2", "ATM-2",
    		"USA", "���", (byte) 0);    
            
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
    public void testGetAllAvailable() {
    	Assert.assertTrue(machineServiceableService.getAllAvailableOrderByTrademark().size() == 1);
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
    
    @Test
    public void testGetAllIdsAndNames() {
    	Assert.assertTrue(machineServiceableService.getAllIdsAndNames().size() == 2);
    }
    
    @Test
    public void testGetAllEntities() {
    	Assert.assertTrue(machineServiceableService.getAllEntities().size() == 2);
    }
    
    @Test
    public void testGetAllEntitiesWithLimits() {
    	Assert.assertTrue(machineServiceableService
    			.getAllEntities((long) 0, (long) 100).size() == 2);
    }
    
    @Test
    public void testGetCountEntitites() {
    	Assert.assertTrue(machineServiceableService.getCountEntities() == 2);
    }
    
    @Test
    public void testAddExisting() {
    	MachineServiceable ms3 = new MachineServiceable("M-S-2", "ATM-2",
        		"USA", "���", (byte) 0);
    	Assert.assertFalse(machineServiceableService.add(ms3));
    }
    
    @Test
    public void testSetMachineServiceableAvailableById() {
    	Assert.assertTrue(machineServiceableService
    			.setMachineServiceableAvailableById((long) 1, (byte) 0) == 1);
    	Assert.assertTrue(machineServiceableService
    			.setMachineServiceableAvailableById((long) 3, (byte) 1) == 0);
    }
    
    @Test
    public void testUpdateMachineServiceableById() {
    	final MachineServiceable ms3 = new MachineServiceable("M-S-1", "ATM-1", "USA", "���");
    	Assert.assertTrue(machineServiceableService
    			.updateMachineServiceableById((long) 1, ms3) == 1);
    	Assert.assertTrue(machineServiceableService
    			.updateMachineServiceableById((long) 3, ms3) == 0);
    }
}
