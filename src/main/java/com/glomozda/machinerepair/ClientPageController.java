package com.glomozda.machinerepair;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.domain.repairtype.RepairType;
import com.glomozda.machinerepair.repository.client.ClientService;
import com.glomozda.machinerepair.repository.machine.MachineService;
import com.glomozda.machinerepair.repository.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.repository.order.OrderService;
import com.glomozda.machinerepair.repository.repairtype.RepairTypeService;

import org.apache.log4j.Logger;

@Controller
public class ClientPageController implements MessageSourceAware {
	
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
	
	private String messageRepeatedRepair = "";
	private String messageRepeatedRepairSerialNumber = "";
	private String messageRepeatedRepairRepairTypeId = "";
	
	private String enteredRepeatedRepairSerialNumber = "";
	private Integer selectedRepeatedRepairRepairTypeId = 0;
	
	private String messageFirstRepairServiceableId = "";
	private String messageFirstRepairSerialNumber = "";
	private String messageFirstRepairYear = "";
	private String messageFirstRepairRepairTypeId = "";
	
	private Integer selectedFirstRepairServiceableId = 0;
	private String enteredFirstRepairSerialNumber = "";
	private Integer selectedFirstRepairYear = 0;
	private Integer selectedFirstRepairRepairTypeId = 0;
	
	private MessageSource messageSource;
	 
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/clientpage", method = RequestMethod.GET)
	public String activate(final Principal principal, final Model model) {
		
		log.info("Activating Client Page for " + principal.getName() + "...");
		
		myClient = clientSvc.getClientByLoginWithFetching(principal.getName());
		if (null == myClient) {
			return "redirect:/index";
		}		
		
		model.addAttribute("clientname", myClient.getClientName());
				
		List<Order> myPastOrders =
				orderSvc.getOrdersForClientIdAndStatusWithFetching(myClient.getClientId(), "finished");
		List<Order> myCurrentOrders =
				orderSvc.getOrdersForClientIdAndStatusWithFetching(myClient.getClientId(), "pending");
		myCurrentOrders.addAll(
				orderSvc.getOrdersForClientIdAndStatusWithFetching(myClient.getClientId(), "ready"));
		myCurrentOrders.addAll(
				orderSvc.getOrdersForClientIdAndStatusWithFetching(myClient.getClientId(), "started"));		
		
		ArrayList<String> myMachinesSNs = new ArrayList<String>();
		
		List<RepairType> repairTypes = repairTypeSvc.getAll();
		List<MachineServiceable> machinesServiceable =
				machineServiceableSvc.getAllOrderByTrademark();
		
		if (myPastOrders.isEmpty() && myCurrentOrders.isEmpty()) {
			model.addAttribute("message", "You have no orders yet.");			
			model.addAttribute("my_past_orders", "");
			model.addAttribute("my_current_orders", "");
			model.addAttribute("my_machines_serial_numbers", myMachinesSNs);
		} else {
			model.addAttribute("message", "");
			
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
			model.addAttribute("my_past_orders", myPastOrders);
			model.addAttribute("my_current_orders", myCurrentOrders);			
			model.addAttribute("my_machines_serial_numbers", myMachinesSNs);
		}
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
		selectedRepeatedRepairRepairTypeId = 0;
		
		model.addAttribute("message_first_repair_serviceable_id",
				messageFirstRepairServiceableId);
		messageFirstRepairServiceableId = "";
		model.addAttribute("first_repair_selected_serviceable_id",
				selectedFirstRepairServiceableId);
		selectedFirstRepairServiceableId = 0;
		
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
		selectedFirstRepairYear = 0;
		
		model.addAttribute("message_first_repair_repairtype_id",
				messageFirstRepairRepairTypeId);
		messageFirstRepairRepairTypeId = "";
		model.addAttribute("first_repair_selected_repairtype_id",
				selectedFirstRepairRepairTypeId);
		selectedFirstRepairRepairTypeId = 0;
		
		model.addAttribute("machines_serviceable", machinesServiceable);
		model.addAttribute("repair_types", repairTypes);
		
		return "clientpage";
	}
	
