package com.glomozda.machinerepair.domain.user;

import org.hibernate.validator.constraints.NotEmpty;

public class UserRegistrationDTO {
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String login;
	
	@NotEmpty
	private String password1;
	
	@NotEmpty	
	private String password2;
	
	public UserRegistrationDTO() {
		
	}
	
	public UserRegistrationDTO(String name, String login, String password1, String password2) {
		this.name = name;
		this.login = login;
		this.password1 = password1;
		this.password2 = password2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password1 == null) ? 0 : password1.hashCode());
		result = prime * result
				+ ((password2 == null) ? 0 : password2.hashCode());
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
		UserRegistrationDTO other = (UserRegistrationDTO) obj;
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (password1 == null) {
			if (other.password1 != null) {
				return false;
			}
		} else if (!password1.equals(other.password1)) {
			return false;
		}
		if (password2 == null) {
			if (other.password2 != null) {
				return false;
			}
		} else if (!password2.equals(other.password2)) {
			return false;
		}
		return true;
	}	
}
