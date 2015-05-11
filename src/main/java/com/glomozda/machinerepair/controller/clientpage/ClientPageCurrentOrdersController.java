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
public class ClientPageCurrentOrdersController extends AbstractRolePageController 
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(ClientPageController.class.getName());
	
	private Client myClient;
	
	private String messagePaymentFailed = "";
	private String messagePaymentSucceeded = "";
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal,
			final Model model) {
		
		model.addAttribute("locale", locale.toString());
		
		model.addAttribute("clientname", myClient.getClientName());
		
		model.addAttribute("message_current_orders", 
				messageSource.getMessage("label.clientpage.noCurrentOrders", null,
						locale));
		
		Long currentOrdersCount = orderSvc.getCountCurrentOrderForClientId(myClient.getClientId());
		model.addAttribute("current_orders_count", currentOrdersCount);
		List<Order> myCurrentOrders;
		
		Long pagesCount = currentOrdersCount / DEFAULT_PAGE_SIZE;
		if (currentOrdersCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		Long pageNumber = sessionScopeInfoService.getSessionScopeInfo().getPageNumber();
		if (pageNumber >= pagesCount) {
			model.addAttribute("page_number", 0L);
			myCurrentOrders =
					orderSvc.getCurrentOrdersForClientIdWithFetching(
							myClient.getClientId(), 0L, DEFAULT_PAGE_SIZE);			
		} else {
			model.addAttribute("page_number", pageNumber);
			myCurrentOrders =
					orderSvc.getCurrentOrdersForClientIdWithFetching(myClient.getClientId(),
							sessionScopeInfoService.getSessionScopeInfo().getPagingFirstIndex(),
							sessionScopeInfoService.getSessionScopeInfo().getPagingLastIndex() 
								- sessionScopeInfoService.getSessionScopeInfo()
									.getPagingFirstIndex() + 1);
		}
		
		model.addAttribute("my_current_orders", myCurrentOrders);
		
		model.addAttribute("dialog_pay_order",
				messageSource.getMessage("label.clientpage.yourOrders.actions.pay.dialog", null,
				locale));
		
		model.addAttribute("message_payment_failed", messagePaymentFailed);
		messagePaymentFailed = "";
		model.addAttribute("message_payment_succeeded", messagePaymentSucceeded);
		messagePaymentSucceeded = "";
		
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final Long id) {		
	}
	
	@RequestMapping(value = "/clientpagecurrentorders", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myClient = clientSvc.getClientByLoginWithFetching(principal.getName());
		if (null == myClient) {
			return "redirect:/index";
		}		
		
		prepareModel(locale, principal, model);
		
		return "clientpagecurrentorders";
	}
	
	@RequestMapping(value = "/clientpagecurrentorders/currentorderspaging",
			method = RequestMethod.POST)
	public String currentOrdersPaging(
			@RequestParam("currentOrdersPageNumber") final Long currentOrdersPageNumber) {
		
		changeSessionScopePagingInfo(currentOrdersPageNumber * DEFAULT_PAGE_SIZE,
				DEFAULT_PAGE_SIZE * (currentOrdersPageNumber + 1) - 1,
				currentOrdersPageNumber);
		
		return "redirect:/clientpagecurrentorders";
	}
	
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public String payForOrder(@RequestParam("order_id") Long orderId, final Locale locale) {
		Order myOrder = orderSvc.getOrderById(orderId);
		if (myOrder == null) {			
			messagePaymentFailed = 
					messageSource
					.getMessage("popup.clientpage.yourOrders.actions.pay.orderNotExists",
							null, locale);
			return "redirect:/clientpagecurrentorders";
		}
		if (!myOrder.getStatus().getOrderStatusName().contentEquals("ready")) {			
			messagePaymentFailed = 
					messageSource
					.getMessage("popup.clientpage.yourOrders.actions.pay.orderNotReady",
							null, locale);
			return "redirect:/clientpagecurrentorders";
		}
		
		orderSvc.setOrderStatusById(orderId,
				orderStatusSvc.getOrderStatusByName("finished").getOrderStatusId());
		messagePaymentSucceeded = 
				messageSource
				.getMessage("popup.clientpage.yourOrders.actions.pay.success",
						null, locale);
		return "redirect:/clientpagecurrentorders";
	}

}
