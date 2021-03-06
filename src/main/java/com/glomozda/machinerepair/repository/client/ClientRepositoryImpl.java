package com.glomozda.machinerepair.repository.client;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.user.User;

@Repository
public class ClientRepositoryImpl extends ClientRepository {
	
	@Override
	public List<Client> getAll() {
		List<Client> result = em.createNamedQuery("Client.findAll", Client.class)				
				.getResultList();
		return result;
	}
	
	@Override
	public List<Client> getAll(Long start, Long length) {
		List<Client> result = em.createNamedQuery("Client.findAll", Client.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Override
	public List<Client> getAllWithFetching() {
		List<Client> result = em.createNamedQuery("Client.findAllWithFetching", Client.class)
				.getResultList();
		return result;
	}
	
	@Override
	public List<Client> getAllWithFetching(Long start, Long length) {
		List<Client> result = em.createNamedQuery("Client.findAllWithFetching", Client.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Override
	public List<Long> getAllClientIds() {
		return em.createNamedQuery("Client.findAllClientIds", Long.class).getResultList();		
	}
	
	@Override
	public Client getClientById(Long clientId) {
		return em.find(Client.class, clientId);
	}
	
	@Override
	public Client getClientByUserId(Long userId) {
		Client result = null;	  
		TypedQuery<Client> query = em.createNamedQuery("Client.findClientByUserId", Client.class);
		query.setParameter("id", userId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	@Override
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
	
	@Override
	public Client getClientByLogin(String login) {
		Client result = null;	  
		TypedQuery<Client> query = em.createNamedQuery("Client.findClientByLogin", Client.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Override
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
	
	@Override
	public Long getClientCount() {
		return em.createNamedQuery("Client.countAll", Long.class).getSingleResult();
	}
	
	@Override
	@Transactional
	public Boolean add(Client c, Long userId) throws PersistenceException {
		User user = em.getReference(User.class, userId);

		Client newClient = new Client();
		newClient.setClientName(c.getClientName());
		newClient.setClientUser(user);
		
		if (em.contains(newClient)) {
			return false;
		}
		
		em.persist(newClient);
		
		if (em.contains(newClient)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	@Transactional
	public Integer updateClientNameById(Long clientId, String name) {
		Query query = em.createNamedQuery("Client.setClientNameById");
		query.setParameter("id", clientId);
		query.setParameter("name", name);
		int updateCount = query.executeUpdate();
		return updateCount;
	}

	@Override
	public Long getClientCountLikeName(String likePattern) {
		return em.createNamedQuery("Client.countLikeName", Long.class)
				.setParameter("likePattern", "%" + likePattern + "%")
				.getSingleResult();
	}
	
	@Override
	public List<Client> getClientsLikeName(String likePattern) {
		return em.createNamedQuery("Client.findClientsLikeName", Client.class)
				.setParameter("likePattern", "%" + likePattern + "%")
				.getResultList();
	}
	
	@Override
	public List<Client> getClientsLikeName(String likePattern, Long start, Long length) {
		return em.createNamedQuery("Client.findClientsLikeName", Client.class)
				.setParameter("likePattern", "%" + likePattern + "%")
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
	}
	
	@Override
	public List<Object[]> getIdsAndNamesLikeName(String likePattern) {
		return em.createNamedQuery("Client.findIdsAndNamesLikeName", Object[].class)
				.setParameter("likePattern", "%" + likePattern + "%")				
				.getResultList();
	}
	
	@Override
	public List<Object[]> getIdsAndNamesLikeName(String likePattern, Long start, Long length) {
		return em.createNamedQuery("Client.findIdsAndNamesLikeName", Object[].class)
				.setParameter("likePattern", "%" + likePattern + "%")
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
	}
}
