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
public class AdminPageMachinesServiceableController implements
		MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageMachinesServiceableController.class.getName());
	
	@Autowired
	private MachineServiceableService machineServiceableSvc;
	
	@Autowired
	private UserService userSvc;
	
	private User myUser;
	
	private MessageSource messageSource;
	
	private static final Long _defaultPageSize = (long) 10;
	
	private String messageEnableDisableFailed = "";
	private String messageEnableDisableSucceeded = "";
	
	private String messageMachineServiceableAdded = "";
	private String messageMachineServiceableNotAdded = "";
	
	private Long machineServiceablePagingFirstIndex = (long) 0;
	private Long machineServiceablePagingLastIndex = _defaultPageSize - 1;
	private Long pageNumber = (long) 0;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/adminpagemachinesserviceable", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("machineServiceable")) {
			model.addAttribute("machineServiceable", new MachineServiceable());
		}
		
		model.addAttribute("machines_serviceable_short", 
				machineServiceableSvc.getAll(machineServiceablePagingFirstIndex, 
					machineServiceablePagingLastIndex - machineServiceablePagingFirstIndex + 1));
		
		Long machinesServiceableCount = machineServiceableSvc.getMachineServiceableCount();
		model.addAttribute("machines_serviceable_count", 
				machinesServiceableCount);
		 
		Long pagesCount = machinesServiceableCount / _defaultPageSize;
		if (machinesServiceableCount % _defaultPageSize != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", _defaultPageSize);
		model.addAttribute("page_number", pageNumber);
		
		model.addAttribute("message_machine_serviceable_added", messageMachineServiceableAdded);
		messageMachineServiceableAdded = "";
		model.addAttribute("message_machine_serviceable_not_added",
				messageMachineServiceableNotAdded);
		messageMachineServiceableNotAdded = "";
		
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
				
		return "adminpagemachinesserviceable";
	}
	
	@RequestMapping(value = "/adminpagemachinesserviceable/machineserviceablepaging",
			method = RequestMethod.POST)
	public String machineServiceablePaging(
			@RequestParam("machineServiceablePageNumber") 
				final Long machineServiceablePageNumber) {
		
		machineServiceablePagingFirstIndex = machineServiceablePageNumber * _defaultPageSize;
		machineServiceablePagingLastIndex = 
				_defaultPageSize * (machineServiceablePageNumber + 1) - 1;
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
			messageMachineServiceableAdded =
					messageSource.getMessage("popup.adminpage.machineServiceableAdded", null,
							locale);
		} else {
			messageMachineServiceableNotAdded = 
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
	public String setUnvailable(
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
