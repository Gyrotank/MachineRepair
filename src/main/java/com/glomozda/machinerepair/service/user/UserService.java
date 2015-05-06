package com.glomozda.machinerepair.service.user;

import java.util.List;

import com.glomozda.machinerepair.domain.user.*;
import com.glomozda.machinerepair.repository.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;

	public User getUserByLoginAndPassword(String login, String passwordText) {
		return userRepository.getUserByLoginAndPassword(login, passwordText);
	}
	
	public User getUserByLoginAndPasswordWithFetching(String login, String passwordText) {
		return userRepository.getUserByLoginAndPasswordWithFetching(login, passwordText);
	}
	
	public User getUserByLogin(String login) {
		return userRepository.getUserByLogin(login);
	}
	
	public User getUserByLoginWithFetching(String login) {
		return userRepository.getUserByLoginWithFetching(login);
	}
	
	public User getUserById(Long userId) {
		return userRepository.getUserById(userId);
	}
	
	public User getUserByIdWithFetching(Long userId) {
		return userRepository.getUserByIdWithFetching(userId);
	}
	
	public List<User> getAll() {
		return userRepository.getAll();
	}

	public List<User> getAll(Long start, Long length) {
		return userRepository.getAll(start, length);
	}
	
	public List<Object[]> getAllIdsAndLogins() {
		return userRepository.getAllIdsAndLogins();
	}
	
	public Long getUserCount() {
		return userRepository.getUserCount();
	}
	
	public Integer setUserEnabledById(Long userId, Byte enabled) {
		return userRepository.setUserEnabledById(userId, enabled);
	}

	public Boolean add(User u) {
		return userRepository.add(u);
	}	
}
