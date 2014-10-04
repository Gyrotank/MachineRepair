package com.glomozda.machinerepair.domain.userauthorization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.glomozda.machinerepair.domain.user.User;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@Entity
@Table(name = "user_authorization")
public class UserAuthorization {	
	@Id
	@GeneratedValue
	@Column(name = "user_authorization_id")
	private Integer userAuthorizationId;
	
	@Column(name = "role")
	@NotEmpty
	private String role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	public UserAuthorization() {
	}
	
	public UserAuthorization(String role) {
		this.role = role;
	}

	public Integer getUserAuthorizationId() {
		return userAuthorizationId;
	}
	
	public void setUserAuthorizationId(final Integer userAuthorizationId) {
		this.userAuthorizationId = userAuthorizationId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(final User user) {
		this.user = user;
		
        if (!user.getUserAuthorizations().contains(this)) {
            user.getUserAuthorizations().add(this);
        }
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 13 * hash + (this.user != null ? this.user.hashCode() : 0);
		hash = 13 * hash + (this.role != null ? this.role.hashCode() : 0);		
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
		final UserAuthorization other = (UserAuthorization) obj;
		if ((this.user == null) ? other.user != null : !this.user.equals(other.user)) {
			return false;
		}
		if ((this.role == null) ? other.role != null : !this.role.equals(other.role)) {
			return false;
		}
		return true;
	}    

	@Override
	public String toString() {
		if (this.user == null) {
			return "userAuthorization{" + "userAuthorizationId=" + userAuthorizationId +
				", role=" + role + ", user= NO USER}\n";
		} else {
			return "userAuthorization{" + "userAuthorizationId=" + userAuthorizationId +
					", role=" + role + ", user=" + this.user.getLogin() + '}' + "\n";
		}
	}
}
