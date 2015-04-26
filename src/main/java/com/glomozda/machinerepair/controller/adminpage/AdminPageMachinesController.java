package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.Calendar;
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

import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.service.machine.MachineService;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.service.user.UserService;

@Controller
public class AdminPageMachinesController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageMachinesController.class.getName());
	
	@Autowired
	private MachineService machineSvc;
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private MachineServiceableService machineServiceableSvc;
	
	private User myUser;
	
	private MessageSource messageSource;
	
	private static final Long _defaultPageSize = (long) 25;
	
	private Long machinePagingFirstIndex = (long) 0;
	private Long machinePagingLastIndex = _defaultPageSize - 1;
	
	private String messageMachineAdded = "";
	private String messageMachineNotAdded = "";
	
	private String messageMachineServiceableId = "";
	private Long selectedMachineServiceableId = (long) 0;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/adminpagemachines", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("machine")) {
			model.addAttribute("machine", new Machine());
		}
		
		model.addAttribute("machines_short", 
				machineSvc.getAllWithFetching(machinePagingFirstIndex, 
						machinePagingLastIndex - machinePagingFirstIndex + 1));
		model.addAttribute("machines_count", machineSvc.getMachineCount());
		model.addAttribute("machines_paging_first", machinePagingFirstIndex);
		model.addAttribute("machines_paging_last", machinePagingLastIndex);
		
		model.addAttribute("message_machine_added", messageMachineAdded);
		messageMachineAdded = "";
		model.addAttribute("message_machine_not_added", messageMachineNotAdded);
		messageMachineNotAdded = "";
		
		model.addAttribute("message_machineserviceable_id", messageMachineServiceableId);
		messageMachineServiceableId = "";		
		model.addAttribute("selected_machineserviceable_id", selectedMachineServiceableId);
		selectedMachineServiceableId = (long) 0;
		model.addAttribute("machines_serviceable", machineServiceableSvc.getAllOrderByName());
		
		return "adminpagemachines";
	}
	
	@RequestMapping(value = "/adminpagemachines/machinepaging", method = RequestMethod.POST)
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
		
		return "redirect:/adminpagemachines";
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
			return "redirect:/adminpagemachines#add_new_machine";
		}
		
		if (machineSvc.add(machine, machineServiceableId)) {
			messageMachineAdded =
					messageSource.getMessage("popup.adminpage.machineAdded", null,
							locale);
		} else {
			messageMachineNotAdded = 
					messageSource.getMessage("popup.adminpage.machineNotAdded", null,
							locale);
		}
		return "redirect:/adminpagemachines";
	}
}
