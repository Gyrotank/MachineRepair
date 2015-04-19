package com.glomozda.machinerepair.service.order;

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
import com.glomozda.machinerepair.service.DAOTestsTemplate;
import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.machine.MachineService;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.service.repairtype.RepairTypeService;
import com.glomozda.machinerepair.service.user.UserService;

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
        
        machineServiceableService.add(new MachineServiceable("M-S-1", "TM-1", "UK", "��"));
        machineServiceableService.add(new MachineServiceable("M-S-2", "TM-2", "USA", "���"));
        
        cal.set(2000, 10, 7);
        o1.setStart(new java.sql.Date(cal.getTimeInMillis()));
        o1.setStatus("finished");
        o2.setStart(new java.sql.Date(new java.util.Date().getTime()));
        o2.setStatus("pending");
        
        final Client cl1 = new Client();
        cl1.setClientName("Ivan");
        final Client cl2 = new Client();
        cl2.setClientName("Petro");
        clientService.add(cl1, (long) 1);
        clientService.add(cl2, (long) 2);
        
        repairTypeService.add(new RepairType("Full", "������", new BigDecimal(10000), 6));
        repairTypeService.add(new RepairType("Partial", "���������", new BigDecimal(5000), 3));
        
        machineService.add(new Machine("SN1", 2010, 2), (long) 1);
        machineService.add(new Machine("SN2", 2013, 1), (long) 2);
        
        orderService.add(o1, (long) 1, (long) 1, (long) 1);
        orderService.add(o2, (long) 2, (long) 2, (long) 2);        
    }
   
    @Test
    public void testGetAll() {
    	Assert.assertTrue(orderService.getAll().size() == 2);
    }
    
    @Test
    public void testGetAllWithLimits() {
    	Assert.assertTrue(orderService.getAll((long) 0, (long) 100).size() == 2);
    }
        
    @Test
    public void testGetAllWithFetching() {    	
    	Assert.assertTrue(orderService.getAllWithFetching().size() == 2);
    	Assert.assertTrue(orderService.getAllWithFetching().get(0)
    			.getRepairType().getRepairTypeName().contentEquals("Partial"));
    	Assert.assertTrue(orderService.getAllWithFetching().get(0)
    			.getClient().getClientName().contentEquals("Petro"));
    	Assert.assertTrue(orderService.getAllWithFetching().get(0)
    			.getMachine().getMachineSerialNumber().contentEquals("SN2"));
    }
    
    @Test
    public void testGetAllWithFetchingWithLimits() {    	
    	Assert.assertTrue(orderService.getAllWithFetching((long) 0, (long) 100).size() == 2);
    	Assert.assertTrue(orderService.getAllWithFetching((long) 0, (long) 100).get(0)
    			.getRepairType().getRepairTypeName().contentEquals("Partial"));
    	Assert.assertTrue(orderService.getAllWithFetching((long) 0, (long) 100).get(0)
    			.getClient().getClientName().contentEquals("Petro"));
    	Assert.assertTrue(orderService.getAllWithFetching((long) 0, (long) 100).get(0)
    			.getMachine().getMachineSerialNumber().contentEquals("SN2"));
    }
    
    @Test
    public void testGetOrderByIdWithFetching() {
    	Assert.assertTrue(orderService.getOrderByIdWithFetching((long) 1)
    			.getRepairType().getRepairTypeName().contentEquals("Full"));
    	Assert.assertTrue(orderService.getOrderByIdWithFetching((long) 1)
    			.getClient().getClientName().contentEquals("Ivan"));
    	Assert.assertTrue(orderService.getOrderByIdWithFetching((long) 1)
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
    public void testGetOrdersForStatusWithFetchingWithLimits() {
    	Assert.assertTrue(orderService
    			.getOrdersForStatusWithFetching("finished", (long) 0, (long) 100).size() == 1);
    	Assert.assertTrue(orderService
    			.getOrdersForStatusWithFetching("finished", (long) 0, (long) 100).get(0)
    			.getRepairType().getRepairTypeName().contentEquals("Full"));
    	Assert.assertTrue(orderService
    			.getOrdersForStatusWithFetching("finished", (long) 0, (long) 100).get(0)
    			.getClient().getClientName().contentEquals("Ivan"));
    	Assert.assertTrue(orderService
    			.getOrdersForStatusWithFetching("finished", (long) 0, (long) 100).get(0)
    			.getMachine().getMachineSerialNumber().contentEquals("SN1"));
    }
    
    @Test
    public void testGetAllForClientId() {
    	Assert.assertTrue(orderService.getAllForClientId((long) 1).size() == 1);
    }
    
    @Test
    public void testGetOrdersForClientIdAndStatusWithFetching() {
    	Assert.assertTrue(orderService.getOrdersForClientIdAndStatusWithFetching((long) 1,
    			"finished")
    			.size() == 1);
    	Assert.assertTrue(orderService.getOrdersForClientIdAndStatusWithFetching((long) 1,
    			"finished")
    			.get(0).getRepairType().getRepairTypeName().contentEquals("Full"));
    	Assert.assertTrue(orderService.getOrdersForClientIdAndStatusWithFetching((long) 1,
    			"finished")
    			.get(0).getClient().getClientName().contentEquals("Ivan"));
    	Assert.assertTrue(orderService.getOrdersForClientIdAndStatusWithFetching((long) 1,
    			"finished")
    			.get(0).getMachine().getMachineSerialNumber().contentEquals("SN1"));
    }
    
    @Test
    public void testGetOrdersForClientIdAndStatusWithFetchingWithLimits() {
    	Assert.assertTrue(orderService
    			.getOrdersForClientIdAndStatusWithFetching((long) 1,
    			"finished", (long) 0, (long) 100)
    			.size() == 1);
    	Assert.assertTrue(orderService
    			.getOrdersForClientIdAndStatusWithFetching((long) 1,
    			"finished", (long) 0, (long) 100)
    			.get(0).getRepairType().getRepairTypeName().contentEquals("Full"));
    	Assert.assertTrue(orderService
    			.getOrdersForClientIdAndStatusWithFetching((long) 1,
    			"finished", (long) 0, (long) 100)
    			.get(0).getClient().getClientName().contentEquals("Ivan"));
    	Assert.assertTrue(orderService
    			.getOrdersForClientIdAndStatusWithFetching((long) 1,
    			"finished", (long) 0, (long) 100)
    			.get(0).getMachine().getMachineSerialNumber().contentEquals("SN1"));
    }
    
    @Test
    public void testGetCurrentOrdersForClientIdWithFetchingWithLimits() {
    	Assert.assertTrue(orderService
    			.getCurrentOrdersForClientIdWithFetching((long) 1,
    			(long) 0, (long) 100)
    			.size() == 0);
    	Assert.assertTrue(orderService
    			.getCurrentOrdersForClientIdWithFetching((long) 2,
    			(long) 0, (long) 100)
    			.size() == 1);
    	Assert.assertTrue(orderService
    			.getCurrentOrdersForClientIdWithFetching((long) 2,
    			(long) 0, (long) 100)
    			.get(0).getMachine().getMachineSerialNumber().contentEquals("SN2"));
    }
    
    @Test
    public void testGetOrderCount() {
    	Assert.assertTrue(orderService.getOrderCount() == 2);
    }
    
    @Test
    public void testGetOrderById() {
    	Assert.assertTrue(orderService.getOrderById((long) 1).getStatus()
    			.contentEquals("finished"));
    }
    
    @Test
    public void testGetCountOrdersForStatus() {
    	Assert.assertTrue(orderService.getCountOrdersForStatus("finished") == 1);
    	Assert.assertTrue(orderService.getCountOrdersForStatus("pending") == 1);
    	Assert.assertTrue(orderService.getCountOrdersForStatus("started") == 0);
    }
    
    @Test
    public void testGetCountAllForClientId() {
    	Assert.assertTrue(orderService.getCountAllForClientId((long) 1) == 1);
    	Assert.assertTrue(orderService.getCountAllForClientId((long) 2) == 1);
    	Assert.assertTrue(orderService.getCountAllForClientId((long) 3) == 0);
    }
    
    @Test
    public void testCountOrdersForClientIdAndStatus() {
    	Assert.assertTrue(orderService
    			.getCountOrdersForClientIdAndStatus((long) 1, "finished") == 1);
    	Assert.assertTrue(orderService
    			.getCountOrdersForClientIdAndStatus((long) 2, "pending") == 1);
    	Assert.assertTrue(orderService
    			.getCountOrdersForClientIdAndStatus((long) 2, "finished") == 0);
    	Assert.assertTrue(orderService
    			.getCountOrdersForClientIdAndStatus((long) 3, "ready") == 0);
    }
    
    @Test
    public void testCountCurrentOrderForClientId() {
    	Assert.assertTrue(orderService
    			.getCountCurrentOrderForClientId((long) 1) == 0);
    	Assert.assertTrue(orderService
    			.getCountCurrentOrderForClientId((long) 2) == 1);    	
    	Assert.assertTrue(orderService
    			.getCountCurrentOrderForClientId((long) 3) == 0);
    }
    
    @Test
    @Ignore
    public void testConfirmOrderById() {    	
        orderService.confirmOrderById((long) 2);        
        Assert.assertTrue(orderService.getOrderByIdWithFetching((long) 2)
        		.getStatus().contentEquals("started"));
    }
    
    @Test
    @Ignore
    public void testSetOrderStatusById() {    	
        orderService.setOrderStatusById((long) 1, "pending");
        Assert.assertTrue(orderService.getOrderByIdWithFetching((long) 1)
        		.getStatus().contentEquals("pending"));
    }
    
    @Test
    @Ignore
    public void testCancelOrderById() {    	
        orderService.cancelOrderById((long) 2);
        Assert.assertNull(orderService.getOrderByIdWithFetching((long) 2));
    }
}
