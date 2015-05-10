package com.glomozda.machinerepair.domain.userrole;

import javax.persistence.Embeddable;

@Embeddable
public class UserRole {
	
	private String role;
	private String descEn;
	private String descRu;
	
	public UserRole() {		
	}
	
	public UserRole(String role, String descEn, String descRu) {
		this.setRole(role);
		this.setDescEn(descEn);
		this.setDescRu(descRu);
	}
	
	public UserRole(String role) {
		this.setRole(role);
		if (role.contentEquals("ROLE_ADMIN")) {
			this.setDescEn("Administrator");
			this.setDescRu("Администратор");
		}
		if (role.contentEquals("ROLE_MANAGER")) {
			this.setDescEn("Manager");
			this.setDescRu("Менеджер");
		}
		if (role.contentEquals("ROLE_CLIENT")) {
			this.setDescEn("Client");
			this.setDescRu("Клиент");
		}
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescEn() {
		return descEn;
	}

	public void setDescEn(String descEn) {
		this.descEn = descEn;
	}

	public String getDescRu() {
		return descRu;
	}

	public void setDescRu(String descRu) {
		this.descRu = descRu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descEn == null) ? 0 : descEn.hashCode());
		result = prime * result + ((descRu == null) ? 0 : descRu.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		UserRole other = (UserRole) obj;
		if (descEn == null) {
			if (other.descEn != null) {
				return false;
			}
		} else if (!descEn.equals(other.descEn)) {
			return false;
		}
		if (descRu == null) {
			if (other.descRu != null) {
				return false;
			}
		} else if (!descRu.equals(other.descRu)) {
			return false;
		}
		if (role == null) {
			if (other.role != null) {
				return false;
			}
		} else if (!role.equals(other.role)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRole [role=");
		builder.append(role);
		builder.append(", descEn=");
		builder.append(descEn);
		builder.append(", descRu=");
		builder.append(descRu);
		builder.append("]");
		return builder.toString();
	}	
}
