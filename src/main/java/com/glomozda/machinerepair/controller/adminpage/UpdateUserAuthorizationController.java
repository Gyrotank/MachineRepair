package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.controller.AbstractRolePageController;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorizationDTO;
import com.glomozda.machinerepair.domain.userrole.UserRole;

@Controller
public class UpdateUserAuthorizationController extends AbstractRolePageController 
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(UpdateUserAuthorizationController.class.getName());
	
	private UserAuthorizationDTO myUserAuthorizationDTO;
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {		
	}

	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final Long userAuthorizationId) {

		model.addAttribute("locale", locale.toString());
		
		model.addAttribute("userRoles", userAuthorizationSvc.getAllRoles());
		
		User userInQuestion = 
				userAuthorizationSvc.getUserForUserAuthorizationId(userAuthorizationId);
		List<UserRole> userInQuestionRoles = 
				userAuthorizationSvc.getRolesForUserId(userInQuestion.getUserId());
		Boolean isOnlyAdmin = false;
		for (UserRole ur : userInQuestionRoles) {
			if (ur.getRole().contentEquals("ROLE_ADMIN")) {
				if (userAuthorizationSvc.getCountUserAuthorizationsForRole("ROLE_ADMIN") == 1) {
					isOnlyAdmin = true;
					break;
				}
			}
		}
		
		myUserAuthorizationDTO = new UserAuthorizationDTO(
				userAuthorizationId,
				userInQuestion,
				userInQuestionRoles,
				isOnlyAdmin
				);
		
		if (!model.containsAttribute("userAuthorizationDTOCurrent")) {
			model.addAttribute("userAuthorizationDTOCurrent", myUserAuthorizationDTO);
		}
		
		if (!model.containsAttribute("userAuthorizationDTO")) {
			model.addAttribute("userAuthorizationDTO", myUserAuthorizationDTO);
		}
		
		model.addAttribute("message_user_authorization_not_updated",
				messageUpdateFailed);
		messageUpdateFailed = "";
		model.addAttribute("message_user_authorization_updated",
				messageUpdateSucceeded);
		messageUpdateSucceeded = "";
		model.addAttribute("message_user_authorization_no_changes",
				messageNoChanges);
		messageNoChanges = "";		
	}
	
	@RequestMapping(value = "/updateuserauthorization", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model, 
			@RequestParam("user-authorization-id") final Long userAuthorizationId) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		prepareModel(locale, principal, model, userAuthorizationId);
		
		return "updateuserauthorization";
	}
	
	@RequestMapping(value = "updateuserauthorization/updateUserAuthorization",
			method = RequestMethod.POST)
	public String updateUserAuthorization(
			@ModelAttribute("userAuthorizationDTO") final UserAuthorizationDTO userAuthorizationDTO,
			final Locale locale) {		
		
		if (userAuthorizationDTO.getIsAdmin() == false
				&& userAuthorizationDTO.getIsClient() == false
				&& userAuthorizationDTO.getIsManager() == false) {
			messageUpdateFailed = 
					messageSource.getMessage("popup.adminpage.userAuthorizationNotUpdated", null,
							locale);
			
			return "redirect:/updateuserauthorization/?user-authorization-id=" 
			+ myUserAuthorizationDTO.getUserAuthorizationId();
		}
		
		if (userAuthorizationDTO.getIsAdmin()
					.equals(myUserAuthorizationDTO.getIsAdmin())
				&& userAuthorizationDTO.getIsClient()
					.equals(myUserAuthorizationDTO.getIsClient())
				&& userAuthorizationDTO.getIsManager()
					.equals(myUserAuthorizationDTO.getIsManager())) {
			messageNoChanges = 
					messageSource.getMessage("popup.adminpage.userAuthorizationNoChanges", null,
							locale);
			
			return "redirect:/updateuserauthorization/?user-authorization-id=" 
			+ myUserAuthorizationDTO.getUserAuthorizationId();
		}
		
		if (userAuthorizationDTO.getIsAdmin() && !myUserAuthorizationDTO.getIsAdmin()) {
			if (!userAuthorizationSvc.add(
					new UserAuthorization(new UserRole("ROLE_ADMIN")),
						myUserAuthorizationDTO.getUser().getUserId())) {
				
				messageUpdateFailed = 
						messageSource.getMessage("popup.adminpage.userAuthorizationNotUpdated",
								null,
								locale);
				
				return "redirect:/updateuserauthorization/?user-authorization-id=" 
				+ myUserAuthorizationDTO.getUserAuthorizationId();
			}
		}
		
		if (!userAuthorizationDTO.getIsAdmin() && myUserAuthorizationDTO.getIsAdmin() 
				&& userAuthorizationSvc.getCountUserAuthorizationsForRole("ROLE_ADMIN") > 1) {
			if (userAuthorizationSvc
					.deleteUserAuthorizationByUserIdAndRole(
							myUserAuthorizationDTO.getUser().getUserId(),
							"ROLE_ADMIN")
					.intValue() != 1) {
				
				messageUpdateFailed = 
						messageSource.getMessage("popup.adminpage.userAuthorizationNotUpdated",
								null,
								locale);
				
				return "redirect:/updateuserauthorization/?user-authorization-id=" 
				+ myUserAuthorizationDTO.getUserAuthorizationId();
			}
		}
		
		if (userAuthorizationDTO.getIsClient() && !myUserAuthorizationDTO.getIsClient()) {
			if (!userAuthorizationSvc.add(
					new UserAuthorization(new UserRole("ROLE_CLIENT")),
						myUserAuthorizationDTO.getUser().getUserId())) {
				
				messageUpdateFailed = 
						messageSource.getMessage("popup.adminpage.userAuthorizationNotUpdated",
								null,
								locale);
				
				return "redirect:/updateuserauthorization/?user-authorization-id=" 
				+ myUserAuthorizationDTO.getUserAuthorizationId();
			}
		}
		
		if (!userAuthorizationDTO.getIsClient() && myUserAuthorizationDTO.getIsClient()) {
			if (userAuthorizationSvc
					.deleteUserAuthorizationByUserIdAndRole(
							myUserAuthorizationDTO.getUser().getUserId(),
							"ROLE_CLIENT")
					.intValue() != 1) {
				
				messageUpdateFailed = 
						messageSource.getMessage("popup.adminpage.userAuthorizationNotUpdated",
								null,
								locale);
				
				return "redirect:/updateuserauthorization/?user-authorization-id=" 
				+ myUserAuthorizationDTO.getUserAuthorizationId();
			}
		}
		
		if (userAuthorizationDTO.getIsManager() && !myUserAuthorizationDTO.getIsManager()) {
			if (!userAuthorizationSvc.add(
					new UserAuthorization(new UserRole("ROLE_MANAGER")),
						myUserAuthorizationDTO.getUser().getUserId())) {
				
				messageUpdateFailed = 
						messageSource.getMessage("popup.adminpage.userAuthorizationNotUpdated",
								null,
								locale);
				
				return "redirect:/updateuserauthorization/?user-authorization-id=" 
				+ myUserAuthorizationDTO.getUserAuthorizationId();
			}
		}
		
		if (!userAuthorizationDTO.getIsManager() && myUserAuthorizationDTO.getIsManager()) {
			if (userAuthorizationSvc
					.deleteUserAuthorizationByUserIdAndRole(
							myUserAuthorizationDTO.getUser().getUserId(),
							"ROLE_MANAGER")
					.intValue() != 1) {
				
				messageUpdateFailed = 
						messageSource.getMessage("popup.adminpage.userAuthorizationNotUpdated",
								null,
								locale);
				
				return "redirect:/updateuserauthorization/?user-authorization-id=" 
				+ myUserAuthorizationDTO.getUserAuthorizationId();
			}
		}
		
		messageUpdateSucceeded =
				messageSource.getMessage("popup.adminpage.userAuthorizationUpdated", null,
						locale);
		
		if (null != userAuthorizationSvc
				.getUserForUserAuthorizationId(myUserAuthorizationDTO.getUserAuthorizationId())) {
			return "redirect:/updateuserauthorization/?user-authorization-id=" 
				+ myUserAuthorizationDTO.getUserAuthorizationId();
		} else {			
			return "redirect:/updateuserauthorization/?user-authorization-id=" 
				+ userAuthorizationSvc
				.getUserAuthorizationsByUserId(myUserAuthorizationDTO.getUser().getUserId())
				.get(0).getUserAuthorizationId();
		}
	}
}
