package com.glomozda.machinerepair;

import java.security.Principal;

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
	public String activate(Model model, Principal principal) {
		
		String login = "";
		String userTokenAuthorities = "";
		UsernamePasswordAuthenticationToken userToken = null;
		
		if (principal != null) {
			userToken = (UsernamePasswordAuthenticationToken)principal;
			login = userToken.getName();
			userTokenAuthorities = userToken.getAuthorities().toString();
			log.info("Activating Home Page for " + login + "...");
		} else {
			log.info("Activating Home Page (no principal)...");
		}
	    model.addAttribute("login", login);
	    model.addAttribute("user_token_authorities", userTokenAuthorities);
	    return "home";
	}
}