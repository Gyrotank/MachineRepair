package com.glomozda.machinerepair.repository.userauthorization;

import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userrole.UserRole;

@Repository
public abstract class UserAuthorizationRepository {

	@PersistenceContext
	protected EntityManager em;

	public abstract List<UserAuthorization> getUserAuthorizationsByUserId(Long userId);
	
	@Transactional
	public abstract Integer deleteUserAuthorizationByUserIdAndRole(Long userId,
			String role);
	
	@Transactional
	public abstract Boolean add(UserAuthorization ua, Long userId);

	public abstract User getUserForUserAuthorizationId(Long userAuthorizationId);

	public abstract List<UserRole> getRolesForUserId(Long userId);

	public abstract Long getCountUserAuthorizationsForRole(String role);

	public abstract Long getUserAuthorizationCount();

	public abstract List<String> getUserLoginsForRole(String role);

	public abstract UserAuthorization getUserAuthorizationForUserIdAndRole(Long userId,
			String role);

	public abstract List<UserRole> getAllRoles();

	public abstract List<UserAuthorization> getAllWithFetching();

	public abstract LinkedHashSet<User> getDistinctUsersWithFetching(Long start,
			Long length);

	public abstract List<UserAuthorization> getAllWithFetching(Long start, Long length);

	public abstract List<UserAuthorization> getAll(Long start, Long length);

	public abstract List<UserAuthorization> getAll();

}