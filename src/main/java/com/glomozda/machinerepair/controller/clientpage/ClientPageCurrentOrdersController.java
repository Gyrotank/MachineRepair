package com.glomozda.machinerepair.controller.clientpage;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.machine.MachineService;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.service.order.OrderService;
import com.glomozda.machinerepair.service.orderstatus.OrderStatusService;
import com.glomozda.machinerepair.service.repairtype.RepairTypeService;

@Controller
public class ClientPageCurrentOrdersController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(ClientPageController.class.getName());
	
	@Autowired
	private ClientService clientSvc;
	
	@Autowired
	private MachineService machineSvc;
	
	@Autowired
	private MachineServiceableService machineServiceableSvc;
	
	@Autowired
	private OrderService orderSvc;
	
	@Autowired
	private RepairTypeService repairTypeSvc;
	
	@Autowired
	private OrderStatusService orderStatusSvc;
	
	private static final Long _defaultPageSize = (long) 25;
	
	private Client myClient;
	
	private MessageSource messageSource;
	
	private String messagePaymentFailed = "";
	private String messagePaymentSucceeded = "";
	
	private Long currentOrdersPagingFirstIndex = (long) 0;
	private Long currentOrdersPagingLastIndex = _defaultPageSize - 1;
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/clientpagecurrentorders", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
//		log.info("Activating Client Page for " + principal.getName() + "...");
		
		myClient = clientSvc.getClientByLoginWithFetching(principal.getName());
		if (null == myClient) {
			return "redirect:/index";
		}		
		
		model.addAttribute("locale", locale.toString());
		
		model.addAttribute("clientname", myClient.getClientName());
		
		model.addAttribute("message_current_orders", 
				messageSource.getMessage("label.clientpage.noCurrentOrders", null,
						locale));
		
		model.addAttribute("current_orders_count", 
				orderSvc.getCountCurrentOrderForClientId(myClient.getClientId()));
		List<Order> myCurrentOrders =
				orderSvc.getCurrentOrdersForClientIdWithFetching(myClient.getClientId(),
						currentOrdersPagingFirstIndex, 
						currentOrdersPagingLastIndex - currentOrdersPagingFirstIndex + 1);
		model.addAttribute("my_current_orders", myCurrentOrders);
		model.addAttribute("current_orders_paging_first", currentOrdersPagingFirstIndex);
		model.addAttribute("current_orders_paging_last", currentOrdersPagingLastIndex);
		
		model.addAttribute("dialog_pay_order",
				messageSource.getMessage("label.clientpage.yourOrders.actions.pay.dialog", null,
				locale));
		
		model.addAttribute("message_payment_failed", messagePaymentFailed);
		messagePaymentFailed = "";
		model.addAttribute("message_payment_succeeded", messagePaymentSucceeded);
		messagePaymentSucceeded = "";
		
		return "clientpagecurrentorders";
	}
	
	@RequestMapping(value = "/clientpagecurrentorders/currentorderspaging",
			method = RequestMethod.POST)
	public String currentOrdersPaging(
			@RequestParam("currentOrdersPageStart") final Long currentOrdersPageStart, 
			@RequestParam("currentOrdersPageEnd") final Long currentOrdersPageEnd) {
		
		long currentOrdersStart;
		long currentOrdersEnd;
		
		if (currentOrdersPageStart == null) {
			currentOrdersStart = (long) 0;
		} else {
			currentOrdersStart = currentOrdersPageStart.longValue() - 1;
		}
		
		if (currentOrdersPageEnd == null) {
			currentOrdersEnd = (long) 0;
		} else {
			currentOrdersEnd = currentOrdersPageEnd.longValue() - 1;
		}		
		
		long currentOrdersCount = 
				orderSvc.getCountCurrentOrderForClientId(myClient.getClientId());
		
		if (currentOrdersStart > currentOrdersEnd) {
			long temp = currentOrdersStart;
			currentOrdersStart = currentOrdersEnd;
			currentOrdersEnd = temp;
		}
		
		if (currentOrdersStart < 0)
			currentOrdersStart = 0;
		
		if (currentOrdersStart >= currentOrdersCount)
			currentOrdersStart = currentOrdersCount - 1;
		
		if (currentOrdersEnd < 0)
			currentOrdersEnd = 0;
		
		if (currentOrdersEnd >= currentOrdersCount)
			currentOrdersEnd = currentOrdersCount - 1;
		
		currentOrdersPagingFirstIndex = currentOrdersStart;
		currentOrdersPagingLastIndex = currentOrdersEnd;		
		
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
