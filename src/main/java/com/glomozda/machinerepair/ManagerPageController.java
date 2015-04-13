package com.glomozda.machinerepair;

import java.security.Principal;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.repository.client.ClientService;
import com.glomozda.machinerepair.repository.machine.MachineService;
import com.glomozda.machinerepair.repository.order.OrderService;
import com.glomozda.machinerepair.repository.user.UserService;

import org.apache.log4j.Logger;

@Controller
public class ManagerPageController {
	
	static Logger log = Logger.getLogger(ManagerPageController.class.getName());
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private OrderService orderSvc;
	
	@Autowired
	private ClientService clientSvc;
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private MachineService machineSvc;
	
	private User myUser;
	
	private static final Long _defaultPageSize = (long) 25;
	
	private Long clientPagingFirstIndex = (long) 0;
	private Long clientPagingLastIndex = _defaultPageSize - 1;
	
	private Long selectedClientId = (long) 0;
	private ArrayList<Order> activeOrdersForSelectedClient = new ArrayList<Order>();
	
	@RequestMapping(value = "/managerpage", method = RequestMethod.GET)
	public String activate(final Principal principal, final Model model) {
		
		log.info("Activating Manager Page for " + principal.getName() + "...");
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		UsernamePasswordAuthenticationToken userToken =
				(UsernamePasswordAuthenticationToken)principal;
		
		model.addAttribute("pending_orders", orderSvc.getOrdersForStatusWithFetching("pending"));
		
		model.addAttribute("selected_client_id", selectedClientId);
		
		model.addAttribute("active_orders_for_selected_client", activeOrdersForSelectedClient);
		
//		model.addAttribute("clients", clientSvc.getAllWithFetching());
		model.addAttribute("clients_short",
				clientSvc.getAllWithFetching(clientPagingFirstIndex,
						clientPagingLastIndex - clientPagingFirstIndex + 1));
		model.addAttribute("clients_count", clientSvc.getClientCount());
		model.addAttribute("clients_paging_first", clientPagingFirstIndex);
		model.addAttribute("clients_paging_last", clientPagingLastIndex);
		
		model.addAttribute("user_token_authorities",
				userToken.getAuthorities().toString());
		
		return "managerpage";
	}
	
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public String confirmOrder(@RequestParam("order_id") Long orderId) {
		Order myOrder = orderSvc.getOrderById(orderId);
		if (myOrder == null) {
			return "redirect:/managerpage";
		}
		if (!myOrder.getStatus().contentEquals("pending")) {
			return "redirect:/managerpage";
		}
		orderSvc.confirmOrderById(orderId);
		return "redirect:/managerpage";
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public String cancelOrder(@RequestParam("order_id") Long orderId) {
		Order myOrder = orderSvc.getOrderById(orderId);
		if (myOrder == null) {
			return "redirect:/managerpage";
		}
		if (!myOrder.getStatus().contentEquals("pending")) {
			return "redirect:/managerpage";
		}
		orderSvc.cancelOrderById(orderId);
		return "redirect:/managerpage";
	}
	
	@RequestMapping(value = "/managerpage/clientpaging", method = RequestMethod.POST)
	public String clientPaging(@RequestParam("clientPageStart") final Long clientPageStart, 
			@RequestParam("clientPageEnd") final Long clientPageEnd) {
		
		long clientStart = clientPageStart.longValue() - 1;
		long clientEnd = clientPageEnd.longValue() - 1;
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
		
		return "redirect:/managerpage";
	}
	
	@RequestMapping(value = "/listactiveordersforselectedclient", method = RequestMethod.POST)
	public String listActiveOrdersForSelectedClient(@RequestParam("clientId") Long clientId) {
		activeOrdersForSelectedClient.clear();
		
		activeOrdersForSelectedClient.addAll(
				orderSvc.getOrdersForClientIdAndStatusWithFetching(clientId, "ready"));
		activeOrdersForSelectedClient.addAll(
				orderSvc.getOrdersForClientIdAndStatusWithFetching(clientId, "started"));
		selectedClientId = clientId;
		return "redirect:/managerpage";
	}
	
	@RequestMapping(value = "/setready", method = RequestMethod.GET)
	public String setOrderReady(@RequestParam("order_id") Long orderId) {
		Order myOrder = orderSvc.getOrderById(orderId);
		if (myOrder == null) {
			return "redirect:/managerpage";
		}
		if (!myOrder.getStatus().contentEquals("started")) {
			return "redirect:/managerpage";
		}
		orderSvc.setOrderStatusById(orderId, "ready");
		myOrder = orderSvc.getOrderByIdWithFetching(orderId);
		activeOrdersForSelectedClient.remove(
				activeOrdersForSelectedClient.indexOf(myOrder));		
		machineSvc.incrementTimesRepairedById(myOrder.getMachine().getMachineId());
		return "redirect:/managerpage";
	}
}
