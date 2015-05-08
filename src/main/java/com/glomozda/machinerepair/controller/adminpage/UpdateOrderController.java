package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class UpdateOrderController extends AbstractRolePageController
	implements MessageSourceAware {

	static Logger log = Logger.getLogger(UpdateOrderController.class.getName());
	
	private Order myOrder;
	
	private String messageOrderRepairTypeId = "";
	private String messageOrderOrderStatusId = "";
	private String messageOrderManager = "";
	
	private String messageOrderStart = "";
	private String enteredOrderStart = "";
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal,
			final Model model) {
	}

	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final Long orderId) {
		
		model.addAttribute("locale", locale.toString());
		
		myOrder = orderSvc.getOrderByIdWithFetching(orderId);
		
		if (!model.containsAttribute("orderCurrent")) {
			model.addAttribute("orderCurrent", myOrder);
		}
		
		if (!model.containsAttribute("order")) {
			model.addAttribute("order", myOrder);
		}
		
		model.addAttribute("repair_types", repairTypeSvc.getAllAvailable());
		
		model.addAttribute("order_statuses", orderStatusSvc.getAll());
		
		List<String> managers = userAuthorizationSvc.getUserLoginsForRole("ROLE_MANAGER");
		managers.addAll(userAuthorizationSvc.getUserLoginsForRole("ROLE_ADMIN"));
		java.util.Collections.sort(managers);
		model.addAttribute("managers", managers);
		
		model.addAttribute("message_order_repair_type_id", messageOrderRepairTypeId);
		messageOrderRepairTypeId = "";
		model.addAttribute("message_order_order_status_id", messageOrderOrderStatusId);
		messageOrderOrderStatusId = "";
		model.addAttribute("message_order_manager", messageOrderManager);
		messageOrderManager = "";
		
		model.addAttribute("message_order_start", messageOrderStart);
		messageOrderStart = "";
		Calendar cal = new GregorianCalendar();
        cal.setTime(myOrder.getStart());
		enteredOrderStart = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + 
				"-" + cal.get(Calendar.YEAR);
		model.addAttribute("entered_order_start", enteredOrderStart);
		enteredOrderStart = "";
		
		model.addAttribute("message_order_not_updated",
				messageUpdateFailed);
		messageUpdateFailed = "";
		model.addAttribute("message_order_updated",
				messageUpdateSucceeded);
		messageUpdateSucceeded = "";
		model.addAttribute("message_order_no_changes",
				messageNoChanges);
		messageNoChanges = "";
	}
	
	@RequestMapping(value = "/updateorder", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model, 
			@RequestParam("order-id") final Long orderId) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		Order currentOrder = orderSvc.getOrderById(orderId);
		if (currentOrder == null) {			
			return "redirect:/adminpageorders";
		}
		
		prepareModel(locale, principal, model, orderId);
		
		return "updateorder";
	}
	
	@RequestMapping(value = "updateorder/updateOrder", method = RequestMethod.POST)
	public String updateOrder(@ModelAttribute("order") @Valid final Order order,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			@RequestParam("repairTypeId") final Long repairTypeId,
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
		
		if (repairTypeId == 0 || orderStatusId == 0 
				|| startSqlDate == null || bindingResult.hasErrors()) {
			
			if (startSqlDate == null) {
				messageOrderStart = 
						messageSource.getMessage("typeMismatch.order.start", null,
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

			if (bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.order", bindingResult);
				redirectAttributes.addFlashAttribute("order", order);			
			}
			
			enteredOrderStart = startDate;			
			return "redirect:/updateorder/?order-id=" + myOrder.getOrderId();
		}
		
		if (order.getManager().contentEquals("-") 
				& !orderStatusSvc.getOrderStatusById(orderStatusId)
					.getOrderStatusName().contentEquals("pending")) {
			messageOrderManager = 
					messageSource.getMessage("error.adminpage.managerRequired", null,
							locale);
			enteredOrderStart = startDate;			
			return "redirect:/updateorder/?order-id=" + myOrder.getOrderId();
		}
		
		order.setStart(startSqlDate);
		order.setRepairType(repairTypeSvc.getRepairTypeById(repairTypeId));
		order.setStatus(orderStatusSvc.getOrderStatusById(orderStatusId));
		
		if (order.getManager().equals(myOrder.getManager())
				&& order.getStatus().equals(myOrder.getStatus())
				&& order.getStart().equals(myOrder.getStart())
				&& order.getRepairType().getRepairTypeId()
					.equals(myOrder.getRepairType().getRepairTypeId())) {
			messageNoChanges = 
					messageSource.getMessage("popup.adminpage.orderNoChanges", null,
							locale);
			return "redirect:/updateorder/?order-id=" + myOrder.getOrderId();
		}
		
		if (orderSvc.updateOrderById(myOrder.getOrderId(), order) == 1) {
			messageUpdateSucceeded =
					messageSource.getMessage("popup.adminpage.orderUpdated", null,
							locale);
		} else {
			messageUpdateFailed = 
					messageSource.getMessage("popup.adminpage.orderNotUpdated", null,
							locale);
		}		
		return "redirect:/updateorder/?order-id=" + myOrder.getOrderId();
	}	
}
