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
import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.machine.MachineService;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.service.order.OrderService;
import com.glomozda.machinerepair.service.repairtype.RepairTypeService;
import com.glomozda.machinerepair.service.user.UserService;
import com.glomozda.machinerepair.service.userauthorization.UserAuthorizationService;

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
	
	private static final Long _defaultPageSize = (long) 25;
	
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
	
	private Long machinePagingFirstIndex = (long) 0;
	private Long machinePagingLastIndex = _defaultPageSize - 1;
	
	private Long machineServiceablePagingFirstIndex = (long) 0;
	private Long machineServiceablePagingLastIndex = _defaultPageSize - 1;
	
	private Long repairTypePagingFirstIndex = (long) 0;
	private Long repairTypePagingLastIndex = _defaultPageSize - 1;
	
	private Long userPagingFirstIndex = (long) 0;
	private Long userPagingLastIndex = _defaultPageSize - 1;
	
	private Long userAuthorizationPagingFirstIndex = (long) 0;
	private Long userAuthorizationPagingLastIndex = _defaultPageSize - 1;
	
	private Long clientPagingFirstIndex = (long) 0;
	private Long clientPagingLastIndex = _defaultPageSize - 1;
	
	private Long orderPagingFirstIndex = (long) 0;
	private Long orderPagingLastIndex = _defaultPageSize - 1;
	 
	
	public void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/adminpage", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
