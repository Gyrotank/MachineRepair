package com.glomozda.machinerepair.service.client;

import java.util.List;

import com.glomozda.machinerepair.domain.client.*;
import com.glomozda.machinerepair.repository.client.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
   
	@Autowired
	private ClientRepository clientRepository;
	
	public List<Client> getAll() {
		return clientRepository.getAll();
	}
	
	public List<Client> getAll(Long start, Long length) {
		return clientRepository.getAll(start, length);
	}
	
	public List<Client> getAllWithFetching() {
		return clientRepository.getAllWithFetching();
	}
	
	public List<Client> getAllWithFetching(Long start, Long length) {
		return clientRepository.getAllWithFetching(start, length);
	}
	
	public List<Long> getAllClientIds() {
		return clientRepository.getAllClientIds();
	}
	
	public Client getClientById(Long clientId) {
		return clientRepository.getClientById(clientId);
	}

	public Client getClientByUserId(Long userId) {
		return clientRepository.getClientByUserId(userId);
	}
	
	public Client getClientByUserIdWithFetching(Long userId) {
		return clientRepository.getClientByUserIdWithFetching(userId);
	}
	
	public Client getClientByLogin(String login) {
		return clientRepository.getClientByLogin(login);
	}
	
	public Client getClientByLoginWithFetching(String login) {
		return clientRepository.getClientByLoginWithFetching(login);
	}
	
	public Long getClientCount() {
		return clientRepository.getClientCount();
	}

	public Boolean add(Client c, Long userId) {
		return clientRepository.add(c, userId);
	}
	
	public Integer updateClientNameById(Long clientId, String name) {
		return clientRepository.updateClientNameById(clientId, name);
	}
}
