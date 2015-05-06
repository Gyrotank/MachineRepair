package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.machine.MachineService;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.service.order.OrderService;
import com.glomozda.machinerepair.service.orderstatus.OrderStatusService;
import com.glomozda.machinerepair.service.repairtype.RepairTypeService;
import com.glomozda.machinerepair.service.user.UserService;
import com.glomozda.machinerepair.service.userauthorization.UserAuthorizationService;

@Controller
public class AdminPageOrdersController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageOrdersController.class.getName());
	
	@Autowired
	private MachineService machineSvc;
	
	@Autowired
	private MachineServiceableService machineServiceableSvc;

	@Autowired
	private RepairTypeService repairTypeSvc;
	
	@Autowired
	private OrderStatusService orderStatusSvc;
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private UserAuthorizationService userAuthorizationSvc;
	
	@Autowired
	private ClientService clientSvc;
	
	@Autowired
	private OrderService orderSvc;
	
	private User myUser;
	
	private MessageSource messageSource;
	
	private static final Long _defaultPageSize = (long) 10;
	
	private String messageOrderAdded = "";
	private String messageOrderNotAdded = "";
	
	private String messageOrderClientId = "";
	private Long selectedOrderClientId = (long) 0;

	private String messageOrderRepairTypeId = "";
	private Long selectedOrderRepairTypeId = (long) 0;
	
	private String messageOrderMachineId = "";
	private Long selectedOrderMachineId = (long) 0;
	
	private String messageOrderOrderStatusId = "";
	private Long selectedOrderOrderStatusId = (long) 0;
	
	private String messageOrderManager = "";
	private String selectedOrderManager = "-";

	private String messageOrderStart = "";
	private String enteredOrderStart = "";
	
	private Long orderPagingFirstIndex = (long) 0;
	private Long orderPagingLastIndex = _defaultPageSize - 1;
	private Long pageNumber = (long) 0;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping(value = "/adminpageorders", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
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
				orderSvc.getAllWithFetching(orderPagingFirstIndex,
						orderPagingLastIndex - orderPagingFirstIndex + 1));
		
		Long ordersCount = orderSvc.getOrderCount();
		model.addAttribute("orders_count", ordersCount);
		Long pagesCount = ordersCount / _defaultPageSize;
		if (ordersCount % _defaultPageSize != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", _defaultPageSize);
		model.addAttribute("page_number", pageNumber);
		
		model.addAttribute("message_order_added",
				messageOrderAdded);
		messageOrderAdded = "";
		model.addAttribute("message_order_not_added",
				messageOrderNotAdded);
		messageOrderNotAdded = "";
		
		model.addAttribute("message_order_client_id", messageOrderClientId);
		messageOrderClientId = "";		
		model.addAttribute("selected_order_client_id", selectedOrderClientId);
		selectedOrderClientId = (long) 0;
		
		model.addAttribute("message_order_repair_type_id", messageOrderRepairTypeId);
		messageOrderRepairTypeId = "";		
		model.addAttribute("selected_order_repair_type_id", selectedOrderRepairTypeId);
		selectedOrderRepairTypeId = (long) 0;
		
		model.addAttribute("message_order_order_status_id", messageOrderOrderStatusId);
		messageOrderOrderStatusId = "";		
		model.addAttribute("selected_order_order_status_id", selectedOrderOrderStatusId);
		selectedOrderOrderStatusId = (long) 0;
		
		model.addAttribute("message_order_machine_id", messageOrderMachineId);
		messageOrderMachineId = "";
		model.addAttribute("selected_order_machine_id", selectedOrderMachineId);
		selectedOrderMachineId = (long) 0;
		
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
		
		return "adminpageorders";
	}
	
	@RequestMapping(value = "/adminpageorders/orderpaging", method = RequestMethod.POST)
	public String orderPaging(@RequestParam("orderPageNumber") final Long orderPageNumber) {
		
		orderPagingFirstIndex = orderPageNumber * _defaultPageSize;
		orderPagingLastIndex = 
				_defaultPageSize * (orderPageNumber + 1) - 1;
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
			messageOrderAdded =
					messageSource.getMessage("popup.adminpage.orderAdded", null,
							locale);
		} else {
			messageOrderNotAdded = 
					messageSource.getMessage("popup.adminpage.orderNotAdded", null,
							locale);
		}		
		return "redirect:/adminpageorders";
	}	
}
