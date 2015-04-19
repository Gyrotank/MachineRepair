package com.glomozda.machinerepair.service.userauthorization;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.service.DAOTestsTemplate;
import com.glomozda.machinerepair.service.user.UserService;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@ContextConfiguration(locations = "classpath:spring-context-test.xml")
@Transactional
public class UserAuthorizationDAOJDBCTest extends DAOTestsTemplate{
    
	@Autowired
    private transient UserAuthorizationService userAuthorizationService;
	
	@Autowired
    private transient UserService userService;
    
    @Before
    public void prepareDB(){
    	jdbcTemplate.execute("TRUNCATE TABLE Users");
        jdbcTemplate.execute("ALTER TABLE Users ALTER COLUMN users_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE User_Authorization");
        jdbcTemplate.execute("ALTER TABLE User_Authorization"
        		+ " ALTER COLUMN user_authorization_id RESTART WITH 1");
        
        String hashed_password_qwerty = BCrypt.hashpw("qwerty", BCrypt.gensalt());
    	String hashed_password_12345 = BCrypt.hashpw("12345", BCrypt.gensalt());
    	
        final User u1 = new User("ivan_user", "qwerty", hashed_password_qwerty);
        final User u2 = new User("petro_user", "12345", hashed_password_12345);
        
    	userService.add(u1);
        userService.add(u2);
        
        userAuthorizationService.add(new UserAuthorization("ROLE_ADMIN"), (long) 1);
        userAuthorizationService.add(new UserAuthorization("ROLE_CLIENT"), (long) 2);
    }
   
    @Test
    public void testGetAll() {
    	Assert.assertTrue(userAuthorizationService.getAll().size() == 2);
    }
    
    @Test
    public void testGetAllWithLimits() {
    	Assert.assertTrue(userAuthorizationService.getAll((long) 0, (long) 100).size() == 2);
    }
    
    @Test
    public void testGetAllWithFetching() {    	
    	Assert.assertTrue(userAuthorizationService.getAllWithFetching().size() == 2);
    	Assert.assertTrue(userAuthorizationService.getAllWithFetching().get(0)
    			.getUser().getLogin().contentEquals("ivan_user"));
    }
    
    @Test
    public void testGetAllWithFetchingWithLimits() {    	
    	Assert.assertTrue(userAuthorizationService
    			.getAllWithFetching((long) 0, (long) 100).size() == 2);
    	Assert.assertTrue(userAuthorizationService
    			.getAllWithFetching((long) 0, (long) 100).get(0)
    			.getUser().getLogin().contentEquals("ivan_user"));
    }
    
    @Test
    public void testGetAllRoles() {
    	Assert.assertTrue(userAuthorizationService.getAllRoles().size() == 2);
    }
    
    @Test
    public void testGetUserAuthorizationForUserIdAndRoleExisting() {
    	Assert.assertTrue(userAuthorizationService
    			.getUserAuthorizationForUserIdAndRole((long) 1, "ROLE_ADMIN") != null);    	
    }
    
    @Test
    public void testGetUserAuthorizationForUserIdAndRoleNonExisting() {
    	Assert.assertTrue(userAuthorizationService
    			.getUserAuthorizationForUserIdAndRole((long) 1, "ROLE_MANAGER") == null);    	
    }    
    
    @Test
    public void testGetUserAutorizationCount() {
    	Assert.assertTrue(userAuthorizationService.getUserAuthorizationCount() == 2);
    }
}
