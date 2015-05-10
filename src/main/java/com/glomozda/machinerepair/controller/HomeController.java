package com.glomozda.machinerepair.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

@Controller
public class HomeController {
	
	static Logger log = Logger.getLogger(HomeController.class.getName());
	
	@RequestMapping("/")
	public String home() {		
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String activate(final Locale locale, final Model model, final Principal principal) {
		
		String login = "";		
		UsernamePasswordAuthenticationToken userToken = null;
		
		if (principal != null) {
			userToken = (UsernamePasswordAuthenticationToken)principal;
			login = userToken.getName();			
		}
		model.addAttribute("locale", locale.toString());
	    model.addAttribute("login", login);	    
	    return "home";
	}
}