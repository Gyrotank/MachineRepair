package com.glomozda.machinerepair.service.userauthorization;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userrole.UserRole;
import com.glomozda.machinerepair.service.DAOTestsTemplate;

@ContextConfiguration(locations = "classpath:spring-context-test.xml")
@Transactional
public class UserAuthorizationDAOJDBCTest extends DAOTestsTemplate{
    
    @Before
    public void prepareDB(){
    	jdbcTemplate.execute("TRUNCATE TABLE Users");
        jdbcTemplate.execute("ALTER TABLE Users ALTER COLUMN users_id RESTART WITH 1");
        
        jdbcTemplate.execute("TRUNCATE TABLE User_Authorization");
        jdbcTemplate.execute("ALTER TABLE User_Authorization"
        		+ " ALTER COLUMN user_authorization_id RESTART WITH 1");
        
        String hashed_password_qwerty = BCrypt.hashpw("qwerty", BCrypt.gensalt());
    	String hashed_password_12345 = BCrypt.hashpw("12345", BCrypt.gensalt());
    	
        final User u1 = new User("ivan_user", hashed_password_qwerty);
        final User u2 = new User("petro_user", hashed_password_12345);
        
    	userService.add(u1);
        userService.add(u2);
        
        UserRole ur1 = new UserRole("ROLE_ADMIN");
        UserRole ur2 = new UserRole("ROLE_CLIENT");
        
        userAuthorizationService.add(new UserAuthorization(ur1), (long) 1);
        userAuthorizationService.add(new UserAuthorization(ur2), (long) 2);
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
    
    @Test
    public void testGetDistinctUsersWithFetching() {
    	userAuthorizationService.add(new UserAuthorization(new UserRole("ROLE_CLIENT")), (long) 1);
    	Assert.assertTrue(userAuthorizationService
    			.getDistinctUsersWithFetching((long) 1, (long) 100).size() == 2);
    }
    
    @Test
    public void testGetUserLoginsForRole() {
    	userAuthorizationService.add(new UserAuthorization(new UserRole("ROLE_CLIENT")), (long) 1);
    	Assert.assertTrue(userAuthorizationService
    			.getUserLoginsForRole("ROLE_CLIENT").size() == 2);
    	Assert.assertTrue(userAuthorizationService
    			.getUserLoginsForRole("ROLE_ADMIN").get(0).contentEquals("ivan_user"));
    }
    
    @Test
    public void testGetRolesForUserId() {
    	userAuthorizationService.add(new UserAuthorization(new UserRole("ROLE_CLIENT")), (long) 1);
    	Assert.assertTrue(userAuthorizationService
    			.getRolesForUserId((long) 1).size() == 2);
    	Assert.assertTrue(userAuthorizationService
    			.getRolesForUserId((long) 2).size() == 1);    	
    }
    
    @Test
    public void testGetUserForUserAuthorizationId() {
    	Assert.assertTrue(userAuthorizationService
    			.getUserForUserAuthorizationId((long) 1)
    			.getLogin().contentEquals("ivan_user"));
    }
    
    @Test
    public void testGetCountUserAuthorizationsForRole() {
    	userAuthorizationService.add(new UserAuthorization(new UserRole("ROLE_CLIENT")), (long) 1);
    	Assert.assertTrue(userAuthorizationService
    			.getCountUserAuthorizationsForRole("ROLE_CLIENT") == 2);
    	Assert.assertTrue(userAuthorizationService
    			.getCountUserAuthorizationsForRole("ROLE_ADMIN") == 1);
    }
    
    @Test
    public void testGetUserAuthorizationsByUserId() {
    	userAuthorizationService.add(new UserAuthorization(new UserRole("ROLE_CLIENT")), (long) 1);
    	Assert.assertTrue(userAuthorizationService
    			.getUserAuthorizationsByUserId((long) 1).size() == 2);
    	Assert.assertTrue(userAuthorizationService
    			.getUserAuthorizationsByUserId((long) 2).size() == 1);
    }
    
    @Test
    public void testGetAllEntities() {
    	Assert.assertTrue(userAuthorizationService.getAllEntities().size() == 2);
    }
    
    @Test
    public void testGetAllEntitiesWithLimits() {
    	Assert.assertTrue(userAuthorizationService
    			.getAllEntities((long) 0, (long) 100).size() == 2);
    }
    
    @Test
    public void testGetCountEntitites() {
    	Assert.assertTrue(userAuthorizationService.getCountEntities() == 2);
    }
    
    @Test
    public void testDeleteUserAuthorizationByUserIdAndRole() {
    	userAuthorizationService.add(new UserAuthorization(new UserRole("ROLE_CLIENT")), (long) 1);
    	Assert.assertTrue(userAuthorizationService
    			.deleteUserAuthorizationByUserIdAndRole((long) 1, "ROLE_CLIENT") == 1);
    	Assert.assertTrue(userAuthorizationService
    			.deleteUserAuthorizationByUserIdAndRole((long) 1, "ROLE_CLIENT") == 0);
    }
}
