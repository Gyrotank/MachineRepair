package com.glomozda.machinerepair.repository.client;

import java.util.List;

import com.glomozda.machinerepair.domain.client.*;
import com.glomozda.machinerepair.domain.user.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
   
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public List<Client> getAll() {
		List<Client> result = em.createNamedQuery("Client.findAll", Client.class)				
				.getResultList();
		return result;
	}
	
	@Transactional
	public List<Client> getAll(Long start, Long length) {
		List<Client> result = em.createNamedQuery("Client.findAll", Client.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Transactional
	public List<Client> getAllWithFetching() {
		List<Client> result = em.createNamedQuery("Client.findAllWithFetching", Client.class)
				.getResultList();
		return result;
	}
	
	@Transactional
	public List<Client> getAllWithFetching(Long start, Long length) {
		List<Client> result = em.createNamedQuery("Client.findAllWithFetching", Client.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Transactional
	public List<Long> getAllClientIds() {
		List<Long> result = 
				em.createNamedQuery("Client.findAllClientIds", Long.class).getResultList();
		return result;
	}

	@Transactional
	public Client getClientByUserId(Long userId) {
		Client result = null;	  
		TypedQuery<Client> query = em.createNamedQuery("Client.findClientByUserId", Client.class);
		query.setParameter("id", userId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public Client getClientByUserIdWithFetching(Long userId) {
		Client result = null;	  
		TypedQuery<Client> query = em.createNamedQuery(
				"Client.findClientByUserIdWithFetching", Client.class);
		query.setParameter("id", userId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public Client getClientByLogin(String login) {
		Client result = null;	  
		TypedQuery<Client> query = em.createNamedQuery("Client.findClientByLogin", Client.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public Client getClientByLoginWithFetching(String login) {
		Client result = null;	  
		TypedQuery<Client> query = em.createNamedQuery(
				"Client.findClientByLoginWithFetching", Client.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public Long getClientCount() {
		return em.createNamedQuery("Client.countAll", Long.class).getSingleResult();
	}

	@Transactional
	public void add(Client c, Long userId) {
		User user = em.getReference(User.class, userId);

		Client newClient = new Client();
		newClient.setClientName(c.getClientName());
		newClient.setClientUser(user);
		em.persist(newClient);	  
	}
}
