package com.glomozda.machinerepair.controller.managerpage;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.controller.AbstractRolePageController;
import com.glomozda.machinerepair.domain.order.Order;

@Controller
public class ManagerPagePendingOrdersController extends AbstractRolePageController
	implements MessageSourceAware {
	
	private String messageConfirmFailed = "";
	private String messageCancelFailed = "";
	private String messageConfirmSucceeded = "";
	private String messageCancelSucceeded = "";
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal,
			final Model model) {
		
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
				pagingFirstIndex,
				pagingLastIndex - pagingFirstIndex + 1);
		model.addAttribute("pending_orders", pendingOrders);
		Long pendingOrdersCount = orderSvc.getCountOrdersForStatus("pending");
		model.addAttribute("pending_orders_count", pendingOrdersCount);
		Long pagesCount = pendingOrdersCount / DEFAULT_PAGE_SIZE;
		if (pendingOrdersCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("page_number", pageNumber);
		
		model.addAttribute("dialog_confirm_order",
				messageSource.getMessage("label.managerpage.pending.actions.confirm.dialog", null,
				locale));
		model.addAttribute("dialog_cancel_order",
				messageSource.getMessage("label.managerpage.pending.actions.cancel.dialog", null,
				locale));
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal,
			final Model model, final Long id) {		
	}
	
	@RequestMapping(value = "/managerpagependingorders", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		prepareModel(locale, principal, model);
		
		return "managerpagependingorders";
	}
	
	@RequestMapping(value = "/manageradminpagependingorders", method = RequestMethod.GET)
	public String activateAdmin(final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		prepareModel(locale, principal, model);
		
		return "manageradminpagependingorders";
	}
	
	@RequestMapping(value = "/managerpagependingorders/pendingorderspaging",
			method = RequestMethod.POST)
	public String pendingOrdersPaging(
			@RequestParam("pendingOrdersPageNumber") final Long pendingOrdersPageNumber) {
		
		pagingFirstIndex = pendingOrdersPageNumber * DEFAULT_PAGE_SIZE;
		pagingLastIndex = DEFAULT_PAGE_SIZE * (pendingOrdersPageNumber + 1) - 1;
		pageNumber = pendingOrdersPageNumber;	
		
		return "redirect:/managerpagependingorders";
	}
	
	@RequestMapping(value = "/manageradminpagependingorders/pendingorderspaging",
			method = RequestMethod.POST)
	public String pendingOrdersPagingAdmin(
			@RequestParam("pendingOrdersPageNumber") final Long pendingOrdersPageNumber) {
		
		pagingFirstIndex = pendingOrdersPageNumber * DEFAULT_PAGE_SIZE;
		pagingLastIndex = DEFAULT_PAGE_SIZE * (pendingOrdersPageNumber + 1) - 1;
		pageNumber = pendingOrdersPageNumber;	
		
		return "redirect:/manageradminpagependingorders";
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
		if (!myOrder.getStatus().getOrderStatusName().contentEquals("pending")) {			
			messageConfirmFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.confirm.failed.orderNotPending",
							null, locale);
			return "redirect:/managerpagependingorders";
		}
		orderSvc.confirmOrderById(orderId, myUser.getLogin(),
				orderStatusSvc.getOrderStatusByName("started").getOrderStatusId());
		messageConfirmSucceeded = 
				messageSource
				.getMessage("popup.managerpage.pending.actions.confirm.succeeded",
						null, locale);
		return "redirect:/managerpagependingorders";
	}
	
	@RequestMapping(value = "/confirmadmin", method = RequestMethod.GET)
	public String confirmOrderAdmin(@RequestParam("order_id") Long orderId, final Locale locale) {
		Order myOrder = orderSvc.getOrderById(orderId);
		if (myOrder == null) {			
			messageConfirmFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.confirm.failed.orderNotExists",
							null, locale);
			return "redirect:/manageradminpagependingorders";
		}
		if (!myOrder.getStatus().getOrderStatusName().contentEquals("pending")) {			
			messageConfirmFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.confirm.failed.orderNotPending",
							null, locale);
			return "redirect:/manageradminpagependingorders";
		}
		orderSvc.confirmOrderById(orderId, myUser.getLogin(),
				orderStatusSvc.getOrderStatusByName("started").getOrderStatusId());
		messageConfirmSucceeded = 
				messageSource
				.getMessage("popup.managerpage.pending.actions.confirm.succeeded",
						null, locale);
		return "redirect:/manageradminpagependingorders";
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
		if (!myOrder.getStatus().getOrderStatusName().contentEquals("pending")) {
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
	
	@RequestMapping(value = "/canceladmin", method = RequestMethod.GET)
	public String cancelOrderAdmin(@RequestParam("order_id") Long orderId, final Locale locale) {
		Order myOrder = orderSvc.getOrderById(orderId);
		if (myOrder == null) {
			messageCancelFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.cancel.failed.orderNotExists",
							null, locale);
			return "redirect:/manageradminpagependingorders";			
		}
		if (!myOrder.getStatus().getOrderStatusName().contentEquals("pending")) {
			messageCancelFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.cancel.failed.orderNotPending",
							null, locale);			
			return "redirect:/manageradminpagependingorders";
		}
		orderSvc.cancelOrderById(orderId);
		messageCancelSucceeded = 
				messageSource
				.getMessage("popup.managerpage.pending.actions.cancel.succeeded",
						null, locale);
		return "redirect:/manageradminpagependingorders";
	}
}
