package com.glomozda.machinerepair.domain.userauthorization;

import java.util.List;

import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userrole.UserRole;

public class UserAuthorizationDTO {
	
	private Long userAuthorizationId;
	private User user;
	private Boolean isAdmin = false;
	private Boolean isClient = false;
	private Boolean isManager = false;
	
	private Boolean isOnlyAdmin = false;
	
	public UserAuthorizationDTO() {
		
	}
	
	public UserAuthorizationDTO(Long userAuthorizationId, User user, 
			List<UserRole> roles, Boolean isOnlyAdmin) {
		
		this.userAuthorizationId = userAuthorizationId;
		
		this.user = user;
		
		for (UserRole ur : roles) {		
			if (ur.getRole().contentEquals("ROLE_ADMIN")) {
				isAdmin = true;
			}
			if (ur.getRole().contentEquals("ROLE_CLIENT")) {
				isClient = true;
			}
			if (ur.getRole().contentEquals("ROLE_MANAGER")) {
				isManager = true;
			}		
		}
		
		this.isOnlyAdmin = isOnlyAdmin;
	}

	public Long getUserAuthorizationId() {
		return userAuthorizationId;
	}

	public void setUserAuthorizationId(Long userAuthorizationId) {
		this.userAuthorizationId = userAuthorizationId;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getIsClient() {
		return isClient;
	}

	public void setIsClient(Boolean isClient) {
		this.isClient = isClient;
	}

	public Boolean getIsManager() {
		return isManager;
	}

	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}

	public Boolean getIsOnlyAdmin() {
		return isOnlyAdmin;
	}

	public void setIsOnlyAdmin(Boolean isOnlyAdmin) {
		this.isOnlyAdmin = isOnlyAdmin;
	}
	
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 13 * hash + (this.user != null ? this.user.hashCode() : 0);
		hash = 13 * hash + (this.isAdmin != null ? this.isAdmin.hashCode() : 0);
		hash = 13 * hash + (this.isClient != null ? this.isClient.hashCode() : 0);
		hash = 13 * hash + (this.isManager != null ? this.isManager.hashCode() : 0);
		hash = 13 * hash + (this.isOnlyAdmin != null ? this.isOnlyAdmin.hashCode() : 0);
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
		final UserAuthorizationDTO other = (UserAuthorizationDTO) obj;
		if ((this.user == null) ? other.user != null : !this.user.equals(other.user)) {
			return false;
		}
		if ((this.isAdmin == null) ? other.isAdmin != null : 
			!this.isAdmin.equals(other.isAdmin)) {
			return false;
		}
		if ((this.isClient == null) ? other.isClient != null : 
			!this.isClient.equals(other.isClient)) {
			return false;
		}
		if ((this.isManager == null) ? other.isManager != null : 
			!this.isManager.equals(other.isManager)) {
			return false;
		}
		if ((this.isOnlyAdmin == null) ? other.isOnlyAdmin != null : 
			!this.isOnlyAdmin.equals(other.isOnlyAdmin)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "userAuthorizationDTO{" + "user=" + user.getLogin() +
					", isAdmin=" + isAdmin + ", isClient=" + isClient + 
					", isManager=" + isManager + ", isOnlyAdmin=" + isOnlyAdmin +
					'}' + "\n";		
	}
}
