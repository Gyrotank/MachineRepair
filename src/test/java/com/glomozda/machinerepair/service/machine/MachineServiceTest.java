package com.glomozda.machinerepair.service.machine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.service.ServiceTestsTemplate;

@Transactional
public class MachineServiceTest extends ServiceTestsTemplate{	
	
    final Machine m1 = new Machine("SN1", 2010, 2);
    final Machine m2 = new Machine("SN2", 2013, 1);
            
    @Before
    public void prepareDB(){
        jdbcTemplate.execute("TRUNCATE TABLE Machines");
        jdbcTemplate.execute("ALTER TABLE Machines ALTER COLUMN machines_id RESTART WITH 1");
        jdbcTemplate.execute("TRUNCATE TABLE Machines_Serviceable");
        jdbcTemplate.execute("ALTER TABLE Machines_Serviceable "
        		+ "ALTER COLUMN machines_serviceable_id RESTART WITH 1");        
        
        machineServiceableService.add(new MachineServiceable("M-S-1", "TM-1", "UK", "��"));
        machineServiceableService.add(new MachineServiceable("M-S-2", "TM-2", "USA", "���"));
        
        machineService.add(m1, (long) 1);
        machineService.add(m2, (long) 2);
    }
   
    @Test
    public void testGetAll() {
    	Assert.assertTrue(machineService.getAll().size() == 2);
    }
    
    @Test
    public void testGetAllWithLimits() {
    	Assert.assertTrue(machineService.getAll((long) 0, (long) 100).size() == 2);
    }
        
    @Test
    public void testGetAllWithFetching() {    	
    	Assert.assertTrue(machineService.getAllWithFetching().get(0)
    			.getMachineServiceable().getMachineServiceableName().contentEquals("M-S-1"));
    }
    
    @Test
    public void testGetAllWithFetchingWithLimits() {    	
    	Assert.assertTrue(machineService.getAllWithFetching((long) 0, (long) 100).get(0)
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
    public void testGetMachineByIdWithFetching() {    	
    	final Machine actualResult = machineService.getMachineByIdWithFetching((long) 1);
        Assert.assertTrue(actualResult.getMachineServiceable()
        		.getMachineServiceableName().contentEquals("M-S-1"));
    }
    
    @Test    
    public void testIncrementTimesRepairedById() {    	
        Machine machine = machineService.getMachineForSerialNumber("SN2");    	        
        Assert.assertTrue(machineService.incrementTimesRepairedById(machine.getMachineId()) == 1);
    }
    
    @Test
    public void testGetMachineCount() {
    	Assert.assertTrue(machineService.getMachineCount() == 2);
    }
    
    @Test
    public void testGetMachineById() {
    	final Machine actualResult = machineService.getMachineById((long) 1);
    	Assert.assertEquals(m1, actualResult);
    }
    
    @Test
    public void testGetIdsAndSNs() {
    	Assert.assertTrue(machineService.getIdsAndSNs().size() == 2);
    }
    
    @Test
    public void testGetAllEntities() {
    	Assert.assertTrue(machineService.getAllEntities().size() == 2);
    }
    
    @Test
    public void testGetAllEntitiesWithLimits() {
    	Assert.assertTrue(machineService
    			.getAllEntities((long) 0, (long) 100).size() == 2);
    }
    
    @Test
    public void testGetCountEntitites() {
    	Assert.assertTrue(machineService.getCountEntities() == 2);
    }
    
    @Test
    public void testAddExisting() {
    	Machine m3 = new Machine("SN2", 2015, 10);
    	Assert.assertFalse(machineService.add(m3, (long) 2));
    }
    
    @Test    
    public void testUpdateMachineById() {    	
        Machine machineExisting = machineService.getMachineForSerialNumber("SN2");
        Machine machineNew = new Machine("SN3", 2014, 2);
        Assert.assertTrue(machineService
        		.updateMachineById(machineExisting.getMachineId(), machineNew) == 1);
        Assert.assertTrue(machineService
        		.updateMachineById((long) 3, machineNew) == 0);
    }
}
