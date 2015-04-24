package com.glomozda.machinerepair;

import java.security.Principal;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.service.repairtype.RepairTypeService;
import com.glomozda.machinerepair.service.user.UserService;

@Controller
public class AdminPageUsersController implements MessageSourceAware {

	static Logger log = Logger.getLogger(AdminPageController.class.getName());
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private RepairTypeService repairTypeSvc;
	
	@Autowired
	private PasswordEncoder encoder;
	
	private User myUser;
	
	private MessageSource messageSource;
	
	private static final Long _defaultPageSize = (long) 25;
	
	private String messageUserAdded = "";
	private String messageUserNotAdded = "";
	
	private Long userPagingFirstIndex = (long) 0;
	private Long userPagingLastIndex = _defaultPageSize - 1;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/adminpageusers", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", new User());
		}
		
		model.addAttribute("users_short", 
				userSvc.getAll(userPagingFirstIndex, 
						userPagingLastIndex - userPagingFirstIndex + 1));
		model.addAttribute("users_count", userSvc.getUserCount());
		model.addAttribute("users_paging_first", userPagingFirstIndex);
		model.addAttribute("users_paging_last", userPagingLastIndex);
		
		model.addAttribute("message_user_added",
				messageUserAdded);
		messageUserAdded = "";
		model.addAttribute("message_user_not_added",
				messageUserNotAdded);
		messageUserNotAdded = "";		
				
		return "adminpageusers";
	}
	
	@RequestMapping(value = "/adminpageusers/userpaging", method = RequestMethod.POST)
	public String userPaging(@RequestParam("userPageStart") final Long userPageStart, 
			@RequestParam("userPageEnd") final Long userPageEnd) {
		
		long userStart;
		long userEnd;
		
		if (userPageStart == null) {
			userStart = (long) 0;
		} else {
			userStart = userPageStart.longValue() - 1;
		}
		
		if (userPageEnd == null) {
			userEnd = (long) 0;
		} else {
			userEnd = userPageEnd.longValue() - 1;
		}
		
		long userCount = userSvc.getUserCount();
		
		if (userStart > userEnd) {
			long temp = userStart;
			userStart = userEnd;
			userEnd = temp;
		}
		
		if (userStart < 0)
			userStart = 0;
		
		if (userStart >= userCount)
			userStart = userCount - 1;
		
		if (userEnd < 0)
			userEnd = 0;
		
		if (userEnd >= userCount)
			userEnd = userCount - 1;
		
		userPagingFirstIndex = userStart;
		userPagingLastIndex = userEnd;		
		
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
			messageUserAdded =
					messageSource.getMessage("popup.adminpage.userAdded", null,
							locale);
		} else {
			messageUserNotAdded = 
					messageSource.getMessage("popup.adminpage.userNotAdded", null,
							locale);
		}
		return "redirect:/adminpageusers";
	}
}