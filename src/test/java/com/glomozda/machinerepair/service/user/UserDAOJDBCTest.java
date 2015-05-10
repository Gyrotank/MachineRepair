package com.glomozda.machinerepair.service.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.service.DAOTestsTemplate;

@ContextConfiguration(locations = "classpath:spring-context-test.xml")
@Transactional
public class UserDAOJDBCTest extends DAOTestsTemplate{
    
	String hashed_password_qwerty = BCrypt.hashpw("qwerty", BCrypt.gensalt());
	
    final User u1 = new User("ivan_user", hashed_password_qwerty);
            
    @Before
    public void prepareDB(){
    	jdbcTemplate.execute("TRUNCATE TABLE Users");
        jdbcTemplate.execute("ALTER TABLE Users ALTER COLUMN users_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE Clients");
        jdbcTemplate.execute("ALTER TABLE Clients ALTER COLUMN clients_id RESTART WITH 1");
        
        Client cl1 = new Client();
        Client cl2 = new Client();
        
        cl1.setClientName("Ivan");
    	clientService.add(cl1, (long) 1);
    	
    	cl2.setClientName("Petro");
    	clientService.add(cl2, (long) 2);
    	
    	userService.add(u1);
        userService.add("petro_user", "12345");
    }
    
    @Test
    public void testGetAll() {
    	Assert.assertTrue(userService.getAll().size() == 2);
    }
    
    @Test
    public void testGetAllWithLimits() {
    	Assert.assertTrue(userService.getAll((long) 0, (long) 100).size() == 2);
    }
    
    @Test
    public void testGetAllIdsAndLogins() {
    	Assert.assertTrue(userService.getAllIdsAndLogins().size() == 2);
    }
    
    @Test
    public void testGetUserByLoginAndPassword() {    	
    	Assert.assertTrue((BCrypt.checkpw("qwerty", 
    			userService.getUserByLoginAndPassword("ivan_user", "qwerty").getPassword())));
    }
    
    @Test
    public void testGetUserByLoginAndPasswordWithFetching() {    	
    	Assert.assertTrue((BCrypt.checkpw("qwerty", 
    			userService.getUserByLoginAndPasswordWithFetching("ivan_user", "qwerty")
    			.getPassword())));
    }
    
    @Test
    public void testGetUserByLogin() {    	
    	Assert.assertTrue((BCrypt.checkpw("qwerty", 
    			userService.getUserByLogin("ivan_user").getPassword())));
    }
    
    @Test
    public void testGetUserByLoginWithFetching() {    	
    	Assert.assertTrue((BCrypt.checkpw("qwerty", 
    			userService.getUserByLoginWithFetching("ivan_user")
    			.getPassword())));
    }
    
    @Test
    public void testGetUserById() {    	
    	Assert.assertTrue((BCrypt.checkpw("qwerty", 
    			userService.getUserById((long) 1).getPassword())));
    }
    
    @Test
    public void testGetUserByIdWithFetching() {    	
    	Assert.assertTrue((BCrypt.checkpw("qwerty", 
    			userService.getUserByIdWithFetching((long) 1)
    			.getPassword())));
    }
    
    @Test
    public void testGetUserCount() {
    	Assert.assertTrue(userService.getUserCount() == 2);
    }
    
    @Test
    public void testSetUserEnabledById() {
    	Assert.assertTrue(userService.setUserEnabledById((long) 1, (byte) 0) == 1);
    	Assert.assertTrue(userService.setUserEnabledById((long) 1, (byte) 1) == 1);
    	Assert.assertTrue(userService.setUserEnabledById((long) 3, (byte) 0) == 0);
    }
}
