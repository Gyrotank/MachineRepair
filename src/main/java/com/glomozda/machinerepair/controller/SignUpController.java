package com.glomozda.machinerepair.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.user.UserRegistrationDTO;
import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.user.UserService;

@Controller
@RequestMapping("/signuppage")
public class SignUpController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(LoginController.class.getName());
	
	private MessageSource messageSource;
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private ClientService clientSvc;
		
	public void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	private String message = "";
	  
	@RequestMapping(method = RequestMethod.GET)
	public String activate(final Locale locale, final Model model) {
		
		model.addAttribute("locale", locale.toString());
		
		model.addAttribute("message", message);
		message = "";
		
		if (!model.containsAttribute("userRegistrationDTO")) {
			model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
		}
		
		return "signuppage";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUp(@ModelAttribute("userRegistrationDTO") @Valid 
				final UserRegistrationDTO userRegistrationDTO,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			Locale locale) {
		
		User queryRes;
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.userRegistrationDTO", 
						bindingResult);
			redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
			return "redirect:/signuppage";				
		}		
		
		if (!userRegistrationDTO.getPassword1()
				.contentEquals(userRegistrationDTO.getPassword2())) {
			bindingResult.rejectValue("password2", "error.signuppage.passwordsNotMatch", null);
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.userRegistrationDTO", 
					bindingResult);
			redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
			return "redirect:/signuppage"; 
		}
		
		queryRes = userSvc.getUserByLogin(userRegistrationDTO.getLogin());
		if (queryRes != null) {			
			bindingResult.rejectValue("login", "error.signuppage.loginInUse", null);
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.userRegistrationDTO", 
					bindingResult);
			redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);			
			return "redirect:/signuppage"; 
		}
		
		if (!userSvc.add(userRegistrationDTO.getLogin(), userRegistrationDTO.getPassword1())) {
			message = messageSource.getMessage("error.signuppage.userNotCreated", null,
					locale);
			return "redirect:/signuppage";
		}
		
		queryRes = userSvc.getUserByLoginAndPassword(userRegistrationDTO.getLogin(),
				userRegistrationDTO.getPassword1());
		Client newClient = new Client();
		newClient.setClientName(userRegistrationDTO.getName());
		if (!clientSvc.createClientAccount(newClient, queryRes.getUserId())) {
			message = messageSource.getMessage("error.signuppage.clientNotCreated", null,
					locale);
			return "redirect:/signuppage";
		}
				
		return "redirect:/login";
	}
}
