package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.user.UserService;

@Controller
public class UpdateClientController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(UpdateClientController.class.getName());
	
	@Autowired
	private ClientService clientSvc;
	
	@Autowired
	private UserService userSvc;
	
	private User myUser;
	
	private Client myClient;
	
	private MessageSource messageSource;
	
	private String messageClientUpdateFailed = "";
	private String messageClientUpdateSucceeded = "";
	private String messageClientNoChanges = "";
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/updateclient", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model, 
			@RequestParam("client-id") final Long clientId) {
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("locale", locale.toString());		
		
		Client currentClient = clientSvc.getClientById(clientId);
		
		if (currentClient == null) {			
			return "redirect:/adminpageclients";
		}
		
		myClient = clientSvc.getClientById(clientId);
		
		if (!model.containsAttribute("clientCurrent")) {
			model.addAttribute("clientCurrent", myClient);
		}
		
		if (!model.containsAttribute("client")) {
			model.addAttribute("client", myClient);
		}
		
		model.addAttribute("message_client_not_updated",
				messageClientUpdateFailed);
		messageClientUpdateFailed = "";
		model.addAttribute("message_client_updated",
				messageClientUpdateSucceeded);
		messageClientUpdateSucceeded = "";
		model.addAttribute("message_client_no_changes",
				messageClientNoChanges);
		messageClientNoChanges = "";
		
		return "updateclient";
	}
	
	@RequestMapping(value = "updateclient/updateClient", method = RequestMethod.POST)
	public String updateClient(@ModelAttribute("client") @Valid final Client client,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
//		log.info("Updating...");
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.client", bindingResult);
			redirectAttributes.addFlashAttribute("client", client);						
			
//			log.info("BindingResult error...");
			return "redirect:/updateclient/?client-id=" + myClient.getClientId();
		}
		
		if (client.getClientName().contentEquals(myClient.getClientName())) {
//			log.info("Client has same name");
			messageClientNoChanges = 
					messageSource.getMessage("popup.adminpage.clientNoChanges", null,
							locale);
			return "redirect:/updateclient/?client-id=" + myClient.getClientId();
		}
		
		if (clientSvc.updateClientNameById(myClient.getClientId(), client.getClientName())
				== 1) {
			messageClientUpdateSucceeded =
					messageSource.getMessage("popup.adminpage.clientUpdated", null,
							locale);
//			log.info("Updated");
			return "redirect:/updateclient/?client-id=" + myClient.getClientId();
		} else {
			messageClientUpdateFailed = 
					messageSource.getMessage("popup.adminpage.clientNotUpdated", null,
							locale);
//			log.info("Not Updated");
			return "redirect:/updateclient/?client-id=" + myClient.getClientId();
		}		
	}
}
