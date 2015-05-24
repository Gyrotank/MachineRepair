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
public class AdminPageRepairTypesController extends AbstractRolePageController 
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageRepairTypesController.class.getName());

	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {

		prepareModelAdminPage(locale, model, new RepairType(), repairTypeSvc);
		
		prepareModelAdminPageWithEnableDisable(locale, model, 
				"label.adminpage.repairTypes.actions.enable.dialog",
				"label.adminpage.repairTypes.actions.disable.dialog");	
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final long id) {				
	}

	@RequestMapping(value = "/adminpagerepairtypes", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		prepareModel(locale, principal, model);
				
		return "adminpagerepairtypes";
	}
	
	@RequestMapping(value = "/adminpagerepairtypes/repairtypepaging", method = RequestMethod.POST)
	public String repairTypePaging(
			@RequestParam("repairTypePageNumber") final Long repairTypePageNumber) {
		
		changeSessionScopePagingInfo(repairTypePageNumber * DEFAULT_PAGE_SIZE,
				DEFAULT_PAGE_SIZE * (repairTypePageNumber + 1) - 1,
				repairTypePageNumber);		 	
		
		return "redirect:/adminpagerepairtypes";
	}
	
	@RequestMapping(value = "/addRepairType", method = RequestMethod.POST)
	public String addRepairType(
			@ModelAttribute("dataObject") @Valid final RepairType repairType,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.dataObject", bindingResult);
			redirectAttributes.addFlashAttribute("dataObject", repairType);
			return "redirect:/adminpagerepairtypes#add_new_repair_type";
		}
		
		addMessages(repairTypeSvc.add(repairType),
				"popup.adminpage.repairTypeAdded",
				"popup.adminpage.repairTypeNotAdded",
				locale);
		
		return "redirect:/adminpagerepairtypes";
	}
	
	@RequestMapping(value = "/setRTAvailable", method = RequestMethod.GET)
	public String setAvailable(
			@RequestParam("repair-type-id") Long repairTypeId,
			final Locale locale) {
		
		RepairType repairTypeInQuestion = 
				repairTypeSvc.getRepairTypeById(repairTypeId);
		
		if (repairTypeInQuestion == null) {			
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.repairTypes.actions.failed.rtNotExists",
							null, locale));
			return "redirect:/adminpagerepairtypes";
		}
		if (repairTypeInQuestion.getAvailable() != 0) {			
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.repairTypes.actions.failed.rtNotUnavailable",
							null, locale));
			return "redirect:/adminpagerepairtypes";
		}
		
		if (repairTypeSvc
				.setRepairTypeAvailableById(repairTypeId, (byte) 1) == 1) {
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableSucceeded( 
				messageSource
				.getMessage("popup.adminpage.repairTypes.actions.succeeded",
						null, locale));
		} else {
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.repairTypes.actions.failed.updateFailed",
							null, locale));
		}
		
		return "redirect:/adminpagerepairtypes";
	}
	
	@RequestMapping(value = "/setRTUnavailable", method = RequestMethod.GET)
	public String setUnavailable(
			@RequestParam("repair-type-id") Long repairTypeId,
			final Locale locale) {
		
		RepairType repairTypeInQuestion = 
				repairTypeSvc.getRepairTypeById(repairTypeId);
		
		if (repairTypeInQuestion == null) {			
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.repairTypes.actions.failed.rtNotExists",
							null, locale));
			return "redirect:/adminpagerepairtypes";
		}
		if (repairTypeInQuestion.getAvailable() != 1) {			
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.repairTypes.actions.failed.rtNotAvailable",
							null, locale));
			return "redirect:/adminpagerepairtypes";
		}
		
		if (repairTypeSvc
				.setRepairTypeAvailableById(repairTypeId, (byte) 0) == 1) {
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableSucceeded( 
				messageSource
				.getMessage("popup.adminpage.repairTypes.actions.succeeded",
						null, locale));
		} else {
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.repairTypes.actions.failed.updateFailed",
							null, locale));
		}
		
		return "redirect:/adminpagerepairtypes";
	}	
}
