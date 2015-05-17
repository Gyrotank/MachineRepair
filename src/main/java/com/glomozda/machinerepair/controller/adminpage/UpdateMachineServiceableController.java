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
			final Model model, final long machineServiceableId) {
		
		myMachineServiceable = 
				machineServiceableSvc.getMachineServiceableById(machineServiceableId);
		
		prepareModelUpdate(locale, model, myMachineServiceable);		
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
			@ModelAttribute("entity") @Valid final MachineServiceable machineServiceable,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.entity", bindingResult);
			redirectAttributes.addFlashAttribute("entity", machineServiceable);
			return "redirect:/updatemachineserviceable/?machine-serviceable-id="
				+ myMachineServiceable.getMachineServiceableId();
		}
		
		if (machineServiceable.equals(myMachineServiceable)) {
			changeSessionScopeUpdateInfo(
					"",
					"", messageSource.getMessage("popup.adminpage.machineServiceableNoChanges",
							null,
							locale));
			
			return "redirect:/updatemachineserviceable/?machine-serviceable-id="
				+ myMachineServiceable.getMachineServiceableId();
		}
		
		if (machineServiceableSvc.updateMachineServiceableById(
				myMachineServiceable.getMachineServiceableId(),
				machineServiceable)
				== 1) {
			changeSessionScopeUpdateInfo(
					"",
					messageSource.getMessage("popup.adminpage.machineServiceableUpdated", null,
							locale), 
					"");			

			return "redirect:/updatemachineserviceable/?machine-serviceable-id="
				+ myMachineServiceable.getMachineServiceableId();
		} else {
			changeSessionScopeUpdateInfo(
					messageSource.getMessage("popup.adminpage.machineServiceableNotUpdated", null,
							locale),
					"", 
					"");
			
			return "redirect:/updatemachineserviceable/?machine-serviceable-id="
				+ myMachineServiceable.getMachineServiceableId();
		}		
	}
}
