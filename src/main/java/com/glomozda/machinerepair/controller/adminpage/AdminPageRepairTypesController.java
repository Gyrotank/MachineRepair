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
public class AdminPageRepairTypesController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageRepairTypesController.class.getName());
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private RepairTypeService repairTypeSvc;
	
	private User myUser;
	
	private MessageSource messageSource;
	
	private static final Long _defaultPageSize = (long) 10;
	
	private String messageEnableDisableFailed = "";
	private String messageEnableDisableSucceeded = "";
	
	private String messageRepairTypeAdded = "";
	private String messageRepairTypeNotAdded = "";
	
	private Long repairTypePagingFirstIndex = (long) 0;
	private Long repairTypePagingLastIndex = _defaultPageSize - 1;
	private Long pageNumber = (long) 0;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping(value = "/adminpagerepairtypes", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("repairType")) {
			model.addAttribute("repairType", new RepairType());
		}
		
		model.addAttribute("repair_types", repairTypeSvc.getAll());
		model.addAttribute("repair_types_short", 
				repairTypeSvc.getAll(repairTypePagingFirstIndex, 
						repairTypePagingLastIndex - repairTypePagingFirstIndex + 1));
		
		Long repairTypesCount = repairTypeSvc.getRepairTypeCount();
		model.addAttribute("repair_types_count", repairTypesCount);
		Long pagesCount = repairTypesCount / _defaultPageSize;
		if (repairTypesCount % _defaultPageSize != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", _defaultPageSize);
		model.addAttribute("page_number", pageNumber);
		
		model.addAttribute("message_repair_type_added",
				messageRepairTypeAdded);
		messageRepairTypeAdded = "";
		model.addAttribute("message_repair_type_not_added",
				messageRepairTypeNotAdded);
		messageRepairTypeNotAdded = "";
		
		model.addAttribute("message_enable_disable_failed",
				messageEnableDisableFailed);
		messageEnableDisableFailed = "";
		model.addAttribute("message_enable_disable_succeeded",
				messageEnableDisableSucceeded);
		messageEnableDisableSucceeded = "";
		
		model.addAttribute("dialog_available_repair_type",
				messageSource.getMessage(
					"label.adminpage.repairTypes.actions.enable.dialog", null,
					locale));
		model.addAttribute("dialog_not_available_repair_type",
				messageSource.getMessage(
					"label.adminpage.repairTypes.actions.disable.dialog", null,
					locale));
				
		return "adminpagerepairtypes";
	}
	
	@RequestMapping(value = "/adminpagerepairtypes/repairtypepaging", method = RequestMethod.POST)
	public String repairTypePaging(
			@RequestParam("repairTypePageNumber") final Long repairTypePageNumber) {
		
		repairTypePagingFirstIndex = repairTypePageNumber * _defaultPageSize;
		repairTypePagingLastIndex = 
				_defaultPageSize * (repairTypePageNumber + 1) - 1;
		pageNumber = repairTypePageNumber;		 	
		
		return "redirect:/adminpagerepairtypes";
	}
	
	@RequestMapping(value = "/addRepairType", method = RequestMethod.POST)
	public String addRepairType(
			@ModelAttribute("repairType") @Valid final RepairType repairType,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.repairType", bindingResult);
			redirectAttributes.addFlashAttribute("repairType", repairType);
			return "redirect:/adminpagerepairtypes#add_new_repair_type";
		}
		
		if (repairTypeSvc.add(repairType)) {
			messageRepairTypeAdded =
					messageSource.getMessage("popup.adminpage.repairTypeAdded", null,
							locale);
		} else {
			messageRepairTypeNotAdded = 
					messageSource.getMessage("popup.adminpage.repairTypeNotAdded", null,
							locale);
		}		
		return "redirect:/adminpagerepairtypes";
	}
	
	@RequestMapping(value = "/setRTAvailable", method = RequestMethod.GET)
	public String setAvailable(
			@RequestParam("repair-type-id") Long repairTypeId,
			final Locale locale) {
		
		RepairType repairTypeInQuestion = 
				repairTypeSvc.getRepairTypeById(repairTypeId);
		
		if (repairTypeInQuestion == null) {			
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.repairTypes.actions.failed.rtNotExists",
							null, locale);
			return "redirect:/adminpagerepairtypes";
		}
		if (repairTypeInQuestion.getAvailable() != 0) {			
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.repairTypes.actions.failed.rtNotUnavailable",
							null, locale);
			return "redirect:/adminpagerepairtypes";
		}
		
		if (repairTypeSvc
				.setRepairTypeAvailableById(repairTypeId, (byte) 1) == 1) {
			messageEnableDisableSucceeded = 
				messageSource
				.getMessage("popup.adminpage.repairTypes.actions.succeeded",
						null, locale);
		} else {
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.repairTypes.actions.failed.updateFailed",
							null, locale);
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
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.repairTypes.actions.failed.rtNotExists",
							null, locale);
			return "redirect:/adminpagerepairtypes";
		}
		if (repairTypeInQuestion.getAvailable() != 1) {			
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.repairTypes.actions.failed.rtNotAvailable",
							null, locale);
			return "redirect:/adminpagerepairtypes";
		}
		
		if (repairTypeSvc
				.setRepairTypeAvailableById(repairTypeId, (byte) 0) == 1) {
			messageEnableDisableSucceeded = 
				messageSource
				.getMessage("popup.adminpage.repairTypes.actions.succeeded",
						null, locale);
		} else {
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.repairTypes.actions.failed.updateFailed",
							null, locale);
		}
		
		return "redirect:/adminpagerepairtypes";
	}
}
