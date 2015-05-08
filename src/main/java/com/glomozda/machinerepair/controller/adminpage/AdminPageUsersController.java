package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@Controller
public class AdminPageUsersController extends AbstractRolePageController
	implements MessageSourceAware {

	static Logger log = Logger.getLogger(AdminPageUsersController.class.getName());
	
	@Autowired
	private PasswordEncoder encoder;
	
	private String messageEnableDisableFailed = "";
	private String messageEnableDisableSucceeded = "";
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", new User());
		}
		
		model.addAttribute("users_short", 
				userSvc.getAll(pagingFirstIndex, 
						pagingLastIndex - pagingFirstIndex + 1));
		
		Long usersCount = userSvc.getUserCount();
		model.addAttribute("users_count", usersCount);
		Long pagesCount = usersCount / DEFAULT_PAGE_SIZE;
		if (usersCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("page_number", pageNumber);
		
		model.addAttribute("dialog_enable_user",
				messageSource.getMessage("label.adminpage.users.actions.enable.dialog", null,
				locale));
		model.addAttribute("dialog_disable_user",
				messageSource.getMessage("label.adminpage.users.actions.disable.dialog", null,
				locale));
		
		model.addAttribute("message_enable_disable_failed",
				messageEnableDisableFailed);
		messageEnableDisableFailed = "";
		model.addAttribute("message_enable_disable_succeeded",
				messageEnableDisableSucceeded);
		messageEnableDisableSucceeded = "";
		
		model.addAttribute("message_user_added",
				messageAdded);
		messageAdded = "";
		model.addAttribute("message_user_not_added",
				messageNotAdded);
		messageNotAdded = "";		
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal,
			final Model model, final Long id) {				
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
		
		pagingFirstIndex = userPageNumber * DEFAULT_PAGE_SIZE;
		pagingLastIndex = 
				DEFAULT_PAGE_SIZE * (userPageNumber + 1) - 1;
		pageNumber = userPageNumber;
		
		return "redirect:/adminpageusers";
	}
	
	@RequestMapping(value = "/enable", method = RequestMethod.GET)
	public String enableUser(@RequestParam("user_id") Long userId, final Locale locale) {
		User userInQuestion = userSvc.getUserById(userId);
		if (userInQuestion == null) {			
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.userNotExists",
							null, locale);
			return "redirect:/adminpageusers";
		}
		if (userInQuestion.getEnabled() != 0) {			
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.userNotDisabled",
							null, locale);
			return "redirect:/adminpageusers";
		}
		
		if (userSvc.setUserEnabledById(userId, (byte) 1) == 1) {
			messageEnableDisableSucceeded = 
				messageSource
				.getMessage("popup.adminpage.users.actions.succeeded",
						null, locale);
		} else {
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.updateFailed",
							null, locale);
		}
		return "redirect:/adminpageusers";
	}
	
	@RequestMapping(value = "/disable", method = RequestMethod.GET)
	public String disableUser(@RequestParam("user_id") Long userId, final Locale locale) {
		User userInQuestion = userSvc.getUserById(userId);
		if (userInQuestion == null) {			
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.userNotExists",
							null, locale);
			return "redirect:/adminpageusers";
		}
		if (userInQuestion.getLogin().contentEquals(myUser.getLogin())) {		
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.sameUser",
							null, locale);
			return "redirect:/adminpageusers";
		}
		if (userInQuestion.getEnabled() != 1) {
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.userNotEnabled",
							null, locale);
			return "redirect:/adminpageusers";
		}
		
		if (userSvc.setUserEnabledById(userId, (byte) 0) == 1) {
			messageEnableDisableSucceeded = 
				messageSource
				.getMessage("popup.adminpage.users.actions.succeeded",
						null, locale);
		} else {
			messageEnableDisableFailed = 
					messageSource
					.getMessage("popup.adminpage.users.actions.failed.updateFailed",
							null, locale);
		}
		return "redirect:/adminpageusers";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(
			@ModelAttribute("user") @Valid final User user,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.user", bindingResult);
			redirectAttributes.addFlashAttribute("user", user);
			return "redirect:/adminpageusers#add_new_user";
		}
		
		if (userSvc.add(new	User(user.getLogin(), user.getPasswordText(),
				encoder.encode(user.getPasswordText())))) {
			messageAdded =
					messageSource.getMessage("popup.adminpage.userAdded", null,
							locale);
		} else {
			messageNotAdded = 
					messageSource.getMessage("popup.adminpage.userNotAdded", null,
							locale);
		}
		return "redirect:/adminpageusers";
	}

}
