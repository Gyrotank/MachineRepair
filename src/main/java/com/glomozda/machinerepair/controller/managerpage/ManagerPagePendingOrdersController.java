package com.glomozda.machinerepair.controller.managerpage;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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
		
		List<Order> pendingOrders;
		
		Long pendingOrdersCount = orderSvc.getCountOrdersForStatus("pending");
		model.addAttribute("pending_orders_count", pendingOrdersCount);
		Long pagesCount = pendingOrdersCount / DEFAULT_PAGE_SIZE;
		if (pendingOrdersCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		Long pageNumber = sessionScopeInfoService.getSessionScopeInfo().getPageNumber();
		if (pageNumber >= pagesCount) {
			model.addAttribute("page_number", 0L);
			pendingOrders =	orderSvc.getOrdersForStatusWithFetching("pending", 
					0L, DEFAULT_PAGE_SIZE);						
		} else {
			model.addAttribute("page_number", pageNumber);
			pendingOrders = orderSvc.getOrdersForStatusWithFetching("pending",
					sessionScopeInfoService.getSessionScopeInfo().getPagingFirstIndex(), 
					sessionScopeInfoService.getSessionScopeInfo().getPagingLastIndex() 
					- sessionScopeInfoService.getSessionScopeInfo()
						.getPagingFirstIndex() + 1);
		}
		
		model.addAttribute("pending_orders", pendingOrders);
		
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
	
	@RequestMapping(value = {"/managerpagependingorders", "/manageradminpagependingorders"},
			method = RequestMethod.GET)
	public String activate(HttpServletRequest request,
			final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		prepareModel(locale, principal, model);
		
		if (request.getRequestURI().contains("admin")) {
			return "manageradminpagependingorders";
		} else {		
			return "managerpagependingorders";
		}
	}
	
	@RequestMapping(value = {"/managerpagependingorders/pendingorderspaging", 
					"/manageradminpagependingorders/pendingorderspaging"},
			method = RequestMethod.POST)
	public String pendingOrdersPaging(HttpServletRequest request,
			@RequestParam("pendingOrdersPageNumber") final Long pendingOrdersPageNumber) {
		
		changeSessionScopePagingInfo(pendingOrdersPageNumber * DEFAULT_PAGE_SIZE,
				DEFAULT_PAGE_SIZE * (pendingOrdersPageNumber + 1) - 1,
				pendingOrdersPageNumber);
		
		if (request.getRequestURI().contains("admin")) {
			return "redirect:/manageradminpagependingorders";
		} else {		
			return "redirect:/managerpagependingorders";
		}		
	}	
	
	@RequestMapping(value = {"/confirm", "/confirmadmin"}, method = RequestMethod.GET)
	public String confirmOrder(HttpServletRequest request,
			@RequestParam("order_id") Long orderId, final Locale locale) {
		
		Order myOrder = orderSvc.getOrderById(orderId);
		if (myOrder == null) {			
			messageConfirmFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.confirm.failed.orderNotExists",
							null, locale);
			if (request.getRequestURI().contains("admin")) {
				return "redirect:/manageradminpagependingorders";
			} else {		
				return "redirect:/managerpagependingorders";
			}			
		}
		if (!myOrder.getStatus().getOrderStatusName().contentEquals("pending")) {			
			messageConfirmFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.confirm.failed.orderNotPending",
							null, locale);
			if (request.getRequestURI().contains("admin")) {
				return "redirect:/manageradminpagependingorders";
			} else {		
				return "redirect:/managerpagependingorders";
			}			
		}
		orderSvc.confirmOrderById(orderId, myUser.getLogin(),
				orderStatusSvc.getOrderStatusByName("started").getOrderStatusId());
		messageConfirmSucceeded = 
				messageSource
				.getMessage("popup.managerpage.pending.actions.confirm.succeeded",
						null, locale);
		if (request.getRequestURI().contains("admin")) {
			return "redirect:/manageradminpagependingorders";
		} else {		
			return "redirect:/managerpagependingorders";
		}		
	}	
	
	@RequestMapping(value = {"/cancel", "/canceladmin"}, method = RequestMethod.GET)
	public String cancelOrder(HttpServletRequest request,
			@RequestParam("order_id") Long orderId, final Locale locale) {
		Order myOrder = orderSvc.getOrderById(orderId);
		if (myOrder == null) {
			messageCancelFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.cancel.failed.orderNotExists",
							null, locale);
			if (request.getRequestURI().contains("admin")) {
				return "redirect:/manageradminpagependingorders";
			} else {		
				return "redirect:/managerpagependingorders";
			}						
		}
		if (!myOrder.getStatus().getOrderStatusName().contentEquals("pending")) {
			messageCancelFailed = 
					messageSource
					.getMessage("popup.managerpage.pending.actions.cancel.failed.orderNotPending",
							null, locale);
			if (request.getRequestURI().contains("admin")) {
				return "redirect:/manageradminpagependingorders";
			} else {		
				return "redirect:/managerpagependingorders";
			}			
		}
		orderSvc.cancelOrderById(orderId);
		messageCancelSucceeded = 
				messageSource
				.getMessage("popup.managerpage.pending.actions.cancel.succeeded",
						null, locale);
		if (request.getRequestURI().contains("admin")) {
			return "redirect:/manageradminpagependingorders";
		} else {		
			return "redirect:/managerpagependingorders";
		}		
	}	
}
