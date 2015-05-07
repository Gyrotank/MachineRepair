package com.glomozda.machinerepair.service.userauthorization;

import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userrole.UserRole;
import com.glomozda.machinerepair.repository.userauthorization.UserAuthorizationRepository;

@Service
public abstract class UserAuthorizationService {

	@Autowired
	protected UserAuthorizationRepository userAuthorizationRepository;

	public abstract List<UserAuthorization> getUserAuthorizationsByUserId(Long userId);

	public abstract Integer deleteUserAuthorizationByUserIdAndRole(Long userId,
			String role);

	public abstract Boolean add(UserAuthorization ua, Long userId);

	public abstract User getUserForUserAuthorizationId(Long userAuthorizationId);

	public abstract List<UserRole> getRolesForUserId(Long userId);

	public abstract Long getCountUserAuthorizationsForRole(String role);

	public abstract Long getUserAuthorizationCount();

	public abstract List<String> getUserLoginsForRole(String role);

	public abstract UserAuthorization getUserAuthorizationForUserIdAndRole(Long userId,
			String role);

	public abstract List<UserRole> getAllRoles();

	public abstract LinkedHashSet<User> getDistinctUsersWithFetching(Long start,
			Long length);

	public abstract List<UserAuthorization> getAllWithFetching();

	public abstract List<UserAuthorization> getAllWithFetching(Long start, Long length);

	public abstract List<UserAuthorization> getAll(Long start, Long length);

	public abstract List<UserAuthorization> getAll();

	public UserAuthorizationService() {
		super();
	}

}