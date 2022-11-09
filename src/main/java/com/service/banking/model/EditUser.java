package com.service.banking.model;

import java.util.Objects;

public class EditUser {
	
	private String username;
	private String newPassword;
	private String oldPassword;
	
	public EditUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EditUser(String username, String newPassword, String oldPassword) {
		super();
		this.username = username;
		this.newPassword = newPassword;
		this.oldPassword = oldPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@Override
	public int hashCode() {
		return Objects.hash(newPassword, oldPassword, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EditUser other = (EditUser) obj;
		return Objects.equals(newPassword, other.newPassword) && Objects.equals(oldPassword, other.oldPassword)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "EditUser [username=" + username + ", newPassword=" + newPassword + ", oldPassword=" + oldPassword + "]";
	}
	
}
