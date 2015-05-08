package com.glomozda.machinerepair.controller.clientpage;

import java.security.Principal;
import java.util.Locale;

import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.glomozda.machinerepair.controller.AbstractRolePageController;
import com.glomozda.machinerepair.domain.client.Client;

import org.apache.log4j.Logger;

@Controller
public class ClientPageController extends AbstractRolePageController 
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(ClientPageController.class.getName());
	
	private Client myClient;
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {	
		
		model.addAttribute("locale", locale.toString());
		
		model.addAttribute("clientname", myClient.getClientName());
	}
	
	@RequestMapping(value = "/clientpage", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myClient = clientSvc.getClientByLoginWithFetching(principal.getName());
		if (null == myClient) {
			return "redirect:/index";
		}		
		
		prepareModel(locale, principal, model);
		
		return "clientpage";
	}



	@Override
	protected void prepareModel(Locale locale, final Principal principal, 
			final Model model, final Long id) {		
	}
}
