package com.glomozda.machinerepair.controller.managerpage;

import java.security.Principal;
import java.util.ArrayList;
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
public class ManagerPageActiveOrdersController extends AbstractRolePageController 
	implements MessageSourceAware {
	
	private String messageSetReadyFailed = "";
	private String messageSetReadySucceeded = "";
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final long selectedId) {		
		
		model.addAttribute("locale", locale.toString());
		
		model.addAttribute("message_set_ready_failed", messageSetReadyFailed);
		messageSetReadyFailed = "";
		model.addAttribute("message_set_ready_succeeded", messageSetReadySucceeded);
		messageSetReadySucceeded = "";
		
		model.addAttribute("dialog_setready_order",
				messageSource.getMessage("label.managerpage.started.actions.setReady.dialog", null,
				locale));
		
		model.addAttribute("selected_client_id", selectedId);
		
		ArrayList<Order> startedOrdersForSelectedClient = new ArrayList<Order>();
		ArrayList<Order> readyOrdersForSelectedClient = new ArrayList<Order>();
		
		if (selectedId != 0L) {			
			
			startedOrdersForSelectedClient.addAll(
				orderSvc.getOrdersForClientIdAndStatusWithFetching(selectedId, 
						"started", 
						sessionScopeInfoService.getSessionScopeInfo().getPagingFirstIndexPlus(), 
						sessionScopeInfoService.getSessionScopeInfo().getPagingLastIndexPlus() 
						- sessionScopeInfoService.getSessionScopeInfo()
							.getPagingFirstIndexPlus() + 1));
			
			readyOrdersForSelectedClient.addAll(
				orderSvc.getOrdersForClientIdAndStatusWithFetching(selectedId, 
					"ready", 
					sessionScopeInfoService.getSessionScopeInfo().getPagingFirstIndexPlusPlus(), 
					sessionScopeInfoService.getSessionScopeInfo().getPagingLastIndexPlusPlus() 
					- sessionScopeInfoService.getSessionScopeInfo()
						.getPagingFirstIndexPlusPlus() + 1));
		}
		
		model.addAttribute("started_orders_for_selected_client", startedOrdersForSelectedClient);		
		Long startedOrdersCount = 
				orderSvc.getCountOrdersForClientIdAndStatus(selectedId, "started");
		model.addAttribute("started_orders_count", startedOrdersCount);
		Long startedOrdersPagesCount = startedOrdersCount / DEFAULT_PAGE_SIZE;
		if (startedOrdersCount % DEFAULT_PAGE_SIZE != 0) {
			startedOrdersPagesCount++;
		}
		model.addAttribute("started_orders_pages_count", startedOrdersPagesCount);
		model.addAttribute("started_orders_pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("started_orders_page_number", 
			sessionScopeInfoService.getSessionScopeInfo().getPageNumberPlus());
		
		model.addAttribute("ready_orders_for_selected_client", readyOrdersForSelectedClient);
		Long readyOrdersCount = 
				orderSvc.getCountOrdersForClientIdAndStatus(selectedId, "ready");
		model.addAttribute("ready_orders_count", readyOrdersCount);
		Long readyOrdersPagesCount = readyOrdersCount / DEFAULT_PAGE_SIZE;
		if (readyOrdersCount % DEFAULT_PAGE_SIZE != 0) {
			readyOrdersPagesCount++;
		}
		model.addAttribute("ready_orders_pages_count", readyOrdersPagesCount);
		model.addAttribute("ready_orders_pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("ready_orders_page_number", 
				sessionScopeInfoService.getSessionScopeInfo().getPageNumberPlusPlus());	
		
		model.addAttribute("clients", 
			clientSvc.getIdsAndNamesLikeName("%", 
				sessionScopeInfoService.getSessionScopeInfo().getPagingFirstIndex(),
				sessionScopeInfoService.getSessionScopeInfo().getPagingLastIndex() 
				- sessionScopeInfoService.getSessionScopeInfo()
					.getPagingFirstIndex() + 1));
		Long clientsCount = clientSvc.getClientCount();
		model.addAttribute("clients_count", clientsCount);
		Long clientPagesCount = clientsCount / DEFAULT_PAGE_SIZE;
		if (clientsCount % DEFAULT_PAGE_SIZE != 0) {
			clientPagesCount++;
		}
		model.addAttribute("client_pages_count", clientPagesCount);
		model.addAttribute("client_pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("client_page_number", 
				sessionScopeInfoService.getSessionScopeInfo().getPageNumber());		
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {		
	}
	
	@RequestMapping(value = {"/managerpageactiveorders", "/manageradminpageactiveorders"},
			method = RequestMethod.GET)
	public String activate(HttpServletRequest request,
			final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}		
		
		prepareModel(locale, principal, model, 
				sessionScopeInfoService.getSessionScopeInfo().getSelectedId());
		
		if (request.getRequestURI().contains("admin")) {
			return "manageradminpageactiveorders";
		} else {		
			return "managerpageactiveorders";
		}
	}
	
	@RequestMapping(value = {"/managerpageactiveorders/clientpaging", 
					"/manageradminpageactiveorders/clientpaging"},
			method = RequestMethod.POST)
	public String clientPaging(HttpServletRequest request,
			@RequestParam("clientPageNumber") final Long clientPageNumber) {
		
		changeSessionScopePagingInfo(clientPageNumber * DEFAULT_PAGE_SIZE,
				DEFAULT_PAGE_SIZE * (clientPageNumber + 1) - 1,
				clientPageNumber);		
		
		sessionScopeInfoService.getSessionScopeInfo().setSelectedId(0L);
		
		if (request.getRequestURI().contains("admin")) {
			return "redirect:/manageradminpageactiveorders";
		} else {		
			return "redirect:/managerpageactiveorders";
		}		
	}
	
	@RequestMapping(value = {"/managerpageactiveorders/startedorderspaging", 
					"/manageradminpageactiveorders/startedorderspaging"},
			method = RequestMethod.POST)
	public String startedOrdersPaging(HttpServletRequest request,
			@RequestParam("startedOrdersPageNumber") final Long startedOrdersPageNumber) {
		
		changeSessionScopePagingPlusInfo(startedOrdersPageNumber * DEFAULT_PAGE_SIZE,
				DEFAULT_PAGE_SIZE * (startedOrdersPageNumber + 1) - 1,
				startedOrdersPageNumber);
		
		if (request.getRequestURI().contains("admin")) {
			return "redirect:/manageradminpageactiveorders";
		} else {		
			return "redirect:/managerpageactiveorders";
		}		
	}
	
	@RequestMapping(value = {"/managerpageactiveorders/readyorderspaging", 
					"/manageradminpageactiveorders/readyorderspaging"},
			method = RequestMethod.POST)
	public String readyOrdersPaging(HttpServletRequest request,
			@RequestParam("readyOrdersPageNumber") final Long readyOrdersPageNumber) {
		
		changeSessionScopePagingPlusPlusInfo(readyOrdersPageNumber * DEFAULT_PAGE_SIZE,
				DEFAULT_PAGE_SIZE * (readyOrdersPageNumber + 1) - 1,
				readyOrdersPageNumber);
		
		if (request.getRequestURI().contains("admin")) {
			return "redirect:/manageradminpageactiveorders";
		} else {		
			return "redirect:/managerpageactiveorders";
		}		
	}
	
	@RequestMapping(value = {"/listactiveordersforselectedclient", 
					"/listactiveordersforselectedclientadmin"},
			method = RequestMethod.POST)
	public String listActiveOrdersForSelectedClient(HttpServletRequest request,
			@RequestParam("clientId") Long clientId) {
		
		sessionScopeInfoService.getSessionScopeInfo().setSelectedId(clientId);
		
		if (request.getRequestURI().contains("admin")) {
			return "redirect:/manageradminpageactiveorders";
		} else {		
			return "redirect:/managerpageactiveorders";
		}		
	}
	
	@RequestMapping(value = {"/setready", "/setreadyadmin"}, method = RequestMethod.GET)
	public String setOrderReady(HttpServletRequest request,
			@RequestParam("order_id") Long orderId, final Locale locale) {
		
		Order myOrder = orderSvc.getOrderById(orderId);
		if (myOrder == null) {
			messageSetReadyFailed = 
					messageSource
					.getMessage("popup.managerpage.started.actions.setReady.failed.orderNotExists",
							null, locale);
			if (request.getRequestURI().contains("admin")) {
				return "redirect:/manageradminpageactiveorders";
			} else {		
				return "redirect:/managerpageactiveorders";
			}			
		}
		if (!myOrder.getStatus().getOrderStatusName().contentEquals("started")) {
			messageSetReadyFailed = 
					messageSource
					.getMessage(
							"popup.managerpage.started.actions.setReady.failed.orderNotStarted",
							null, locale);
			if (request.getRequestURI().contains("admin")) {
				return "redirect:/manageradminpageactiveorders";
			} else {		
				return "redirect:/managerpageactiveorders";
			}			
		}
		orderSvc.setOrderStatusById(orderId, 
			orderStatusSvc.getOrderStatusByName("ready").getOrderStatusId());
		myOrder = orderSvc.getOrderByIdWithFetching(orderId);
		machineSvc.incrementTimesRepairedById(myOrder.getMachine().getMachineId());
		messageSetReadySucceeded = 
				messageSource
				.getMessage("popup.managerpage.started.actions.setReady.succeeded",
						null, locale);
		if (request.getRequestURI().contains("admin")) {
			return "redirect:/manageradminpageactiveorders";
		} else {		
			return "redirect:/managerpageactiveorders";
		}		
	}
}
