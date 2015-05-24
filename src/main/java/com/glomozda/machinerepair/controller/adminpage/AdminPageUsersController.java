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
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.user.UserDTO;

@Controller
public class AdminPageUsersController extends AbstractRolePageController
	implements MessageSourceAware {

	static Logger log = Logger.getLogger(AdminPageUsersController.class.getName());
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
		
		prepareModelAdminPage(locale, model, new UserDTO(), userSvc);
		
		prepareModelAdminPageWithEnableDisable(locale, model, 
				"label.adminpage.users.actions.enable.dialog",
				"label.adminpage.users.actions.disable.dialog");
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal,
			final Model model, final long id) {				
	}
	
	@RequestMapping(value = "/adminpageusers", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		prepareModel(locale, principal, model);
		
		return "adminpageusers";
	}
	
	@RequestMapping(value = "/adminpageusers/userpaging", method = RequestMethod.POST)
	public String userPaging(@RequestParam("userPageNumber") final Long userPageNumber) {
		
		changeSessionScopePagingInfo(userPageNumber * DEFAULT_PAGE_SIZE,
				DEFAULT_PAGE_SIZE * (userPageNumber + 1) - 1,
				userPageNumber);
		
		return "redirect:/adminpageusers";
	}
	
	@RequestMapping(value = "/enable", method = RequestMethod.GET)
	public String enableUser(@RequestParam("user_id") Long userId, final Locale locale) {
		User userInQuestion = userSvc.getUserById(userId);
		if (userInQuestion == null) {			
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.userNotExists",
							null, locale));
			return "redirect:/adminpageusers";
		}
		if (userInQuestion.getEnabled() != 0) {			
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.userNotDisabled",
							null, locale));
			return "redirect:/adminpageusers";
		}
		
		if (userSvc.setUserEnabledById(userId, (byte) 1) == 1) {
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableSucceeded( 
				messageSource
				.getMessage("popup.adminpage.users.actions.succeeded",
						null, locale));
		} else {
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.updateFailed",
							null, locale));
		}
		return "redirect:/adminpageusers";
	}
	
	@RequestMapping(value = "/disable", method = RequestMethod.GET)
	public String disableUser(@RequestParam("user_id") Long userId, final Locale locale) {
		User userInQuestion = userSvc.getUserById(userId);
		if (userInQuestion == null) {			
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.userNotExists",
							null, locale));
			return "redirect:/adminpageusers";
		}
		if (userInQuestion.getLogin().contentEquals(myUser.getLogin())) {		
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.sameUser",
							null, locale));
			return "redirect:/adminpageusers";
		}
		if (userInQuestion.getEnabled() != 1) {
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.userNotEnabled",
							null, locale));
			return "redirect:/adminpageusers";
		}
		
		if (userSvc.setUserEnabledById(userId, (byte) 0) == 1) {
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableSucceeded( 
				messageSource
				.getMessage("popup.adminpage.users.actions.succeeded",
						null, locale));
		} else {
			sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed( 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.updateFailed",
							null, locale));
		}
		return "redirect:/adminpageusers";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(
			@ModelAttribute("dataObject") @Valid final UserDTO userDTO,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.dataObject", bindingResult);
			redirectAttributes.addFlashAttribute("dataObject", userDTO);
			return "redirect:/adminpageusers#add_new_user";
		}
		
		addMessages(userSvc.add(userDTO.getLogin(), userDTO.getPasswordText()),
				"popup.adminpage.userAdded",
				"popup.adminpage.userNotAdded",
				locale);
		
		return "redirect:/adminpageusers";
	}

}
