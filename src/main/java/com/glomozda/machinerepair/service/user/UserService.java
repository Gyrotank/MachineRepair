package com.glomozda.machinerepair.service.user;

import java.util.List;

import com.glomozda.machinerepair.domain.user.*;
import com.glomozda.machinerepair.repository.user.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User getUserByLoginAndPassword(String login, String passwordText) {
		return userRepository.getUserByLoginAndPassword(login, passwordText);
	}
	
	@Transactional
	public User getUserByLoginAndPasswordWithFetching(String login, String passwordText) {
		return userRepository.getUserByLoginAndPasswordWithFetching(login, passwordText);
	}
	
	@Transactional
	public User getUserByLogin(String login) {
		return userRepository.getUserByLogin(login);
	}
	
	@Transactional
	public User getUserByLoginWithFetching(String login) {
		return userRepository.getUserByLoginWithFetching(login);
	}
	
	@Transactional
	public User getUserById(Long userId) {
		return userRepository.getUserById(userId);
	}
	
	@Transactional
	public User getUserByIdWithFetching(Long userId) {
		return userRepository.getUserByIdWithFetching(userId);
	}
	
	@Transactional
	public List<User> getAll() {
		return userRepository.getAll();
	}

	@Transactional
	public List<User> getAll(Long start, Long length) {
		return userRepository.getAll(start, length);
	}
	
	@Transactional
	public List<Object[]> getAllIdsAndLogins() {
		return userRepository.getAllIdsAndLogins();
	}
	
	@Transactional
	public Long getUserCount() {
		return userRepository.getUserCount();
	}
	
	@Transactional
	public Integer setUserEnabledById(Long userId, Byte enabled) {
		return userRepository.setUserEnabledById(userId, enabled);
	}

	@Transactional
	public Boolean add(User u) {
		return userRepository.add(u);
	}	
}
