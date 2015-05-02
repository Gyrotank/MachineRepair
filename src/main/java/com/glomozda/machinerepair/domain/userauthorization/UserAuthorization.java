package com.glomozda.machinerepair.domain.userauthorization;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userrole.UserRole;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@NamedQueries({
	@NamedQuery(name="UserAuthorization.findAll", query="SELECT ua FROM UserAuthorization ua "
			+ "ORDER BY ua.user.login, ua.role.role"),
	@NamedQuery(name="UserAuthorization.findAllWithFetching",
		query="SELECT ua FROM UserAuthorization ua "
			+ "LEFT JOIN FETCH ua.user "
			+ "ORDER BY ua.user.login, ua.role.role"),
	@NamedQuery(name="UserAuthorization.findAllRoles", query="SELECT DISTINCT (ua.role) "
			+ "FROM UserAuthorization ua "
			+ "ORDER BY ua.role.role"),
	@NamedQuery(name="UserAuthorization.findUserAuthorizationForUserIdAndRole",
		query="SELECT ua FROM UserAuthorization ua "
			+ "LEFT JOIN FETCH ua.user "
			+ "WHERE ua.user.userId = :id AND ua.role.role = :role"),
	@NamedQuery(name="UserAuthorization.findUserAuthorizationForRole",
		query="SELECT ua FROM UserAuthorization ua "
			+ "LEFT JOIN FETCH ua.user "
			+ "WHERE ua.role.role = :role"),
	@NamedQuery(name="UserAuthorization.findUserLoginsForRole",
		query="SELECT ua.user.login FROM UserAuthorization ua "			
			+ "WHERE ua.role.role = :role"),
	@NamedQuery(name="UserAuthorization.countUserAuthorizationsForRole",
		query="SELECT COUNT (ua) FROM UserAuthorization ua "			
			+ "WHERE ua.role.role = :role"),
	@NamedQuery(name="UserAuthorization.findRolesForUserId",
		query="SELECT ua.role FROM UserAuthorization ua "
			+ "WHERE ua.user.userId = :id"),
	@NamedQuery(name="UserAuthorization.findUserForUserAuthorizationId",
		query="SELECT ua.user FROM UserAuthorization ua "
			+ "WHERE ua.userAuthorizationId = :id"),
	@NamedQuery(name="UserAuthorization.countAll", query="SELECT COUNT(ua) "
			+ "FROM UserAuthorization ua"),
	@NamedQuery(name="UserAuthorization.deleteUserAuthorizationByUserIdAndRole", 
		query="DELETE FROM UserAuthorization ua"
			+ " WHERE ua.user.userId = :id AND ua.role.role = :role"),
	@NamedQuery(name="UserAuthorization.findUserAuthorizationsByUserId",
		query="SELECT ua FROM UserAuthorization ua"
			+ " WHERE ua.user.userId = :id")
})
@Entity
@Table(name = "user_authorization")
public class UserAuthorization {	
	@Id
	@GeneratedValue
	@Column(name = "user_authorization_id")
	private Long userAuthorizationId;
	
	@Embedded
	private UserRole role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	public UserAuthorization() {
	}
	
	public UserAuthorization(UserRole role) {
		this.role = role;
	}

	public Long getUserAuthorizationId() {
		return userAuthorizationId;
	}
	
	public void setUserAuthorizationId(final Long userAuthorizationId) {
		this.userAuthorizationId = userAuthorizationId;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(final UserRole role) {
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
