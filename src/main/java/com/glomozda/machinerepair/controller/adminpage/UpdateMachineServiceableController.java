package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
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

import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.service.user.UserService;

@Controller
public class UpdateMachineServiceableController implements MessageSourceAware {

	static Logger log = Logger.getLogger(UpdateMachineServiceableController.class.getName());
	
	@Autowired
	private MachineServiceableService machineServiceableSvc;
	
	@Autowired
	private UserService userSvc;
	
	private User myUser;
	
	private MachineServiceable myMachineServiceable;
	
	private MessageSource messageSource;
	
	private String messageMachineServiceableUpdateFailed = "";
	private String messageMachineServiceableUpdateSucceeded = "";
	private String messageMachineServiceableNoChanges = "";
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/updatemachineserviceable", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model, 
			@RequestParam("machine-serviceable-id") final Long machineServiceableId) {
		
		if (null == principal) {
			return "redirect:/index";
		}
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("locale", locale.toString());		
		
		MachineServiceable currentMachineServiceable = 
				machineServiceableSvc.getMachineServiceableById(machineServiceableId);
		
		if (currentMachineServiceable == null) {			
			return "redirect:/adminpagemachinesserviceable";
		}
		
		myMachineServiceable = machineServiceableSvc.getMachineServiceableById(machineServiceableId);
		
		if (!model.containsAttribute("machineServiceableCurrent")) {
			model.addAttribute("machineServiceableCurrent", myMachineServiceable);
		}
		
		if (!model.containsAttribute("machineServiceable")) {
			model.addAttribute("machineServiceable", myMachineServiceable);
		}
		
		model.addAttribute("message_machine_serviceable_not_updated",
				messageMachineServiceableUpdateFailed);
		messageMachineServiceableUpdateFailed = "";
		model.addAttribute("message_machine_serviceable_updated",
				messageMachineServiceableUpdateSucceeded);
		messageMachineServiceableUpdateSucceeded = "";
		model.addAttribute("message_machine_serviceable_no_changes",
				messageMachineServiceableNoChanges);
		messageMachineServiceableNoChanges = "";
		
		return "updatemachineserviceable";
	}
	
	@RequestMapping(value = "updatemachineserviceable/updateMachineServiceable",
			method = RequestMethod.POST)
	public String updateMachineServiceable(
			@ModelAttribute("machineServiceable") @Valid final MachineServiceable machineServiceable,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
//		log.info("Updating...");
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.machineServiceable", bindingResult);
			redirectAttributes.addFlashAttribute("machineServiceable", machineServiceable);
			return "redirect:/updatemachineserviceable/?machine-serviceable-id="
				+ myMachineServiceable.getMachineServiceableId();
		}
		
		if (machineServiceable.equals(myMachineServiceable)) {
//			log.info("Client has same name");
			messageMachineServiceableNoChanges = 
					messageSource.getMessage("popup.adminpage.machineServiceableNoChanges", null,
							locale);
			return "redirect:/updatemachineserviceable/?machine-serviceable-id="
				+ myMachineServiceable.getMachineServiceableId();
		}
		
		if (machineServiceableSvc.updateMachineServiceableById(
				myMachineServiceable.getMachineServiceableId(),
				machineServiceable)
				== 1) {
			messageMachineServiceableUpdateSucceeded =
					messageSource.getMessage("popup.adminpage.machineServiceableUpdated", null,
							locale);
//			log.info("Updated");
			return "redirect:/updatemachineserviceable/?machine-serviceable-id="
				+ myMachineServiceable.getMachineServiceableId();
		} else {
			messageMachineServiceableUpdateFailed = 
					messageSource.getMessage("popup.adminpage.machineServiceableNotUpdated", null,
							locale);
//			log.info("Not Updated");
			return "redirect:/updatemachineserviceable/?machine-serviceable-id="
				+ myMachineServiceable.getMachineServiceableId();
		}		
	}
}
