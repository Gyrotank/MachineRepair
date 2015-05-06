package com.glomozda.machinerepair.repository.client;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.user.User;

@Repository
public class ClientRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Client> getAll() {
		List<Client> result = em.createNamedQuery("Client.findAll", Client.class)				
				.getResultList();
		return result;
	}
	
	public List<Client> getAll(Long start, Long length) {
		List<Client> result = em.createNamedQuery("Client.findAll", Client.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	public List<Client> getAllWithFetching() {
		List<Client> result = em.createNamedQuery("Client.findAllWithFetching", Client.class)
				.getResultList();
		return result;
	}
	
	public List<Client> getAllWithFetching(Long start, Long length) {
		List<Client> result = em.createNamedQuery("Client.findAllWithFetching", Client.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	public List<Long> getAllClientIds() {
		List<Long> result = 
				em.createNamedQuery("Client.findAllClientIds", Long.class).getResultList();
		return result;
	}
	
	public Client getClientById(Long clientId) {
		return em.find(Client.class, clientId);
	}

	public Client getClientByUserId(Long userId) {
		Client result = null;	  
		TypedQuery<Client> query = em.createNamedQuery("Client.findClientByUserId", Client.class);
		query.setParameter("id", userId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
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
	
	public Client getClientByLogin(String login) {
		Client result = null;	  
		TypedQuery<Client> query = em.createNamedQuery("Client.findClientByLogin", Client.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
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
	
	public Long getClientCount() {
		return em.createNamedQuery("Client.countAll", Long.class).getSingleResult();
	}
	
	@Transactional
	public Boolean add(Client c, Long userId) {
		User user = em.getReference(User.class, userId);

		Client newClient = new Client();
		newClient.setClientName(c.getClientName());
		newClient.setClientUser(user);
		em.persist(newClient);
		
		if (em.contains(newClient)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional
	public Integer updateClientNameById(Long clientId, String name) {
		Query query = em.createNamedQuery("Client.setClientNameById");
		query.setParameter("id", clientId);
		query.setParameter("name", name);
		int updateCount = query.executeUpdate();
		return updateCount;
	}
}
