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
import com.glomozda.machinerepair.controller.SearchQuery;
import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.domain.order.OrderDTO;

@Controller
public class AdminPageOrdersController extends AbstractRolePageController
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageOrdersController.class.getName());
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
		
		prepareModelAdminPage(locale, model, new OrderDTO(), orderSvc);
		
		if (!model.containsAttribute("clientSearchQuery")) {
			model.addAttribute("clientSearchQuery", new SearchQuery());
		}
		
		if (sessionScopeInfoService.getSessionScopeInfo()
				.getSearchQuery().getSearchQueryArgument().isEmpty()) {
			model.addAttribute("clients", clientSvc.getAll(0L, DEFAULT_PAGE_SIZE * 10));
			model.addAttribute("message_search_results", "");
		} else {
			model.addAttribute("clientSearchQuery",
					sessionScopeInfoService.getSessionScopeInfo().getSearchQuery());
			Long resultSetSize 
				= clientSvc.getClientCountLikeName(sessionScopeInfoService.getSessionScopeInfo()
						.getSearchQuery().getSearchQueryArgument());
			if (resultSetSize > DEFAULT_PAGE_SIZE * 2) {
				model.addAttribute("clients", 
					clientSvc.getClientsLikeName(sessionScopeInfoService.getSessionScopeInfo()
						.getSearchQuery().getSearchQueryArgument(), 0L, (DEFAULT_PAGE_SIZE * 2 - 1)));
				model.addAttribute("message_search_results",
						messageSource.getMessage(
							"popup.adminpage.addNewOrder.clientSearchMessageTooMany", 
								new Object[] { resultSetSize, DEFAULT_PAGE_SIZE * 2 }, locale));
			} else {
				model.addAttribute("clients", 
						clientSvc.getClientsLikeName(sessionScopeInfoService.getSessionScopeInfo()
							.getSearchQuery().getSearchQueryArgument()));
					model.addAttribute("message_search_results",
							messageSource.getMessage(
								"popup.adminpage.addNewOrder.clientSearchMessageNormal", 
									new Object[] { resultSetSize }, locale));
			}
		}
		
		model.addAttribute("repair_types", repairTypeSvc.getAllAvailable());
		model.addAttribute("machines", machineSvc.getAll());
		model.addAttribute("order_statuses", orderStatusSvc.getAll());
		List<String> managers = userAuthorizationSvc.getUserLoginsForRole("ROLE_MANAGER");
		managers.addAll(userAuthorizationSvc.getUserLoginsForRole("ROLE_ADMIN"));
		java.util.Collections.sort(managers);
		model.addAttribute("managers", managers);
				
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final long id) {				
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
	
	@RequestMapping(value = "/searchClients", method = RequestMethod.POST)
	public String searchClients(
		@ModelAttribute("clientSearchQuery") final SearchQuery clientSearchQuery, 
		final Model model, final Locale locale) {
		
		sessionScopeInfoService.getSessionScopeInfo().setSearchQuery(clientSearchQuery);
		
		return "redirect:/adminpageorders#add_new_order";
	}
	
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public String addOrder(@ModelAttribute("dataObject") @Valid final OrderDTO orderDTO,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,			
			final Locale locale) {
		
		java.sql.Date startSqlDate = StringToSqlDateParser(orderDTO.getStartDate());
		
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
				("org.springframework.validation.BindingResult.dataObject", bindingResult);
			redirectAttributes.addFlashAttribute("dataObject", orderDTO);			
			
			return "redirect:/adminpageorders#add_new_order";
		}		
		
		Order newOrder = new Order();
		newOrder.setStart(startSqlDate);
		newOrder.setManager(orderDTO.getManager());
		
		addMessages(orderSvc.add(newOrder, orderDTO.getClientId(), orderDTO.getRepairTypeId(),
				orderDTO.getMachineId(), orderDTO.getOrderStatusId()),
				"popup.adminpage.orderAdded",
				"popup.adminpage.orderNotAdded",
				locale);
		
		return "redirect:/adminpageorders";
	}
}
