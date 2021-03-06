package com.glomozda.machinerepair.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.ui.Model;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.service.EntityService;
import com.glomozda.machinerepair.service.SessionScopeInfoService;
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
	
	@Autowired
	protected SessionScopeInfoService sessionScopeInfoService;
	
    protected User myUser;
	
	protected static final long DEFAULT_PAGE_SIZE = 10;
	
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
	
	public void changeSessionScopePagingInfo(final long pagingFirstIndex,
			final long pagingLastIndex,
			final long pageNumber) {
    	this.sessionScopeInfoService
    		.changeSessionScopePagingInfo(pagingFirstIndex, pagingLastIndex, pageNumber);
    }
	
	public void changeSessionScopePagingPlusInfo(final long pagingFirstIndexPlus,
			final long pagingLastIndexPlus,
			final long pageNumberPlus) {
    	this.sessionScopeInfoService
    		.changeSessionScopePagingPlusInfo(pagingFirstIndexPlus,
    				pagingLastIndexPlus, pageNumberPlus);
    }
	
	public void changeSessionScopePagingPlusPlusInfo(final long pagingFirstIndexPlusPlus,
			final long pagingLastIndexPlusPlus,
			final long pageNumberPlusPlus) {
    	this.sessionScopeInfoService
    		.changeSessionScopePagingPlusPlusInfo(pagingFirstIndexPlusPlus,
    				pagingLastIndexPlusPlus, pageNumberPlusPlus);
    }
	
	public void changeSessionScopeAddingInfo(final String messageAdded,
			final String messageNotAdded) {
    	this.sessionScopeInfoService
    		.changeSessionScopeAddingInfo(messageAdded, messageNotAdded);
    }
	
	public void changeSessionScopeUpdateInfo(final String messageUpdateFailed,
			final String messageUpdateSucceeded,
			final String messageNoChanges) {
    	this.sessionScopeInfoService
    		.changeSessionScopeUpdateInfo(messageUpdateFailed, messageUpdateSucceeded, 
    			messageNoChanges);
    }
	
	protected abstract void prepareModel(final Locale locale,
			final Principal principal, 
			final Model model);
	
	protected abstract void prepareModel(final Locale locale,
			final Principal principal, 
			final Model model,
			final long id);
	
	protected void prepareModelAdminPage(final Locale locale, final Model model,
		final Object dataObject, final EntityService entityService) {
		
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("dataObject")) {
			model.addAttribute("dataObject", dataObject);
		}
		
		Long entitiesCount = entityService.getCountEntities();
		model.addAttribute("entities_count", entitiesCount);
		
		Long pagesCount = entitiesCount / DEFAULT_PAGE_SIZE;
		if (entitiesCount % DEFAULT_PAGE_SIZE != 0) {
			pagesCount++;
		}
		model.addAttribute("pages_count", pagesCount);
		model.addAttribute("pages_size", DEFAULT_PAGE_SIZE);
		Long pageNumber = sessionScopeInfoService.getSessionScopeInfo().getPageNumber();
		if (pageNumber >= pagesCount) {
			model.addAttribute("page_number", 0L);
			model.addAttribute("entities", 
					entityService.getAllEntities(
							0L, DEFAULT_PAGE_SIZE));
		} else {
			model.addAttribute("page_number", pageNumber);
			model.addAttribute("entities", 
					entityService.getAllEntities(
							sessionScopeInfoService.getSessionScopeInfo().getPagingFirstIndex(), 
							sessionScopeInfoService.getSessionScopeInfo().getPagingLastIndex() 
							- sessionScopeInfoService
								.getSessionScopeInfo().getPagingFirstIndex() + 1));
		}
		
		model.addAttribute("message_added", 
			sessionScopeInfoService.getSessionScopeInfo().getMessageAdded());
		sessionScopeInfoService.getSessionScopeInfo().setMessageAdded("");
		model.addAttribute("message_not_added",
			sessionScopeInfoService.getSessionScopeInfo().getMessageNotAdded());
		sessionScopeInfoService.getSessionScopeInfo().setMessageNotAdded("");
	}
	
	protected void prepareModelAdminPageWithEnableDisable (final Locale locale,
			final Model model,
			final String messageDialogEnableLabel,
			final String messageDialogDisableLabel) {
		
		model.addAttribute("message_enable_disable_failed",
				sessionScopeInfoService.getSessionScopeInfo().getMessageEnableDisableFailed());
		sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableFailed("");
		model.addAttribute("message_enable_disable_succeeded",
				sessionScopeInfoService.getSessionScopeInfo().getMessageEnableDisableSucceeded());
		sessionScopeInfoService.getSessionScopeInfo().setMessageEnableDisableSucceeded("");
		
		model.addAttribute("dialog_enable",
				messageSource.getMessage(
					messageDialogEnableLabel, null,
					locale));
		model.addAttribute("dialog_disable",
				messageSource.getMessage(
					messageDialogDisableLabel, null,
					locale));
	}
	
	protected void prepareModelUpdate(final Locale locale, final Model model, final Object entity) {
		model.addAttribute("locale", locale.toString());
		
		if (!model.containsAttribute("entityCurrent")) {
			model.addAttribute("entityCurrent", entity);
		}
		
		if (!model.containsAttribute("entity")) {
			model.addAttribute("entity", entity);
		}
		
		model.addAttribute("message_entity_not_updated",
				sessionScopeInfoService.getSessionScopeInfo().getMessageUpdateFailed());
		sessionScopeInfoService.getSessionScopeInfo().setMessageUpdateFailed("");
		model.addAttribute("message_entity_updated",
				sessionScopeInfoService.getSessionScopeInfo().getMessageUpdateSucceeded());
		sessionScopeInfoService.getSessionScopeInfo().setMessageUpdateSucceeded("");
		model.addAttribute("message_entity_no_changes",
				sessionScopeInfoService.getSessionScopeInfo().getMessageNoChanges());
		sessionScopeInfoService.getSessionScopeInfo().setMessageNoChanges("");
	}	
	
	protected Date StringToSqlDateParser(String stringToParse) {		
		Date result;
		
		try {
			String startParsed = new String();
			startParsed = startParsed.concat(stringToParse.substring(6));			
			startParsed = startParsed.concat("-");			
			startParsed = startParsed.concat(stringToParse.substring(3, 5));			
			startParsed = startParsed.concat("-");			
			startParsed = startParsed.concat(stringToParse.substring(0, 2));			
			result = java.sql.Date.valueOf(startParsed);			
		} catch (java.lang.StringIndexOutOfBoundsException e) {
			result = null;
		} catch (IllegalArgumentException e) {
			result = null;
		} catch (NullPointerException e) {
			result = null;
		}
		
		return result;
	}
	
	protected void addMessages(Boolean condition,
			String messageIfTrue,
			String messageIfFalse,
			final Locale locale) {
		
		if (condition) {
			changeSessionScopeAddingInfo(
					messageSource.getMessage(messageIfTrue, null, locale),
					"");
		} else {
			changeSessionScopeAddingInfo(
					"",
					messageSource.getMessage(messageIfFalse, null, locale));
		}		
	}
}
