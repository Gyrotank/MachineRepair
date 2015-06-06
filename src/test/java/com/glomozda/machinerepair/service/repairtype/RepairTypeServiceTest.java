package com.glomozda.machinerepair.service.repairtype;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.repairtype.RepairType;
import com.glomozda.machinerepair.service.ServiceTestsTemplate;

@Transactional
public class RepairTypeServiceTest extends ServiceTestsTemplate{
    
    final RepairType rt1 = new RepairType("Full", "������", new BigDecimal(10000), 6);
    final RepairType rt2 = new RepairType("Partial", "���������", new BigDecimal(5000),
    		3, (byte) 0);
            
    @Before
    public void prepareDB(){
    	jdbcTemplate.execute("TRUNCATE TABLE Repair_Types");
        jdbcTemplate.execute("ALTER TABLE Repair_Types "
        		+ "ALTER COLUMN repair_types_id RESTART WITH 1");        
        
        repairTypeService.add(rt1);
        repairTypeService.add(rt2);        
    }
   
    @Test
    public void testGetAll() {
    	Assert.assertTrue(repairTypeService.getAll().size() == 2);
    }
    
    @Test
    public void testGetAllAvailable() {
    	Assert.assertTrue(repairTypeService.getAllAvailable().size() == 1);
    }
    
    @Test
    public void testGetAllWithLimits() {
    	Assert.assertTrue(repairTypeService.getAll((long) 0, (long) 100).size() == 2);
    }
    
    @Test
    public void testGetRepairTypeForName() {    	
    	Assert.assertTrue(repairTypeService.getRepairTypeForName("Full")
    			.getRepairTypePrice().intValue() == 10000);
    }
    
    @Test
    public void testGetRepairTypeCount() {
    	Assert.assertTrue(repairTypeService.getRepairTypeCount() == 2);
    }
    
    @Test
    public void testGetRepairTypeById() {
    	Assert.assertTrue(repairTypeService.getRepairTypeById((long) 1)
    			.getRepairTypeName().contentEquals("Full"));
    }
    
    @Test
    public void testGetIdsAndNamesOfAvailable() {
    	Assert.assertTrue(repairTypeService.getIdsAndNamesOfAvailable().size() == 1);
    }
    
    @Test
    public void testGetIdsAndNamesRuOfAvailable() {
    	Assert.assertTrue(repairTypeService.getIdsAndNamesRuOfAvailable().size() == 1);
    }
    
    @Test
    public void testGetAllEntities() {
    	Assert.assertTrue(repairTypeService.getAllEntities().size() == 2);
    }
    
    @Test
    public void testGetAllEntitiesWithLimits() {
    	Assert.assertTrue(repairTypeService
    			.getAllEntities((long) 0, (long) 100).size() == 2);
    }
    
    @Test
    public void testGetCountEntitites() {
    	Assert.assertTrue(repairTypeService.getCountEntities() == 2);
    }
    
    @Test
    public void testAddExisting() {
    	RepairType rt3 = new RepairType("Partial", "���������", new BigDecimal(500),
        		1, (byte) 1);
    	Assert.assertFalse(repairTypeService.add(rt3));
    }
    
    @Test
    public void testSetRepairTypeAvailableById() {
    	Assert.assertTrue(repairTypeService.setRepairTypeAvailableById((long) 1, (byte) 0) == 1);
    	Assert.assertTrue(repairTypeService.setRepairTypeAvailableById((long) 3, (byte) 0) == 0);
    }
    
    @Test
    public void testUpdateRepairTypeById() {
    	RepairType rt3 = new RepairType("PI", "ЧС", new BigDecimal(5000), 3);
    	Assert.assertTrue(repairTypeService.updateRepairTypeById((long) 1, rt3) == 1);
    	Assert.assertTrue(repairTypeService.updateRepairTypeById((long) 3, rt3) == 0);
    }
}
