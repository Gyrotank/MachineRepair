package com.glomozda.machinerepair;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userrole.UserRole;
import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.user.UserService;
import com.glomozda.machinerepair.service.userauthorization.UserAuthorizationService;

@Controller
@RequestMapping("/signuppage")
public class SignUpController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(LoginController.class.getName());
	
	private MessageSource messageSource;
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private ClientService clientSvc;
	
	@Autowired
	private UserAuthorizationService userAuthorizationSvc;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	private String message = "";
	private String enteredName = "";
	private String enteredLogin = "";
	  
	@RequestMapping(method = RequestMethod.GET)
	public String activate(final Locale locale, final Model model) {
		
//		log.info("Activating SignUp Page...");
		
		model.addAttribute("locale", locale.toString());
		
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
			@RequestParam("password2") String password2,
			Locale locale) {
		
		User queryRes;
		
		enteredName = name;
		enteredLogin = login;
		
		if (name.isEmpty()) {
			message = messageSource.getMessage("error.signuppage.nameEmpty", null,
					locale);
			return "redirect:/signuppage"; 
		}
		
		if (login.isEmpty()) {
			message = messageSource.getMessage("error.signuppage.loginEmpty", null,
					locale);
			return "redirect:/signuppage"; 
		}
		
		if (password1.isEmpty()) {
			message = messageSource.getMessage("error.signuppage.passwordEmpty", null,
					locale);
			return "redirect:/signuppage"; 
		}
		
		if (!password1.contentEquals(password2)) {
			message = messageSource.getMessage("error.signuppage.passwordsNotMatch", null,
					locale);
			return "redirect:/signuppage"; 
		}
		
		queryRes = userSvc.getUserByLogin(login);
		if (queryRes != null) {
			message = messageSource.getMessage("error.signuppage.loginInUse", null,
					locale);
			return "redirect:/signuppage"; 
		}
		
		String passwordHashed = encoder.encode(password1);
		User newUser = new User(login, password1, passwordHashed);
		userSvc.add(newUser);
		queryRes = userSvc.getUserByLoginAndPassword(login, password1);
		
		Client newClient = new Client();
		newClient.setClientName(name);
		clientSvc.add(newClient, queryRes.getUserId());
		
		UserAuthorization newUserAuthorization = 
				new UserAuthorization(new UserRole("ROLE_CLIENT"));
		userAuthorizationSvc.add(newUserAuthorization, queryRes.getUserId());
				
		return "redirect:/login";
	}
	
//	@RequestMapping(value = "/generate", method = RequestMethod.POST)
//	public String generateClients() {
//		User queryRes;
//		
//		for (long i = 0; i < 50000; i++) {
//			String login = "vip" + i;
//			String password1 = "pass" + i;
//			String passwordHashed = encoder.encode(password1);
//			User newUser = new User(login, password1, passwordHashed);
//			userSvc.add(newUser);
//			queryRes = userSvc.getUserByLoginAndPassword(login, password1);
//			
//			Client newClient = new Client();
//			newClient.setClientName("Very Important Client " + i);
//			clientSvc.add(newClient, queryRes.getUserId());
//			
//			UserAuthorization newUserAuthorization = new UserAuthorization("ROLE_CLIENT");
//			userAuthorizationSvc.add(newUserAuthorization, queryRes.getUserId());
//		}
//		
//		return "redirect:/signuppage";
//	}
}
