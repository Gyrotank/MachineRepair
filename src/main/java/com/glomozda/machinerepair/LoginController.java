package com.glomozda.machinerepair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.glomozda.machinerepair.repository.client.ClientService;
import com.glomozda.machinerepair.repository.user.UserService;

import org.apache.log4j.Logger;

@Controller
public class LoginController {
	
	static Logger log = Logger.getLogger(LoginController.class.getName());
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private ClientService clientSvc;
	
	private String message = "";
		  
	@RequestMapping(method = RequestMethod.GET)
	public String activate(Model model) {
		
		log.info("Activating Login Page...");
		
		model.addAttribute("message", message);
		message = "";
		return "login";
	}	
}