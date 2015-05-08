package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
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
import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;

@Controller
public class UpdateMachineServiceableController extends AbstractRolePageController 
	implements MessageSourceAware {

	static Logger log = Logger.getLogger(UpdateMachineServiceableController.class.getName());
	
	private MachineServiceable myMachineServiceable;
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {				
	}

	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final Long machineServiceableId) {
		
		model.addAttribute("locale", locale.toString());
		
		myMachineServiceable = 
				machineServiceableSvc.getMachineServiceableById(machineServiceableId);
		
		if (!model.containsAttribute("machineServiceableCurrent")) {
			model.addAttribute("machineServiceableCurrent", myMachineServiceable);
		}
		
		if (!model.containsAttribute("machineServiceable")) {
			model.addAttribute("machineServiceable", myMachineServiceable);
		}
		
		model.addAttribute("message_machine_serviceable_not_updated",
				messageUpdateFailed);
		messageUpdateFailed = "";
		model.addAttribute("message_machine_serviceable_updated",
				messageUpdateSucceeded);
		messageUpdateSucceeded = "";
		model.addAttribute("message_machine_serviceable_no_changes",
				messageNoChanges);
		messageNoChanges = "";
	}
	
	@RequestMapping(value = "/updatemachineserviceable", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model, 
			@RequestParam("machine-serviceable-id") final Long machineServiceableId) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		MachineServiceable currentMachineServiceable = 
				machineServiceableSvc.getMachineServiceableById(machineServiceableId);
		if (currentMachineServiceable == null) {			
			return "redirect:/adminpagemachinesserviceable";
		}
		
		prepareModel(locale, principal, model, machineServiceableId);		
		
		return "updatemachineserviceable";
	}
	
	@RequestMapping(value = "updatemachineserviceable/updateMachineServiceable",
			method = RequestMethod.POST)
	public String updateMachineServiceable(
			@ModelAttribute("machineServiceable") @Valid final MachineServiceable machineServiceable,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.machineServiceable", bindingResult);
			redirectAttributes.addFlashAttribute("machineServiceable", machineServiceable);
			return "redirect:/updatemachineserviceable/?machine-serviceable-id="
				+ myMachineServiceable.getMachineServiceableId();
		}
		
		if (machineServiceable.equals(myMachineServiceable)) {
			messageNoChanges = 
					messageSource.getMessage("popup.adminpage.machineServiceableNoChanges", null,
							locale);
			return "redirect:/updatemachineserviceable/?machine-serviceable-id="
				+ myMachineServiceable.getMachineServiceableId();
		}
		
		if (machineServiceableSvc.updateMachineServiceableById(
				myMachineServiceable.getMachineServiceableId(),
				machineServiceable)
				== 1) {
			messageUpdateSucceeded =
					messageSource.getMessage("popup.adminpage.machineServiceableUpdated", null,
							locale);

			return "redirect:/updatemachineserviceable/?machine-serviceable-id="
				+ myMachineServiceable.getMachineServiceableId();
		} else {
			messageUpdateFailed = 
					messageSource.getMessage("popup.adminpage.machineServiceableNotUpdated", null,
							locale);

			return "redirect:/updatemachineserviceable/?machine-serviceable-id="
				+ myMachineServiceable.getMachineServiceableId();
		}		
	}
}
