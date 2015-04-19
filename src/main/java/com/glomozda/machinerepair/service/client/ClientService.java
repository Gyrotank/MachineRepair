package com.glomozda.machinerepair.service.client;

import java.util.List;

import com.glomozda.machinerepair.domain.client.*;
import com.glomozda.machinerepair.repository.client.ClientRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
   
	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional
	public List<Client> getAll() {
		return clientRepository.getAll();
	}
	
	@Transactional
	public List<Client> getAll(Long start, Long length) {
		return clientRepository.getAll(start, length);
	}
	
	@Transactional
	public List<Client> getAllWithFetching() {
		return clientRepository.getAllWithFetching();
	}
	
	@Transactional
	public List<Client> getAllWithFetching(Long start, Long length) {
		return clientRepository.getAllWithFetching(start, length);
	}
	
	@Transactional
	public List<Long> getAllClientIds() {
		return clientRepository.getAllClientIds();
	}

	@Transactional
	public Client getClientByUserId(Long userId) {
		return clientRepository.getClientByUserId(userId);
	}
	
	@Transactional
	public Client getClientByUserIdWithFetching(Long userId) {
		return clientRepository.getClientByUserIdWithFetching(userId);
	}
	
	@Transactional
	public Client getClientByLogin(String login) {
		return clientRepository.getClientByLogin(login);
	}
	
	@Transactional
	public Client getClientByLoginWithFetching(String login) {
		return clientRepository.getClientByLoginWithFetching(login);
	}
	
	@Transactional
	public Long getClientCount() {
		return clientRepository.getClientCount();
	}

	@Transactional
	public Boolean add(Client c, Long userId) {
		return clientRepository.add(c, userId);
	}
}
