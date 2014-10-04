package com.glomozda.machinerepair;

import java.security.Principal;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	private Integer selectedClientId = 0;
	private ArrayList<Order> activeOrdersForSelectedClient = new ArrayList<Order>();
	
	@RequestMapping(value = "/managerpage", method = RequestMethod.GET)
	public String activate(final Principal principal, final Model model) {
		
		log.info("Activating Manager Page for " + principal.getName() + "...");
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("pending_orders", orderSvc.getOrdersForStatusWithFetching("pending"));
		model.addAttribute("selected_client_id", selectedClientId);
		model.addAttribute("active_orders_for_selected_client", activeOrdersForSelectedClient);
		model.addAttribute("clients", clientSvc.getAllWithFetching());
		
		return "managerpage";
	}
	
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public String confirmOrder(@RequestParam("order_id") Integer orderId) {
		orderSvc.confirmOrderById(orderId);
		return "redirect:/managerpage";
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public String cancelOrder(@RequestParam("order_id") Integer orderId) {
		orderSvc.cancelOrderById(orderId);
		return "redirect:/managerpage";
	}
	
	@RequestMapping(value = "/listactiveordersforselectedclient", method = RequestMethod.POST)
	public String listActiveOrdersForSelectedClient(@RequestParam("clientId") Integer clientId) {
		activeOrdersForSelectedClient.clear();
		
		activeOrdersForSelectedClient.addAll(
				orderSvc.getOrdersForClientIdAndStatusWithFetching(clientId, "ready"));
		activeOrdersForSelectedClient.addAll(
				orderSvc.getOrdersForClientIdAndStatusWithFetching(clientId, "started"));
		selectedClientId = clientId;
		return "redirect:/managerpage";
	}
	
	@RequestMapping(value = "/setready", method = RequestMethod.GET)
	public String setOrderReady(@RequestParam("order_id") Integer orderId) {
		orderSvc.setOrderStatusById(orderId, "ready");
		Order order = orderSvc.getOrderByIdWithFetching(orderId);
		activeOrdersForSelectedClient.remove(
				activeOrdersForSelectedClient.indexOf(order));		
		machineSvc.incrementTimesRepairedById(order.getMachine().getMachineId());
		return "redirect:/managerpage";
	}
}
