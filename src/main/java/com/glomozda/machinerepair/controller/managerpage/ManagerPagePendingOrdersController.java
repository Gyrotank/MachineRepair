package com.glomozda.machinerepair.controller.managerpage;

import java.security.Principal;
import java.util.List;
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
import com.glomozda.machinerepair.service.user.UserService;

@Controller
public class ManagerPagePendingOrdersController implements MessageSourceAware {
	
	@Autowired
	private OrderService orderSvc;
	
	@Autowired
	private ClientService clientSvc;
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private MachineService machineSvc;
	
	private User myUser;
	
	private MessageSource messageSource;
	
	private String messageConfirmFailed = "";
	private String messageCancelFailed = "";
	private String messageConfirmSucceeded = "";
	private String messageCancelSucceeded = "";
	
	private static final Long _defaultPageSize = (long) 25;
	
	private Long pendingOrdersPagingFirstIndex = (long) 0;
	private Long pendingOrdersPagingLastIndex = _defaultPageSize - 1;
	
	public void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/managerpagependingorders", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
//		log.info("Activating Manager Page for " + principal.getName() + "...");
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		UsernamePasswordAuthenticationToken userToken =
				(UsernamePasswordAuthenticationToken)principal;
		
		model.addAttribute("locale", locale.toString());
		
		model.addAttribute("message_confirm_failed", messageConfirmFailed);
		messageConfirmFailed = "";
		model.addAttribute("message_cancel_failed", messageCancelFailed);
		messageCancelFailed = "";
		model.addAttribute("message_confirm_succeeded", messageConfirmSucceeded);
		messageConfirmSucceeded = "";
		model.addAttribute("message_cancel_succeeded", messageCancelSucceeded);
		messageCancelSucceeded = "";
		
		List<Order> pendingOrders = orderSvc.getOrdersForStatusWithFetching("pending",
				pendingOrdersPagingFirstIndex,
				pendingOrdersPagingLastIndex - pendingOrdersPagingFirstIndex + 1);
		model.addAttribute("pending_orders", pendingOrders);
		model.addAttribute("pending_orders_count", orderSvc.getCountOrdersForStatus("pending"));
		model.addAttribute("pending_orders_paging_first", pendingOrdersPagingFirstIndex);
		model.addAttribute("pending_orders_paging_last", pendingOrdersPagingLastIndex);
		
		model.addAttribute("dialog_confirm_order",
				messageSource.getMessage("label.managerpage.pending.actions.confirm.dialog", null,
				locale));
		model.addAttribute("dialog_cancel_order",
				messageSource.getMessage("label.managerpage.pending.actions.cancel.dialog", null,
				locale));
		
		model.addAttribute("user_token_authorities",
				userToken.getAuthorities().toString());
		
		return "managerpagependingorders";
	}
	
	@RequestMapping(value = "/managerpagependingorders/pendingorderspaging",
			method = RequestMethod.POST)
	public String pendingOrdersPaging(
			@RequestParam("pendingOrdersPageStart") final Long pendingOrdersPageStart, 
			@RequestParam("pendingOrdersPageEnd") final Long pendingOrdersPageEnd) {
		
		long pendingOrdersStart;
		long pendingOrdersEnd;
		
		if (pendingOrdersPageStart == null) {
			pendingOrdersStart = (long) 0;
		} else {
			pendingOrdersStart = pendingOrdersPageStart.longValue() - 1;
		}
		
		if (pendingOrdersPageEnd == null) {
			pendingOrdersEnd = (long) 0;
		} else {
			pendingOrdersEnd = pendingOrdersPageEnd.longValue() - 1;
		}
		
		long pendingOrdersCount = orderSvc.getCountOrdersForStatus("pending");
		
		if (pendingOrdersStart > pendingOrdersEnd) {
			long temp = pendingOrdersStart;
			pendingOrdersStart = pendingOrdersEnd;
			pendingOrdersEnd = temp;
		}
		
		if (pendingOrdersStart < 0)
			pendingOrdersStart = 0;
		
		if (pendingOrdersStart >= pendingOrdersCount)
			pendingOrdersStart = pendingOrdersCount - 1;
		
		if (pendingOrdersEnd < 0)
			pendingOrdersEnd = 0;
		
		if (pendingOrdersEnd >= pendingOrdersCount)
			pendingOrdersEnd = pendingOrdersCount - 1;
		
		pendingOrdersPagingFirstIndex = pendingOrdersStart;
		pendingOrdersPagingLastIndex = pendingOrdersEnd;		
		
		return "redirect:/managerpagependingorders";
	}
	
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public String confirmOrder(@RequestParam("order_id") Long orderId, final Locale locale) {
		Order myOrder = orderSvc.getOrderById(orderId);
		if (myOrder == null) {			
			messageConfirmFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.confirm.failed.orderNotExists",
							null, locale);
			return "redirect:/managerpagependingorders";
		}
		if (!myOrder.getStatus().contentEquals("pending")) {			
			messageConfirmFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.confirm.failed.orderNotPending",
							null, locale);
			return "redirect:/managerpagependingorders";
		}
		orderSvc.confirmOrderById(orderId, myUser.getLogin());
		messageConfirmSucceeded = 
				messageSource
				.getMessage("popup.managerpage.pending.actions.confirm.succeeded",
						null, locale);
		return "redirect:/managerpagependingorders";
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public String cancelOrder(@RequestParam("order_id") Long orderId, final Locale locale) {
		Order myOrder = orderSvc.getOrderById(orderId);
		if (myOrder == null) {
			messageCancelFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.cancel.failed.orderNotExists",
							null, locale);
			return "redirect:/managerpagependingorders";			
		}
		if (!myOrder.getStatus().contentEquals("pending")) {
			messageCancelFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.cancel.failed.orderNotPending",
							null, locale);			
			return "redirect:/managerpagependingorders";
		}
		orderSvc.cancelOrderById(orderId);
		messageCancelSucceeded = 
				messageSource
				.getMessage("popup.managerpage.pending.actions.cancel.succeeded",
						null, locale);
		return "redirect:/managerpagependingorders";
	}
}
