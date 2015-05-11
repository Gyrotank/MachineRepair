package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.glomozda.machinerepair.controller.AbstractRolePageController;
import com.glomozda.machinerepair.domain.client.Client;

@Controller
public class UpdateClientController extends AbstractRolePageController 
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(UpdateClientController.class.getName());
	
	private Client myClient;
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {		
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final Long clientId) {
		
		model.addAttribute("locale", locale.toString());
		
		myClient = clientSvc.getClientById(clientId);
		
		if (!model.containsAttribute("clientCurrent")) {
			model.addAttribute("clientCurrent", myClient);
		}
		
		if (!model.containsAttribute("client")) {
			model.addAttribute("client", myClient);
		}
		
		model.addAttribute("message_client_not_updated",
				sessionScopeInfoService.getSessionScopeInfo().getMessageUpdateFailed());
		sessionScopeInfoService.getSessionScopeInfo().setMessageUpdateFailed("");
		model.addAttribute("message_client_updated",
				sessionScopeInfoService.getSessionScopeInfo().getMessageUpdateSucceeded());
		sessionScopeInfoService.getSessionScopeInfo().setMessageUpdateSucceeded("");
		model.addAttribute("message_client_no_changes",
				sessionScopeInfoService.getSessionScopeInfo().getMessageNoChanges());
		sessionScopeInfoService.getSessionScopeInfo().setMessageNoChanges("");		
	}
	
	@RequestMapping(value = "/updateclient", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model, 
			@RequestParam("client-id") final Long clientId) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		Client currentClient = clientSvc.getClientById(clientId);
		if (currentClient == null) {			
			return "redirect:/adminpageclients";
		}
		
		prepareModel(locale, principal, model, clientId);		
		
		return "updateclient";
	}
	
	@RequestMapping(value = "updateclient/updateClient", method = RequestMethod.POST)
	public String updateClient(@ModelAttribute("client") @Valid final Client client,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.client", bindingResult);
			redirectAttributes.addFlashAttribute("client", client);						
			
			return "redirect:/updateclient/?client-id=" + myClient.getClientId();
		}
		
		if (client.getClientName().contentEquals(myClient.getClientName())) {
			changeSessionScopeUpdateInfo(
					"",
					"", messageSource.getMessage("popup.adminpage.clientNoChanges", null,
							locale));			
			
			return "redirect:/updateclient/?client-id=" + myClient.getClientId();
		}
		
		if (clientSvc.updateClientNameById(myClient.getClientId(), client.getClientName())
				== 1) {
			changeSessionScopeUpdateInfo(
					"",
					messageSource.getMessage("popup.adminpage.clientUpdated", null,
							locale), 
					"");	
			
			return "redirect:/updateclient/?client-id=" + myClient.getClientId();
		} else {
			changeSessionScopeUpdateInfo(
					messageSource.getMessage("popup.adminpage.clientNotUpdated", null,
							locale),
					"", 
					"");			

			return "redirect:/updateclient/?client-id=" + myClient.getClientId();
		}		
	}
}
