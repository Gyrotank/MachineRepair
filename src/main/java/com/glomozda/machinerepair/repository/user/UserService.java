package com.glomozda.machinerepair.repository.user;

import java.util.List;

import com.glomozda.machinerepair.domain.user.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private PasswordEncoder encoder;

	@Transactional
	public User getUserByLoginAndPassword(String login, String passwordText) {
		User result = null;
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u"
				+ " WHERE u.login = :login", User.class);
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
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u"
				+ " LEFT JOIN FETCH u.client"
				+ " WHERE u.login = :login", User.class);
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
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u"
				+ " WHERE u.login = :login", User.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public User getUserByLoginWithFetching(String login) {
		User result = null;	  
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u"
				+ " LEFT JOIN FETCH u.client"
				+ " WHERE u.login = :login", User.class);
		query.setParameter("login", login);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public User getUserById(Integer userId) {
		User result = null;	  
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u"
				+ " WHERE u.userId = :id", User.class);
		query.setParameter("id", userId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}
	
	@Transactional
	public User getUserByIdWithFetching(Integer userId) {
		User result = null;	  
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u"
				+ " LEFT JOIN FETCH u.client"
				+ " WHERE u.userId = :id", User.class);
		query.setParameter("id", userId);	  
		try {
			result = query.getSingleResult();
		} catch (NoResultException nre){}
		
		return result;
	}

	@Transactional
	public List<User> getAll() {
		List<User> result = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		return result;
	}

	@Transactional
	public void add(User u) {
		em.persist(u);
	}	
}
