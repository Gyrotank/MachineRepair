package com.glomozda.machinerepair.repository.client;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.repository.DAOTestsTemplate;
import com.glomozda.machinerepair.repository.user.UserService;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context-test.xml")
@Transactional
public class ClientDAOJDBCTest extends DAOTestsTemplate{
    
    @Autowired
    private transient ClientService clientService;
    
    @Autowired
    private transient UserService userService;
    
    final Client cl1 = new Client();
    final Client cl2 = new Client();
            
    @Before
    public void prepareDB(){
        jdbcTemplate.execute("TRUNCATE TABLE Clients");
        jdbcTemplate.execute("ALTER TABLE Clients ALTER COLUMN clients_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE Users");
        jdbcTemplate.execute("ALTER TABLE Users ALTER COLUMN users_id RESTART WITH 1");
        
        cl1.setClientName("Ivan");
    	clientService.add(cl1, (long) 1);
    	
    	cl2.setClientName("Petro");
    	clientService.add(cl2, (long) 2);
        
        userService.add(new User("ivan_user", "qwerty", "qwerty_encoded"));
        userService.add(new User("petro_user", "12345", "12345_encoded"));
    }    
   
    @Test
    public void testGetAll() {
    	Assert.assertTrue(clientService.getAll().size() == 2);
    }
    
    @Test
    public void testGetAllWithLimits() {
    	Assert.assertTrue(clientService.getAll((long) 0, (long) 100).size() == 2);
    }
    
    @Test
    public void testGetAllWithFetching() {    	
    	Assert.assertTrue(clientService.getAllWithFetching().get(0)
        		.getClientUser().getLogin().contentEquals("ivan_user"));
    }
    
    @Test
    public void testGetAllWithFetchingWithLimits() {    	
    	Assert.assertTrue(clientService.getAllWithFetching((long) 0, (long) 100).get(0)
        		.getClientUser().getLogin().contentEquals("ivan_user"));
    }
    
    @Test
    public void testGetAllClientIds() {
    	Assert.assertTrue(clientService.getAllClientIds()
    			.containsAll(Arrays.asList((long) 1, (long) 2)));
    }
    
    @Test
    public void testGetClientByUserId() {    	
        final Client actualResult = clientService.getClientByUserId((long) 1);
        Assert.assertEquals(cl1, actualResult);
    }
    
    @Test   
    public void testGetClientByUserIdWithFetching() {        
        final Client actualResult = clientService.getClientByUserIdWithFetching((long) 1);
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
}
