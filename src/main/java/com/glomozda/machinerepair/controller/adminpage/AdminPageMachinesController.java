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
import com.glomozda.machinerepair.domain.machine.MachineDTO;

@Controller
public class AdminPageMachinesController extends AbstractRolePageController
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageMachinesController.class.getName());
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("machineDTO")) {
			model.addAttribute("machineDTO", new MachineDTO());
		}
		
		model.addAttribute("machines_short", 
				machineSvc.getAllWithFetching(
						sessionScopeInfoService.getSessionScopeInfo().getPagingFirstIndex(), 
						sessionScopeInfoService.getSessionScopeInfo().getPagingLastIndex() 
						- sessionScopeInfoService
							.getSessionScopeInfo().getPagingFirstIndex() + 1));
		
		Long machinesCount = machineSvc.getMachineCount();
		model.addAttribute("machines_count", machinesCount);
		
		Long pagesCount = machinesCount / DEFAULT_PAGE_SIZE;
		if (machinesCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		Long pageNumber = sessionScopeInfoService.getSessionScopeInfo().getPageNumber();
		if (pageNumber >= pagesCount) {
			model.addAttribute("page_number", 0L);
			model.addAttribute("machines_short", 
					machineSvc.getAllWithFetching(
							0L, DEFAULT_PAGE_SIZE));
		} else {
			model.addAttribute("page_number", pageNumber);
			model.addAttribute("machines_short", 
					machineSvc.getAllWithFetching(
							sessionScopeInfoService.getSessionScopeInfo().getPagingFirstIndex(), 
							sessionScopeInfoService.getSessionScopeInfo().getPagingLastIndex() 
							- sessionScopeInfoService
								.getSessionScopeInfo().getPagingFirstIndex() + 1));
		}
		
		model.addAttribute("message_machine_added", 
			sessionScopeInfoService.getSessionScopeInfo().getMessageAdded());
		sessionScopeInfoService.getSessionScopeInfo().setMessageAdded("");
		model.addAttribute("message_machine_not_added",
			sessionScopeInfoService.getSessionScopeInfo().getMessageNotAdded());
		sessionScopeInfoService.getSessionScopeInfo().setMessageNotAdded("");
		
		model.addAttribute("machines_serviceable", machineServiceableSvc.getAllOrderByName());
		
		model.addAttribute("dialog_delete_machine",
				messageSource.getMessage("label.adminpage.machines.actions.delete.dialog", null,
				locale));		
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final long id) {				
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
		
		changeSessionScopePagingInfo(machinePageNumber * DEFAULT_PAGE_SIZE,
				DEFAULT_PAGE_SIZE * (machinePageNumber + 1) - 1,
				machinePageNumber);
		
		return "redirect:/adminpagemachines";
	}
	
	@RequestMapping(value = "/addMachine", method = RequestMethod.POST)
	public String addMachine(@ModelAttribute("machineDTO") @Valid final MachineDTO machineDTO,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,			
			final Locale locale) {
		
		if (machineDTO.getMachineYear() != null)
			if (machineDTO.getMachineYear() 
					> java.util.Calendar.getInstance().get(Calendar.YEAR)) {
				bindingResult.rejectValue("machineYear", "error.adminpage.machineYear", null);
			}
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.machineDTO", bindingResult);
			redirectAttributes.addFlashAttribute("machineDTO", machineDTO);			
			return "redirect:/adminpagemachines#add_new_machine";
		}
		
		Machine newMachine = new Machine(machineDTO.getMachineSerialNumber(),
				machineDTO.getMachineYear(), machineDTO.getMachineTimesRepaired());
		
		if (machineSvc.add(newMachine, machineDTO.getMachineServiceableId())) {
			changeSessionScopeAddingInfo(
					messageSource.getMessage("popup.adminpage.machineAdded", null,locale),
					"");			
		} else {
			changeSessionScopeAddingInfo(
					"",
					messageSource.getMessage("popup.adminpage.machineNotAdded", null,
							locale));			
		}
		return "redirect:/adminpagemachines";
	}
}
