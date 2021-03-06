package com.glomozda.machinerepair.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;

@NamedQueries({
	@NamedQuery(name="User.findUserByLogin", query="SELECT u FROM User u "
			+ "WHERE u.login = :login"),
	@NamedQuery(name="User.findUserByLoginWithFetching",
		query="SELECT u FROM User u "
			+ " LEFT JOIN FETCH u.client"
			+ " WHERE u.login = :login"),	
	@NamedQuery(name="User.findUserByIdWithFetching", query="SELECT u FROM User u "
			+ "LEFT JOIN FETCH u.client "
			+ "WHERE u.userId = :id"),
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u "
			+ "ORDER BY u.login"),
	@NamedQuery(name="User.findAllIdsAndLogins", query="SELECT u.userId, u.login FROM User u "
			+ "ORDER BY u.login"),
	@NamedQuery(name="User.setUserEnabledById", query="UPDATE User u SET enabled = :enabled "
			+ " WHERE u.userId = :id"),
	@NamedQuery(name="User.countAll", query="SELECT COUNT(u) FROM User u"),
	@NamedQuery(name="User.countLikeName", query="SELECT COUNT(u) FROM User u "
			+ " WHERE u.client.clientName LIKE :likePattern")
})
@Entity
@Table(name = "users")
public class User {	
	@Id
	@GeneratedValue
	@Column(name = "users_id")
	private Long userId;
	
	@Column(name = "login")
	@NotEmpty
	private String login;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "enabled")
	private Byte enabled;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy="clientUser")
	private Client client;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private List<UserAuthorization> userAuthorizations = new ArrayList<UserAuthorization>();
	
	public User(){
	}

	public User(final String login, final String password) {
		this.login = login;
		this.password = password;
		this.enabled = 1;
	}	
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {		
		this.password = password;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Byte getEnabled() {
		return enabled;
	}

	public void setEnabled(Byte enabled) {
		this.enabled = enabled;
	}

	public List<UserAuthorization> getUserAuthorizations() {
		return userAuthorizations;
	}
	
	public void addUserAuthorization(final UserAuthorization userAuthorization) {
        this.userAuthorizations.add(userAuthorization);
        if (userAuthorization.getUser() != this) {
        	userAuthorization.setUser(this);
        }
    }

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 13 * hash + (this.login != null ? this.login.hashCode() : 0);		
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if ((this.login == null) ? other.login != null : !this.login.equals(other.login)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [userId=");
		builder.append(userId);
		builder.append(", login=");
		builder.append(login);
		builder.append(", password=");
		builder.append(password);
		builder.append(", enabled=");
		builder.append(enabled);
		builder.append(", client=");
		builder.append(client);
		builder.append(", userAuthorizations=");
		builder.append(userAuthorizations);
		builder.append("]");
		return builder.toString();
	}
}
