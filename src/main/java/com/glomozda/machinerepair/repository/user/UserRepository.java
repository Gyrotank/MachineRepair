package com.glomozda.machinerepair.repository.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.user.User;

@Repository
public class UserRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private PasswordEncoder encoder;

	@Transactional
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
	
	@Transactional
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
	
	@Transactional
	public User getUserByLogin(String login) {
		User result = null;	  
		TypedQuery<User> query = em.createNamedQuery("User.findUserByLogin", User.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
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
	
	@Transactional
	public User getUserById(Long userId) {
		return em.find(User.class, userId);
//		if (result == null) {
//			throw new NoResultException();
//		}		
//		return result;
	}
	
	@Transactional
	public User getUserByIdWithFetching(Long userId) {
		User result = null;	  
		TypedQuery<User> query = em.createNamedQuery("User.findUserByIdWithFetching", User.class);
		query.setParameter("id", userId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public List<User> getAll() {
		List<User> result = em.createNamedQuery("User.findAll", User.class)				
				.getResultList();
		return result;
	}

	@Transactional
	public List<User> getAll(Long start, Long length) {
		List<User> result = em.createNamedQuery("User.findAll", User.class)
				.setFirstResult(start.intValue())
				.setMaxResults(length.intValue())
				.getResultList();
		return result;
	}
	
	@Transactional
	public List<Object[]> getAllIdsAndLogins() {
		List<Object[]> result = em.createNamedQuery("User.findAllIdsAndLogins", Object[].class)				
				.getResultList();
		return result;
	}
	
	@Transactional
	public Long getUserCount() {
		return em.createNamedQuery("User.countAll", Long.class).getSingleResult();
	}
	
	@Transactional
	public Integer setUserEnabledById(Long userId, Byte enabled) {
		Query query = em.createNamedQuery("User.setUserEnabledById");
		query.setParameter("id", userId);
		query.setParameter("enabled", enabled);
		int updateCount = query.executeUpdate();
		return updateCount;
	}

	@Transactional
	public Boolean add(User u) {
		em.persist(u);
		if (em.contains(u)) {
			return true;
		} else {
			return false;
		}
	}
}
