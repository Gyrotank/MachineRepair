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
}
