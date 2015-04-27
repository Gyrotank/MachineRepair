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
import com.glomozda.machinerepair.service.user.UserService;

@Controller
public class UpdateMachineController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(UpdateMachineController.class.getName());
	
	@Autowired
	private MachineService machineSvc;
	
	@Autowired
	private UserService userSvc;
	
	private User myUser;
	
	private Machine myMachine;
	
	private MessageSource messageSource;
	
	private String messageMachineUpdateFailed = "";
	private String messageMachineUpdateSucceeded = "";
	private String messageMachineNoChanges = "";
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/updatemachine", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model, 
			@RequestParam("machine-id") final Long machineId) {
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("locale", locale.toString());		
		
		Machine currentMachine = machineSvc.getMachineById(machineId);
		
		if (currentMachine == null) {			
			return "redirect:/adminpagemachines";
		}
		
		myMachine = machineSvc.getMachineById(machineId);
		
		if (!model.containsAttribute("machineCurrent")) {
			model.addAttribute("machineCurrent", myMachine);
		}
		
		if (!model.containsAttribute("machine")) {
			model.addAttribute("machine", myMachine);
		}
		
		model.addAttribute("message_machine_not_updated",
				messageMachineUpdateFailed);
		messageMachineUpdateFailed = "";
		model.addAttribute("message_machine_updated",
				messageMachineUpdateSucceeded);
		messageMachineUpdateSucceeded = "";
		model.addAttribute("message_machine_no_changes",
				messageMachineNoChanges);
		messageMachineNoChanges = "";
		
		return "updatemachine";
	}
	
	@RequestMapping(value = "updatemachine/updateMachine", method = RequestMethod.POST)
	public String updateMachine(@ModelAttribute("machine") @Valid final Machine machine,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
//		log.info("Updating...");
		
		if (machine.getMachineYear() != null)
			if (machine.getMachineYear() > java.util.Calendar.getInstance().get(Calendar.YEAR)) {
				bindingResult.rejectValue("machineYear", "error.adminpage.machineYear", null);
			}
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.machine", bindingResult);
			redirectAttributes.addFlashAttribute("machine", machine);						
			
//			log.info("BindingResult error...");
			return "redirect:/updatemachine/?machine-id=" + myMachine.getMachineId();
		}
		
		if (machine.getMachineSerialNumber().contentEquals(myMachine.getMachineSerialNumber())
				&& machine.getMachineYear().intValue() == myMachine.getMachineYear().intValue()
				&& machine.getMachineTimesRepaired().intValue() 
					== myMachine.getMachineTimesRepaired().intValue()) {
//			log.info("Client has same name");
			messageMachineNoChanges = 
					messageSource.getMessage("popup.adminpage.machineNoChanges", null,
							locale);
			return "redirect:/updatemachine/?machine-id=" + myMachine.getMachineId();
		}
		
		if (machineSvc.updateMachineById(myMachine.getMachineId(), machine)
				== 1) {
			messageMachineUpdateSucceeded =
					messageSource.getMessage("popup.adminpage.machineUpdated", null,
							locale);
//			log.info("Updated");
			return "redirect:/updatemachine/?machine-id=" + myMachine.getMachineId();
		} else {
			messageMachineUpdateFailed = 
					messageSource.getMessage("popup.adminpage.machineNotUpdated", null,
							locale);
//			log.info("Not Updated");
			return "redirect:/updatemachine/?machine-id=" + myMachine.getMachineId();
		}		
	}
}
