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
		User result = em.find(User.class, userId);
		if (result == null) {
			throw new NoResultException();
		}		
		return result;
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
		List<User> result = em.createNamedQuery("User.findAll", User.class).getResultList();
		return result;
	}

	@Transactional
	public void add(User u) {
		em.persist(u);
	}	
}
