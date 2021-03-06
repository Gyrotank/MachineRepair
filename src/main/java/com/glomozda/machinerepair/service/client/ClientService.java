package com.glomozda.machinerepair.service.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.repository.client.ClientRepository;
import com.glomozda.machinerepair.service.EntityService;
import com.glomozda.machinerepair.service.userauthorization.UserAuthorizationService;

@Service
public abstract class ClientService implements EntityService {

	@Autowired
	protected ClientRepository clientRepository;
	
	@Autowired
	protected UserAuthorizationService userAuthorizationSvc;

	public abstract Integer updateClientNameById(Long clientId, String name);

	public abstract Boolean add(Client c, Long userId);
	
	public abstract Boolean createClientAccount(Client c, Long userId);

	public abstract Long getClientCount();

	public abstract Client getClientByLoginWithFetching(String login);

	public abstract Client getClientByLogin(String login);

	public abstract Client getClientByUserIdWithFetching(Long userId);

	public abstract Client getClientByUserId(Long userId);

	public abstract Client getClientById(Long clientId);

	public abstract List<Long> getAllClientIds();

	public abstract List<Client> getAllWithFetching(Long start, Long length);

	public abstract List<Client> getAllWithFetching();

	public abstract List<Client> getAll(Long start, Long length);

	public abstract List<Client> getAll();

	public abstract Long getClientCountLikeName(String searchQueryArgument);
	
	public abstract List<Client> getClientsLikeName(String searchQueryArgument);

	public abstract List<Client> getClientsLikeName(String likePattern, Long start,	Long length);

	public abstract Map<Long, String> getIdsAndNamesLikeName(String likePattern);

	public abstract Map<Long, String> getIdsAndNamesLikeName(String likePattern,
			Long start, Long length);
}