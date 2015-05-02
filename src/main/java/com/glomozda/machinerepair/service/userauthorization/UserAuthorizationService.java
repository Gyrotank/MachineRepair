package com.glomozda.machinerepair.service.userauthorization;

import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.domain.userrole.UserRole;
import com.glomozda.machinerepair.repository.userauthorization.UserAuthorizationRepository;

@Service
public class UserAuthorizationService {
	
	@Autowired
	private UserAuthorizationRepository userAuthorizationRepository;
	
	@Transactional
	public List<UserAuthorization> getAll() {
		return userAuthorizationRepository.getAll();
	}
	
	@Transactional
	public List<UserAuthorization> getAll(Long start, Long length) {
		return userAuthorizationRepository.getAll(start, length);
	}
	
	@Transactional
	public List<UserAuthorization> getAllWithFetching(Long start, Long length) {
		return userAuthorizationRepository.getAllWithFetching(start, length);
	}
	
	@Transactional
	public List<UserAuthorization> getAllWithFetching() {
		return userAuthorizationRepository.getAllWithFetching();
	}
	
	@Transactional
	public LinkedHashSet<User> getDistinctUsersWithFetching(Long start, Long length) {
		return userAuthorizationRepository.getDistinctUsersWithFetching(start, length);
	}
	
	@Transactional
	public List<UserRole> getAllRoles() {
		return userAuthorizationRepository.getAllRoles();
	}
	
	@Transactional
	public UserAuthorization getUserAuthorizationForUserIdAndRole(Long userId, String role) {
		return userAuthorizationRepository.getUserAuthorizationForUserIdAndRole(userId, role);
	}
	
	@Transactional
	public List<String> getUserLoginsForRole(String role) {
		return userAuthorizationRepository.getUserLoginsForRole(role);
	}
	
	@Transactional
	public Long getUserAuthorizationCount() {
		return userAuthorizationRepository.getUserAuthorizationCount();
	}
	
	@Transactional
	public Long getCountUserAuthorizationsForRole(String role) {
		return userAuthorizationRepository.getCountUserAuthorizationsForRole(role);
	}
	
	@Transactional
	public List<UserRole> getRolesForUserId(Long userId) {
		return userAuthorizationRepository.getRolesForUserId(userId);
	}
	
	@Transactional
	public User getUserForUserAuthorizationId(Long userAuthorizationId) {
		return userAuthorizationRepository.getUserForUserAuthorizationId(userAuthorizationId);
	}

	@Transactional
	public Boolean add(UserAuthorization ua, Long userId) {
		return userAuthorizationRepository.add(ua, userId);
	}
	
	@Transactional
	public Integer deleteUserAuthorizationByUserIdAndRole(Long userId, String role) {
		return userAuthorizationRepository.deleteUserAuthorizationByUserIdAndRole(userId, role);
	}
	
	@Transactional
	public List<UserAuthorization> getUserAuthorizationsByUserId(Long userId) {
		return userAuthorizationRepository.getUserAuthorizationsByUserId(userId);
	}
}
