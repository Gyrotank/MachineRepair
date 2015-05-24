package com.glomozda.machinerepair.service.user;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import com.glomozda.machinerepair.domain.user.*;

import org.hibernate.exception.ConstraintViolationException;
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
	public Map<Long, String> getAllIdsAndLogins() {		
		List<Object[]> idsAndLoginsList = userRepository.getAllIdsAndLogins();
				
				Map<Long, String> idsAndLoginsMap = 
						new LinkedHashMap<Long, String>(idsAndLoginsList.size());
				for (Object[] idAndLogin : idsAndLoginsList)
					idsAndLoginsMap.put((Long)idAndLogin[0], (String)idAndLogin[1]);
				
		return idsAndLoginsMap;
	}
	
	@Override
	public Map<Long, String> getAllIdsAndLogins(Long start, Long length) {
		List<Object[]> idsAndLoginsList = userRepository.getAllIdsAndLogins(start, length);
		
		Map<Long, String> idsAndLoginsMap = 
				new LinkedHashMap<Long, String>(idsAndLoginsList.size());
		for (Object[] idAndLogin : idsAndLoginsList)
			idsAndLoginsMap.put((Long)idAndLogin[0], (String)idAndLogin[1]);
		
		return idsAndLoginsMap;		
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
		Boolean result = false;
		
		try {
			result = userRepository.add(u);
		} catch (PersistenceException e) {
			Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		        return false;
		    }
		    throw (e);
		}
		
		return result;
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

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllEntities() {
		return getAll();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllEntities(Long start, Long length) {
		return getAll(start, length);
	}

	@Override
	public Long getCountEntities() {
		return getUserCount();
	}
}
