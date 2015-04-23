package com.glomozda.machinerepair.repository.userauthorization;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userrole.UserRole;

@Repository
public class UserAuthorizationRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public List<UserAuthorization> getAll() {
		List<UserAuthorization> result = 
				em.createNamedQuery("UserAuthorization.findAll",
						UserAuthorization.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<UserAuthorization> getAll(Long start, Long length) {
		List<UserAuthorization> result = 
				em.createNamedQuery("UserAuthorization.findAll",
						UserAuthorization.class)
						.setFirstResult(start.intValue())
						.setMaxResults(length.intValue())
						.getResultList();
		return result;
	}
	
	@Transactional
	public List<UserAuthorization> getAllWithFetching(Long start, Long length) {
		List<UserAuthorization> result = 
				em.createNamedQuery("UserAuthorization.findAllWithFetching",
						UserAuthorization.class)
						.setFirstResult(start.intValue())
						.setMaxResults(length.intValue())
						.getResultList();
		return result;
	}
	
	@Transactional
	public LinkedHashSet<User> getDistinctUsersWithFetching(Long start, Long length) {
		List<UserAuthorization> query_result = 
				em.createNamedQuery("UserAuthorization.findAllWithFetching",
						UserAuthorization.class)
						.setFirstResult(start.intValue())
						.setMaxResults(length.intValue())						
						.getResultList();
		LinkedHashSet<User> result = 
				new LinkedHashSet<User>();
		for (UserAuthorization ua : query_result) {
			result.add(ua.getUser());
		}
		return result;
	}	
	
	@Transactional
	public List<UserAuthorization> getAllWithFetching() {
		List<UserAuthorization> result = 
				em.createNamedQuery("UserAuthorization.findAllWithFetching",
						UserAuthorization.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<UserRole> getAllRoles() {
		List<UserRole> result = 
				em.createNamedQuery("UserAuthorization.findAllRoles",
						UserRole.class).getResultList();
		return result;
	}
	
	@Transactional
	public UserAuthorization getUserAuthorizationForUserIdAndRole(Long userId, String role) {
		UserAuthorization result = null;
		try {
		result = 
				em.createNamedQuery("UserAuthorization.findUserAuthorizationForUserIdAndRole",
						UserAuthorization.class)
						.setParameter("id", userId)
						.setParameter("role", role)
						.getSingleResult();
		} catch (NoResultException nre){}
		return result;
	}
	
	@Transactional
	public List<String> getUserLoginsForRole(String role) {
		List<UserAuthorization> queryResult = 
				em.createNamedQuery("UserAuthorization.findUserAuthorizationForRole",
						UserAuthorization.class)
						.setParameter("role", role)
						.getResultList();
		
		List<String> result = new ArrayList<String>();		
		for (UserAuthorization ua : queryResult) {
			result.add(ua.getUser().getLogin());
		}
		return result;		
	}
	
	@Transactional
	public Long getUserAuthorizationCount() {
		return em.createNamedQuery("UserAuthorization.countAll", Long.class).getSingleResult();
	}

	@Transactional
	public Boolean add(UserAuthorization ua, Long userId) {
		User user = em.getReference(User.class, userId);
		
		UserAuthorization newUserAuthorization = new UserAuthorization();
		newUserAuthorization.setRole(ua.getRole());
		newUserAuthorization.setUser(user);
		
		em.persist(newUserAuthorization);
		
		if (em.contains(newUserAuthorization)) {
			return true;
		} else {
			return false;
		}
	}
}
