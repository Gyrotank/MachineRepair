package com.glomozda.machinerepair.controller.clientpage;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.service.client.ClientService;
import com.glomozda.machinerepair.service.machine.MachineService;
import com.glomozda.machinerepair.service.machineserviceable.MachineServiceableService;
import com.glomozda.machinerepair.service.order.OrderService;
import com.glomozda.machinerepair.service.repairtype.RepairTypeService;

@Controller
public class ClientPagePastOrdersController implements MessageSourceAware {
	
static Logger log = Logger.getLogger(ClientPageController.class.getName());
	
	@Autowired
	private ClientService clientSvc;
	
	@Autowired
	private MachineService machineSvc;
	
	@Autowired
	private MachineServiceableService machineServiceableSvc;
	
	@Autowired
	private OrderService orderSvc;
	
	@Autowired
	private RepairTypeService repairTypeSvc;
	
	private static final Long _defaultPageSize = (long) 25;
	
	private Client myClient;
	
	private MessageSource messageSource;
	
	private Long pastOrdersPagingFirstIndex = (long) 0;
	private Long pastOrdersPagingLastIndex = _defaultPageSize - 1;
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/clientpagepastorders", method = RequestMethod.GET)
	public String activate(final Locale locale, final Principal principal, final Model model) {
		
//		log.info("Activating Client Page for " + principal.getName() + "...");
		
		myClient = clientSvc.getClientByLoginWithFetching(principal.getName());
		if (null == myClient) {
			return "redirect:/index";
		}		
		
		model.addAttribute("locale", locale.toString());
		
		model.addAttribute("clientname", myClient.getClientName());
		
		model.addAttribute("message_past_orders",
				messageSource.getMessage("label.clientpage.noPastOrders", null,
						locale));
		
		model.addAttribute("past_orders_count", 
				orderSvc.getCountOrdersForClientIdAndStatus(myClient.getClientId(), "finished"));
		List<Order> myPastOrders =
				orderSvc.getOrdersForClientIdAndStatusWithFetching(myClient.getClientId(),
						"finished", pastOrdersPagingFirstIndex, 
						pastOrdersPagingLastIndex - pastOrdersPagingFirstIndex + 1);
		model.addAttribute("my_past_orders", myPastOrders);
		model.addAttribute("past_orders_paging_first", pastOrdersPagingFirstIndex);
		model.addAttribute("past_orders_paging_last", pastOrdersPagingLastIndex);

		return "clientpagepastorders";
	}
	
	@RequestMapping(value = "/clientpagepastorders/pastorderspaging",
			method = RequestMethod.POST)
	public String pastOrdersPaging(
			@RequestParam("pastOrdersPageStart") final Long pastOrdersPageStart, 
			@RequestParam("pastOrdersPageEnd") final Long pastOrdersPageEnd) {
		
		long pastOrdersStart;
		long pastOrdersEnd;
		
		if (pastOrdersPageStart == null) {
			pastOrdersStart = (long) 0;
		} else {
			pastOrdersStart = pastOrdersPageStart.longValue() - 1;
		}
		
		if (pastOrdersPageEnd == null) {
			pastOrdersEnd = (long) 0;
		} else {
			pastOrdersEnd = pastOrdersPageEnd.longValue() - 1;
		}
		
		long pastOrdersCount = 
				orderSvc.getCountOrdersForClientIdAndStatus(myClient.getClientId(), "finished");
		
		if (pastOrdersStart > pastOrdersEnd) {
			long temp = pastOrdersStart;
			pastOrdersStart = pastOrdersEnd;
			pastOrdersEnd = temp;
		}
		
		if (pastOrdersStart < 0)
			pastOrdersStart = 0;
		
		if (pastOrdersStart >= pastOrdersCount)
			pastOrdersStart = pastOrdersCount - 1;
		
		if (pastOrdersEnd < 0)
			pastOrdersEnd = 0;
		
		if (pastOrdersEnd >= pastOrdersCount)
			pastOrdersEnd = pastOrdersCount - 1;
		
		pastOrdersPagingFirstIndex = pastOrdersStart;
		pastOrdersPagingLastIndex = pastOrdersEnd;		
		
		return "redirect:/clientpagepastorders";
	}
	
	
}
