package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorizationDTO;
import com.glomozda.machinerepair.domain.userrole.UserRole;
import com.glomozda.machinerepair.service.user.UserService;
import com.glomozda.machinerepair.service.userauthorization.UserAuthorizationService;

@Controller
public class UpdateUserAuthorizationController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(UpdateUserAuthorizationController.class.getName());
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private UserAuthorizationService userAuthorizationSvc;
	
	private User myUser;
	
	private UserAuthorizationDTO myUserAuthorizationDTO;
	
	private MessageSource messageSource;
	
	private String messageUserAuthorizationUpdateFailed = "";
	private String messageUserAuthorizationUpdateSucceeded = "";
	private String messageUserAuthorizationNoChanges = "";
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/updateuserauthorization", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model, 
			@RequestParam("user-authorization-id") final Long userAuthorizationId) {
		
		if (null == principal) {
			return "redirect:/index";
		}
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
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
				messageUserAuthorizationUpdateFailed);
		messageUserAuthorizationUpdateFailed = "";
		model.addAttribute("message_user_authorization_updated",
				messageUserAuthorizationUpdateSucceeded);
		messageUserAuthorizationUpdateSucceeded = "";
		model.addAttribute("message_user_authorization_no_changes",
				messageUserAuthorizationNoChanges);
		messageUserAuthorizationNoChanges = "";		
		
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
			messageUserAuthorizationUpdateFailed = 
					messageSource.getMessage("popup.adminpage.userAuthorizationNotUpdated", null,
							locale);
			
			return "redirect:/updateuserauthorization/?user-authorization-id=" 
			+ myUserAuthorizationDTO.getUserAuthorizationId();
		}
		
//		log.info(userAuthorizationDTO);
//		log.info(myUserAuthorizationDTO);
		
		if (userAuthorizationDTO.getIsAdmin()
					.equals(myUserAuthorizationDTO.getIsAdmin())
				&& userAuthorizationDTO.getIsClient()
					.equals(myUserAuthorizationDTO.getIsClient())
				&& userAuthorizationDTO.getIsManager()
					.equals(myUserAuthorizationDTO.getIsManager())) {
			messageUserAuthorizationNoChanges = 
					messageSource.getMessage("popup.adminpage.userAuthorizationNoChanges", null,
							locale);
			
			return "redirect:/updateuserauthorization/?user-authorization-id=" 
			+ myUserAuthorizationDTO.getUserAuthorizationId();
		}
		
		if (userAuthorizationDTO.getIsAdmin() && !myUserAuthorizationDTO.getIsAdmin()) {
			if (!userAuthorizationSvc.add(
					new UserAuthorization(new UserRole("ROLE_ADMIN")),
						myUserAuthorizationDTO.getUser().getUserId())) {
				
				messageUserAuthorizationUpdateFailed = 
						messageSource.getMessage("popup.adminpage.userAuthorizationNotUpdated",
								null,
								locale);
				
				return "redirect:/updateuserauthorization/?user-authorization-id=" 
				+ myUserAuthorizationDTO.getUserAuthorizationId();
			}
		}
		
		if (!userAuthorizationDTO.getIsAdmin() && myUserAuthorizationDTO.getIsAdmin()) {
			if (userAuthorizationSvc
					.deleteUserAuthorizationByUserIdAndRole(
							myUserAuthorizationDTO.getUser().getUserId(),
							"ROLE_ADMIN")
					.intValue() != 1) {
				
				messageUserAuthorizationUpdateFailed = 
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
				
				messageUserAuthorizationUpdateFailed = 
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
				
				messageUserAuthorizationUpdateFailed = 
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
				
				messageUserAuthorizationUpdateFailed = 
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
				
				messageUserAuthorizationUpdateFailed = 
						messageSource.getMessage("popup.adminpage.userAuthorizationNotUpdated",
								null,
								locale);
				
				return "redirect:/updateuserauthorization/?user-authorization-id=" 
				+ myUserAuthorizationDTO.getUserAuthorizationId();
			}
		}
		
		messageUserAuthorizationUpdateSucceeded =
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
