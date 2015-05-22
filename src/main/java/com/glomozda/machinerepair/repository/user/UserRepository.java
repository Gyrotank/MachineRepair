package com.glomozda.machinerepair.repository.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glomozda.machinerepair.domain.user.User;

@Repository
public abstract class UserRepository {

	@PersistenceContext
	protected EntityManager em;
	
	@Autowired
	protected PasswordEncoder encoder;
	
	@Transactional
	public abstract Boolean add(User u) throws PersistenceException;

	@Transactional
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