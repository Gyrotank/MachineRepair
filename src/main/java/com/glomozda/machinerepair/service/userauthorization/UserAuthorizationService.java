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
public class UserAuthorizationService {
	
	@Autowired
	private UserAuthorizationRepository userAuthorizationRepository;
	
	public List<UserAuthorization> getAll() {
		return userAuthorizationRepository.getAll();
	}
	
	public List<UserAuthorization> getAll(Long start, Long length) {
		return userAuthorizationRepository.getAll(start, length);
	}
	
	public List<UserAuthorization> getAllWithFetching(Long start, Long length) {
		return userAuthorizationRepository.getAllWithFetching(start, length);
	}
	
	public List<UserAuthorization> getAllWithFetching() {
		return userAuthorizationRepository.getAllWithFetching();
	}
	
	public LinkedHashSet<User> getDistinctUsersWithFetching(Long start, Long length) {
		return userAuthorizationRepository.getDistinctUsersWithFetching(start, length);
	}
	
	public List<UserRole> getAllRoles() {
		return userAuthorizationRepository.getAllRoles();
	}
	
	public UserAuthorization getUserAuthorizationForUserIdAndRole(Long userId, String role) {
		return userAuthorizationRepository.getUserAuthorizationForUserIdAndRole(userId, role);
	}
	
	public List<String> getUserLoginsForRole(String role) {
		return userAuthorizationRepository.getUserLoginsForRole(role);
	}
	
	public Long getUserAuthorizationCount() {
		return userAuthorizationRepository.getUserAuthorizationCount();
	}
	
	public Long getCountUserAuthorizationsForRole(String role) {
		return userAuthorizationRepository.getCountUserAuthorizationsForRole(role);
	}
	
	public List<UserRole> getRolesForUserId(Long userId) {
		return userAuthorizationRepository.getRolesForUserId(userId);
	}
	
	public User getUserForUserAuthorizationId(Long userAuthorizationId) {
		return userAuthorizationRepository.getUserForUserAuthorizationId(userAuthorizationId);
	}

	public Boolean add(UserAuthorization ua, Long userId) {
		return userAuthorizationRepository.add(ua, userId);
	}
	
	public Integer deleteUserAuthorizationByUserIdAndRole(Long userId, String role) {
		return userAuthorizationRepository.deleteUserAuthorizationByUserIdAndRole(userId, role);
	}
	
	public List<UserAuthorization> getUserAuthorizationsByUserId(Long userId) {
		return userAuthorizationRepository.getUserAuthorizationsByUserId(userId);
	}
}
