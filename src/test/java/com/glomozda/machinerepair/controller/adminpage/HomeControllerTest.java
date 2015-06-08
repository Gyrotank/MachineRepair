package com.glomozda.machinerepair.controller.adminpage;

import org.junit.Test;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.glomozda.machinerepair.controller.ControllerTestsTemplate;

@WebAppConfiguration
public class HomeControllerTest extends ControllerTestsTemplate {
	
	@Test
	public void activateTestNoPrincipal() throws Exception {
		mockMvc.perform(get("/index"))
        	.andExpect(status().isOk())
        	.andExpect(view().name("home"))
        	.andExpect(model().attribute("login", equalToIgnoringCase("")))
        	.andExpect(model().attribute("locale", notNullValue()));
	}
}
