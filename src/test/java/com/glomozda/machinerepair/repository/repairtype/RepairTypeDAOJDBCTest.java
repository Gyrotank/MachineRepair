package com.glomozda.machinerepair.repository.repairtype;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.repairtype.RepairType;
import com.glomozda.machinerepair.repository.DAOTestsTemplate;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@ContextConfiguration(locations = "classpath:spring-context-test.xml")
@Transactional
public class RepairTypeDAOJDBCTest extends DAOTestsTemplate{
    
	@Autowired
    private transient RepairTypeService repairTypeService;
    
    final RepairType rt1 = new RepairType("Full", new BigDecimal(10000), 6);
    final RepairType rt2 = new RepairType("Partial", new BigDecimal(5000), 3);
            
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
    public void testGetRepairTypeForName() {    	
    	Assert.assertTrue(repairTypeService.getRepairTypeForName("Full")
    			.getRepairTypePrice().intValue() == 10000);
    }               
}
