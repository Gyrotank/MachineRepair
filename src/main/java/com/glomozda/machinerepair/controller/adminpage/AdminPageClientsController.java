package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
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
import com.glomozda.machinerepair.domain.client.ClientDTO;

@Controller
public class AdminPageClientsController extends AbstractRolePageController 
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageClientsController.class.getName());
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal,	final Model model) {
		
		prepareModelAdminPage(locale, model, new ClientDTO(), clientSvc);
				
		model.addAttribute("users", userSvc.getAllIdsAndLogins());
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final long id) {				
	}
	
	@RequestMapping(value = "/adminpageclients", method = RequestMethod.GET)
	public String activate(HttpServletRequest request, 
			final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}	
		
		prepareModel(locale, principal, model);
				
		return "adminpageclients";
	}
	
	@RequestMapping(value = "/adminpageclients/clientpaging", method = RequestMethod.POST)
	public String clientPaging(@RequestParam("clientPageNumber") final Long clientPageNumber) {
		
		changeSessionScopePagingInfo(clientPageNumber * DEFAULT_PAGE_SIZE,
				DEFAULT_PAGE_SIZE * (clientPageNumber + 1) - 1,
				clientPageNumber);

		return "redirect:/adminpageclients";
	}

	@RequestMapping(value = "/addClient", method = RequestMethod.POST)
	public String addClient(@ModelAttribute("dataObject") @Valid final ClientDTO clientDTO,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.dataObject", bindingResult);
			redirectAttributes.addFlashAttribute("dataObject", clientDTO);
			return "redirect:/adminpageclients#add_new_client";				
		}
		
		addMessages(clientSvc.add(new Client(clientDTO.getClientName()), clientDTO.getUserId()),
				"popup.adminpage.clientAdded",
				"popup.adminpage.clientNotAdded",
				locale);
		
		return "redirect:/adminpageclients";
	}	
}
