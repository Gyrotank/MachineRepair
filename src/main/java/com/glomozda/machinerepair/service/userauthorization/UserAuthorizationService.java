package com.glomozda.machinerepair.service.userauthorization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
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
	public List<String> getAllRoles() {
		return userAuthorizationRepository.getAllRoles();
	}
	
	@Transactional
	public Long getUserAuthorizationCount() {
		return userAuthorizationRepository.getUserAuthorizationCount();
	}

	@Transactional
	public void add(UserAuthorization ua, Long userId) {
		userAuthorizationRepository.add(ua, userId);
	}
}
