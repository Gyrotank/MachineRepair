package com.glomozda.machinerepair.service.userauthorization;

import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userrole.UserRole;

@Service
public class UserAuthorizationServiceImpl extends UserAuthorizationService {
	
	@Override
	public List<UserAuthorization> getAll() {
		return userAuthorizationRepository.getAll();
	}
	
	@Override
	public List<UserAuthorization> getAll(Long start, Long length) {
		return userAuthorizationRepository.getAll(start, length);
	}
	
	@Override
	public List<UserAuthorization> getAllWithFetching(Long start, Long length) {
		return userAuthorizationRepository.getAllWithFetching(start, length);
	}
	
	@Override
	public List<UserAuthorization> getAllWithFetching() {
		return userAuthorizationRepository.getAllWithFetching();
	}
	
	@Override
	public LinkedHashSet<User> getDistinctUsersWithFetching(Long start, Long length) {
		return userAuthorizationRepository.getDistinctUsersWithFetching(start, length);
	}
	
	@Override
	public List<UserRole> getAllRoles() {
		return userAuthorizationRepository.getAllRoles();
	}
	
	@Override
	public UserAuthorization getUserAuthorizationForUserIdAndRole(Long userId, String role) {
		return userAuthorizationRepository.getUserAuthorizationForUserIdAndRole(userId, role);
	}
	
	@Override
	public List<String> getUserLoginsForRole(String role) {
		return userAuthorizationRepository.getUserLoginsForRole(role);
	}
	
	@Override
	public Long getUserAuthorizationCount() {
		return userAuthorizationRepository.getUserAuthorizationCount();
	}
	
	@Override
	public Long getCountUserAuthorizationsForRole(String role) {
		return userAuthorizationRepository.getCountUserAuthorizationsForRole(role);
	}
	
	@Override
	public List<UserRole> getRolesForUserId(Long userId) {
		return userAuthorizationRepository.getRolesForUserId(userId);
	}
	
	@Override
	public User getUserForUserAuthorizationId(Long userAuthorizationId) {
		return userAuthorizationRepository.getUserForUserAuthorizationId(userAuthorizationId);
	}

	@Override
	public Boolean add(UserAuthorization ua, Long userId) {
		return userAuthorizationRepository.add(ua, userId);
	}
	
	@Override
	public Integer deleteUserAuthorizationByUserIdAndRole(Long userId, String role) {
		return userAuthorizationRepository.deleteUserAuthorizationByUserIdAndRole(userId, role);
	}
	
	@Override
	public List<UserAuthorization> getUserAuthorizationsByUserId(Long userId) {
		return userAuthorizationRepository.getUserAuthorizationsByUserId(userId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllEntities(Long start, Long length) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getCountEntities() {
		return getUserAuthorizationCount();
	}
}
