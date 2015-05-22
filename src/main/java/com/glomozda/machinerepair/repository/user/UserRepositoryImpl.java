package com.glomozda.machinerepair.repository.user;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.user.User;

@Repository
public class UserRepositoryImpl extends UserRepository {
	
	@Override
	public User getUserByLoginAndPassword(String login, String passwordText) {
		User result = null;
		TypedQuery<User> query = em.createNamedQuery("User.findUserByLogin",
				User.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		if (null != result) {
			if (!encoder.matches(passwordText, result.getPassword())) {
				result = null;
			}
		}
		return result;
	}
	
	@Override
	public User getUserByLoginAndPasswordWithFetching(String login, String passwordText) {
		User result = null;	  
		TypedQuery<User> query = em.createNamedQuery("User.findUserByLoginWithFetching",
				User.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		if (null != result) {
			if (!encoder.matches(passwordText, result.getPassword())) {
				result = null;
			}
		}
		return result;
	}
	
	@Override
	public User getUserByLogin(String login) {
		User result = null;	  
		TypedQuery<User> query = em.createNamedQuery("User.findUserByLogin", User.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Override
	public User getUserByLoginWithFetching(String login) {
		User result = null;	  
		TypedQuery<User> query = em.createNamedQuery("User.findUserByLoginWithFetching",
				User.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Override
	public User getUserById(Long userId) {
		return em.find(User.class, userId);
	}
	
	@Override
	public User getUserByIdWithFetching(Long userId) {
		User result = null;	  
		TypedQuery<User> query = em.createNamedQuery("User.findUserByIdWithFetching", User.class);
		query.setParameter("id", userId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Override
	public List<User> getAll() {
		List<User> result = em.createNamedQuery("User.findAll", User.class)				
				.getResultList();
		return result;
	}

	@Override
	public List<User> getAll(Long start, Long length) {
		List<User> result = em.createNamedQuery("User.findAll", User.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
		
	@Override
	public List<Object[]> getAllIdsAndLogins() {
		List<Object[]> result = em.createNamedQuery("User.findAllIdsAndLogins", Object[].class)				
				.getResultList();
		return result;
	}
	
	@Override
	public Long getUserCount() {
		return em.createNamedQuery("User.countAll", Long.class).getSingleResult();
	}
	
	@Override
	@Transactional
	public Integer setUserEnabledById(Long userId, Byte enabled) {
		Query query = em.createNamedQuery("User.setUserEnabledById");
		query.setParameter("id", userId);
		query.setParameter("enabled", enabled);
		int updateCount = query.executeUpdate();
		return updateCount;
	}

	@Override
	@Transactional
	public Boolean add(User u) throws PersistenceException {
		if (em.contains(u)) {
			return false;
		}
		
		em.persist(u);
		
		if (em.contains(u)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Long getUserCountLikeName(String likePattern) {
		return em.createNamedQuery("User.countLikeName", Long.class)
			.setParameter("likePattern", "%" + likePattern + "%")
			.getSingleResult();
	}
}
