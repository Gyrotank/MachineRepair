package com.glomozda.machinerepair.service.client;

import java.util.List;

import com.glomozda.machinerepair.domain.client.*;

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
		return clientRepository.add(c, userId);
	}
	
	@Override
	public Integer updateClientNameById(Long clientId, String name) {
		return clientRepository.updateClientNameById(clientId, name);
	}
}
