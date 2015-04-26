package com.glomozda.machinerepair.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.user.UserService;

import org.apache.log4j.Logger;

@Controller
public class LoginController {
	
	static Logger log = Logger.getLogger(LoginController.class.getName());
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private ClientService clientSvc;
	
	private String message = "";
		  
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String activate(final Locale locale, final Model model) {
		
//		log.info("Activating Login Page...");
		
		model.addAttribute("locale", locale.toString());
		model.addAttribute("message", message);
		message = "";
		return "login";
	}	
}