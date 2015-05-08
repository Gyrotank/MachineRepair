package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
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

import com.glomozda.machinerepair.controller.AbstractRolePageController;
import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;

@Controller
public class AdminPageMachinesServiceableController extends AbstractRolePageController implements
		MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageMachinesServiceableController.class.getName());
	
	private String messageEnableDisableFailed = "";
	private String messageEnableDisableSucceeded = "";
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("machineServiceable")) {
			model.addAttribute("machineServiceable", new MachineServiceable());
		}
		
		model.addAttribute("machines_serviceable_short", 
				machineServiceableSvc.getAll(pagingFirstIndex, 
					pagingLastIndex - pagingFirstIndex + 1));
		
		Long machinesServiceableCount = machineServiceableSvc.getMachineServiceableCount();
		model.addAttribute("machines_serviceable_count", 
				machinesServiceableCount);
		 
		Long pagesCount = machinesServiceableCount / DEFAULT_PAGE_SIZE;
		if (machinesServiceableCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("page_number", pageNumber);
		
		model.addAttribute("message_machine_serviceable_added", messageAdded);
		messageAdded = "";
		model.addAttribute("message_machine_serviceable_not_added",
				messageNotAdded);
		messageNotAdded = "";
		
		model.addAttribute("message_enable_disable_failed",
				messageEnableDisableFailed);
		messageEnableDisableFailed = "";
		model.addAttribute("message_enable_disable_succeeded",
				messageEnableDisableSucceeded);
		messageEnableDisableSucceeded = "";
		
		model.addAttribute("dialog_available_machine_serviceable",
				messageSource.getMessage(
					"label.adminpage.machinesServiceable.actions.enable.dialog", null,
					locale));
		model.addAttribute("dialog_not_available_machine_serviceable",
				messageSource.getMessage(
					"label.adminpage.machinesServiceable.actions.disable.dialog", null,
					locale));		
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final Long id) {				
	}
	
	@RequestMapping(value = "/adminpagemachinesserviceable", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		prepareModel(locale, principal, model);
				
		return "adminpagemachinesserviceable";
	}
	
	@RequestMapping(value = "/adminpagemachinesserviceable/machineserviceablepaging",
			method = RequestMethod.POST)
	public String machineServiceablePaging(
			@RequestParam("machineServiceablePageNumber") 
				final Long machineServiceablePageNumber) {
		
		pagingFirstIndex = machineServiceablePageNumber * DEFAULT_PAGE_SIZE;
		pagingLastIndex = 
				DEFAULT_PAGE_SIZE * (machineServiceablePageNumber + 1) - 1;
		pageNumber = machineServiceablePageNumber;				
		
		return "redirect:/adminpagemachinesserviceable";
	}
	
	@RequestMapping(value = "/addMachineServiceable", method = RequestMethod.POST)
	public String addMachineServiceable(
			@ModelAttribute("machineServiceable") 
				@Valid final MachineServiceable machineServiceable,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.machineServiceable", bindingResult);
			redirectAttributes.addFlashAttribute("machineServiceable", machineServiceable);
			return "redirect:/adminpagemachinesserviceable#add_new_serviceable_machine";
		}
		
		if (machineServiceableSvc.add(machineServiceable)) {
			messageAdded =
					messageSource.getMessage("popup.adminpage.machineServiceableAdded", null,
							locale);
		} else {
			messageNotAdded = 
					messageSource.getMessage("popup.adminpage.machineServiceableNotAdded", null,
							locale);
		}		
		return "redirect:/adminpagemachinesserviceable";
	}
	
	@RequestMapping(value = "/setMSAvailable", method = RequestMethod.GET)
	public String setAvailable(
			@RequestParam("machine-serviceable-id") Long machineServiceableId,
			final Locale locale) {
		
		MachineServiceable machineServiceableInQuestion = 
				machineServiceableSvc.getMachineServiceableById(machineServiceableId);
		
		if (machineServiceableInQuestion == null) {			
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.machinesServiceable.actions.failed.msNotExists",
							null, locale);
			return "redirect:/adminpagemachinesserviceable";
		}
		if (machineServiceableInQuestion.getAvailable() != 0) {			
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.machinesServiceable.actions.failed.msNotUnavailable",
							null, locale);
			return "redirect:/adminpagemachinesserviceable";
		}
		
		if (machineServiceableSvc
				.setMachineServiceableAvailableById(machineServiceableId, (byte) 1) == 1) {
			messageEnableDisableSucceeded = 
				messageSource
				.getMessage("popup.adminpage.machinesServiceable.actions.succeeded",
						null, locale);
		} else {
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.machinesServiceable.actions.failed.updateFailed",
							null, locale);
		}
		
		return "redirect:/adminpagemachinesserviceable";
	}
	
	@RequestMapping(value = "/setMSUnavailable", method = RequestMethod.GET)
	public String setUnavailable(
			@RequestParam("machine-serviceable-id") Long machineServiceableId,
			final Locale locale) {
		
		MachineServiceable machineServiceableInQuestion = 
				machineServiceableSvc.getMachineServiceableById(machineServiceableId);
		
		if (machineServiceableInQuestion == null) {			
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.machinesServiceable.actions.failed.msNotExists",
							null, locale);
			return "redirect:/adminpagemachinesserviceable";
		}
		if (machineServiceableInQuestion.getAvailable() != 1) {			
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.machinesServiceable.actions.failed.msNotAvailable",
							null, locale);
			return "redirect:/adminpagemachinesserviceable";
		}
		
		if (machineServiceableSvc
				.setMachineServiceableAvailableById(machineServiceableId, (byte) 0) == 1) {
			messageEnableDisableSucceeded = 
				messageSource
				.getMessage("popup.adminpage.machinesServiceable.actions.succeeded",
						null, locale);
		} else {
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.machinesServiceable.actions.failed.updateFailed",
							null, locale);
		}
		
		return "redirect:/adminpagemachinesserviceable";
	}
}
