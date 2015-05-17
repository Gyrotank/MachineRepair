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
import com.glomozda.machinerepair.domain.order.OrderDTO;

@Controller
public class UpdateOrderController extends AbstractRolePageController
	implements MessageSourceAware {

	static Logger log = Logger.getLogger(UpdateOrderController.class.getName());
	
	private Order myOrder;
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal,
			final Model model) {
	}

	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final long orderId) {
		
		myOrder = orderSvc.getOrderByIdWithFetching(orderId);
		
		prepareModelUpdate(locale, model, myOrder);
		
		Calendar cal = new GregorianCalendar();
        cal.setTime(myOrder.getStart());
        
        StringBuilder enteredStartDate = new StringBuilder();
        if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
        	enteredStartDate.append("0");
        }
        enteredStartDate.append(cal.get(Calendar.DAY_OF_MONTH)); 
        enteredStartDate.append("-");
        if (cal.get(Calendar.MONTH) < 9) {
        	enteredStartDate.append("0");
        }
        enteredStartDate.append(cal.get(Calendar.MONTH) + 1);
        enteredStartDate.append("-");
        enteredStartDate.append(cal.get(Calendar.YEAR));
        
		OrderDTO orderDTO = new OrderDTO(myOrder.getClient().getClientId(),
				myOrder.getRepairType().getRepairTypeId(),
				myOrder.getMachine().getMachineId(),
				enteredStartDate.toString(), myOrder.getStatus().getOrderStatusId(),
				myOrder.getManager());
		
		if (!model.containsAttribute("orderDTO")) {
			model.addAttribute("orderDTO", orderDTO);
		}
		
		model.addAttribute("repair_types", repairTypeSvc.getAllAvailable());
		
		model.addAttribute("order_statuses", orderStatusSvc.getAll());
		
		List<String> managers = userAuthorizationSvc.getUserLoginsForRole("ROLE_MANAGER");
		managers.addAll(userAuthorizationSvc.getUserLoginsForRole("ROLE_ADMIN"));
		java.util.Collections.sort(managers);
		model.addAttribute("managers", managers);
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
	public String updateOrder(@ModelAttribute("orderDTO") @Valid final OrderDTO orderDTO,
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
			("org.springframework.validation.BindingResult.orderDTO", bindingResult);
			redirectAttributes.addFlashAttribute("orderDTO", orderDTO);			
			return "redirect:/updateorder/?order-id=" + myOrder.getOrderId();
		}		
		
		Order newOrder = new Order();
		newOrder.setStart(startSqlDate);
		newOrder.setRepairType(repairTypeSvc.getRepairTypeById(orderDTO.getRepairTypeId()));
		newOrder.setStatus(orderStatusSvc.getOrderStatusById(orderDTO.getOrderStatusId()));
		newOrder.setManager(orderDTO.getManager());
		
		if (newOrder.getManager().equals(myOrder.getManager())
				&& newOrder.getStatus().equals(myOrder.getStatus())
				&& newOrder.getStart().equals(myOrder.getStart())
				&& newOrder.getRepairType().getRepairTypeId()
					.equals(myOrder.getRepairType().getRepairTypeId())) {
			
			changeSessionScopeUpdateInfo(
					"",
					"", messageSource.getMessage("popup.adminpage.orderNoChanges",
							null,
							locale));
			return "redirect:/updateorder/?order-id=" + myOrder.getOrderId();
		}
		
		if (orderSvc.updateOrderById(myOrder.getOrderId(), newOrder) == 1) {
			changeSessionScopeUpdateInfo(
					"",
					messageSource.getMessage("popup.adminpage.orderUpdated", null,
							locale), 
					"");
			
		} else {
			changeSessionScopeUpdateInfo(
					messageSource.getMessage("popup.adminpage.orderNotUpdated", null,
							locale),
					"", 
					"");
			
		}		
		return "redirect:/updateorder/?order-id=" + myOrder.getOrderId();
	}	
}
