package com.glomozda.machinerepair.repository.userauthorization;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;

@Service
public class UserAuthorizationService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public List<UserAuthorization> getAll() {
		List<UserAuthorization> result = 
				em.createQuery("SELECT ua FROM UserAuthorization ua",
						UserAuthorization.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<UserAuthorization> getAllWithFetching() {
		List<UserAuthorization> result = 
				em.createQuery("SELECT ua FROM UserAuthorization ua"
						+ " LEFT JOIN FETCH ua.user",
						UserAuthorization.class).getResultList();
		return result;
	}
	
	@Transactional
	public List<String> getAllRoles() {
		List<String> result = 
				em.createQuery("SELECT DISTINCT (ua.role) FROM UserAuthorization ua",
						String.class).getResultList();
		return result;
	}

	@Transactional
	public void add(UserAuthorization ua, Integer userId) {
		User user = em.getReference(User.class, userId);
		
		UserAuthorization newUserAuthorization = new UserAuthorization();
		newUserAuthorization.setRole(ua.getRole());
		newUserAuthorization.setUser(user);
		
		em.persist(newUserAuthorization);
	}
}
