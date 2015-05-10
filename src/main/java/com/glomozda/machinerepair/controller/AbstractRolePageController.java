package com.glomozda.machinerepair.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.ui.Model;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.machine.MachineService;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.service.order.OrderService;
import com.glomozda.machinerepair.service.orderstatus.OrderStatusService;
import com.glomozda.machinerepair.service.repairtype.RepairTypeService;
import com.glomozda.machinerepair.service.user.UserService;
import com.glomozda.machinerepair.service.userauthorization.UserAuthorizationService;

public abstract class AbstractRolePageController implements MessageSourceAware {
	
	protected MessageSource messageSource;
	
	@Autowired
	protected MachineService machineSvc;
	
	@Autowired
	protected MachineServiceableService machineServiceableSvc;
	
	@Autowired
	protected RepairTypeService repairTypeSvc;
	
	@Autowired
	protected OrderStatusService orderStatusSvc;
	
	@Autowired
	protected UserService userSvc;
	
	@Autowired
	protected UserAuthorizationService userAuthorizationSvc;
	
	@Autowired
	protected ClientService clientSvc;
	
	@Autowired
	protected OrderService orderSvc;
	
	protected User myUser;
	
	protected static final Long DEFAULT_PAGE_SIZE = 10L;
	
	protected String messageAdded = "";
	protected String messageNotAdded = "";
	
	protected String messageUpdateFailed = "";
	protected String messageUpdateSucceeded = "";
	protected String messageNoChanges = "";
	
	protected Long pagingFirstIndex = 0L;
	protected Long pagingLastIndex = DEFAULT_PAGE_SIZE - 1;
	protected Long pageNumber = 0L;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	protected Boolean isMyUserSet(final Principal principal) {
		if (null == principal) {
			return false;
		}
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return false;
		}
		
		return true;
	}
	
	protected abstract void prepareModel(final Locale locale,
			final Principal principal, 
			final Model model);
	
	protected abstract void prepareModel(final Locale locale,
			final Principal principal, 
			final Model model,
			final Long id);
}
