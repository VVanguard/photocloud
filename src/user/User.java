package user;

import java.awt.Image;

public class User {
	
	private String name;
	private String surname;
	private String email;
	private int age;
	private String username;
	private String password;
	
	private Image ppImg;
	
	private UserTiers tier;
	
	
	public User(String name, String surname, String email, int age, String username, String password) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.age = age;
		this.username = username;
		this.password = password;
	}

	
	/*
	 * Getters & Setters 
	 * 
	 */
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Image getPpImg() {
		return ppImg;
	}


	public void setPpImg(Image ppImg) {
		this.ppImg = ppImg;
	}


	public UserTiers getTier() {
		return tier;
	}


	public void setTier(UserTiers tier) {
		this.tier = tier;
	}	
}
