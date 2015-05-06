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
	
	private static final Long _defaultPageSize = (long) 10;
	
	private Client myClient;
	
	private MessageSource messageSource;
	
	private String messagePaymentFailed = "";
	private String messagePaymentSucceeded = "";
	
	private Long currentOrdersPagingFirstIndex = (long) 0;
	private Long currentOrdersPagingLastIndex = _defaultPageSize - 1;
	private Long pageNumber = (long) 0;
	
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
		
		Long currentOrdersCount = orderSvc.getCountCurrentOrderForClientId(myClient.getClientId());
		model.addAttribute("current_orders_count", currentOrdersCount);
		List<Order> myCurrentOrders =
				orderSvc.getCurrentOrdersForClientIdWithFetching(myClient.getClientId(),
						currentOrdersPagingFirstIndex, 
						currentOrdersPagingLastIndex - currentOrdersPagingFirstIndex + 1);
		model.addAttribute("my_current_orders", myCurrentOrders);
		Long pagesCount = currentOrdersCount / _defaultPageSize;
		if (currentOrdersCount % _defaultPageSize != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", _defaultPageSize);
		model.addAttribute("page_number", pageNumber);
		
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
			@RequestParam("currentOrdersPageNumber") final Long currentOrdersPageNumber) {
		
		currentOrdersPagingFirstIndex = currentOrdersPageNumber * _defaultPageSize;
		currentOrdersPagingLastIndex = _defaultPageSize * (currentOrdersPageNumber + 1) - 1;
		pageNumber = currentOrdersPageNumber;
		
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
