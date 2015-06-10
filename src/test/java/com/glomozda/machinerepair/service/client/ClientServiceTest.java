package com.glomozda.machinerepair.service.client;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.service.ServiceTestsTemplate;

@Transactional
public class ClientServiceTest extends ServiceTestsTemplate{
    
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
    public void testGetIdsAndNamesLikeName() {
    	Assert.assertTrue(clientService.getIdsAndNamesLikeName("%").size() == 2);
    	Assert.assertTrue(clientService.getIdsAndNamesLikeName("van").size() == 1);
    	Assert.assertTrue(clientService.getIdsAndNamesLikeName("bbb").size() == 0);
    }
    
    @Test
    public void testGetIdsAndNamesLikeNameWithLimits() {
    	Assert.assertTrue(clientService
    			.getIdsAndNamesLikeName("%", 0L, 100L).size() == 2);
    	Assert.assertTrue(clientService
    			.getIdsAndNamesLikeName("van", 0L, 100L).size() == 1);
    	Assert.assertTrue(clientService
    			.getIdsAndNamesLikeName("bbb", 0L, 100L).size() == 0);
    }
    
    @Test
    public void testGetAllEntities() {
    	Assert.assertTrue(clientService.getAllEntities().size() == 2);
    }
    
    @Test
    public void testGetAllEntitiesWithLimits() {
    	Assert.assertTrue(clientService
    			.getAllEntities((long) 0, (long) 100).size() == 2);
    }
    
    @Test
    public void testGetCountEntitites() {
    	Assert.assertTrue(clientService.getCountEntities() == 2);
    }
    
    @Test
    public void testUpdateClientNameById() {
    	Assert.assertTrue(clientService.updateClientNameById(1L, "i_user") == 1);
    	Assert.assertTrue(clientService.updateClientNameById(3L, "s_user") == 0);
    }
    
    @Test
    public void testGetClientCountLikeName() {
    	Assert.assertTrue(clientService.getClientCountLikeName("va") == 1);
    	Assert.assertTrue(clientService.getClientCountLikeName("Pet") == 1);
    	Assert.assertTrue(clientService.getClientCountLikeName("oo") == 0);
    }
    
    @Test
    public void testGetClientsLikeName() {
    	Assert.assertTrue(clientService.getClientsLikeName("va").size() == 1);
    	Assert.assertTrue(clientService.getClientsLikeName("Pet").size() == 1);
    	Assert.assertTrue(clientService.getClientsLikeName("oo").isEmpty());
    }
    
    @Test
    public void testGetClientsLikeNameWithLimits() {
    	Assert.assertTrue(clientService
    			.getClientsLikeName("va", 0L, 100L).size() == 1);
    	Assert.assertTrue(clientService
    			.getClientsLikeName("Pet", 0L, 100L).size() == 1);
    	Assert.assertTrue(clientService
    			.getClientsLikeName("oo", 0L, 100L).isEmpty());
    }
    
    @Test
    public void testAddExisting() {
    	Client cl3 = new Client();
    	cl3.setClientName("Petro");
    	Assert.assertFalse(clientService.add(cl3, 2L));
    }
}
