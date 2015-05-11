package com.glomozda.machinerepair.controller.managerpage;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.glomozda.machinerepair.controller.AbstractRolePageController;

import org.apache.log4j.Logger;

@Controller
public class ManagerPageController extends AbstractRolePageController 
	implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(ManagerPageController.class.getName());
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal,
			final Model model) {
		
		model.addAttribute("locale", locale.toString());
	}
	
	@Override
	protected void prepareModel(final Locale locale, final Principal principal,
			final Model model, final Long id) {		
	}
	
	@RequestMapping(value = {"/managerpage", "/manageradminpage"}, method = RequestMethod.GET)
	public String activate(
			HttpServletRequest request,
			final Locale locale,
			final Principal principal,
			final Model model) {
		
		if (!isMyUserSet(principal)) {
			return "redirect:/index";
		}	
				
		prepareModel(locale, principal, model);
		
		if (request.getRequestURI().contains("admin")) {
			return "manageradminpage";
		} else {		
			return "managerpage";
		}
	}
}
