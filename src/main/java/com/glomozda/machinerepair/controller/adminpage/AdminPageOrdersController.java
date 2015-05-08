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

@Controller
public class AdminPageOrdersController extends AbstractRolePageController
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageOrdersController.class.getName());
	
	private String messageOrderClientId = "";
	private Long selectedOrderClientId = 0L;

	private String messageOrderRepairTypeId = "";
	private Long selectedOrderRepairTypeId = 0L;
	
	private String messageOrderMachineId = "";
	private Long selectedOrderMachineId = 0L;
	
	private String messageOrderOrderStatusId = "";
	private Long selectedOrderOrderStatusId = 0L;
	
	private String messageOrderManager = "";
	private String selectedOrderManager = "-";

	private String messageOrderStart = "";
	private String enteredOrderStart = "";
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("order")) {
			model.addAttribute("order", new Order());
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
		
		model.addAttribute("orders_short", 
				orderSvc.getAllWithFetching(pagingFirstIndex,
						pagingLastIndex - pagingFirstIndex + 1));
		
		Long ordersCount = orderSvc.getOrderCount();
		model.addAttribute("orders_count", ordersCount);
		Long pagesCount = ordersCount / DEFAULT_PAGE_SIZE;
		if (ordersCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("page_number", pageNumber);
		
		model.addAttribute("message_order_added",
				messageAdded);
		messageAdded = "";
		model.addAttribute("message_order_not_added",
				messageNotAdded);
		messageNotAdded = "";
		
		model.addAttribute("message_order_client_id", messageOrderClientId);
		messageOrderClientId = "";		
		model.addAttribute("selected_order_client_id", selectedOrderClientId);
		selectedOrderClientId = 0L;
		
		model.addAttribute("message_order_repair_type_id", messageOrderRepairTypeId);
		messageOrderRepairTypeId = "";		
		model.addAttribute("selected_order_repair_type_id", selectedOrderRepairTypeId);
		selectedOrderRepairTypeId = 0L;
		
		model.addAttribute("message_order_order_status_id", messageOrderOrderStatusId);
		messageOrderOrderStatusId = "";		
		model.addAttribute("selected_order_order_status_id", selectedOrderOrderStatusId);
		selectedOrderOrderStatusId = 0L;
		
		model.addAttribute("message_order_machine_id", messageOrderMachineId);
		messageOrderMachineId = "";
		model.addAttribute("selected_order_machine_id", selectedOrderMachineId);
		selectedOrderMachineId = 0L;
		
		model.addAttribute("message_order_start", messageOrderStart);
		messageOrderStart = "";
		model.addAttribute("entered_order_start", enteredOrderStart);
		enteredOrderStart = "";
		
		model.addAttribute("message_order_manager", messageOrderManager);
		messageOrderManager = "";
		model.addAttribute("selected_order_manager", selectedOrderManager);
		selectedOrderManager = "-";
		
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
		
		pagingFirstIndex = orderPageNumber * DEFAULT_PAGE_SIZE;
		pagingLastIndex = 
				DEFAULT_PAGE_SIZE * (orderPageNumber + 1) - 1;
		pageNumber = orderPageNumber;
		
		return "redirect:/adminpageorders";
	}
	
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public String addOrder(@ModelAttribute("order") @Valid final Order order,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			@RequestParam("clientId") final Long clientId, 
			@RequestParam("repairTypeId") final Long repairTypeId, 
			@RequestParam("machineId") final Long machineId,
			@RequestParam("orderStatusId") final Long orderStatusId,
			@RequestParam("startDate") final String startDate,			
			final Locale locale) {
		
		java.sql.Date startSqlDate = new java.sql.Date(0);
		
		try {
			String startParsed = new String();
			startParsed = startParsed.concat(startDate.substring(6));			
			startParsed = startParsed.concat("-");			
			startParsed = startParsed.concat(startDate.substring(3, 5));			
			startParsed = startParsed.concat("-");			
			startParsed = startParsed.concat(startDate.substring(0, 2));			
			startSqlDate = java.sql.Date.valueOf(startParsed);			
		} catch (java.lang.StringIndexOutOfBoundsException e) {
			startSqlDate = null;
		} catch (IllegalArgumentException e) {
			startSqlDate = null;
		} catch (NullPointerException e) {
			startSqlDate = null;
		}		
		
		if (clientId == 0 || repairTypeId == 0 || machineId == 0 ||
				orderStatusId == 0 || startSqlDate == null || bindingResult.hasErrors()) {
			
			if (clientId == 0) {
				messageOrderClientId = 
						messageSource.getMessage("error.adminpage.clientId", null,
								locale);			
			}

			if (repairTypeId == 0) {
				messageOrderRepairTypeId = 
						messageSource.getMessage("error.adminpage.repairTypeId", null,
								locale);			
			}
			
			if (orderStatusId == 0) {
				messageOrderOrderStatusId = 
						messageSource.getMessage("error.adminpage.orderStatusId", null,
								locale);			
			}

			if (machineId == 0) {
				messageOrderMachineId = 
						messageSource.getMessage("error.adminpage.machineId", null,
								locale);			
			}
			
			if (startSqlDate == null) {
				messageOrderStart = 
						messageSource.getMessage("typeMismatch.order.start", null,
								locale);
			}

			if (bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.order", bindingResult);
				redirectAttributes.addFlashAttribute("order", order);			
			}
			
			selectedOrderClientId = clientId;
			selectedOrderRepairTypeId = repairTypeId;
			selectedOrderMachineId = machineId;
			selectedOrderOrderStatusId = orderStatusId;
			enteredOrderStart = startDate;
			selectedOrderManager = order.getManager();
			return "redirect:/adminpageorders#add_new_order";
		}
		
		if (order.getManager().contentEquals("-") 
				& !orderStatusSvc.getOrderStatusById(orderStatusId)
					.getOrderStatusName().contentEquals("pending")) {
			messageOrderManager = 
					messageSource.getMessage("error.adminpage.managerRequired", null,
							locale);
			selectedOrderClientId = clientId;
			selectedOrderRepairTypeId = repairTypeId;
			selectedOrderMachineId = machineId;
			selectedOrderOrderStatusId = orderStatusId;
			enteredOrderStart = startDate;
			selectedOrderManager = order.getManager();
			return "redirect:/adminpageorders#add_new_order";
		}
		
		order.setStart(startSqlDate);		
		
		if (orderSvc.add(order, clientId, repairTypeId, machineId, orderStatusId)) {
			messageAdded =
					messageSource.getMessage("popup.adminpage.orderAdded", null,
							locale);
		} else {
			messageNotAdded = 
					messageSource.getMessage("popup.adminpage.orderNotAdded", null,
							locale);
		}		
		return "redirect:/adminpageorders";
	}
}
