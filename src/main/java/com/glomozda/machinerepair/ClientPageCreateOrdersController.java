package com.glomozda.machinerepair;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.domain.repairtype.RepairType;
import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.machine.MachineService;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.service.order.OrderService;
import com.glomozda.machinerepair.service.repairtype.RepairTypeService;

@Controller
public class ClientPageCreateOrdersController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(ClientPageController.class.getName());
	
	@Autowired
	private ClientService clientSvc;
	
	@Autowired
	private MachineService machineSvc;
	
	@Autowired
	private MachineServiceableService machineServiceableSvc;
	
	@Autowired
	private OrderService orderSvc;
	
	@Autowired
	private RepairTypeService repairTypeSvc;
	
	private Client myClient;	
	
	private MessageSource messageSource;
	
	private String messageRepeatedRepair = "";
	private String messageRepeatedRepairSerialNumber = "";
	private String messageRepeatedRepairRepairTypeId = "";
	private String messageRepeatedRepairCreated = "";
	private String messageRepeatedRepairNotCreated = "";
	
	private String enteredRepeatedRepairSerialNumber = "";
	private Long selectedRepeatedRepairRepairTypeId = (long) 0;
	
	private String messageFirstRepairServiceableId = "";
	private String messageFirstRepairSerialNumber = "";
	private String messageFirstRepairYear = "";
	private String messageFirstRepairRepairTypeId = "";
	private String messageFirstRepairCreated = "";
	private String messageFirstRepairNotCreated = "";
	
	private Long selectedFirstRepairServiceableId = (long) 0;
	private String enteredFirstRepairSerialNumber = "";
	private Integer selectedFirstRepairYear = 1950;
	private Long selectedFirstRepairRepairTypeId = (long) 0;
	
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
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/clientpagecreateorders", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myClient = clientSvc.getClientByLoginWithFetching(principal.getName());
		if (null == myClient) {
			return "redirect:/index";
		}		
		
		model.addAttribute("locale", locale.toString());
		
		model.addAttribute("clientname", myClient.getClientName());
		
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
		
		List<RepairType> repairTypes = repairTypeSvc.getAll();
		List<MachineServiceable> machinesServiceable =
				machineServiceableSvc.getAllOrderByTrademark();
		
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
		
		model.addAttribute("message_repeated_order", messageRepeatedRepair);
		messageRepeatedRepair = "";
				
		model.addAttribute("message_repeated_repair_serial_number",
				messageRepeatedRepairSerialNumber);
		messageRepeatedRepairSerialNumber = "";
		model.addAttribute("repeated_repair_entered_serial_number",
				enteredRepeatedRepairSerialNumber);
		enteredRepeatedRepairSerialNumber = "";
		
		model.addAttribute("message_repeated_repair_repairtype_id",
				messageRepeatedRepairRepairTypeId);
		messageRepeatedRepairRepairTypeId = "";
		model.addAttribute("repeated_repair_selected_repairtype_id",
				selectedRepeatedRepairRepairTypeId);
		selectedRepeatedRepairRepairTypeId = (long) 0;
		
		model.addAttribute("message_first_repair_serviceable_id",
				messageFirstRepairServiceableId);
		messageFirstRepairServiceableId = "";
		model.addAttribute("first_repair_selected_serviceable_id",
				selectedFirstRepairServiceableId);
		selectedFirstRepairServiceableId = (long) 0;
		
		model.addAttribute("message_first_repair_serial_number",
				messageFirstRepairSerialNumber);
		messageFirstRepairSerialNumber = "";
		model.addAttribute("first_repair_entered_serial_number",
				enteredFirstRepairSerialNumber);
		enteredFirstRepairSerialNumber = "";
		
		model.addAttribute("message_first_repair_year",
				messageFirstRepairYear);
		messageFirstRepairYear = "";
		model.addAttribute("first_repair_selected_year",
				selectedFirstRepairYear);
		selectedFirstRepairYear = 1950;
		
		model.addAttribute("message_first_repair_repairtype_id",
				messageFirstRepairRepairTypeId);
		messageFirstRepairRepairTypeId = "";
		model.addAttribute("first_repair_selected_repairtype_id",
				selectedFirstRepairRepairTypeId);
		selectedFirstRepairRepairTypeId = (long) 0;
		
		model.addAttribute("machines_serviceable", machinesServiceable);
		model.addAttribute("repair_types", repairTypes);
		
		return "clientpagecreateorders";
	}
	
	@RequestMapping(value = "/createorderforrepeatedrepair", method = RequestMethod.POST)
	public String createOrderForRepeatedRepair(
			@RequestParam("machineSerialNumber") String machineSerialNumber,
			@RequestParam("repairTypeId") Long repairTypeId,
			final Locale locale) {		
		
		if (machineSerialNumber.isEmpty() || repairTypeId == 0) {
			if (machineSerialNumber.isEmpty()) {
				messageRepeatedRepairSerialNumber = 
						messageSource.getMessage("error.clientpage.repeatedRepairSerialNumber",
								null, locale);
			}
			if (repairTypeId == 0) {
				messageRepeatedRepairRepairTypeId =
						messageSource.getMessage("error.clientpage.repeatedRepairRepairTypeId",
								null, locale);
			}
			
			enteredRepeatedRepairSerialNumber = machineSerialNumber;
			selectedRepeatedRepairRepairTypeId = repairTypeId;
			return "redirect:/clientpagecreateorders";
		}
		
		Machine machine = machineSvc.getMachineForSerialNumberWithFetching(machineSerialNumber);
		if (null == machine) {
			messageRepeatedRepair = "Error fetching machine ID";
			return "redirect:/clientpagecreateorders";
		}
		
//		log.info("Adding repeated order...");
//		log.info(orderSvc
//				.getOrderByClientIdAndMachineSNAndNotFinished
//				(myClient.getClientId(), machineSerialNumber));
		
		if (!orderSvc
				.getOrdersByClientIdAndMachineSNAndNotFinished
					(myClient.getClientId(), machineSerialNumber).isEmpty()) {
//			log.info("Order exists already...");
			messageRepeatedRepairNotCreated = 
					messageSource.getMessage("popup.clientpage.orderExistsForSN", null,
							locale);			
			return "redirect:/clientpagecreateorders";
		}
		
		Order order = new Order(new java.sql.Date(new java.util.Date().getTime()));
		order.setManager("-");
		
//		log.info("Order not exists yet...");		
		
		if (orderSvc.add(order, myClient.getClientId(),
				repairTypeId, machine.getMachineId())) {
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
			@RequestParam("machineServiceableId") Long machineServiceableId,
			@RequestParam("machineSerialNumber") String machineSerialNumber,
			@RequestParam("machineYear") String machineYear,
			@RequestParam("repairTypeId") Long repairTypeId,
			final Locale locale) {
		
		if (machineServiceableId == 0 || machineSerialNumber.isEmpty()
				|| machineYear.isEmpty()
				|| !isNonNegativeInteger(machineYear.toString())
				|| Integer.parseInt(machineYear) < 1950
				|| Integer.parseInt(machineYear)
					> java.util.Calendar.getInstance().get(Calendar.YEAR)				
				|| repairTypeId == 0) {
			if (machineServiceableId == 0) {
				messageFirstRepairServiceableId = 
						messageSource.getMessage("error.clientpage.firstRepairServiceableId",
								null, locale);
			}
			if (machineSerialNumber.isEmpty()) {
				messageFirstRepairSerialNumber =
						messageSource.getMessage("error.clientpage.firstRepairSerialNumber",
								null, locale);
			}
			if (machineYear.isEmpty()) {
				messageFirstRepairYear = 
						messageSource.getMessage("error.clientpage.firstRepairYear",
								null, locale);
			}
			if (!isNonNegativeInteger(machineYear.toString())) {
				messageFirstRepairYear = 
						messageSource.getMessage("error.clientpage.firstRepairYearFormat",
								null, locale);
			}
			if (isNonNegativeInteger(machineYear.toString())) {
				if (Integer.parseInt(machineYear) < 1950) {
					messageFirstRepairYear = 
							messageSource.getMessage("Min.machine.machineYear",
								null, locale);
				}
				if (Integer.parseInt(machineYear) >
				java.util.Calendar.getInstance().get(Calendar.YEAR)) {
					messageFirstRepairYear = 
							messageSource.getMessage("error.clientpage.firstRepairYearFuture",
								null, locale);
				}
			}
			if (repairTypeId == 0) {
				messageFirstRepairRepairTypeId = 
						messageSource.getMessage("error.clientpage.firstRepairRepairTypeId",
								null, locale);
			}			
						
			selectedFirstRepairServiceableId = machineServiceableId;
			enteredFirstRepairSerialNumber = machineSerialNumber;
			if (isNonNegativeInteger(machineYear.toString())) {
				selectedFirstRepairYear = Integer.parseInt(machineYear);
			} else {
				selectedFirstRepairYear = 1950;
			}
			selectedFirstRepairRepairTypeId = repairTypeId;
			return "redirect:/clientpagecreateorders";
		}
		
		if (machineSvc.getMachineForSerialNumber(machineSerialNumber) != null) {			
			messageFirstRepairNotCreated = 
					messageSource.getMessage("popup.clientpage.machineExistsForSN", null,
							locale);
			return "redirect:/clientpagecreateorders";
		}
		
		Machine m = new Machine(machineSerialNumber, Integer.parseInt(machineYear));
		machineSvc.add(m, machineServiceableId);
		
		m = machineSvc.getMachineForSerialNumberWithFetching(machineSerialNumber);
		
		Order order = new Order(new java.sql.Date(new java.util.Date().getTime()));
		order.setManager("-");
		
//		log.info(order.toString());
		
		if (orderSvc.add(order, myClient.getClientId(), repairTypeId, m.getMachineId())) {
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
}
