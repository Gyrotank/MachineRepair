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
	
	private static final Long _defaultPageSize = (long) 25;
	
	private String messageRepairTypeAdded = "";
	private String messageRepairTypeNotAdded = "";
	private Long repairTypePagingFirstIndex = (long) 0;
	private Long repairTypePagingLastIndex = _defaultPageSize - 1;
	
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
		model.addAttribute("repair_types_count", 
				repairTypeSvc.getRepairTypeCount());
		model.addAttribute("repair_types_paging_first", 
				repairTypePagingFirstIndex);
		model.addAttribute("repair_types_paging_last", 
				repairTypePagingLastIndex);
		
		model.addAttribute("message_repair_type_added",
				messageRepairTypeAdded);
		messageRepairTypeAdded = "";
		model.addAttribute("message_repair_type_not_added",
				messageRepairTypeNotAdded);
		messageRepairTypeNotAdded = "";		
				
		return "adminpagerepairtypes";
	}
	
	@RequestMapping(value = "/adminpagerepairtypes/repairtypepaging", method = RequestMethod.POST)
	public String repairTypePaging(
			@RequestParam("repairTypePageStart") final Long repairTypePageStart, 
			@RequestParam("repairTypePageEnd") final Long repairTypePageEnd) {
		
		long repairTypeStart;
		long repairTypeEnd;
		
		if (repairTypePageStart == null) {
			repairTypeStart = (long) 0;
		} else {
			repairTypeStart = repairTypePageStart.longValue() - 1;
		}
		
		if (repairTypePageEnd == null) {
			repairTypeEnd = (long) 0;
		} else {
			repairTypeEnd = repairTypePageEnd.longValue() - 1;
		}
		
		long repairTypeCount = repairTypeSvc.getRepairTypeCount();
		
		if (repairTypeStart > repairTypeEnd) {
			long temp = repairTypeStart;
			repairTypeStart = repairTypeEnd;
			repairTypeEnd = temp;
		}
		
		if (repairTypeStart < 0)
			repairTypeStart = 0;
		
		if (repairTypeStart >= repairTypeCount)
			repairTypeStart = repairTypeCount - 1;
		
		if (repairTypeEnd < 0)
			repairTypeEnd = 0;
		
		if (repairTypeEnd >= repairTypeCount)
			repairTypeEnd = repairTypeCount - 1;
		
		repairTypePagingFirstIndex = repairTypeStart;
		repairTypePagingLastIndex = repairTypeEnd;		
		
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
}
