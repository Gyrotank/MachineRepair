package com.glomozda.machinerepair.repository.userauthorization;

import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userrole.UserRole;

@Repository
public class UserAuthorizationRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<UserAuthorization> getAll() {
		List<UserAuthorization> result = 
				em.createNamedQuery("UserAuthorization.findAll",
						UserAuthorization.class).getResultList();
		return result;
	}
	
	public List<UserAuthorization> getAll(Long start, Long length) {
		List<UserAuthorization> result = 
				em.createNamedQuery("UserAuthorization.findAll",
						UserAuthorization.class)
						.setFirstResult(start.intValue())
						.setMaxResults(length.intValue())
						.getResultList();
		return result;
	}
	
	public List<UserAuthorization> getAllWithFetching(Long start, Long length) {
		List<UserAuthorization> result = 
				em.createNamedQuery("UserAuthorization.findAllWithFetching",
						UserAuthorization.class)
						.setFirstResult(start.intValue())
						.setMaxResults(length.intValue())
						.getResultList();
		return result;
	}
	
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
	
	public List<UserAuthorization> getAllWithFetching() {
		List<UserAuthorization> result = 
				em.createNamedQuery("UserAuthorization.findAllWithFetching",
						UserAuthorization.class).getResultList();
		return result;
	}
	
	public List<UserRole> getAllRoles() {
		List<UserRole> result = 
				em.createNamedQuery("UserAuthorization.findAllRoles",
						UserRole.class).getResultList();
		return result;
	}
	
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
	
	public List<String> getUserLoginsForRole(String role) {
		return em.createNamedQuery("UserAuthorization.findUserLoginsForRole",
					String.class)
					.setParameter("role", role)
					.getResultList();	
	}
	
	public Long getUserAuthorizationCount() {
		Long result = null;
		try {
			result = em.createNamedQuery("UserAuthorization.countAll", Long.class)
						.getSingleResult();
		} catch (NoResultException nre){}
		return result;
	}
	
	public Long getCountUserAuthorizationsForRole(String role) {
		Long result = null;
		try {
			result = em.createNamedQuery("UserAuthorization.countUserAuthorizationsForRole",
				Long.class)
				.setParameter("role", role)
				.getSingleResult();
		} catch (NoResultException nre){}
		return result;
	}
	
	public List<UserRole> getRolesForUserId(Long userId) {
		return em.createNamedQuery("UserAuthorization.findRolesForUserId",
						UserRole.class)
						.setParameter("id", userId)
						.getResultList();		
	}
	
	public User getUserForUserAuthorizationId(Long userAuthorizationId) {
		User result = null;
		try {
			result = em.createNamedQuery("UserAuthorization.findUserForUserAuthorizationId",
				User.class)
				.setParameter("id", userAuthorizationId)
				.getSingleResult();
		} catch (NoResultException nre){}
		return result;
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
	
	@Transactional
	public Integer deleteUserAuthorizationByUserIdAndRole(Long userId, String role) {
		Query query = em.createNamedQuery(
				"UserAuthorization.deleteUserAuthorizationByUserIdAndRole");
		query.setParameter("id", userId);
		query.setParameter("role", role);
		int deletedCount = query.executeUpdate();
		return deletedCount;
	}
	
	public List<UserAuthorization> getUserAuthorizationsByUserId(Long userId) {
		return em.createNamedQuery("UserAuthorization.findUserAuthorizationsByUserId",
				UserAuthorization.class)
				.setParameter("id", userId)
				.getResultList();
	}
}
