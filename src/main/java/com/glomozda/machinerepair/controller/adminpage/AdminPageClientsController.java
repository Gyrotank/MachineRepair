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
public class AdminPageClientsController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageClientsController.class.getName());
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private ClientService clientSvc;
	
	private User myUser;
	
	private MessageSource messageSource;
	
	private static final Long _defaultPageSize = (long) 25;
	
	private String messageClientAdded = "";
	private String messageClientNotAdded = "";
	
	private String messageClientUserId = "";
	private Long selectedClientUserId = (long) 0;
	
	private Long clientPagingFirstIndex = (long) 0;
	private Long clientPagingLastIndex = _defaultPageSize - 1;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/adminpageclients", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("client")) {
			model.addAttribute("client", new Client());
		}
		
		model.addAttribute("users", userSvc.getAll((long) 0, (long) 99));
		
		model.addAttribute("clients_short",
				clientSvc.getAllWithFetching(clientPagingFirstIndex,
						clientPagingLastIndex - clientPagingFirstIndex + 1));
		model.addAttribute("clients_count", clientSvc.getClientCount());
		model.addAttribute("clients_paging_first", clientPagingFirstIndex);
		model.addAttribute("clients_paging_last", clientPagingLastIndex);
		
		model.addAttribute("message_client_added",
				messageClientAdded);
		messageClientAdded = "";
		model.addAttribute("message_client_not_added",
				messageClientNotAdded);
		messageClientNotAdded = "";
		
		model.addAttribute("message_client_user_id", messageClientUserId);
		messageClientUserId = "";		
		model.addAttribute("selected_client_user_id", selectedClientUserId);
		selectedClientUserId = (long) 0;
		
		model.addAttribute("dialog_delete_client",
				messageSource.getMessage("label.adminpage.clients.actions.delete.dialog", null,
				locale));
		
		return "adminpageclients";
	}
	
	@RequestMapping(value = "/adminpageclients/clientpaging", method = RequestMethod.POST)
	public String clientPaging(@RequestParam("clientPageStart") final Long clientPageStart, 
			@RequestParam("clientPageEnd") final Long clientPageEnd) {
		
		long clientStart;
		long clientEnd;
		
		if (clientPageStart == null) {
			clientStart = (long) 0;
		} else {
			clientStart = clientPageStart.longValue() - 1;
		}
		
		if (clientPageEnd == null) {
			clientEnd = (long) 0;
		} else {
			clientEnd = clientPageEnd.longValue() - 1;
		}
		
		long clientCount = clientSvc.getClientCount();
		
		if (clientStart > clientEnd) {
			long temp = clientStart;
			clientStart = clientEnd;
			clientEnd = temp;
		}
		
		if (clientStart < 0)
			clientStart = 0;
		
		if (clientStart >= clientCount)
			clientStart = clientCount - 1;
		
		if (clientEnd < 0)
			clientEnd = 0;
		
		if (clientEnd >= clientCount)
			clientEnd = clientCount - 1;
		
		clientPagingFirstIndex = clientStart;
		clientPagingLastIndex = clientEnd;		
		
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
			messageClientAdded =
					messageSource.getMessage("popup.adminpage.clientAdded", null,
							locale);
		} else {
			messageClientNotAdded = 
					messageSource.getMessage("popup.adminpage.clientNotAdded", null,
							locale);
		}		
		return "redirect:/adminpageclients";
	}	
}
