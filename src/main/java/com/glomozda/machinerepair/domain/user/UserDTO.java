package com.glomozda.machinerepair.domain.user;

import org.hibernate.validator.constraints.NotEmpty;

public class UserDTO {
	
	@NotEmpty
	private String login;
	
	@NotEmpty
	private String passwordText;
	
	public UserDTO() {
		
	}
	
	public UserDTO(String login, String passwordText) {
		this.login = login;
		this.passwordText = passwordText;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswordText() {
		return passwordText;
	}

	public void setPasswordText(String passwordText) {
		this.passwordText = passwordText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result
				+ ((passwordText == null) ? 0 : passwordText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (passwordText == null) {
			if (other.passwordText != null)
				return false;
		} else if (!passwordText.equals(other.passwordText))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "userDTO{" + "login=" + login +
					", passwordText=" + passwordText + '}' + "\n";		
	}
}
