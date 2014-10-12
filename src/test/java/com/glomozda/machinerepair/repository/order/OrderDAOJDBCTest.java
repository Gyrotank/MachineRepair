package com.glomozda.machinerepair.repository.order;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.domain.repairtype.RepairType;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.repository.DAOTestsTemplate;
import com.glomozda.machinerepair.repository.client.ClientService;
import com.glomozda.machinerepair.repository.machine.MachineService;
import com.glomozda.machinerepair.repository.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.repository.repairtype.RepairTypeService;
import com.glomozda.machinerepair.repository.user.UserService;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@ContextConfiguration(locations = "classpath:spring-context-test.xml")
@Transactional
public class OrderDAOJDBCTest extends DAOTestsTemplate{
    
	@Autowired
    private transient OrderService orderService;
	
	@Autowired
    private transient ClientService clientService;
	
	@Autowired
    private transient RepairTypeService repairTypeService;
	
	@Autowired
    private transient MachineService machineService;
	
	@Autowired
    private transient MachineServiceableService machineServiceableService;
	
	@Autowired
	private transient UserService userService;
    
	Calendar cal = Calendar.getInstance();
	
    final Order o1 = new Order();
    final Order o2 = new Order();

    @Before
    public void prepareDB(){
        jdbcTemplate.execute("TRUNCATE TABLE Orders");
        jdbcTemplate.execute("ALTER TABLE Orders "
        		+ "ALTER COLUMN orders_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE Clients");
        jdbcTemplate.execute("ALTER TABLE Clients "
        		+ "ALTER COLUMN clients_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE Repair_Types");
        jdbcTemplate.execute("ALTER TABLE Repair_Types "
        		+ "ALTER COLUMN repair_types_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE Machines");
        jdbcTemplate.execute("ALTER TABLE Machines "
        		+ "ALTER COLUMN machines_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE Machines_Serviceable");
        jdbcTemplate.execute("ALTER TABLE Machines_Serviceable "
        		+ "ALTER COLUMN machines_serviceable_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE Users");
        jdbcTemplate.execute("ALTER TABLE Users ALTER COLUMN users_id RESTART WITH 1");
        
        userService.add(new User("ivan_user", "qwerty", "qwerty_encoded"));
        userService.add(new User("petro_user", "12345", "12345_encoded"));
        
        machineServiceableService.add(new MachineServiceable("M-S-1", "TM-1", "UK"));
        machineServiceableService.add(new MachineServiceable("M-S-2", "TM-2", "USA"));
        
        cal.set(2000, 10, 7);
        o1.setStart(new java.sql.Date(cal.getTimeInMillis()));
        o1.setStatus("finished");
        o2.setStart(new java.sql.Date(new java.util.Date().getTime()));
        o2.setStatus("pending");
        
        final Client cl1 = new Client();
        cl1.setClientName("Ivan");
        final Client cl2 = new Client();
        cl2.setClientName("Petro");
        clientService.add(cl1, 1);
        clientService.add(cl2, 2);
        
        repairTypeService.add(new RepairType("Full", new BigDecimal(10000), 6));
        repairTypeService.add(new RepairType("Partial", new BigDecimal(5000), 3));
        
        machineService.add(new Machine("SN1", 2010, 2), 1);
        machineService.add(new Machine("SN2", 2013, 1), 2);
        
        orderService.add(o1, 1, 1, 1);
        orderService.add(o2, 2, 2, 2);        
    }
   
    @Test
    public void testGetAll() {
    	Assert.assertTrue(orderService.getAll().size() == 2);
    }
        
    @Test
    public void testGetAllWithFetching() {    	
    	Assert.assertTrue(orderService.getAllWithFetching().size() == 2);
    	Assert.assertTrue(orderService.getAllWithFetching().get(0)
    			.getRepairType().getRepairTypeName().contentEquals("Full"));
    	Assert.assertTrue(orderService.getAllWithFetching().get(0)
    			.getClient().getClientName().contentEquals("Ivan"));
    	Assert.assertTrue(orderService.getAllWithFetching().get(0)
    			.getMachine().getMachineSerialNumber().contentEquals("SN1"));
    }
    
    @Test
    public void testGetOrderByIdWithFetching() {
    	Assert.assertTrue(orderService.getOrderByIdWithFetching(1)
    			.getRepairType().getRepairTypeName().contentEquals("Full"));
    	Assert.assertTrue(orderService.getOrderByIdWithFetching(1)
    			.getClient().getClientName().contentEquals("Ivan"));
    	Assert.assertTrue(orderService.getOrderByIdWithFetching(1)
    			.getMachine().getMachineSerialNumber().contentEquals("SN1"));
    }
    
    @Test
    public void testGetOrdersForStatus() {
    	Assert.assertTrue(orderService.getOrdersForStatus("finished").size() == 1);
    }
    
    @Test
    public void testGetOrdersForStatusWithFetching() {
    	Assert.assertTrue(orderService.getOrdersForStatusWithFetching("finished").size() == 1);
    	Assert.assertTrue(orderService.getOrdersForStatusWithFetching("finished").get(0)
    			.getRepairType().getRepairTypeName().contentEquals("Full"));
    	Assert.assertTrue(orderService.getOrdersForStatusWithFetching("finished").get(0)
    			.getClient().getClientName().contentEquals("Ivan"));
    	Assert.assertTrue(orderService.getOrdersForStatusWithFetching("finished").get(0)
    			.getMachine().getMachineSerialNumber().contentEquals("SN1"));
    }
    
    @Test
    public void testGetAllForClientId() {
    	Assert.assertTrue(orderService.getAllForClientId(1).size() == 1);
    }
    
    @Test
    public void testGetOrdersForClientIdAndStatusWithFetching() {
    	Assert.assertTrue(orderService.getOrdersForClientIdAndStatusWithFetching(1, "finished")
    			.size() == 1);
    	Assert.assertTrue(orderService.getOrdersForClientIdAndStatusWithFetching(1, "finished")
    			.get(0).getRepairType().getRepairTypeName().contentEquals("Full"));
    	Assert.assertTrue(orderService.getOrdersForClientIdAndStatusWithFetching(1, "finished")
    			.get(0).getClient().getClientName().contentEquals("Ivan"));
    	Assert.assertTrue(orderService.getOrdersForClientIdAndStatusWithFetching(1, "finished")
    			.get(0).getMachine().getMachineSerialNumber().contentEquals("SN1"));
    }
    
    @Test
    @Ignore
    public void testConfirmOrderById() {    	
        orderService.confirmOrderById(2);        
        Assert.assertTrue(orderService.getOrderByIdWithFetching(2)
        		.getStatus().contentEquals("started"));
    }
    
    @Test
    @Ignore
    public void testSetOrderStatusById() {    	
        orderService.setOrderStatusById(1, "pending");
        Assert.assertTrue(orderService.getOrderByIdWithFetching(1)
        		.getStatus().contentEquals("pending"));
    }
    
    @Test
    @Ignore
    public void testCancelOrderById() {    	
        orderService.cancelOrderById(2);
        Assert.assertNull(orderService.getOrderByIdWithFetching(2));
    }
}
