package com.glomozda.machinerepair.repository.client;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.client.Client;

@Repository
public abstract class ClientRepository {
	
	@PersistenceContext
	protected EntityManager em;
	
	public abstract List<Client> getAll();
	
	public abstract List<Client> getAll(Long start, Long length);
	
	public abstract List<Client> getAllWithFetching();
	
	public abstract List<Client> getAllWithFetching(Long start, Long length);
	
	public abstract List<Long> getAllClientIds();
	
	public abstract Client getClientById(Long clientId);
	
	public abstract Client getClientByUserId(Long userId);
	
	public abstract Client getClientByUserIdWithFetching(Long userId);
	
	public abstract Client getClientByLogin(String login);
	
	public abstract Client getClientByLoginWithFetching(String login);
	
	public abstract Long getClientCount();
	
	@Transactional
	public abstract Boolean add(Client c, Long userId);
	
	@Transactional
	public abstract Integer updateClientNameById(Long clientId, String name);

	public abstract Long getClientCountLikeName(String likePattern);

	public abstract List<Client> getClientsLikeName(String likePattern);

	public abstract List<Client> getClientsLikeName(String likePattern, Long start, Long length);
	
}
