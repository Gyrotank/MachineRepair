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
	
	private static final Long _defaultPageSize = (long) 25;
	
	private Long clientPagingFirstIndex = (long) 0;
	private Long clientPagingLastIndex = _defaultPageSize - 1;
	
	private Long startedOrdersPagingFirstIndex = (long) 0;
	private Long startedOrdersPagingLastIndex = _defaultPageSize - 1;
	
	private Long readyOrdersPagingFirstIndex = (long) 0;
	private Long readyOrdersPagingLastIndex = _defaultPageSize - 1;
	
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
		model.addAttribute("started_orders_count", 
				orderSvc.getCountOrdersForClientIdAndStatus(selectedClientId, "started"));
		model.addAttribute("started_orders_paging_first", startedOrdersPagingFirstIndex);
		model.addAttribute("started_orders_paging_last", startedOrdersPagingLastIndex);
		
		model.addAttribute("ready_orders_for_selected_client", readyOrdersForSelectedClient);		
		model.addAttribute("ready_orders_count", 
				orderSvc.getCountOrdersForClientIdAndStatus(selectedClientId, "ready"));
		model.addAttribute("ready_orders_paging_first", readyOrdersPagingFirstIndex);
		model.addAttribute("ready_orders_paging_last", readyOrdersPagingLastIndex);		
		
		model.addAttribute("clients_short",
				clientSvc.getAllWithFetching(clientPagingFirstIndex,
						clientPagingLastIndex - clientPagingFirstIndex + 1));
		model.addAttribute("clients_count", clientSvc.getClientCount());
		model.addAttribute("clients_paging_first", clientPagingFirstIndex);
		model.addAttribute("clients_paging_last", clientPagingLastIndex);
		
		model.addAttribute("user_token_authorities",
				userToken.getAuthorities().toString());
		
		return "managerpageactiveorders";
	}
	
	@RequestMapping(value = "/managerpageactiveorders/clientpaging", method = RequestMethod.POST)
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
		
		selectedClientId = (long) 0;
		startedOrdersForSelectedClient.clear();
		readyOrdersForSelectedClient.clear();
		
		return "redirect:/managerpageactiveorders";
	}
	
	@RequestMapping(value = "/managerpageactiveorders/startedorderspaging",
			method = RequestMethod.POST)
	public String startedOrdersPaging(			
			@RequestParam("startedOrdersPageStart") final Long startedOrdersPageStart, 
			@RequestParam("startedOrdersPageEnd") final Long startedOrdersPageEnd) {
		
		long startedOrdersStart;
		long startedOrdersEnd;
		
		if (startedOrdersPageStart == null) {
			startedOrdersStart = (long) 0;
		} else {
			startedOrdersStart = startedOrdersPageStart.longValue() - 1;
		}
		
		if (startedOrdersPageEnd == null) {
			startedOrdersEnd = (long) 0;
		} else {
			startedOrdersEnd = startedOrdersPageEnd.longValue() - 1;
		}
		
		if (startedOrdersStart > startedOrdersEnd) {
			long temp = startedOrdersStart;
			startedOrdersStart = startedOrdersEnd;
			startedOrdersEnd = temp;
		}
		
		if (startedOrdersStart < 0)
			startedOrdersStart = 0;
		
		if (startedOrdersStart >= startedOrdersForSelectedClient.size())
			startedOrdersStart = startedOrdersForSelectedClient.size() - 1;
		
		if (startedOrdersEnd < 0)
			startedOrdersEnd = 0;
		
		if (startedOrdersEnd >= startedOrdersForSelectedClient.size())
			startedOrdersEnd = startedOrdersForSelectedClient.size() - 1;
		
		startedOrdersPagingFirstIndex = startedOrdersStart;
		startedOrdersPagingLastIndex = startedOrdersEnd;		
		
		return "redirect:/managerpageactiveorders";
	}
	
	@RequestMapping(value = "/managerpageactiveorders/readyorderspaging",
			method = RequestMethod.POST)
	public String readyOrdersPaging(			
			@RequestParam("readyOrdersPageStart") final Long readyOrdersPageStart, 
			@RequestParam("readyOrdersPageEnd") final Long readyOrdersPageEnd) {
		
		long readyOrdersStart;
		long readyOrdersEnd;
		
		if (readyOrdersPageStart == null) {
			readyOrdersStart = (long) 0;
		} else {
			readyOrdersStart = readyOrdersPageStart.longValue() - 1;
		}
		
		if (readyOrdersPageEnd == null) {
			readyOrdersEnd = (long) 0;
		} else {
			readyOrdersEnd = readyOrdersPageEnd.longValue() - 1;
		}
		
		if (readyOrdersStart > readyOrdersEnd) {
			long temp = readyOrdersStart;
			readyOrdersStart = readyOrdersEnd;
			readyOrdersEnd = temp;
		}
		
		if (readyOrdersStart < 0)
			readyOrdersStart = 0;
		
		if (readyOrdersStart >= readyOrdersForSelectedClient.size())
			readyOrdersStart = readyOrdersForSelectedClient.size() - 1;
		
		if (readyOrdersEnd < 0)
			readyOrdersEnd = 0;
		
		if (readyOrdersEnd >= readyOrdersForSelectedClient.size())
			readyOrdersEnd = readyOrdersForSelectedClient.size() - 1;
		
		readyOrdersPagingFirstIndex = readyOrdersStart;
		readyOrdersPagingLastIndex = readyOrdersEnd;		
		
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
