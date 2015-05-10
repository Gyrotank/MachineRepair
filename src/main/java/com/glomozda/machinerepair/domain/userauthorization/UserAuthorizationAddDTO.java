package com.glomozda.machinerepair.domain.userauthorization;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

public class UserAuthorizationAddDTO {
	
	@Min(1)
	private Long userId;
	
	@NotEmpty
	private String role;

	public UserAuthorizationAddDTO() {		
	}

	public UserAuthorizationAddDTO(Long userId, String role) {
		this.userId = userId;
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserAuthorizationAddDTO other = (UserAuthorizationAddDTO) obj;
		if (role == null) {
			if (other.role != null) {
				return false;
			}
		} else if (!role.equals(other.role)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserAuthorizationAddDTO [userId=");
		builder.append(userId);
		builder.append(", role=");
		builder.append(role);
		builder.append("]");
		return builder.toString();
	}	
}
