package com.glomozda.machinerepair.service.client;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import com.glomozda.machinerepair.domain.client.*;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userrole.UserRole;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl extends ClientService {
   
	@Override
	public List<Client> getAll() {
		return clientRepository.getAll();
	}
	
	@Override
	public List<Client> getAll(Long start, Long length) {
		return clientRepository.getAll(start, length);
	}
	
	@Override
	public List<Client> getAllWithFetching() {
		return clientRepository.getAllWithFetching();
	}
	
	@Override
	public List<Client> getAllWithFetching(Long start, Long length) {
		return clientRepository.getAllWithFetching(start, length);
	}
	
	@Override
	public List<Long> getAllClientIds() {
		return clientRepository.getAllClientIds();
	}
	
	@Override
	public Client getClientById(Long clientId) {
		return clientRepository.getClientById(clientId);
	}

	@Override
	public Client getClientByUserId(Long userId) {
		return clientRepository.getClientByUserId(userId);
	}
	
	@Override
	public Client getClientByUserIdWithFetching(Long userId) {
		return clientRepository.getClientByUserIdWithFetching(userId);
	}
	
	@Override
	public Client getClientByLogin(String login) {
		return clientRepository.getClientByLogin(login);
	}
	
	@Override
	public Client getClientByLoginWithFetching(String login) {
		return clientRepository.getClientByLoginWithFetching(login);
	}
	
	@Override
	public Long getClientCount() {
		return clientRepository.getClientCount();
	}

	@Override
	public Boolean add(Client c, Long userId) {
		Boolean result = false;
		
		try {
			result = clientRepository.add(c, userId);
		} catch (PersistenceException e) {
			Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		        return false;
		    }
		    throw(e);
		}
		
		return result;
	}
	
	@Override
	public Boolean createClientAccount(Client c, Long userId) {
		
		if (!add(c, userId)) {
			return false;
		}
		
		UserAuthorization newUserAuthorization = 
				new UserAuthorization(new UserRole("ROLE_CLIENT"));		
		return userAuthorizationSvc.add(newUserAuthorization, userId);
	}
	
	@Override
	public Integer updateClientNameById(Long clientId, String name) {
		return clientRepository.updateClientNameById(clientId, name);
	}

	@Override
	public Long getClientCountLikeName(String likePattern) {
		return clientRepository.getClientCountLikeName(likePattern);
	}

	@Override
	public List<Client> getClientsLikeName(String likePattern) {
		return clientRepository.getClientsLikeName(likePattern);
	}
	
	@Override
	public List<Client> getClientsLikeName(String likePattern, Long start, Long length) {
		return clientRepository.getClientsLikeName(likePattern, start, length);
	}
	
	@Override
	public Map<Long, String> getIdsAndNamesLikeName(String likePattern) {
		List<Object[]> idsAndNamesList = clientRepository.getIdsAndNamesLikeName(likePattern);
		
		Map<Long, String> idsAndNamesMap = 
				new LinkedHashMap<Long, String>(idsAndNamesList.size());
		for (Object[] idAndName : idsAndNamesList)
			idsAndNamesMap.put((Long)idAndName[0], (String)idAndName[1]);
		
		return idsAndNamesMap;
	}
	
	@Override
	public Map<Long, String> getIdsAndNamesLikeName(String likePattern, Long start, Long length) {
		List<Object[]> idsAndNamesList 
			= clientRepository.getIdsAndNamesLikeName(likePattern, start, length);
		
		Map<Long, String> idsAndNamesMap = 
				new LinkedHashMap<Long, String>(idsAndNamesList.size());
		for (Object[] idAndName : idsAndNamesList)
			idsAndNamesMap.put((Long)idAndName[0], (String)idAndName[1]);
		
		return idsAndNamesMap;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllEntities() {
		return getAllWithFetching();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllEntities(Long start, Long length) {
		return getAllWithFetching(start, length);
	}

	@Override
	public Long getCountEntities() {
		return getClientCount();
	}
}
