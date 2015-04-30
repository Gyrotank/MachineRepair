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

import com.glomozda.machinerepair.domain.repairtype.RepairType;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.service.repairtype.RepairTypeService;
import com.glomozda.machinerepair.service.user.UserService;

@Controller
public class UpdateRepairTypeController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(UpdateRepairTypeController.class.getName());
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private RepairTypeService repairTypeSvc;
	
	private User myUser;
	
	private RepairType myRepairType;
	
	private MessageSource messageSource;
	
	private String messageRepairTypeUpdateFailed = "";
	private String messageRepairTypeUpdateSucceeded = "";
	private String messageRepairTypeNoChanges = "";
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/updaterepairtype", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model, 
			@RequestParam("repair-type-id") final Long repairTypeId) {
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("locale", locale.toString());
		
		myRepairType = repairTypeSvc.getRepairTypeById(repairTypeId);
		
		if (!model.containsAttribute("repairTypeCurrent")) {
			model.addAttribute("repairTypeCurrent", myRepairType);
		}
		
		if (!model.containsAttribute("repairType")) {
			model.addAttribute("repairType", myRepairType);
		}
		
		model.addAttribute("message_repair_type_not_updated",
				messageRepairTypeUpdateFailed);
		messageRepairTypeUpdateFailed = "";
		model.addAttribute("message_repair_type_updated",
				messageRepairTypeUpdateSucceeded);
		messageRepairTypeUpdateSucceeded = "";
		model.addAttribute("message_repair_type_no_changes",
				messageRepairTypeNoChanges);
		messageRepairTypeNoChanges = "";		
		
		return "updaterepairtype";
	}
	
	@RequestMapping(value = "updaterepairtype/updateRepairType", method = RequestMethod.POST)
	public String updateRepairType(
			@ModelAttribute("repairType") @Valid final RepairType repairType,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.repairType", bindingResult);
			redirectAttributes.addFlashAttribute("repairType", repairType);
			return "redirect:/updaterepairtype/?repair-type-id=" + myRepairType.getRepairTypeId();
		}
		
		if (repairType.equals(myRepairType)) {
			messageRepairTypeNoChanges = 
					messageSource.getMessage("popup.adminpage.repairTypeNoChanges", null,
							locale);
			return "redirect:/updaterepairtype/?repair-type-id=" + myRepairType.getRepairTypeId();
		}
		
		if (repairTypeSvc.updateRepairTypeById(myRepairType.getRepairTypeId(),
				repairType) == 1) {
			messageRepairTypeUpdateSucceeded =
					messageSource.getMessage("popup.adminpage.repairTypeUpdated", null,
							locale);
		} else {
			messageRepairTypeUpdateFailed = 
					messageSource.getMessage("popup.adminpage.repairTypeNotUpdated", null,
							locale);
		}		
		return "redirect:/updaterepairtype/?repair-type-id=" + myRepairType.getRepairTypeId();
	}
}