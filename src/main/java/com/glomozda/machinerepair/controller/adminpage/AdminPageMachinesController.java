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
public class AdminPageMachinesController extends AbstractRolePageController
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageMachinesController.class.getName());
	
	private String messageMachineServiceableId = "";
	private Long selectedMachineServiceableId = 0L;
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("machine")) {
			model.addAttribute("machine", new Machine());
		}
		
		model.addAttribute("machines_short", 
				machineSvc.getAllWithFetching(pagingFirstIndex, 
						pagingLastIndex - pagingFirstIndex + 1));
		
		Long machinesCount = machineSvc.getMachineCount();
		model.addAttribute("machines_count", machinesCount);
		
		Long pagesCount = machinesCount / DEFAULT_PAGE_SIZE;
		if (machinesCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("page_number", pageNumber);
		
		model.addAttribute("message_machine_added", messageAdded);
		messageAdded = "";
		model.addAttribute("message_machine_not_added", messageNotAdded);
		messageNotAdded = "";
		
		model.addAttribute("message_machineserviceable_id", messageMachineServiceableId);
		messageMachineServiceableId = "";		
		model.addAttribute("selected_machineserviceable_id", selectedMachineServiceableId);
		selectedMachineServiceableId = 0L;
		model.addAttribute("machines_serviceable", machineServiceableSvc.getAllOrderByName());
		
		model.addAttribute("dialog_delete_machine",
				messageSource.getMessage("label.adminpage.machines.actions.delete.dialog", null,
				locale));		
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final Long id) {				
	}
	
	@RequestMapping(value = "/adminpagemachines", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		prepareModel(locale, principal, model);		
		
		return "adminpagemachines";
	}
	
	@RequestMapping(value = "/adminpagemachines/machinepaging", method = RequestMethod.POST)
	public String machinePaging(@RequestParam("machinePageNumber") final Long machinePageNumber) {
		
		pagingFirstIndex = machinePageNumber * DEFAULT_PAGE_SIZE;
		pagingLastIndex = DEFAULT_PAGE_SIZE * (machinePageNumber + 1) - 1;
		pageNumber = machinePageNumber;
		
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
			messageAdded =
					messageSource.getMessage("popup.adminpage.machineAdded", null,
							locale);
		} else {
			messageNotAdded = 
					messageSource.getMessage("popup.adminpage.machineNotAdded", null,
							locale);
		}
		return "redirect:/adminpagemachines";
	}
}
