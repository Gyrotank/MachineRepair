package com.glomozda.machinerepair.service.orderstatus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.orderstatus.OrderStatus;
import com.glomozda.machinerepair.service.ServiceTestsTemplate;

@Transactional
public class OrderStatusServiceTest extends ServiceTestsTemplate {
	
	@Before
    public void prepareDB(){
        jdbcTemplate.execute("TRUNCATE TABLE Orders");
        jdbcTemplate.execute("ALTER TABLE Orders "
        		+ "ALTER COLUMN orders_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE Clients");
        jdbcTemplate.execute("ALTER TABLE Clients "
        		+ "ALTER COLUMN clients_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE Order_Statuses");
        jdbcTemplate.execute("ALTER TABLE Order_Statuses "
        		+ "ALTER COLUMN order_statuses_id RESTART WITH 1");
        
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
        
        orderStatusService.add(new OrderStatus(1, "pending", "в обработке"));
        orderStatusService.add(new OrderStatus(2, "started", "выполняется"));
        orderStatusService.add(new OrderStatus(3, "finished", "завершен"));
	}
	
	@Test
	public void testGetAll() {
		Assert.assertTrue(orderStatusService.getAll().size() == 3);
	}
	
	@Test
	public void testGetOrderStatusById() {
		Assert.assertTrue(orderStatusService.getOrderStatusById((long) 1)
				.getOrderStatusName().contentEquals("pending"));
		Assert.assertTrue(orderStatusService.getOrderStatusById((long) 2)
				.getOrderStatusName().contentEquals("started"));
		Assert.assertTrue(orderStatusService.getOrderStatusById((long) 3)
				.getOrderStatusName().contentEquals("finished"));
		Assert.assertTrue(orderStatusService.getOrderStatusById((long) 4) == null);
	}
	
	@Test
	public void testGetOrderStatusByName() {
		Assert.assertTrue(orderStatusService.getOrderStatusByName("pending")
				.getOrderStatusNameRu().contentEquals("в обработке"));
		Assert.assertTrue(orderStatusService.getOrderStatusByName("started")
				.getOrderStatusNameRu().contentEquals("выполняется"));
		Assert.assertTrue(orderStatusService.getOrderStatusByName("finished")
				.getOrderStatusNameRu().contentEquals("завершен"));
		Assert.assertTrue(orderStatusService.getOrderStatusByName("cancelled") == null);
	}
	
	@Test
	public void testGetIdsAndNames() {
		Assert.assertTrue(orderStatusService.getIdsAndNames().size() == 3);
	}
	
	@Test
	public void testGetIdsAndNamesRu() {
		Assert.assertTrue(orderStatusService.getIdsAndNamesRu().size() == 3);
	}
}