//		log.info("Activating Admin Page for " + principal.getName() + "...");
//		log.info("Locale is " + locale.toString());
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("locale", locale.toString());
		
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
		
		model.addAttribute("machines_short", 
				machineSvc.getAllWithFetching(machinePagingFirstIndex, 
						machinePagingLastIndex - machinePagingFirstIndex + 1));
		model.addAttribute("machines_count", machineSvc.getMachineCount());
		model.addAttribute("machines_paging_first", machinePagingFirstIndex);
		model.addAttribute("machines_paging_last", machinePagingLastIndex);
		
		model.addAttribute("machines_serviceable", machineServiceableSvc.getAllOrderByName());
		model.addAttribute("machines_serviceable_short", 
				machineServiceableSvc.getAll(machineServiceablePagingFirstIndex, 
					machineServiceablePagingLastIndex - machineServiceablePagingFirstIndex + 1));
		model.addAttribute("machines_serviceable_count", 
				machineServiceableSvc.getMachineServiceableCount());
		model.addAttribute("machines_serviceable_paging_first", 
				machineServiceablePagingFirstIndex);
		model.addAttribute("machines_serviceable_paging_last", 
				machineServiceablePagingLastIndex);
		
		model.addAttribute("repair_types", repairTypeSvc.getAll());
		model.addAttribute("repair_types_short", 
				repairTypeSvc.getAll(repairTypePagingFirstIndex, 
						repairTypePagingLastIndex - repairTypePagingFirstIndex + 1));
		model.addAttribute("repair_types_count", 
				repairTypeSvc.getRepairTypeCount());
		model.addAttribute("repair_types_paging_first", 
				repairTypePagingFirstIndex);
		model.addAttribute("repair_types_paging_last", 
				repairTypePagingLastIndex);
		
		model.addAttribute("users_short", 
				userSvc.getAll(userPagingFirstIndex, 
						userPagingLastIndex - userPagingFirstIndex + 1));
		model.addAttribute("users_count", userSvc.getUserCount());
		model.addAttribute("users_paging_first", userPagingFirstIndex);
		model.addAttribute("users_paging_last", userPagingLastIndex);
		
		model.addAttribute("user_authorizations_short", 
				userAuthorizationSvc.getAllWithFetching(userAuthorizationPagingFirstIndex, 
						userAuthorizationPagingLastIndex - userAuthorizationPagingFirstIndex + 1)
						);
		model.addAttribute("user_authorizations_short_users", 
				userAuthorizationSvc.getDistinctUsersWithFetching(userAuthorizationPagingFirstIndex, 
						userAuthorizationPagingLastIndex - userAuthorizationPagingFirstIndex + 1)
				);
		model.addAttribute("user_authorizations_count",
				userAuthorizationSvc.getUserAuthorizationCount());
		model.addAttribute("user_authorizations_paging_first", userAuthorizationPagingFirstIndex);
		model.addAttribute("user_authorizations_paging_last", userAuthorizationPagingLastIndex);
		
		model.addAttribute("user_roles", userAuthorizationSvc.getAllRoles());
		
		model.addAttribute("clients_short",
				clientSvc.getAllWithFetching(clientPagingFirstIndex,
						clientPagingLastIndex - clientPagingFirstIndex + 1));
		model.addAttribute("clients_count", clientSvc.getClientCount());
		model.addAttribute("clients_paging_first", clientPagingFirstIndex);
		model.addAttribute("clients_paging_last", clientPagingLastIndex);
		
		model.addAttribute("orders_short", 
				orderSvc.getAllWithFetching(orderPagingFirstIndex,
						orderPagingLastIndex - orderPagingFirstIndex + 1));
		model.addAttribute("orders_count", orderSvc.getOrderCount());
		model.addAttribute("orders_paging_first", orderPagingFirstIndex);
		model.addAttribute("orders_paging_last", orderPagingLastIndex);
		
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
	
	@RequestMapping(value = "/adminpage/machinepaging", method = RequestMethod.POST)
	public String machinePaging(@RequestParam("machinePageStart") final Long machinePageStart, 
			@RequestParam("machinePageEnd") final Long machinePageEnd) {
		
		long machineStart;
		long machineEnd;
		
		if (machinePageStart == null) {
			machineStart = (long) 0;
		} else {
			machineStart = machinePageStart.longValue() - 1;
		}
		
		if (machinePageEnd == null) {
			machineEnd = (long) 0;
		} else {
			machineEnd = machinePageEnd.longValue() - 1;
		}		
		
		long machineCount = machineSvc.getMachineCount();
		
		if (machineStart > machineEnd) {
			long temp = machineStart;
			machineStart = machineEnd;
			machineEnd = temp;
		}
		
		if (machineStart < 0)
			machineStart = 0;
		
		if (machineStart >= machineCount)
			machineStart = machineCount - 1;
		
		if (machineEnd < 0)
			machineEnd = 0;
		
		if (machineEnd >= machineCount)
			machineEnd = machineCount - 1;
		
		machinePagingFirstIndex = machineStart;
		machinePagingLastIndex = machineEnd;		
		
		return "redirect:/adminpage#machines";
	}
	
	@RequestMapping(value = "/addMachine", method = RequestMethod.POST)
	public String addMachine(@ModelAttribute("machine") @Valid final Machine machine,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,			
			@RequestParam("machineServiceableId") final Long machineServiceableId,
			final Locale locale) {
		
		if (machine.getMachineYear() != null)
			if (machine.getMachineYear() > java.util.Calendar.getInstance().get(Calendar.YEAR)) {
				bindingResult.rejectValue("machineYear", "error.adminpage.machineYear", null);
			}
		
		if (machineServiceableId == 0 || bindingResult.hasErrors()) {
			if (machineServiceableId == 0) {
				messageMachineServiceableId = 
						messageSource.getMessage("error.adminpage.machineServiceableId", null,
								locale);
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
	
	@RequestMapping(value = "/adminpage/machineserviceablepaging", method = RequestMethod.POST)
	public String machineServiceablePaging(
			@RequestParam("machineServiceablePageStart") final Long machineServiceablePageStart, 
			@RequestParam("machineServiceablePageEnd") final Long machineServiceablePageEnd) {
		
		long machineServiceableStart;
		long machineServiceableEnd;
		
		if (machineServiceablePageStart == null) {
			machineServiceableStart = (long) 0;
		} else {
			machineServiceableStart = machineServiceablePageStart.longValue() - 1;
		}
		
		if (machineServiceablePageEnd == null) {
			machineServiceableEnd = (long) 0;
		} else {
			machineServiceableEnd = machineServiceablePageEnd.longValue() - 1;
		}		
		
		long machineServiceableCount = machineServiceableSvc.getMachineServiceableCount();
		
		if (machineServiceableStart > machineServiceableEnd) {
			long temp = machineServiceableStart;
			machineServiceableStart = machineServiceableEnd;
			machineServiceableEnd = temp;
		}
		
		if (machineServiceableStart < 0)
			machineServiceableStart = 0;
		
		if (machineServiceableStart >= machineServiceableCount)
			machineServiceableStart = machineServiceableCount - 1;
		
		if (machineServiceableEnd < 0)
			machineServiceableEnd = 0;
		
		if (machineServiceableEnd >= machineServiceableCount)
			machineServiceableEnd = machineServiceableCount - 1;
		
		machineServiceablePagingFirstIndex = machineServiceableStart;
		machineServiceablePagingLastIndex = machineServiceableEnd;		
		
		return "redirect:/adminpage#serviceable_machines";
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

	@RequestMapping(value = "/adminpage/repairtypepaging", method = RequestMethod.POST)
	public String repairTypePaging(
			@RequestParam("repairTypePageStart") final Long repairTypePageStart, 
			@RequestParam("repairTypePageEnd") final Long repairTypePageEnd) {
		
		long repairTypeStart;
		long repairTypeEnd;
		
		if (repairTypePageStart == null) {
			repairTypeStart = (long) 0;
		} else {
			repairTypeStart = repairTypePageStart.longValue() - 1;
		}
		
		if (repairTypePageEnd == null) {
			repairTypeEnd = (long) 0;
		} else {
			repairTypeEnd = repairTypePageEnd.longValue() - 1;
		}
		
		long repairTypeCount = repairTypeSvc.getRepairTypeCount();
		
		if (repairTypeStart > repairTypeEnd) {
			long temp = repairTypeStart;
			repairTypeStart = repairTypeEnd;
			repairTypeEnd = temp;
		}
		
		if (repairTypeStart < 0)
			repairTypeStart = 0;
		
		if (repairTypeStart >= repairTypeCount)
			repairTypeStart = repairTypeCount - 1;
		
		if (repairTypeEnd < 0)
			repairTypeEnd = 0;
		
		if (repairTypeEnd >= repairTypeCount)
			repairTypeEnd = repairTypeCount - 1;
		
		repairTypePagingFirstIndex = repairTypeStart;
		repairTypePagingLastIndex = repairTypeEnd;		
		
		return "redirect:/adminpage#repair_types";
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
	
	@RequestMapping(value = "/adminpage/userpaging", method = RequestMethod.POST)
	public String userPaging(@RequestParam("userPageStart") final Long userPageStart, 
			@RequestParam("userPageEnd") final Long userPageEnd) {
		
		long userStart;
		long userEnd;
		
		if (userPageStart == null) {
			userStart = (long) 0;
		} else {
			userStart = userPageStart.longValue() - 1;
		}
		
		if (userPageEnd == null) {
			userEnd = (long) 0;
		} else {
			userEnd = userPageEnd.longValue() - 1;
		}
		
		long userCount = userSvc.getUserCount();
		
		if (userStart > userEnd) {
			long temp = userStart;
			userStart = userEnd;
			userEnd = temp;
		}
		
		if (userStart < 0)
			userStart = 0;
		
		if (userStart >= userCount)
			userStart = userCount - 1;
		
		if (userEnd < 0)
			userEnd = 0;
		
		if (userEnd >= userCount)
			userEnd = userCount - 1;
		
		userPagingFirstIndex = userStart;
		userPagingLastIndex = userEnd;		
		
		return "redirect:/adminpage#users";
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
	
	@RequestMapping(value = "/adminpage/userauthorizationpaging", method = RequestMethod.POST)
	public String userAuthorizationPaging(
			@RequestParam("userAuthorizationPageStart") final Long userAuthorizationPageStart, 
			@RequestParam("userAuthorizationPageEnd") final Long userAuthorizationPageEnd) {
		
		long userAuthorizationStart;
		long userAuthorizationEnd;
		
		if (userAuthorizationPageStart == null) {
			userAuthorizationStart = (long) 0;
		} else {
			userAuthorizationStart = userAuthorizationPageStart.longValue() - 1;
		}
		
		if (userAuthorizationPageEnd == null) {
			userAuthorizationEnd = (long) 0;
		} else {
			userAuthorizationEnd = userAuthorizationPageEnd.longValue() - 1;
		}
		
		long userAuthorizationCount = userAuthorizationSvc.getUserAuthorizationCount();
		
		if (userAuthorizationStart > userAuthorizationEnd) {
			long temp = userAuthorizationStart;
			userAuthorizationStart = userAuthorizationEnd;
			userAuthorizationEnd = temp;
		}
		
		if (userAuthorizationStart < 0)
			userAuthorizationStart = 0;
		
		if (userAuthorizationStart >= userAuthorizationCount)
			userAuthorizationStart = userAuthorizationCount - 1;
		
		if (userAuthorizationEnd < 0)
			userAuthorizationEnd = 0;
		
		if (userAuthorizationEnd >= userAuthorizationCount)
			userAuthorizationEnd = userAuthorizationCount - 1;
		
		userAuthorizationPagingFirstIndex = userAuthorizationStart;
		userAuthorizationPagingLastIndex = userAuthorizationEnd;		
		
		return "redirect:/adminpage#user_auths";
	}
	
	@RequestMapping(value = "/addUserAuthorization", method = RequestMethod.POST)
	public String addUserAuthorization
		(@ModelAttribute("userAuthorization") @Valid final UserAuthorization userAuthorization,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			@RequestParam("userId") final Long userId,
			final Locale locale) {
		
		if (userId == 0 || bindingResult.hasErrors()) {
			if (userId == 0) {
				messageUserAuthorizationUserId = 
						messageSource.getMessage("error.adminpage.userId", null,
								locale);			
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
	
	@RequestMapping(value = "/adminpage/clientpaging", method = RequestMethod.POST)
	public String clientPaging(@RequestParam("clientPageStart") final Long clientPageStart, 
			@RequestParam("clientPageEnd") final Long clientPageEnd) {
		
		long clientStart;
		long clientEnd;
		
		if (clientPageStart == null) {
			clientStart = (long) 0;
		} else {
			clientStart = clientPageStart.longValue() - 1;
		}
		
		if (clientPageEnd == null) {
			clientEnd = (long) 0;
		} else {
			clientEnd = clientPageEnd.longValue() - 1;
		}
		
		long clientCount = clientSvc.getClientCount();
		
		if (clientStart > clientEnd) {
			long temp = clientStart;
			clientStart = clientEnd;
			clientEnd = temp;
		}
		
		if (clientStart < 0)
			clientStart = 0;
		
		if (clientStart >= clientCount)
			clientStart = clientCount - 1;
		
		if (clientEnd < 0)
			clientEnd = 0;
		
		if (clientEnd >= clientCount)
			clientEnd = clientCount - 1;
		
		clientPagingFirstIndex = clientStart;
		clientPagingLastIndex = clientEnd;		
		
		return "redirect:/adminpage#clients";
	}

	@RequestMapping(value = "/addClient", method = RequestMethod.POST)
	public String addClient(@ModelAttribute("client") @Valid final Client client,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			@RequestParam("userId") final Long userId,
			final Locale locale) {
		
		if (userId == 0 || bindingResult.hasErrors()) {
			if (userId == 0) {
				messageClientUserId = 
						messageSource.getMessage("error.adminpage.userId", null,
								locale);			
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

	@RequestMapping(value = "/adminpage/orderpaging", method = RequestMethod.POST)
	public String orderPaging(@RequestParam("orderPageStart") final Long orderPageStart, 
			@RequestParam("orderPageEnd") final Long orderPageEnd) {
		
		long orderStart;
		long orderEnd;
		
		if (orderPageStart == null) {
			orderStart = (long) 0;
		} else {
			orderStart = orderPageStart.longValue() - 1;
		}
		
		if (orderPageEnd == null) {
			orderEnd = (long) 0;
		} else {
			orderEnd = orderPageEnd.longValue() - 1;
		}
		
		long orderCount = orderSvc.getOrderCount();
		
		if (orderStart > orderEnd) {
			long temp = orderStart;
			orderStart = orderEnd;
			orderEnd = temp;
		}
		
		if (orderStart < 0)
			orderStart = 0;
		
		if (orderStart >= orderCount)
			orderStart = orderCount - 1;
		
		if (orderEnd < 0)
			orderEnd = 0;
		
		if (orderEnd >= orderCount)
			orderEnd = orderCount - 1;
		
		orderPagingFirstIndex = orderStart;
		orderPagingLastIndex = orderEnd;		
		
		return "redirect:/adminpage#orders";
	}
	
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public String addOrder(@ModelAttribute("order") @Valid final Order order,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			@RequestParam("clientId") final Long clientId, 
			@RequestParam("repairTypeId") final Long repairTypeId, 
			@RequestParam("machineId") final Long machineId,
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
		
		if (clientId == 0 || repairTypeId == 0 || 
				machineId == 0 || startSqlDate == null || bindingResult.hasErrors()) {
			
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
			enteredOrderStart = startDate;
			return "redirect:/adminpage#orders";
		}
		
		order.setStart(startSqlDate);		
		orderSvc.add(order, clientId, repairTypeId, machineId);
		return "redirect:/adminpage#orders";
	}	
}