package com.glomozda.machinerepair.controller.managerpage;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.machine.MachineService;
import com.glomozda.machinerepair.service.order.OrderService;
import com.glomozda.machinerepair.service.orderstatus.OrderStatusService;
import com.glomozda.machinerepair.service.user.UserService;

@Controller
public class ManagerPageActiveOrdersController implements MessageSourceAware {
	
	@Autowired
	private OrderService orderSvc;
	
	@Autowired
	private OrderStatusService orderStatusSvc;
	
	@Autowired
	private ClientService clientSvc;
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private MachineService machineSvc;
	
	private User myUser;
	
	private MessageSource messageSource;
	
	private String messageSetReadyFailed = "";
	private String messageSetReadySucceeded = "";
	
	private static final Long _defaultPageSize = (long) 10;
	
	private Long clientPagingFirstIndex = (long) 0;
	private Long clientPagingLastIndex = _defaultPageSize - 1;
	private Long clientPageNumber = (long) 0;
	
	private Long startedOrdersPagingFirstIndex = (long) 0;
	private Long startedOrdersPagingLastIndex = _defaultPageSize - 1;
	private Long startedOrdersPageNumber = (long) 0;
	
	private Long readyOrdersPagingFirstIndex = (long) 0;
	private Long readyOrdersPagingLastIndex = _defaultPageSize - 1;
	private Long readyOrdersPageNumber = (long) 0;
	
	private Long selectedClientId = (long) 0;
	private ArrayList<Order> startedOrdersForSelectedClient = new ArrayList<Order>();
	private ArrayList<Order> readyOrdersForSelectedClient = new ArrayList<Order>();
	
	@Override
	public void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/managerpageactiveorders", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		UsernamePasswordAuthenticationToken userToken =
				(UsernamePasswordAuthenticationToken)principal;
		
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
		Long startedOrdersPagesCount = startedOrdersCount / _defaultPageSize;
		if (startedOrdersCount % _defaultPageSize != 0) {
			startedOrdersPagesCount++;
		}
		model.addAttribute("started_orders_pages_count", startedOrdersPagesCount);
		model.addAttribute("started_orders_pages_size", _defaultPageSize);
		model.addAttribute("started_orders_page_number", startedOrdersPageNumber);
		
		model.addAttribute("ready_orders_for_selected_client", readyOrdersForSelectedClient);
		Long readyOrdersCount = orderSvc.getCountOrdersForClientIdAndStatus(selectedClientId, "ready");
		model.addAttribute("ready_orders_count", readyOrdersCount);
		Long readyOrdersPagesCount = readyOrdersCount / _defaultPageSize;
		if (readyOrdersCount % _defaultPageSize != 0) {
			readyOrdersPagesCount++;
		}
		model.addAttribute("ready_orders_pages_count", readyOrdersPagesCount);
		model.addAttribute("ready_orders_pages_size", _defaultPageSize);
		model.addAttribute("ready_orders_page_number", readyOrdersPageNumber);	
		
		model.addAttribute("clients_short",
				clientSvc.getAllWithFetching(clientPagingFirstIndex,
						clientPagingLastIndex - clientPagingFirstIndex + 1));
		Long clientsCount = clientSvc.getClientCount();
		model.addAttribute("clients_count", clientsCount);
		Long clientPagesCount = clientsCount / _defaultPageSize;
		if (clientsCount % _defaultPageSize != 0) {
			clientPagesCount++;
		}
		model.addAttribute("client_pages_count", clientPagesCount);
		model.addAttribute("client_pages_size", _defaultPageSize);
		model.addAttribute("client_page_number", clientPageNumber);
		
		model.addAttribute("user_token_authorities",
				userToken.getAuthorities().toString());
		
		return "managerpageactiveorders";
	}
	
	@RequestMapping(value = "/managerpageactiveorders/clientpaging", method = RequestMethod.POST)
	public String clientPaging(@RequestParam("clientPageNumber") final Long clientPageNumber) {
		
		clientPagingFirstIndex = clientPageNumber * _defaultPageSize;
		clientPagingLastIndex = _defaultPageSize * (clientPageNumber + 1) - 1;
		this.clientPageNumber = clientPageNumber;		
		
		selectedClientId = (long) 0;
		startedOrdersForSelectedClient.clear();
		readyOrdersForSelectedClient.clear();
		
		return "redirect:/managerpageactiveorders";
	}
	
	@RequestMapping(value = "/managerpageactiveorders/startedorderspaging",
			method = RequestMethod.POST)
	public String startedOrdersPaging(			
			@RequestParam("startedOrdersPageNumber") final Long startedOrdersPageNumber) {
		
		startedOrdersPagingFirstIndex = startedOrdersPageNumber * _defaultPageSize;
		startedOrdersPagingLastIndex = _defaultPageSize * (startedOrdersPageNumber + 1) - 1;
		this.startedOrdersPageNumber = startedOrdersPageNumber;
		
		return "redirect:/managerpageactiveorders";
	}
	
	@RequestMapping(value = "/managerpageactiveorders/readyorderspaging",
			method = RequestMethod.POST)
	public String readyOrdersPaging(			
			@RequestParam("readyOrdersPageNumber") final Long readyOrdersPageNumber) {
		
		readyOrdersPagingFirstIndex = readyOrdersPageNumber * _defaultPageSize;
		readyOrdersPagingLastIndex = _defaultPageSize * (readyOrdersPageNumber + 1) - 1;
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
