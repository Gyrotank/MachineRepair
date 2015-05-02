package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userrole.UserRole;
import com.glomozda.machinerepair.service.user.UserService;
import com.glomozda.machinerepair.service.userauthorization.UserAuthorizationService;

@Controller
public class AdminPageUserAuthorizationsController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageUserAuthorizationsController.class.getName());
	
	@Autowired
	private UserAuthorizationService userAuthorizationSvc;
	
	@Autowired
	private UserService userSvc;
	
	private User myUser;
	
	private MessageSource messageSource;
	
	private static final Long _defaultPageSize = (long) 25;
	
	private String messageUserAuthorizationAdded = "";
	private String messageUserAuthorizationNotAdded = "";
	
	private String messageUserAuthorizationUserId = "";
	private String messageUserAuthorizationRole = "";
	private Long selectedUserAuthorizationUserId = (long) 0;
	
	private Long userAuthorizationPagingFirstIndex = (long) 0;
	private Long userAuthorizationPagingLastIndex = _defaultPageSize - 1;	
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/adminpageuserauthorizations", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("userAuthorization")) {
			model.addAttribute("userAuthorization", new UserAuthorization());
		}
		
		List<UserAuthorization> uas = userAuthorizationSvc
				.getAllWithFetching(userAuthorizationPagingFirstIndex, 
				userAuthorizationPagingLastIndex - userAuthorizationPagingFirstIndex + 1);
		List<UserAuthorization> uash = new ArrayList<UserAuthorization>();
		for (UserAuthorization ua : uas) {
			if (uash.isEmpty()) {
				uash.add(ua);				
				continue;
			}
			if (uash.get(uash.size() - 1).getUser().getLogin()
					.contentEquals(ua.getUser().getLogin())) {
				uash.get(uash.size() - 1).getRole()
					.setDescEn(uash.get(uash.size() - 1).getRole().getDescEn()
							.concat(", " + ua.getRole().getDescEn()));
				uash.get(uash.size() - 1).getRole()
					.setDescRu(uash.get(uash.size() - 1).getRole().getDescRu()
						.concat(", " + ua.getRole().getDescRu()));
				continue;
			}
			uash.add(ua);
		}		
		model.addAttribute("user_authorizations_short", uash);
		model.addAttribute("user_authorizations_short_users", 
				userAuthorizationSvc
					.getDistinctUsersWithFetching(userAuthorizationPagingFirstIndex, 
						userAuthorizationPagingLastIndex - userAuthorizationPagingFirstIndex + 1)
				);
		
		model.addAttribute("user_authorizations_count",
				userAuthorizationSvc.getUserAuthorizationCount());
		model.addAttribute("user_authorizations_paging_first", userAuthorizationPagingFirstIndex);
		model.addAttribute("user_authorizations_paging_last", userAuthorizationPagingLastIndex);
		
		model.addAttribute("user_roles", userAuthorizationSvc.getAllRoles());
		
		model.addAttribute("message_user_authorization_added",
				messageUserAuthorizationAdded);
		messageUserAuthorizationAdded = "";
		model.addAttribute("message_user_authorization_not_added",
				messageUserAuthorizationNotAdded);
		messageUserAuthorizationNotAdded = "";
		
		model.addAttribute("message_user_authorization_user_id", messageUserAuthorizationUserId);
		messageUserAuthorizationUserId = "";
		model.addAttribute("message_user_authorization_role", messageUserAuthorizationRole);
		messageUserAuthorizationRole = "";
		model.addAttribute("selected_user_authorization_user_id", selectedUserAuthorizationUserId);
		selectedUserAuthorizationUserId = (long) 0;
		
		model.addAttribute("dialog_delete_user_authorization",
				messageSource.getMessage(
						"label.adminpage.userAuthorizations.actions.delete.dialog", null,
				locale));
		
		return "adminpageuserauthorizations";
	}
	
	@RequestMapping(value = "/adminpageuserauthorizations/userauthorizationpaging",
			method = RequestMethod.POST)
	public String userAuthorizationPaging(
			@RequestParam("userAuthorizationPageStart") final Long userAuthorizationPageStart, 
			@RequestParam("userAuthorizationPageEnd") final Long userAuthorizationPageEnd) {
		
		long userAuthorizationStart;
		long userAuthorizationEnd;
		
		if (userAuthorizationPageStart == null) {
			userAuthorizationStart = (long) 0;
		} else {
			userAuthorizationStart = userAuthorizationPageStart.longValue() - 1;
		}
		
		if (userAuthorizationPageEnd == null) {
			userAuthorizationEnd = (long) 0;
		} else {
			userAuthorizationEnd = userAuthorizationPageEnd.longValue() - 1;
		}
		
		long userAuthorizationCount = userAuthorizationSvc.getUserAuthorizationCount();
		
		if (userAuthorizationStart > userAuthorizationEnd) {
			long temp = userAuthorizationStart;
			userAuthorizationStart = userAuthorizationEnd;
			userAuthorizationEnd = temp;
		}
		
		if (userAuthorizationStart < 0)
			userAuthorizationStart = 0;
		
		if (userAuthorizationStart >= userAuthorizationCount)
			userAuthorizationStart = userAuthorizationCount - 1;
		
		if (userAuthorizationEnd < 0)
			userAuthorizationEnd = 0;
		
		if (userAuthorizationEnd >= userAuthorizationCount)
			userAuthorizationEnd = userAuthorizationCount - 1;
		
		userAuthorizationPagingFirstIndex = userAuthorizationStart;
		userAuthorizationPagingLastIndex = userAuthorizationEnd;		
		
		return "redirect:/adminpageuserauthorizations";
	}
	
	@RequestMapping(value = "/addUserAuthorization", method = RequestMethod.POST)
	public String addUserAuthorization
		(@ModelAttribute("userAuthorization") @Valid final UserAuthorization userAuthorization,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			@RequestParam("userId") final Long userId,
			@RequestParam("role") final String role,
			final Locale locale) {
		
		if (userId == 0 || bindingResult.hasErrors()) {
			if (userId == 0) {
				messageUserAuthorizationUserId = 
						messageSource.getMessage("error.adminpage.userId", null,
								locale);			
			}

			if (bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.userAuthorization", bindingResult);
				redirectAttributes.addFlashAttribute("userAuthorization", userAuthorization);				
			}
			
			selectedUserAuthorizationUserId = userId;
			return "redirect:/adminpageuserauthorizations#add_new_user_authorization";
		}
		
		if (role.isEmpty()) {
			messageUserAuthorizationRole = 
					messageSource.getMessage("error.adminpage.userAuthorizationNoRoleChosen", null,
							locale);
			selectedUserAuthorizationUserId = userId;
			return "redirect:/adminpageuserauthorizations#add_new_user_authorization";
		}
		
		if (userAuthorizationSvc
			.getUserAuthorizationForUserIdAndRole(userId,
					userAuthorization.getRole().getRole()) != null) {
				messageUserAuthorizationUserId = 
					messageSource.getMessage("error.adminpage.userAuthorizationExists", null,
							locale);
			selectedUserAuthorizationUserId = userId;
			return "redirect:/adminpageuserauthorizations#add_new_user_authorization";
		}
		
		if (userAuthorizationSvc.add(
				new UserAuthorization(new UserRole(role)), userId)) {
			messageUserAuthorizationAdded =
					messageSource.getMessage("popup.adminpage.userAuthorizationAdded", null,
							locale);
		} else {
			messageUserAuthorizationNotAdded = 
					messageSource.getMessage("popup.adminpage.userAuthorizationNotAdded", null,
							locale);
		}		
		return "redirect:/adminpageuserauthorizations";
	}
	
	@RequestMapping(value = "/deleteuserauthorization", method = RequestMethod.GET)
	public String deleteUserAuthorization(
			@RequestParam("user-authorization-id") final Long userAuthorizationId,
			final Locale locale) {
		
//		if (clientSvc.add(client, userId)) {
//			messageClientAdded =
//					messageSource.getMessage("popup.adminpage.clientAdded", null,
//							locale);
//		} else {
//			messageClientNotAdded = 
//					messageSource.getMessage("popup.adminpage.clientNotAdded", null,
//							locale);
//		}		
		return "redirect:/adminpageuserauthorizations";
	}
}
