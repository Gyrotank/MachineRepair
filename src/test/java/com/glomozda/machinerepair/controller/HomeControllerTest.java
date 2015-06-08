package com.glomozda.machinerepair.controller;

import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.glomozda.machinerepair.controller.ControllerTestsTemplate;

@WebAppConfiguration
public class HomeControllerTest extends ControllerTestsTemplate {
	
	@Test
	public void testHome() throws Exception {
		mockMvc.perform(get("/"))
        	.andExpect(status().isFound())
        	.andExpect(view().name("redirect:/index"));        	
	}
	
	@Test
	public void testActivateNoPrincipal() throws Exception {
		mockMvc.perform(get("/index"))
        	.andExpect(status().isOk())
        	.andExpect(view().name("home"))
        	.andExpect(forwardedUrl("/WEB-INF/pages/home.jsp"))
        	.andExpect(model().attribute("login", equalToIgnoringCase("")))
        	.andExpect(model().attribute("locale", notNullValue()));
	}
	
	@Test
	public void testActivateWithPrincipal() throws Exception {
		User user = new User("client01","", AuthorityUtils.createAuthorityList("ROLE_CLIENT"));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
        	= new UsernamePasswordAuthenticationToken(user,null);
        		
		mockMvc.perform(get("/index").principal(usernamePasswordAuthenticationToken))
        	.andExpect(status().isOk())
        	.andExpect(view().name("home"))
        	.andExpect(forwardedUrl("/WEB-INF/pages/home.jsp"))
        	.andExpect(model().attribute("login", equalToIgnoringCase("client01")))
        	.andExpect(model().attribute("locale", notNullValue()));
	}
}
