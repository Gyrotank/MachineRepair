package com.glomozda.machinerepair;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.repository.client.ClientService;
import com.glomozda.machinerepair.repository.user.UserService;
import com.glomozda.machinerepair.repository.userauthorization.UserAuthorizationService;

@Controller
@RequestMapping("/signuppage")
public class SignUpController {
	
	static Logger log = Logger.getLogger(LoginController.class.getName());

	@Autowired
	private UserService userSvc;
	
	@Autowired
	private ClientService clientSvc;
	
	@Autowired
	private UserAuthorizationService userAuthorizationSvc;
	
	@Autowired
	private PasswordEncoder encoder;
	
	private String message = "";
	private String enteredName = "";
	private String enteredLogin = "";
	  
	@RequestMapping(method = RequestMethod.GET)
	public String activate(Model model) {
		
		log.info("Activating SignUp Page...");
		
		model.addAttribute("message", message);
		message = "";
		
		model.addAttribute("entered_name", enteredName);
		enteredName = "";
		
		model.addAttribute("entered_login", enteredLogin);
		enteredLogin = "";
		
		return "signuppage";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUp(@RequestParam("name") String name,
			@RequestParam("login") String login,
			@RequestParam("password1") String password1,
			@RequestParam("password2") String password2) {
		
		User queryRes;
		
		enteredName = name;
		enteredLogin = login;
		
		if (name.isEmpty()) {
			message = "Name can't be empty!";			
			return "redirect:/signuppage"; 
		}
		
		if (login.isEmpty()) {
			message = "Login can't be empty!";			
			return "redirect:/signuppage"; 
		}
		
		if (password1.isEmpty()) {
			message = "Password can't be empty!";			
			return "redirect:/signuppage"; 
		}
		
		if (!password1.contentEquals(password2)) {
			message = "Passwords don't match!";			
			return "redirect:/signuppage"; 
		}
		
		queryRes = userSvc.getUserByLogin(login);
		if (queryRes != null) {
			message = "Login is in use already!";			
			return "redirect:/signuppage"; 
		}
		
		String passwordHashed = encoder.encode(password1);
		User newUser = new User(login, password1, passwordHashed);
		userSvc.add(newUser);
		queryRes = userSvc.getUserByLoginAndPassword(login, password1);
		
		Client newClient = new Client();
		newClient.setClientName(name);
		clientSvc.add(newClient, queryRes.getUserId());
		
		UserAuthorization newUserAuthorization = new UserAuthorization("ROLE_CLIENT");
		userAuthorizationSvc.add(newUserAuthorization, queryRes.getUserId());
		
		return "redirect:/login";
	}
}
