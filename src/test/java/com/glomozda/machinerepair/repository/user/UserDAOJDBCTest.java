package com.glomozda.machinerepair.repository.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.repository.DAOTestsTemplate;
import com.glomozda.machinerepair.repository.client.ClientService;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@ContextConfiguration(locations = "classpath:spring-context-test.xml")
@Transactional
public class UserDAOJDBCTest extends DAOTestsTemplate{
    
	@Autowired
    private transient UserService userService;
	
	@Autowired
    private transient ClientService clientService;
    
	String hashed_password_qwerty = BCrypt.hashpw("qwerty", BCrypt.gensalt());
	String hashed_password_12345 = BCrypt.hashpw("12345", BCrypt.gensalt());
	
    final User u1 = new User("ivan_user", "qwerty", hashed_password_qwerty);
    final User u2 = new User("petro_user", "12345", hashed_password_12345);
            
    @Before
    public void prepareDB(){
    	jdbcTemplate.execute("TRUNCATE TABLE Users");
        jdbcTemplate.execute("ALTER TABLE Users ALTER COLUMN users_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE Clients");
        jdbcTemplate.execute("ALTER TABLE Clients ALTER COLUMN clients_id RESTART WITH 1");
        
        Client cl1 = new Client();
        Client cl2 = new Client();
        
        cl1.setClientName("Ivan");
    	clientService.add(cl1, 1);
    	
    	cl2.setClientName("Petro");
    	clientService.add(cl2, 2);
    	
    	userService.add(u1);
        userService.add(u2);
    }
   
    @Test
    public void testGetAll() {
    	Assert.assertTrue(userService.getAll().size() == 2);
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
    			userService.getUserById(1).getPassword())));
    }
    
    @Test
    public void testGetUserByIdWithFetching() {    	
    	Assert.assertTrue((BCrypt.checkpw("qwerty", 
    			userService.getUserByIdWithFetching(1)
    			.getPassword())));
    }
}
