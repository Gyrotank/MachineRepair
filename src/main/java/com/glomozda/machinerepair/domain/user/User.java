package com.glomozda.machinerepair.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@Entity
@Table(name = "users")
public class User {	
	@Id
	@GeneratedValue
	@Column(name = "users_id")
	private Integer userId;
	
	@Column(name = "login")
	@NotEmpty
	private String login;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "password_text")
	@NotEmpty
	private String passwordText;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy="clientUser")
	private Client client;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private List<UserAuthorization> userAuthorizations = new ArrayList<UserAuthorization>();
	
	public User(){
	}

	public User(final String login, final String passwordText, final String password) {
		this.login = login;
		this.passwordText = passwordText;		
		this.password = password;
	}	
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(final Integer userId) {
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
	
	public String getPasswordText() {
		return passwordText;
	}

	public void setPasswordText(final String passwordText) {
		this.passwordText = passwordText;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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
		return "user{" + "userId=" + userId + ", login=" + login + 
				", password=" + password + ", passwordText=" + passwordText + '}'+"\n";
	}	
}
