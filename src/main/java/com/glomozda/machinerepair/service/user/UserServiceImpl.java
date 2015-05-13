package com.glomozda.machinerepair.service.user;

import java.util.List;

import com.glomozda.machinerepair.domain.user.*;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends UserService {

	@Override
	public User getUserByLoginAndPassword(String login, String passwordText) {
		return userRepository.getUserByLoginAndPassword(login, passwordText);
	}
	
	@Override
	public User getUserByLoginAndPasswordWithFetching(String login, String passwordText) {
		return userRepository.getUserByLoginAndPasswordWithFetching(login, passwordText);
	}
	
	@Override
	public User getUserByLogin(String login) {
		return userRepository.getUserByLogin(login);
	}
	
	@Override
	public User getUserByLoginWithFetching(String login) {
		return userRepository.getUserByLoginWithFetching(login);
	}
	
	@Override
	public User getUserById(Long userId) {
		return userRepository.getUserById(userId);
	}
	
	@Override
	public User getUserByIdWithFetching(Long userId) {
		return userRepository.getUserByIdWithFetching(userId);
	}
	
	@Override
	public List<User> getAll() {
		return userRepository.getAll();
	}

	@Override
	public List<User> getAll(Long start, Long length) {
		return userRepository.getAll(start, length);
	}
	
	@Override
	public List<Object[]> getAllIdsAndLogins() {
		return userRepository.getAllIdsAndLogins();
	}
	
	@Override
	public Long getUserCount() {
		return userRepository.getUserCount();
	}
	
	@Override
	public Integer setUserEnabledById(Long userId, Byte enabled) {
		return userRepository.setUserEnabledById(userId, enabled);
	}

	@Override
	public Boolean add(User u) {
		return userRepository.add(u);
	}
	
	@Override
	public Boolean add(String login, String passwordText) {
		String passwordHashed = encoder.encode(passwordText);
		User newUser = new User(login, passwordHashed);
		
		return add(newUser);
	}

	@Override
	public Long getUserCountLikeName(String likePattern) {
		return userRepository.getUserCountLikeName(likePattern);
	}
}
