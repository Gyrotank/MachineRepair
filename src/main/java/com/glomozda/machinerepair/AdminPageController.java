package com.glomozda.machinerepair;

import java.security.Principal;
import java.util.Calendar;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.domain.repairtype.RepairType;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.repository.client.ClientService;
import com.glomozda.machinerepair.repository.machine.MachineService;
import com.glomozda.machinerepair.repository.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.repository.order.OrderService;
import com.glomozda.machinerepair.repository.repairtype.RepairTypeService;
import com.glomozda.machinerepair.repository.user.UserService;
import com.glomozda.machinerepair.repository.userauthorization.UserAuthorizationService;

@Controller
public class AdminPageController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageController.class.getName());

	@Autowired
	private MachineService machineSvc;
	
	@Autowired
	private MachineServiceableService machineServiceableSvc;

	@Autowired
	private RepairTypeService repairTypeSvc;

	@Autowired
	private UserService userSvc;
	
	@Autowired
	private UserAuthorizationService userAuthorizationSvc;

	@Autowired
	private ClientService clientSvc;

	@Autowired
	private OrderService orderSvc;
	
	@Autowired
	private PasswordEncoder encoder;
	
	private User myUser;
	
	private MessageSource messageSource;
	
	private String messageMachineServiceableId = "";
	private Long selectedMachineServiceableId = (long) 0;
	
	private String messageUserAuthorizationUserId = "";
	private Long selectedUserAuthorizationUserId = (long) 0;

	private String messageClientUserId = "";
	private Long selectedClientUserId = (long) 0;	

	private String messageOrderClientId = "";
	private Long selectedOrderClientId = (long) 0;

	private String messageOrderRepairTypeId = "";
	private Long selectedOrderRepairTypeId = (long) 0;
	
	private String messageOrderMachineId = "";
	private Long selectedOrderMachineId = (long) 0;

	private String messageOrderStart = "";
	private String enteredOrderStart = "";
	 
	public void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/adminpage", method = RequestMethod.GET)
	public String activate(final Principal principal, final Model model) {
		
		log.info("Activating Admin Page for " + principal.getName() + "...");
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		if (!model.containsAttribute("machine")) {
			model.addAttribute("machine", new Machine());
		}
		if (!model.containsAttribute("machineServiceable")) {
			model.addAttribute("machineServiceable", new MachineServiceable());
		}
		if (!model.containsAttribute("repairType")) {
			model.addAttribute("repairType", new RepairType());
		}
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", new User());
		}
		if (!model.containsAttribute("userAuthorization")) {
			model.addAttribute("userAuthorization", new UserAuthorization());
		}
		if (!model.containsAttribute("client")) {
			model.addAttribute("client", new Client());
		}
		if (!model.containsAttribute("order")) {
			model.addAttribute("order", new Order());
		}
		
		model.addAttribute("machines", machineSvc.getAllWithFetching());
		model.addAttribute("machines_serviceable", machineServiceableSvc.getAll()); 
		model.addAttribute("repair_types", repairTypeSvc.getAll());
		model.addAttribute("users", userSvc.getAll());
		model.addAttribute("user_authorizations", userAuthorizationSvc.getAllWithFetching());
		model.addAttribute("user_roles", userAuthorizationSvc.getAllRoles());
		model.addAttribute("clients", clientSvc.getAllWithFetching());
		model.addAttribute("orders", orderSvc.getAllWithFetching());		
		
		model.addAttribute("message_machineserviceable_id", messageMachineServiceableId);
		messageMachineServiceableId = "";		
		model.addAttribute("selected_machineserviceable_id", selectedMachineServiceableId);
		selectedMachineServiceableId = (long) 0;
		
		model.addAttribute("message_user_authorization_user_id", messageUserAuthorizationUserId);
		messageUserAuthorizationUserId = "";		
		model.addAttribute("selected_user_authorization_user_id", selectedUserAuthorizationUserId);
		selectedUserAuthorizationUserId = (long) 0;
		
		model.addAttribute("message_client_user_id", messageClientUserId);
		messageClientUserId = "";		
		model.addAttribute("selected_client_user_id", selectedClientUserId);
		selectedClientUserId = (long) 0;
		
		model.addAttribute("message_order_client_id", messageOrderClientId);
		messageOrderClientId = "";		
		model.addAttribute("selected_order_client_id", selectedOrderClientId);
		selectedOrderClientId = (long) 0;
		
		model.addAttribute("message_order_repair_type_id", messageOrderRepairTypeId);
		messageOrderRepairTypeId = "";		
		model.addAttribute("selected_order_repair_type_id", selectedOrderRepairTypeId);
		selectedOrderRepairTypeId = (long) 0;
		
		model.addAttribute("message_order_machine_id", messageOrderMachineId);
		messageOrderMachineId = "";
		model.addAttribute("selected_order_machine_id", selectedOrderMachineId);
		selectedOrderMachineId = (long) 0;
		
		model.addAttribute("message_order_start", messageOrderStart);
		messageOrderStart = "";
		model.addAttribute("entered_order_start", enteredOrderStart);
		enteredOrderStart = "";
		
		return "adminpage";
	}	
	
	@RequestMapping(value = "/addMachine", method = RequestMethod.POST)
	public String addMachine(@ModelAttribute("machine") @Valid final Machine machine,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,			
			@RequestParam("machineServiceableId") final Long machineServiceableId) {
		
		if (machine.getMachineYear() != null)
			if (machine.getMachineYear() > java.util.Calendar.getInstance().get(Calendar.YEAR)) {
				bindingResult.rejectValue("machineYear", "error.adminpage.machineYear", null);
			}
		
		if (machineServiceableId == 0 || bindingResult.hasErrors()) {
			if (machineServiceableId == 0) {
				messageMachineServiceableId = 
						messageSource.getMessage("error.adminpage.machineServiceableId", null,
								Locale.getDefault());
			}			

			if (bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.machine", bindingResult);
				redirectAttributes.addFlashAttribute("machine", machine);				
			}
			
			selectedMachineServiceableId = machineServiceableId;
			return "redirect:/adminpage#machines";
		}
		
		machineSvc.add(machine, machineServiceableId);
		return "redirect:/adminpage#machines";
	}
	
	@RequestMapping(value = "/addMachineServiceable", method = RequestMethod.POST)
	public String addMachineServiceable(
			@ModelAttribute("machineServiceable") @Valid final MachineServiceable machineServiceable,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.machineServiceable", bindingResult);
			redirectAttributes.addFlashAttribute("machineServiceable", machineServiceable);
			return "redirect:/adminpage#serviceable_machines";
		}
		
		machineServiceableSvc.add(machineServiceable);
		return "redirect:/adminpage#serviceable_machines";
	}

	@RequestMapping(value = "/addRepairType", method = RequestMethod.POST)
	public String addRepairType(
			@ModelAttribute("repairType") @Valid final RepairType repairType,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.repairType", bindingResult);
			redirectAttributes.addFlashAttribute("repairType", repairType);
			return "redirect:/adminpage#repair_types";
		}
		
		repairTypeSvc.add(repairType);
		return "redirect:/adminpage#repair_types";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(
			@ModelAttribute("user") @Valid final User user,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.user", bindingResult);
			redirectAttributes.addFlashAttribute("user", user);
			return "redirect:/adminpage#users";
		}
		
		userSvc.add(new	User(user.getLogin(), user.getPasswordText(),
				encoder.encode(user.getPasswordText())));
		return "redirect:/adminpage#users";
	}
	
	@RequestMapping(value = "/addUserAuthorization", method = RequestMethod.POST)
	public String addUserAuthorization
		(@ModelAttribute("userAuthorization") @Valid final UserAuthorization userAuthorization,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			@RequestParam("userId") final Long userId) {
		
		if (userId == 0 || bindingResult.hasErrors()) {
			if (userId == 0) {
				messageUserAuthorizationUserId = 
						messageSource.getMessage("error.adminpage.userId", null,
								Locale.getDefault());			
			}

			if (bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.userAuthorization", bindingResult);
				redirectAttributes.addFlashAttribute("userAuthorization", userAuthorization);				
			}
			
			selectedUserAuthorizationUserId = userId;
			return "redirect:/adminpage#user_auths";
		}
		
		userAuthorizationSvc.add(new UserAuthorization(userAuthorization.getRole()),
				userId);
		return "redirect:/adminpage#user_auths";
	}

	@RequestMapping(value = "/addClient", method = RequestMethod.POST)
	public String addClient(@ModelAttribute("client") @Valid final Client client,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			@RequestParam("userId") final Long userId) {
		
		if (userId == 0 || bindingResult.hasErrors()) {
			if (userId == 0) {
				messageClientUserId = 
						messageSource.getMessage("error.adminpage.userId", null,
								Locale.getDefault());			
			}

			if (bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.client", bindingResult);
				redirectAttributes.addFlashAttribute("client", client);				
			}
			
			selectedClientUserId = userId;
			return "redirect:/adminpage#clients";
		}
		
		clientSvc.add(client, userId);
		return "redirect:/adminpage#clients";
	}

	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public String addOrder(@ModelAttribute("order") @Valid final Order order,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			@RequestParam("clientId") final Long clientId, 
			@RequestParam("repairTypeId") final Long repairTypeId, 
			@RequestParam("machineId") final Long machineId,
			@RequestParam("startDate") final String startDate) {
		
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
		
		if (clientId == 0 || repairTypeId == 0 || 
				machineId == 0 || startSqlDate == null || bindingResult.hasErrors()) {
			
			if (clientId == 0) {
				messageOrderClientId = 
						messageSource.getMessage("error.adminpage.clientId", null,
								Locale.getDefault());			
			}

			if (repairTypeId == 0) {
				messageOrderRepairTypeId = 
						messageSource.getMessage("error.adminpage.repairTypeId", null,
								Locale.getDefault());			
			}

			if (machineId == 0) {
				messageOrderMachineId = 
						messageSource.getMessage("error.adminpage.machineId", null,
								Locale.getDefault());			
			}
			
			if (startSqlDate == null) {
				messageOrderStart = 
						messageSource.getMessage("typeMismatch.order.start", null,
								Locale.getDefault());
			}

			if (bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.order", bindingResult);
				redirectAttributes.addFlashAttribute("order", order);			
			}
			
			selectedOrderClientId = clientId;
			selectedOrderRepairTypeId = repairTypeId;
			selectedOrderMachineId = machineId;
			enteredOrderStart = startDate;
			return "redirect:/adminpage#orders";
		}
		
		order.setStart(startSqlDate);		
		orderSvc.add(order, clientId, repairTypeId, machineId);
		return "redirect:/adminpage#orders";
	}
}