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
import com.glomozda.machinerepair.domain.repairtype.RepairType;

@Controller
public class UpdateRepairTypeController extends AbstractRolePageController 
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(UpdateRepairTypeController.class.getName());
	
	private RepairType myRepairType;
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
	}

	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final long repairTypeId) {
		
		myRepairType = repairTypeSvc.getRepairTypeById(repairTypeId);
		
		prepareModelUpdate(locale, model, myRepairType);
	
	}
	
	@RequestMapping(value = "/updaterepairtype", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model, 
			@RequestParam("repair-type-id") final Long repairTypeId) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		RepairType currentRepairType = repairTypeSvc.getRepairTypeById(repairTypeId);
		if (currentRepairType == null) {			
			return "redirect:/adminpageorders";
		}		
		
		prepareModel(locale, principal, model, repairTypeId);
		
		return "updaterepairtype";
	}
	
	@RequestMapping(value = "updaterepairtype/updateRepairType", method = RequestMethod.POST)
	public String updateRepairType(
			@ModelAttribute("entity") @Valid final RepairType repairType,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.entity", bindingResult);
			redirectAttributes.addFlashAttribute("entity", repairType);			
			return "redirect:/updaterepairtype/?repair-type-id=" + myRepairType.getRepairTypeId();
		}
		
		if (repairType.equals(myRepairType)) {
			changeSessionScopeUpdateInfo(
					"",
					"", messageSource.getMessage("popup.adminpage.repairTypeNoChanges",
							null,
							locale));
						
			return "redirect:/updaterepairtype/?repair-type-id=" + myRepairType.getRepairTypeId();
		}
		
		if (repairTypeSvc.updateRepairTypeById(myRepairType.getRepairTypeId(),
				repairType) == 1) {
			changeSessionScopeUpdateInfo(
					"",
					messageSource.getMessage("popup.adminpage.repairTypeUpdated", null,
							locale), 
					"");
					
		} else {
			changeSessionScopeUpdateInfo(
					messageSource.getMessage("popup.adminpage.repairTypeNotUpdated", null,
							locale),
					"", 
					"");
						
		}		
		return "redirect:/updaterepairtype/?repair-type-id=" + myRepairType.getRepairTypeId();
	}
}