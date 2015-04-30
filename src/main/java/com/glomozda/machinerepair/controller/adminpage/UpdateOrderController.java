package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import com.glomozda.machinerepair.service.order.OrderService;
import com.glomozda.machinerepair.service.repairtype.RepairTypeService;
import com.glomozda.machinerepair.service.user.UserService;
import com.glomozda.machinerepair.service.userauthorization.UserAuthorizationService;

@Controller
public class UpdateOrderController implements MessageSourceAware {

	static Logger log = Logger.getLogger(UpdateOrderController.class.getName());
	
	@Autowired
	private OrderService orderSvc;
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private RepairTypeService repairTypeSvc;
	
	@Autowired
	private UserAuthorizationService userAuthorizationSvc;
	
	private User myUser;
	
	private Order myOrder;
	
	private MessageSource messageSource;
	
	private String messageOrderStart = "";
	private String enteredOrderStart = "";
	
	private String messageOrderUpdateFailed = "";
	private String messageOrderUpdateSucceeded = "";
	private String messageOrderNoChanges = "";
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/updateorder", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model, 
			@RequestParam("order-id") final Long orderId) {
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("locale", locale.toString());		
		
		Order currentOrder = orderSvc.getOrderById(orderId);
		
		if (currentOrder == null) {			
			return "redirect:/adminpageorders";
		}
		
		myOrder = orderSvc.getOrderByIdWithFetching(orderId);
		
		if (!model.containsAttribute("orderCurrent")) {
			model.addAttribute("orderCurrent", myOrder);
		}
		
		if (!model.containsAttribute("order")) {
			model.addAttribute("order", myOrder);
		}
		
		model.addAttribute("repair_types", repairTypeSvc.getAll((long) 0, (long) 99));
		
		List<String> managers = userAuthorizationSvc.getUserLoginsForRole("ROLE_MANAGER");
		managers.addAll(userAuthorizationSvc.getUserLoginsForRole("ROLE_ADMIN"));
		java.util.Collections.sort(managers);
		model.addAttribute("managers", managers);
		
		model.addAttribute("message_order_start", messageOrderStart);
		messageOrderStart = "";
		Calendar cal = new GregorianCalendar();
        cal.setTime(myOrder.getStart());
		enteredOrderStart = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + 
				"-" + cal.get(Calendar.YEAR);
		model.addAttribute("entered_order_start", enteredOrderStart);
		enteredOrderStart = "";
		
		model.addAttribute("message_order_not_updated",
				messageOrderUpdateFailed);
		messageOrderUpdateFailed = "";
		model.addAttribute("message_order_updated",
				messageOrderUpdateSucceeded);
		messageOrderUpdateSucceeded = "";
		model.addAttribute("message_order_no_changes",
				messageOrderNoChanges);
		messageOrderNoChanges = "";		
		
		return "updateorder";
	}
	
	@RequestMapping(value = "updateorder/updateOrder", method = RequestMethod.POST)
	public String updateOrder(@ModelAttribute("order") @Valid final Order order,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
//			@RequestParam("repairTypeId") final Long repairTypeId,
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
		
		if (startSqlDate == null || bindingResult.hasErrors()) {
			
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
			
			enteredOrderStart = startDate;			
			return "redirect:/updateorder/?order-id=" + myOrder.getOrderId();
		}
		
		if (order.getManager().contentEquals("-") 
				& !order.getStatus().contentEquals("pending")) {
			messageOrderUpdateFailed = 
					messageSource.getMessage("error.adminpage.managerRequired", null,
							locale);
			enteredOrderStart = startDate;			
			return "redirect:/updateorder/?order-id=" + myOrder.getOrderId();
		}
		
		order.setStart(startSqlDate);
//		order.setRepairType(repairTypeSvc.getRepairTypeById(repairTypeId));
//		order.setRepairType(repairTypeSvc.getRepairTypeById(myOrder.getRepairType()
//				.getRepairTypeId()));
		
		if (order.getManager().equals(myOrder.getManager())
				&& order.getStatus().equals(myOrder.getStatus())
				&& order.getStart().equals(myOrder.getStart())) {
//			log.info("Client has same name");
			messageOrderNoChanges = 
					messageSource.getMessage("popup.adminpage.orderNoChanges", null,
							locale);
			return "redirect:/updateorder/?order-id=" + myOrder.getOrderId();
		}
		
		if (orderSvc.updateOrderById(myOrder.getOrderId(), order) == 1) {
			messageOrderUpdateSucceeded =
					messageSource.getMessage("popup.adminpage.orderUpdated", null,
							locale);
		} else {
			messageOrderUpdateFailed = 
					messageSource.getMessage("popup.adminpage.orderNotUpdated", null,
							locale);
		}		
		return "redirect:/updateorder/?order-id=" + myOrder.getOrderId();
	}
}
