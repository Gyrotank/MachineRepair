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
		List<Client> result = em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<Client> getAllWithFetching() {
		List<Client> result = em.createQuery("SELECT c FROM Client c"
				+ " LEFT JOIN FETCH c.clientUser", Client.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<Integer> getAllClientIds() {
		List<Integer> result = 
				em.createQuery("SELECT c.clientId FROM Client c", Integer.class).getResultList();
		return result;
	}

	@Transactional
	public Client getClientByUserId(Integer userId) {
		Client result = null;	  
		TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c"
				+ " WHERE c.clientUser.userId = :id", Client.class);
		query.setParameter("id", userId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public Client getClientByUserIdWithFetching(Integer userId) {
		Client result = null;	  
		TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c"
				+ " LEFT JOIN FETCH c.clientUser"
				+ " WHERE c.clientUser.userId = :id", Client.class);
		query.setParameter("id", userId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public Client getClientByLogin(String login) {
		Client result = null;	  
		TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c"
				+ " WHERE c.clientUser.login = :login", Client.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public Client getClientByLoginWithFetching(String login) {
		Client result = null;	  
		TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c"
				+ " LEFT JOIN FETCH c.clientUser"
				+ " WHERE c.clientUser.login = :login", Client.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}

	@Transactional
	public void add(Client c, Integer userId) {
		User user = em.getReference(User.class, userId);

		Client newClient = new Client();
		newClient.setClientName(c.getClientName());
		newClient.setClientUser(user);
		em.persist(newClient);	  
	}
}
