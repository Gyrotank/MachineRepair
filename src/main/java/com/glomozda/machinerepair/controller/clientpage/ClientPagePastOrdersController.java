package com.glomozda.machinerepair.controller.clientpage;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.controller.AbstractRolePageController;
import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.order.Order;

@Controller
public class ClientPagePastOrdersController extends AbstractRolePageController
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(ClientPageController.class.getName());
	
	private Client myClient;

	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
		
		model.addAttribute("locale", locale.toString());
		
		model.addAttribute("clientname", myClient.getClientName());
		
		model.addAttribute("message_past_orders",
				messageSource.getMessage("label.clientpage.noPastOrders", null,
						locale));
		
		Long pastOrdersCount = orderSvc.getCountOrdersForClientIdAndStatus(myClient.getClientId(), "finished");
		model.addAttribute("past_orders_count", pastOrdersCount);
		List<Order> myPastOrders;
		
		Long pagesCount = pastOrdersCount / DEFAULT_PAGE_SIZE;
		if (pastOrdersCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		Long pageNumber = sessionScopeInfoService.getSessionScopeInfo().getPageNumber();
		if (pageNumber >= pagesCount) {
			model.addAttribute("page_number", 0L);
			myPastOrders =
					orderSvc.getOrdersForClientIdAndStatusWithFetching(myClient.getClientId(),
							"finished", 
							0L, DEFAULT_PAGE_SIZE);						
		} else {
			model.addAttribute("page_number", pageNumber);
			myPastOrders =
					orderSvc.getOrdersForClientIdAndStatusWithFetching(myClient.getClientId(),
							"finished", 
							sessionScopeInfoService.getSessionScopeInfo().getPagingFirstIndex(), 
							sessionScopeInfoService.getSessionScopeInfo().getPagingLastIndex() 
							- sessionScopeInfoService.getSessionScopeInfo()
								.getPagingFirstIndex() + 1);
		}
		model.addAttribute("my_past_orders", myPastOrders);
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final long id) {		
	}
	
	@RequestMapping(value = "/clientpagepastorders", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myClient = clientSvc.getClientByLoginWithFetching(principal.getName());
		if (null == myClient) {
			return "redirect:/index";
		}		
		
		prepareModel(locale, principal, model);

		return "clientpagepastorders";
	}
	
	@RequestMapping(value = "/clientpagepastorders/pastorderspaging",
			method = RequestMethod.POST)
	public String pastOrdersPaging(
			@RequestParam("pastOrdersPageNumber") final Long pastOrdersPageNumber) {
		
		changeSessionScopePagingInfo(pastOrdersPageNumber * DEFAULT_PAGE_SIZE,
				DEFAULT_PAGE_SIZE * (pastOrdersPageNumber + 1) - 1,
				pastOrdersPageNumber);		
		
		return "redirect:/clientpagepastorders";
	}	
}
