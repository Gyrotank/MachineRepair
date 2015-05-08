package com.glomozda.machinerepair.controller.managerpage;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Locale;

import org.springframework.context.MessageSourceAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.controller.AbstractRolePageController;
import com.glomozda.machinerepair.domain.order.Order;

@Controller
public class ManagerPageActiveOrdersController extends AbstractRolePageController 
	implements MessageSourceAware {
	
	private String messageSetReadyFailed = "";
	private String messageSetReadySucceeded = "";
	
	private Long startedOrdersPagingFirstIndex = 0L;
	private Long startedOrdersPagingLastIndex = DEFAULT_PAGE_SIZE - 1;
	private Long startedOrdersPageNumber = 0L;
	
	private Long readyOrdersPagingFirstIndex = 0L;
	private Long readyOrdersPagingLastIndex = DEFAULT_PAGE_SIZE - 1;
	private Long readyOrdersPageNumber = 0L;
	
	private Long selectedClientId = 0L;
	private ArrayList<Order> startedOrdersForSelectedClient = new ArrayList<Order>();
	private ArrayList<Order> readyOrdersForSelectedClient = new ArrayList<Order>();
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {		
		
		model.addAttribute("locale", locale.toString());
		
		model.addAttribute("message_set_ready_failed", messageSetReadyFailed);
		messageSetReadyFailed = "";
		model.addAttribute("message_set_ready_succeeded", messageSetReadySucceeded);
		messageSetReadySucceeded = "";
		
		model.addAttribute("dialog_setready_order",
				messageSource.getMessage("label.managerpage.started.actions.setReady.dialog", null,
				locale));
		
		model.addAttribute("selected_client_id", selectedClientId);
		if (selectedClientId != 0) {
			startedOrdersForSelectedClient.clear();
			readyOrdersForSelectedClient.clear();
			
			startedOrdersForSelectedClient.addAll(
					orderSvc.getOrdersForClientIdAndStatusWithFetching(selectedClientId, "started", 
							startedOrdersPagingFirstIndex,
							startedOrdersPagingLastIndex - startedOrdersPagingFirstIndex + 1));
			
			readyOrdersForSelectedClient.addAll(
					orderSvc.getOrdersForClientIdAndStatusWithFetching(selectedClientId, "ready", 
							readyOrdersPagingFirstIndex,
							readyOrdersPagingLastIndex - readyOrdersPagingFirstIndex + 1));
		}
		
		model.addAttribute("started_orders_for_selected_client", startedOrdersForSelectedClient);		
		Long startedOrdersCount = orderSvc.getCountOrdersForClientIdAndStatus(selectedClientId, "started");
		model.addAttribute("started_orders_count", startedOrdersCount);
		Long startedOrdersPagesCount = startedOrdersCount / DEFAULT_PAGE_SIZE;
		if (startedOrdersCount % DEFAULT_PAGE_SIZE != 0) {
			startedOrdersPagesCount++;
		}
		model.addAttribute("started_orders_pages_count", startedOrdersPagesCount);
		model.addAttribute("started_orders_pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("started_orders_page_number", startedOrdersPageNumber);
		
		model.addAttribute("ready_orders_for_selected_client", readyOrdersForSelectedClient);
		Long readyOrdersCount = orderSvc.getCountOrdersForClientIdAndStatus(selectedClientId, "ready");
		model.addAttribute("ready_orders_count", readyOrdersCount);
		Long readyOrdersPagesCount = readyOrdersCount / DEFAULT_PAGE_SIZE;
		if (readyOrdersCount % DEFAULT_PAGE_SIZE != 0) {
			readyOrdersPagesCount++;
		}
		model.addAttribute("ready_orders_pages_count", readyOrdersPagesCount);
		model.addAttribute("ready_orders_pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("ready_orders_page_number", readyOrdersPageNumber);	
		
		model.addAttribute("clients_short",
				clientSvc.getAllWithFetching(pagingFirstIndex,
						pagingLastIndex - pagingFirstIndex + 1));
		Long clientsCount = clientSvc.getClientCount();
		model.addAttribute("clients_count", clientsCount);
		Long clientPagesCount = clientsCount / DEFAULT_PAGE_SIZE;
		if (clientsCount % DEFAULT_PAGE_SIZE != 0) {
			clientPagesCount++;
		}
		model.addAttribute("client_pages_count", clientPagesCount);
		model.addAttribute("client_pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("client_page_number", pageNumber);
		
		UsernamePasswordAuthenticationToken userToken =
				(UsernamePasswordAuthenticationToken)principal;		
		model.addAttribute("user_token_authorities",
				userToken.getAuthorities().toString());
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final Long id) {		
	}
	
	@RequestMapping(value = "/managerpageactiveorders", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}		
		
		prepareModel(locale, principal, model);
		
		return "managerpageactiveorders";
	}
	
	@RequestMapping(value = "/managerpageactiveorders/clientpaging", method = RequestMethod.POST)
	public String clientPaging(@RequestParam("clientPageNumber") final Long clientPageNumber) {
		
		pagingFirstIndex = clientPageNumber * DEFAULT_PAGE_SIZE;
		pagingLastIndex = DEFAULT_PAGE_SIZE * (clientPageNumber + 1) - 1;
		this.pageNumber = clientPageNumber;		
		
		selectedClientId = 0L;
		startedOrdersForSelectedClient.clear();
		readyOrdersForSelectedClient.clear();
		
		return "redirect:/managerpageactiveorders";
	}
	
	@RequestMapping(value = "/managerpageactiveorders/startedorderspaging",
			method = RequestMethod.POST)
	public String startedOrdersPaging(			
			@RequestParam("startedOrdersPageNumber") final Long startedOrdersPageNumber) {
		
		startedOrdersPagingFirstIndex = startedOrdersPageNumber * DEFAULT_PAGE_SIZE;
		startedOrdersPagingLastIndex = DEFAULT_PAGE_SIZE * (startedOrdersPageNumber + 1) - 1;
		this.startedOrdersPageNumber = startedOrdersPageNumber;
		
		return "redirect:/managerpageactiveorders";
	}
	
	@RequestMapping(value = "/managerpageactiveorders/readyorderspaging",
			method = RequestMethod.POST)
	public String readyOrdersPaging(			
			@RequestParam("readyOrdersPageNumber") final Long readyOrdersPageNumber) {
		
		readyOrdersPagingFirstIndex = readyOrdersPageNumber * DEFAULT_PAGE_SIZE;
		readyOrdersPagingLastIndex = DEFAULT_PAGE_SIZE * (readyOrdersPageNumber + 1) - 1;
		this.readyOrdersPageNumber = readyOrdersPageNumber;
		
		return "redirect:/managerpageactiveorders";
	}
	
	@RequestMapping(value = "/listactiveordersforselectedclient", method = RequestMethod.POST)
	public String listActiveOrdersForSelectedClient(@RequestParam("clientId") Long clientId) {
		startedOrdersForSelectedClient.clear();
		readyOrdersForSelectedClient.clear();
		
		startedOrdersForSelectedClient.addAll(
				orderSvc.getOrdersForClientIdAndStatusWithFetching(clientId, "started", 
						startedOrdersPagingFirstIndex,
						startedOrdersPagingLastIndex - startedOrdersPagingFirstIndex + 1));
		
		readyOrdersForSelectedClient.addAll(
				orderSvc.getOrdersForClientIdAndStatusWithFetching(clientId, "ready", 
						readyOrdersPagingFirstIndex,
						readyOrdersPagingLastIndex - readyOrdersPagingFirstIndex + 1));
		
		selectedClientId = clientId;
		return "redirect:/managerpageactiveorders";
	}	
	
	@RequestMapping(value = "/setready", method = RequestMethod.GET)
	public String setOrderReady(@RequestParam("order_id") Long orderId, final Locale locale) {
		Order myOrder = orderSvc.getOrderById(orderId);
		if (myOrder == null) {
			messageSetReadyFailed = 
					messageSource
					.getMessage("popup.managerpage.started.actions.setReady.failed.orderNotExists",
							null, locale);
			return "redirect:/managerpageactiveorders";
		}
		if (!myOrder.getStatus().getOrderStatusName().contentEquals("started")) {
			messageSetReadyFailed = 
					messageSource
					.getMessage(
							"popup.managerpage.started.actions.setReady.failed.orderNotStarted",
							null, locale);
			return "redirect:/managerpageactiveorders";
		}
		orderSvc.setOrderStatusById(orderId, 
			orderStatusSvc.getOrderStatusByName("ready").getOrderStatusId());
		myOrder = orderSvc.getOrderByIdWithFetching(orderId);
		startedOrdersForSelectedClient.remove(
				startedOrdersForSelectedClient.indexOf(myOrder));		
		machineSvc.incrementTimesRepairedById(myOrder.getMachine().getMachineId());
		readyOrdersForSelectedClient.add(myOrder);
		messageSetReadySucceeded = 
				messageSource
				.getMessage("popup.managerpage.started.actions.setReady.succeeded",
						null, locale);
		return "redirect:/managerpageactiveorders";
	}
}
