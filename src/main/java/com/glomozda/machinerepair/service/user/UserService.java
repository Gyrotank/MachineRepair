package com.glomozda.machinerepair.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.repository.user.UserRepository;

@Service
public abstract class UserService {

	@Autowired
	protected PasswordEncoder encoder;
	
	@Autowired
	protected UserRepository userRepository;

	public abstract Boolean add(User u);
	
	public abstract Boolean add(String login, String passwordText);

	public abstract Integer setUserEnabledById(Long userId, Byte enabled);

	public abstract Long getUserCount();

	public abstract List<Object[]> getAllIdsAndLogins();

	public abstract List<User> getAll(Long start, Long length);

	public abstract List<User> getAll();

	public abstract User getUserByIdWithFetching(Long userId);

	public abstract User getUserById(Long userId);

	public abstract User getUserByLoginWithFetching(String login);

	public abstract User getUserByLogin(String login);

	public abstract User getUserByLoginAndPasswordWithFetching(String login,
			String passwordText);

	public abstract User getUserByLoginAndPassword(String login, String passwordText);

	public abstract Long getUserCountLikeName(String likePattern);

}