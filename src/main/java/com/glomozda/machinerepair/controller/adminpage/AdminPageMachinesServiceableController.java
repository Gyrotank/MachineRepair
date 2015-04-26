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
	
	static Logger log = Logger.getLogger(AdminPageController.class.getName());
	
	@Autowired
	private MachineServiceableService machineServiceableSvc;
	
	@Autowired
	private UserService userSvc;
	
	private User myUser;
	
	private MessageSource messageSource;
	
	private static final Long _defaultPageSize = (long) 25;
	
	private String messageMachineServiceableAdded = "";
	private String messageMachineServiceableNotAdded = "";
	private Long machineServiceablePagingFirstIndex = (long) 0;
	private Long machineServiceablePagingLastIndex = _defaultPageSize - 1;
	
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
		model.addAttribute("machines_serviceable_count", 
				machineServiceableSvc.getMachineServiceableCount());
		model.addAttribute("machines_serviceable_paging_first", 
				machineServiceablePagingFirstIndex);
		model.addAttribute("machines_serviceable_paging_last", 
				machineServiceablePagingLastIndex);
		
		model.addAttribute("message_machine_serviceable_added", messageMachineServiceableAdded);
		messageMachineServiceableAdded = "";
		model.addAttribute("message_machine_serviceable_not_added",
				messageMachineServiceableNotAdded);
		messageMachineServiceableNotAdded = "";		
				
		return "adminpagemachinesserviceable";
	}
	
	@RequestMapping(value = "/adminpagemachinesserviceable/machineserviceablepaging",
			method = RequestMethod.POST)
	public String machineServiceablePaging(
			@RequestParam("machineServiceablePageStart") final Long machineServiceablePageStart, 
			@RequestParam("machineServiceablePageEnd") final Long machineServiceablePageEnd) {
		
		long machineServiceableStart;
		long machineServiceableEnd;
		
		if (machineServiceablePageStart == null) {
			machineServiceableStart = (long) 0;
		} else {
			machineServiceableStart = machineServiceablePageStart.longValue() - 1;
		}
		
		if (machineServiceablePageEnd == null) {
			machineServiceableEnd = (long) 0;
		} else {
			machineServiceableEnd = machineServiceablePageEnd.longValue() - 1;
		}		
		
		long machineServiceableCount = machineServiceableSvc.getMachineServiceableCount();
		
		if (machineServiceableStart > machineServiceableEnd) {
			long temp = machineServiceableStart;
			machineServiceableStart = machineServiceableEnd;
			machineServiceableEnd = temp;
		}
		
		if (machineServiceableStart < 0)
			machineServiceableStart = 0;
		
		if (machineServiceableStart >= machineServiceableCount)
			machineServiceableStart = machineServiceableCount - 1;
		
		if (machineServiceableEnd < 0)
			machineServiceableEnd = 0;
		
		if (machineServiceableEnd >= machineServiceableCount)
			machineServiceableEnd = machineServiceableCount - 1;
		
		machineServiceablePagingFirstIndex = machineServiceableStart;
		machineServiceablePagingLastIndex = machineServiceableEnd;		
		
		return "redirect:/adminpagemachinesserviceable";
	}
	
	@RequestMapping(value = "/addMachineServiceable", method = RequestMethod.POST)
	public String addMachineServiceable(
			@ModelAttribute("machineServiceable") @Valid final MachineServiceable machineServiceable,
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
}
