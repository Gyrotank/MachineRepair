package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.glomozda.machinerepair.controller.AbstractRolePageController;
import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.domain.order.OrderDTO;

@Controller
public class AdminPageOrdersController extends AbstractRolePageController
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageOrdersController.class.getName());
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("orderDTO")) {
			model.addAttribute("orderDTO", new OrderDTO());
		}
		
		model.addAttribute("users", userSvc.getAll());
		
		model.addAttribute("clients", clientSvc.getAll());
		model.addAttribute("repair_types", repairTypeSvc.getAllAvailable());
		model.addAttribute("machines", machineSvc.getAll());
		model.addAttribute("order_statuses", orderStatusSvc.getAll());
		List<String> managers = userAuthorizationSvc.getUserLoginsForRole("ROLE_MANAGER");
		managers.addAll(userAuthorizationSvc.getUserLoginsForRole("ROLE_ADMIN"));
		java.util.Collections.sort(managers);
		model.addAttribute("managers", managers);
				
		Long ordersCount = orderSvc.getOrderCount();
		model.addAttribute("orders_count", ordersCount);
		Long pagesCount = ordersCount / DEFAULT_PAGE_SIZE;
		if (ordersCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		Long pageNumber = sessionScopeInfoService.getSessionScopeInfo().getPageNumber();
		if (pageNumber >= pagesCount) {
			model.addAttribute("page_number", 0L);
			model.addAttribute("orders_short", 
					orderSvc.getAllWithFetching(
							0L, DEFAULT_PAGE_SIZE));			
		} else {
			model.addAttribute("page_number", pageNumber);
			model.addAttribute("orders_short", 
					orderSvc.getAllWithFetching(
						sessionScopeInfoService.getSessionScopeInfo().getPagingFirstIndex(), 
						sessionScopeInfoService.getSessionScopeInfo().getPagingLastIndex() 
							- sessionScopeInfoService
								.getSessionScopeInfo().getPagingFirstIndex() + 1));
		}
		
		model.addAttribute("message_order_added",
			sessionScopeInfoService.getSessionScopeInfo().getMessageAdded());
			sessionScopeInfoService.getSessionScopeInfo().setMessageAdded("");
		model.addAttribute("message_order_not_added",
			sessionScopeInfoService.getSessionScopeInfo().getMessageNotAdded());
			sessionScopeInfoService.getSessionScopeInfo().setMessageNotAdded("");
		
		model.addAttribute("dialog_delete_order",
				messageSource.getMessage(
						"label.adminpage.orders.actions.delete.dialog", null,
				locale));		
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final Long id) {				
	}

	@RequestMapping(value = "/adminpageorders", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		prepareModel(locale, principal, model);
		
		return "adminpageorders";
	}
	
	@RequestMapping(value = "/adminpageorders/orderpaging", method = RequestMethod.POST)
	public String orderPaging(@RequestParam("orderPageNumber") final Long orderPageNumber) {
		
		changeSessionScopePagingInfo(orderPageNumber * DEFAULT_PAGE_SIZE,
				DEFAULT_PAGE_SIZE * (orderPageNumber + 1) - 1,
				orderPageNumber);
		
		return "redirect:/adminpageorders";
	}
	
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public String addOrder(@ModelAttribute("orderDTO") @Valid final OrderDTO orderDTO,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,			
			final Locale locale) {
		
		java.sql.Date startSqlDate = new java.sql.Date(0);
		
		try {
			String startParsed = new String();
			startParsed = startParsed.concat(orderDTO.getStartDate().substring(6));			
			startParsed = startParsed.concat("-");			
			startParsed = startParsed.concat(orderDTO.getStartDate().substring(3, 5));			
			startParsed = startParsed.concat("-");			
			startParsed = startParsed.concat(orderDTO.getStartDate().substring(0, 2));			
			startSqlDate = java.sql.Date.valueOf(startParsed);			
		} catch (java.lang.StringIndexOutOfBoundsException e) {
			startSqlDate = null;
		} catch (IllegalArgumentException e) {
			startSqlDate = null;
		} catch (NullPointerException e) {
			startSqlDate = null;
		}
		
		if (orderDTO.getStartDate() != null && !orderDTO.getStartDate().isEmpty() 
				&& startSqlDate == null) {
			bindingResult.rejectValue("startDate", "typeMismatch.order.start", null);
		}
		
		if (orderDTO.getOrderStatusId() != 0 && orderDTO.getManager().contentEquals("-") 
				&& !orderStatusSvc.getOrderStatusById(orderDTO.getOrderStatusId())
				.getOrderStatusName().contentEquals("pending")) {
			bindingResult.rejectValue("manager", "error.adminpage.managerRequired", null);
		}
				
		if (bindingResult.hasErrors()) {			
			redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.orderDTO", bindingResult);
			redirectAttributes.addFlashAttribute("orderDTO", orderDTO);			
			
			return "redirect:/adminpageorders#add_new_order";
		}		
		
		Order newOrder = new Order();
		newOrder.setStart(startSqlDate);
		newOrder.setManager(orderDTO.getManager());
		
		if (orderSvc.add(newOrder, orderDTO.getClientId(), orderDTO.getRepairTypeId(),
				orderDTO.getMachineId(), orderDTO.getOrderStatusId())) {
			changeSessionScopeAddingInfo(
					messageSource.getMessage("popup.adminpage.orderAdded", null, 
							locale),
					"");
		} else {
			changeSessionScopeAddingInfo(
					"",
					messageSource.getMessage("popup.adminpage.orderNotAdded", null,
							locale));			
		}		
		return "redirect:/adminpageorders";
	}
}
