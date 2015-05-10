package com.glomozda.machinerepair.controller.clientpage;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.glomozda.machinerepair.controller.AbstractRolePageController;
import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.domain.order.OrderCreateFirstDTO;
import com.glomozda.machinerepair.domain.order.OrderCreateRepeatedDTO;
import com.glomozda.machinerepair.domain.repairtype.RepairType;

@Controller
public class ClientPageCreateOrdersController extends AbstractRolePageController
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(ClientPageController.class.getName());
	
	private Client myClient;	
	
	private String messageRepeatedRepairCreated = "";
	private String messageRepeatedRepairNotCreated = "";
	
	private String messageFirstRepairCreated = "";
	private String messageFirstRepairNotCreated = "";
	
	private boolean isNonNegativeInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		if (str.charAt(0) == '-') {
			return false;
		}
		for (int i = 0; i < length; i++) {
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
		
		model.addAttribute("locale", locale.toString());
		
		model.addAttribute("clientname", myClient.getClientName());
		
		if (!model.containsAttribute("orderCreateFirstDTO")) {
			model.addAttribute("orderCreateFirstDTO", new OrderCreateFirstDTO());
		}
		
		if (!model.containsAttribute("orderCreateRepeatedDTO")) {
			model.addAttribute("orderCreateRepeatedDTO", new OrderCreateRepeatedDTO());
		}
		
		model.addAttribute("past_orders_count", 
				orderSvc.getCountOrdersForClientIdAndStatus(myClient.getClientId(), "finished"));
		List<Order> myPastOrders =
				orderSvc.getOrdersForClientIdAndStatusWithFetching(myClient.getClientId(),
						"finished");
		
		model.addAttribute("current_orders_count", 
				orderSvc.getCountCurrentOrderForClientId(myClient.getClientId()));
		List<Order> myCurrentOrders =
				orderSvc.getCurrentOrdersForClientIdWithFetching(myClient.getClientId());
		
		ArrayList<String> myMachinesSNs = new ArrayList<String>();
		
		List<RepairType> repairTypes = repairTypeSvc.getAllAvailable();
		List<MachineServiceable> machinesServiceable =
				machineServiceableSvc.getAllAvailableOrderByTrademark();
		
		if (myPastOrders.isEmpty() && myCurrentOrders.isEmpty()) {			
			model.addAttribute("my_machines_serial_numbers", myMachinesSNs);
		} else {
			
			if (!myPastOrders.isEmpty()) {
				for (Order o : myPastOrders) {
					if (!myMachinesSNs.contains(o.getMachine().getMachineSerialNumber())) {
						myMachinesSNs.add(o.getMachine().getMachineSerialNumber());
					}
				}
			}
			
			if (!myCurrentOrders.isEmpty()) {
				for (Order o : myCurrentOrders) {
					if (!myMachinesSNs.contains(o.getMachine().getMachineSerialNumber())) {
						myMachinesSNs.add(o.getMachine().getMachineSerialNumber());
					}
				}
			}

			model.addAttribute("my_machines_serial_numbers", myMachinesSNs);
		}
		
		model.addAttribute("message_repeated_repair_created", messageRepeatedRepairCreated);
		messageRepeatedRepairCreated = "";
		model.addAttribute("message_repeated_repair_not_created",
				messageRepeatedRepairNotCreated);
		messageRepeatedRepairNotCreated = "";
		model.addAttribute("message_first_repair_created", messageFirstRepairCreated);
		messageFirstRepairCreated = "";
		model.addAttribute("message_first_repair_not_created", messageFirstRepairNotCreated);
		messageFirstRepairNotCreated = "";
				
		model.addAttribute("machines_serviceable", machinesServiceable);
		model.addAttribute("repair_types", repairTypes);
		
	}

	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final Long id) {
	}
	
	@RequestMapping(value = "/clientpagecreateorders", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myClient = clientSvc.getClientByLoginWithFetching(principal.getName());
		if (null == myClient) {
			return "redirect:/index";
		}		
		
		prepareModel(locale, principal, model);
		
		return "clientpagecreateorders";
	}
	
	@RequestMapping(value = "/createorderforrepeatedrepair", method = RequestMethod.POST)
	public String createOrderForRepeatedRepair(
			@ModelAttribute("orderCreateRepeatedDTO") 
				@Valid final OrderCreateRepeatedDTO orderCreateRepeatedDTO,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {		
		
		Machine machine = new Machine();
		
		if (orderCreateRepeatedDTO.getMachineSerialNumber() != null 
				&& !orderCreateRepeatedDTO.getMachineSerialNumber().isEmpty()) {
			machine = machineSvc
					.getMachineForSerialNumberWithFetching(orderCreateRepeatedDTO
							.getMachineSerialNumber());
			
			if (null == machine) {
				bindingResult.rejectValue("machineSerialNumber", 
						"error.clientpage.repeatedRepairSerialNumberNoMachine", null);
			}
			
			if (!orderSvc.getOrdersByClientIdAndMachineSNAndNotFinished
					(myClient.getClientId(),
							orderCreateRepeatedDTO.getMachineSerialNumber()).isEmpty()) {
				bindingResult.rejectValue("machineSerialNumber", 
						"popup.clientpage.orderExistsForSN", null);				
			}
		}		
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.orderCreateRepeatedDTO",
						bindingResult);
			redirectAttributes.addFlashAttribute("orderCreateRepeatedDTO", orderCreateRepeatedDTO);
			return "redirect:/clientpagecreateorders";				
		}
		
		Order order = new Order(new java.sql.Date(new java.util.Date().getTime()));
		
		if (orderSvc.add(order, myClient.getClientId(),
				orderCreateRepeatedDTO.getRepairTypeId(), machine.getMachineId(),
				orderStatusSvc.getOrderStatusByName("pending").getOrderStatusId())) {
			messageRepeatedRepairCreated =
					messageSource.getMessage("popup.clientpage.orderAdded", null,
							locale);
		} else {
			messageRepeatedRepairNotCreated = 
					messageSource.getMessage("popup.clientpage.orderNotAdded", null,
							locale);
		}		
		return "redirect:/clientpagecreateorders";
	}
	
	@RequestMapping(value = "/createorderforfirstrepair", method = RequestMethod.POST)
	public String createOrderForFirstRepair(
			@ModelAttribute("orderCreateFirstDTO") 
				@Valid final OrderCreateFirstDTO orderCreateFirstDTO,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (orderCreateFirstDTO.getMachineYear() != null
				&& !orderCreateFirstDTO.getMachineYear().isEmpty()) {
			
			if (!isNonNegativeInteger(orderCreateFirstDTO.getMachineYear().toString())) {				
				bindingResult.rejectValue("machineYear", 
						"error.clientpage.firstRepairYearFormat", null);				
			}
			
			if (isNonNegativeInteger(orderCreateFirstDTO.getMachineYear().toString())) {
				if (Integer.parseInt(orderCreateFirstDTO.getMachineYear()) < 1950) {
					bindingResult.rejectValue("machineYear", 
							"Min.machine.machineYear", null);
				}
				if (Integer.parseInt(orderCreateFirstDTO.getMachineYear()) >
				java.util.Calendar.getInstance().get(Calendar.YEAR)) {
					bindingResult.rejectValue("machineYear", 
							"error.clientpage.firstRepairYearFuture", null);					
				}
			}			
		}
		
		if (orderCreateFirstDTO.getMachineSerialNumber() != null &&
				!orderCreateFirstDTO.getMachineSerialNumber().isEmpty() && 
				machineSvc.getMachineForSerialNumber(orderCreateFirstDTO.getMachineSerialNumber())
					!= null) {
			bindingResult.rejectValue("machineSerialNumber", 
					"popup.clientpage.machineExistsForSN", null);			
		}
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.orderCreateFirstDTO",
						bindingResult);
			redirectAttributes.addFlashAttribute("orderCreateFirstDTO", orderCreateFirstDTO);
			return "redirect:/clientpagecreateorders";				
		}		
		
		Machine m = new Machine(orderCreateFirstDTO.getMachineSerialNumber(), 
				Integer.parseInt(orderCreateFirstDTO.getMachineYear()));
		if (!machineSvc.add(m, orderCreateFirstDTO.getMachineServiceableId())) {
			messageFirstRepairNotCreated = 
					messageSource.getMessage("popup.clientpage.orderNotAdded", null,
							locale);			
			return "redirect:/clientpagecreateorders";
		}
		
		m = machineSvc.getMachineForSerialNumberWithFetching(
				orderCreateFirstDTO.getMachineSerialNumber());
		
		Order order = new Order(new java.sql.Date(new java.util.Date().getTime()));
		
		if (orderSvc.add(order, myClient.getClientId(), orderCreateFirstDTO.getRepairTypeId(),
				m.getMachineId(),
				orderStatusSvc.getOrderStatusByName("pending").getOrderStatusId())) {
			messageFirstRepairCreated =
					messageSource.getMessage("popup.clientpage.orderAdded", null,
							locale);
		} else {
			messageFirstRepairNotCreated = 
					messageSource.getMessage("popup.clientpage.orderNotAdded", null,
							locale);
		}
		return "redirect:/clientpagecreateorders";
	}
}
