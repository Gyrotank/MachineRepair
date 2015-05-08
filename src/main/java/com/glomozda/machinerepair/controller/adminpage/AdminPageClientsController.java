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
public class AdminPageClientsController extends AbstractRolePageController 
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageClientsController.class.getName());	
	
	private String messageClientUserId = "";
	private Long selectedClientUserId = 0L;
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("client")) {
			model.addAttribute("client", new Client());
		}
		
		model.addAttribute("users", userSvc.getAll((long) 0, (long) 99));
		
		model.addAttribute("clients_short",
				clientSvc.getAllWithFetching(pagingFirstIndex,
						pagingLastIndex - pagingFirstIndex + 1));

		Long clientsCount = clientSvc.getClientCount();
		model.addAttribute("clients_count", clientsCount);
		
		Long pagesCount = clientsCount / DEFAULT_PAGE_SIZE;
		if (clientsCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("page_number", pageNumber);
				
		model.addAttribute("message_client_added",
				messageAdded);
		messageAdded = "";
		model.addAttribute("message_client_not_added",
				messageNotAdded);
		messageNotAdded = "";
		
		model.addAttribute("message_client_user_id", messageClientUserId);
		messageClientUserId = "";		
		model.addAttribute("selected_client_user_id", selectedClientUserId);
		selectedClientUserId = (long) 0;
		
		model.addAttribute("dialog_delete_client",
				messageSource.getMessage("label.adminpage.clients.actions.delete.dialog", null,
				locale));
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final Long id) {				
	}
	
	@RequestMapping(value = "/adminpageclients", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		prepareModel(locale, principal, model);
		
		return "adminpageclients";
	}
	
	@RequestMapping(value = "/adminpageclients/clientpaging", method = RequestMethod.POST)
	public String clientPaging(@RequestParam("clientPageNumber") final Long clientPageNumber) {
		
		pagingFirstIndex = clientPageNumber * DEFAULT_PAGE_SIZE;
		pagingLastIndex = DEFAULT_PAGE_SIZE * (clientPageNumber + 1) - 1;
		pageNumber = clientPageNumber;
		
		return "redirect:/adminpageclients";
	}

	@RequestMapping(value = "/addClient", method = RequestMethod.POST)
	public String addClient(@ModelAttribute("client") @Valid final Client client,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			@RequestParam("userId") final Long userId,
			final Locale locale) {
		
		if (userId == 0 || bindingResult.hasErrors()) {
			if (userId == 0) {
				messageClientUserId = 
						messageSource.getMessage("error.adminpage.userId", null,
								locale);			
			}

			if (bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.client", bindingResult);
				redirectAttributes.addFlashAttribute("client", client);				
			}
			
			selectedClientUserId = userId;
			return "redirect:/adminpageclients#add_new_client";
		}
		
		if (clientSvc.add(client, userId)) {
			messageAdded =
					messageSource.getMessage("popup.adminpage.clientAdded", null,
							locale);
		} else {
			messageNotAdded = 
					messageSource.getMessage("popup.adminpage.clientNotAdded", null,
							locale);
		}		
		return "redirect:/adminpageclients";
	}	
}
