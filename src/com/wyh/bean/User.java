package com.wyh.bean;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String password;
	private String email;
	private int sex;
	private String place;
	private String birthday;
	private String headimg;

	public String toString() {
		return "User [id=" + this.id + ", username=" + this.username + ", password=" + this.password + ", email="
				+ this.email + ", sex=" + this.sex + ", place=" + this.place + ", birthday=" + this.birthday
				+ ", headimg=" + this.headimg + "]";
	}

	public int getSex() {
		return this.sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeadimg() {
		return this.headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
}