	@RequestMapping(value = "/createorderforrepeatedrepair", method = RequestMethod.POST)
	public String createOrderForRepeatedRepair(
			@RequestParam("machineSerialNumber") String machineSerialNumber,
			@RequestParam("repairTypeId") Integer repairTypeId) {		
		
		if (machineSerialNumber.isEmpty() || repairTypeId == 0) {
			if (machineSerialNumber.isEmpty()) {
				messageRepeatedRepairSerialNumber = 
						messageSource.getMessage("error.clientpage.repeatedRepairSerialNumber",
								null, Locale.getDefault());
			}
			if (repairTypeId == 0) {
				messageRepeatedRepairRepairTypeId =
						messageSource.getMessage("error.clientpage.repeatedRepairRepairTypeId",
								null, Locale.getDefault());
			}
			
			enteredRepeatedRepairSerialNumber = machineSerialNumber;
			selectedRepeatedRepairRepairTypeId = repairTypeId;
			return "redirect:/clientpage";
		}
		
		Machine machine = machineSvc.getMachineForSerialNumberWithFetching(machineSerialNumber);
		if (null == machine) {
			messageRepeatedRepair = "Error fetching machine ID";
			return "redirect:/clientpage";
		}		
		
		Order order = new Order(new java.sql.Date(new java.util.Date().getTime()));
		
		orderSvc.add(order, myClient.getClientId(),
				repairTypeId, machine.getMachineId());
		return "redirect:/clientpage";
	}
	
	@RequestMapping(value = "/createorderforfirstrepair", method = RequestMethod.POST)
	public String createOrderForFirstRepair(
			@RequestParam("machineServiceableId") Integer machineServiceableId,
			@RequestParam("machineSerialNumber") String machineSerialNumber,
			@RequestParam("machineYear") Integer machineYear,
			@RequestParam("repairTypeId") Integer repairTypeId) {
		
		if (machineServiceableId == 0 || machineSerialNumber.isEmpty() ||
				machineYear == 0 || repairTypeId == 0) {
			if (machineServiceableId == 0) {
				messageFirstRepairServiceableId = 
						messageSource.getMessage("error.clientpage.firstRepairServiceableId",
								null, Locale.getDefault());
			}
			if (machineSerialNumber.isEmpty()) {
				messageFirstRepairSerialNumber =
						messageSource.getMessage("error.clientpage.firstRepairSerialNumber",
								null, Locale.getDefault());
			}
			if (machineYear == 0) {
				messageFirstRepairYear = 
						messageSource.getMessage("error.clientpage.firstRepairYear",
								null, Locale.getDefault());
			}
			if (repairTypeId == 0) {
				messageFirstRepairRepairTypeId = 
						messageSource.getMessage("error.clientpage.firstRepairRepairTypeId",
								null, Locale.getDefault());
			}
			
			selectedFirstRepairServiceableId = machineServiceableId;
			enteredFirstRepairSerialNumber = machineSerialNumber;
			selectedFirstRepairYear = machineYear;
			selectedFirstRepairRepairTypeId = repairTypeId;
			return "redirect:/clientpage";
		}
		
		Machine m = new Machine(machineSerialNumber, machineYear);
		machineSvc.add(m, machineServiceableId);
		
		m = machineSvc.getMachineForSerialNumberWithFetching(machineSerialNumber);
		
		Order order = new Order(new java.sql.Date(new java.util.Date().getTime()));
		
		orderSvc.add(order, myClient.getClientId(), repairTypeId, m.getMachineId());
		return "redirect:/clientpage";
	}
	
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public String setOrderFinished(@RequestParam("order_id") Integer orderId) {
		orderSvc.setOrderStatusById(orderId, "finished");		
		return "redirect:/clientpage";
	}
}
