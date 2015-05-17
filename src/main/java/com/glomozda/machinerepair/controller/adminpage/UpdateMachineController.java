package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.Calendar;
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
import com.glomozda.machinerepair.domain.machine.Machine;

@Controller
public class UpdateMachineController extends AbstractRolePageController
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(UpdateMachineController.class.getName());
	
	private Machine myMachine;
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {		
	}

	@Override
	protected void prepareModel(final Locale locale, final Principal principal,
			final Model model, final long machineId) {
		
		myMachine = machineSvc.getMachineByIdWithFetching(machineId);
		
		prepareModelUpdate(locale, model, myMachine);		

	}
	
	@RequestMapping(value = "/updatemachine", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model, 
			@RequestParam("machine-id") final Long machineId) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		Machine currentMachine = machineSvc.getMachineById(machineId);
		if (currentMachine == null) {			
			return "redirect:/adminpagemachines";
		}
		
		prepareModel(locale, principal, model, machineId);		
		
		return "updatemachine";
	}
	
	@RequestMapping(value = "updatemachine/updateMachine", method = RequestMethod.POST)
	public String updateMachine(@ModelAttribute("entity") @Valid final Machine machine,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (machine.getMachineYear() != null)
			if (machine.getMachineYear() > java.util.Calendar.getInstance().get(Calendar.YEAR)) {
				bindingResult.rejectValue("machineYear", "error.adminpage.machineYear", null);
			}
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.entity", bindingResult);
			redirectAttributes.addFlashAttribute("entity", machine);						
			
			return "redirect:/updatemachine/?machine-id=" + myMachine.getMachineId();
		}
		
		if (machine.getMachineSerialNumber().contentEquals(myMachine.getMachineSerialNumber())
				&& machine.getMachineYear().intValue() == myMachine.getMachineYear().intValue()
				&& machine.getMachineTimesRepaired().intValue() 
					== myMachine.getMachineTimesRepaired().intValue()) {
			
			changeSessionScopeUpdateInfo(
					"",
					"", messageSource.getMessage("popup.adminpage.machineNoChanges", null,
							locale));
			
			return "redirect:/updatemachine/?machine-id=" + myMachine.getMachineId();
		}
		
		if (machineSvc.updateMachineById(myMachine.getMachineId(), machine)
				== 1) {
			changeSessionScopeUpdateInfo(
					"",
					messageSource.getMessage("popup.adminpage.machineUpdated", null,
							locale), 
					"");
			
			return "redirect:/updatemachine/?machine-id=" + myMachine.getMachineId();
		} else {
			changeSessionScopeUpdateInfo(
					messageSource.getMessage("popup.adminpage.machineNotUpdated", null,
							locale),
					"", 
					"");			

			return "redirect:/updatemachine/?machine-id=" + myMachine.getMachineId();
		}		
	}
}
