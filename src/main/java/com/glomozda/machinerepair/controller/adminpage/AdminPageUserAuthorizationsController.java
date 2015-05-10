package com.glomozda.machinerepair.controller.adminpage;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorizationAddDTO;
import com.glomozda.machinerepair.domain.userrole.UserRole;

@Controller
public class AdminPageUserAuthorizationsController extends AbstractRolePageController
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageUserAuthorizationsController.class.getName());
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model) {
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("userAuthorizationAddDTO")) {
			model.addAttribute("userAuthorizationAddDTO", new UserAuthorizationAddDTO());
		}
		
		List<UserAuthorization> uas = userAuthorizationSvc
				.getAllWithFetching(pagingFirstIndex, 
				pagingLastIndex - pagingFirstIndex + 1);
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
					.getDistinctUsersWithFetching(pagingFirstIndex, 
						pagingLastIndex - pagingFirstIndex + 1)
				);
		
		Long userAuthorizationsCount = userAuthorizationSvc.getUserAuthorizationCount();
		model.addAttribute("user_authorizations_count",	userAuthorizationsCount);
		Long pagesCount = userAuthorizationsCount / DEFAULT_PAGE_SIZE;
		if (userAuthorizationsCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		model.addAttribute("page_number", pageNumber);
		
		model.addAttribute("user_roles", userAuthorizationSvc.getAllRoles());
		
		model.addAttribute("message_user_authorization_added",
				messageAdded);
		messageAdded = "";
		model.addAttribute("message_user_authorization_not_added",
				messageNotAdded);
		messageNotAdded = "";
		
		model.addAttribute("dialog_delete_user_authorization",
				messageSource.getMessage(
						"label.adminpage.userAuthorizations.actions.delete.dialog", null,
				locale));		
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal, 
			final Model model, final Long id) {				
	}
	
	@RequestMapping(value = "/adminpageuserauthorizations", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}
		
		prepareModel(locale, principal, model);		
		
		return "adminpageuserauthorizations";
	}
	
	@RequestMapping(value = "/adminpageuserauthorizations/userauthorizationpaging",
			method = RequestMethod.POST)
	public String userAuthorizationPaging(
			@RequestParam("userAuthorizationPageNumber") final Long userAuthorizationPageNumber) {
		
		pagingFirstIndex = userAuthorizationPageNumber * DEFAULT_PAGE_SIZE;
		pagingLastIndex = 
				DEFAULT_PAGE_SIZE * (userAuthorizationPageNumber + 1) - 1;
		pageNumber = userAuthorizationPageNumber;				
		
		return "redirect:/adminpageuserauthorizations";
	}
	
	@RequestMapping(value = "/addUserAuthorization", method = RequestMethod.POST)
	public String addUserAuthorization
		(@ModelAttribute("userAuthorizationAddDTO") 
			@Valid final UserAuthorizationAddDTO userAuthorizationAddDTO,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,			
			final Locale locale) {

		if (!userAuthorizationAddDTO.getRole().isEmpty() && userAuthorizationSvc
			.getUserAuthorizationForUserIdAndRole(userAuthorizationAddDTO.getUserId(),
					userAuthorizationAddDTO.getRole()) != null) {
			bindingResult.rejectValue("role", 
					"error.adminpage.userAuthorizationExists", null);			
			return "redirect:/adminpageuserauthorizations#add_new_user_authorization";
		}
		
		if (bindingResult.hasErrors()) {			
			redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.userAuthorizationAddDTO",
						bindingResult);
			redirectAttributes.addFlashAttribute("userAuthorizationAddDTO", 
					userAuthorizationAddDTO);			
			return "redirect:/adminpageuserauthorizations#add_new_user_authorization";
		}
		
		if (userAuthorizationSvc.add(
				new UserAuthorization(new UserRole(userAuthorizationAddDTO.getRole())), 
				userAuthorizationAddDTO.getUserId())) {
			messageAdded =
					messageSource.getMessage("popup.adminpage.userAuthorizationAdded", null,
							locale);
		} else {
			messageNotAdded = 
					messageSource.getMessage("popup.adminpage.userAuthorizationNotAdded", null,
							locale);
		}		
		return "redirect:/adminpageuserauthorizations";
	}
}
