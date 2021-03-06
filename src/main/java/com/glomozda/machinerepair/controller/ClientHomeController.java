package com.glomozda.machinerepair.controller;

import java.security.Principal;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ClientHomeController {
	
	static Logger log = Logger.getLogger(ClientHomeController.class.getName());
	
	@RequestMapping(value = "/clienthome", method = RequestMethod.GET)
	public String activate(final Locale locale, final Model model, final Principal principal) {
		
		if (null == principal) {
			return "redirect:/index";
		}
		
		UsernamePasswordAuthenticationToken userToken 
			= (UsernamePasswordAuthenticationToken)principal;
				
		model.addAttribute("locale", locale.toString());
	    model.addAttribute("login", userToken.getName());
	    return "clienthome";
	}
}
