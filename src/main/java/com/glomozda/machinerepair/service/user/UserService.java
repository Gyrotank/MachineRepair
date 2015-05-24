package com.glomozda.machinerepair.service.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.repository.user.UserRepository;
import com.glomozda.machinerepair.service.EntityService;

@Service
public abstract class UserService implements EntityService {

	@Autowired
	protected PasswordEncoder encoder;
	
	@Autowired
	protected UserRepository userRepository;

	public abstract Boolean add(User u);
	
	public abstract Boolean add(String login, String passwordText);

	public abstract Integer setUserEnabledById(Long userId, Byte enabled);

	public abstract Long getUserCount();

	public abstract Map<Long, String> getAllIdsAndLogins();
	
	public abstract Map<Long, String> getAllIdsAndLogins(Long start, Long length);

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