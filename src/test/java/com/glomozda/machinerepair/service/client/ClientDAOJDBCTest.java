package com.glomozda.machinerepair.service.client;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.service.DAOTestsTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context-test.xml")
@Transactional
public class ClientDAOJDBCTest extends DAOTestsTemplate{
    
    final Client cl1 = new Client();
    final Client cl2 = new Client();
            
    @Before
    public void prepareDB(){
        jdbcTemplate.execute("TRUNCATE TABLE Clients");
        jdbcTemplate.execute("ALTER TABLE Clients ALTER COLUMN clients_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE Users");
        jdbcTemplate.execute("ALTER TABLE Users ALTER COLUMN users_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE User_Authorization");
        jdbcTemplate.execute("ALTER TABLE User_Authorization"
        		+ " ALTER COLUMN user_authorization_id RESTART WITH 1");
        
        userService.add(new User("ivan_user", "qwerty_encoded"));
        userService.add(new User("petro_user", "12345_encoded"));
        
        cl1.setClientName("Ivan");
    	clientService.add(cl1, 1L);
    	
    	cl2.setClientName("Petro");
    	clientService.createClientAccount(cl2, 2L);
    }    
   
    @Test
    public void testGetAll() {
    	Assert.assertTrue(clientService.getAll().size() == 2);
    }
    
    @Test
    public void testGetAllWithLimits() {
    	Assert.assertTrue(clientService.getAll(0L, 100L).size() == 2);
    }
    
    @Test
    public void testGetAllWithFetching() {    	
    	Assert.assertTrue(clientService.getAllWithFetching().get(0)
        		.getClientUser().getLogin().contentEquals("ivan_user"));
    }
    
    @Test
    public void testGetAllWithFetchingWithLimits() {    	
    	Assert.assertTrue(clientService.getAllWithFetching(0L, 100L).get(0)
        		.getClientUser().getLogin().contentEquals("ivan_user"));
    }
    
    @Test
    public void testGetAllClientIds() {
    	Assert.assertTrue(clientService.getAllClientIds()
    			.containsAll(Arrays.asList(1L, 2L)));
    }
    
    @Test
    public void testGetClientByUserId() {    	
        final Client actualResult = clientService.getClientByUserId(1L);
        Assert.assertEquals(cl1, actualResult);
    }
    
    @Test   
    public void testGetClientByUserIdWithFetching() {        
        final Client actualResult = clientService.getClientByUserIdWithFetching(1L);
        Assert.assertTrue(actualResult.getClientUser().getLogin().contentEquals("ivan_user"));
    }
    
    @Test       
    public void testGetClientByLogin() {  
    	final Client actualResult = clientService.getClientByLogin("ivan_user");
    	Assert.assertEquals(cl1, actualResult);
    }
    
    @Test   
    public void testGetClientByLoginWithFetching() {        
        final Client actualResult = clientService.getClientByLoginWithFetching("ivan_user");
        Assert.assertTrue(actualResult.getClientUser().getLogin().contentEquals("ivan_user"));
    }
    
    @Test
    public void testGetClientCount() {
    	Assert.assertTrue(clientService.getClientCount() == 2);
    }
    
    @Test
    public void testGetClientById() {
    	final Client actualResult = clientService.getClientById(1L);
        Assert.assertEquals(cl1, actualResult);
    }
    
    @Test
    public void testUpdateClientNameById() {
    	Assert.assertTrue(clientService.updateClientNameById(1L, "i_user") == 1);
    	Assert.assertTrue(clientService.updateClientNameById(3L, "s_user") == 0);
    }
}
