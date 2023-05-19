package user;


public class User {
	
	private String name;
	private String surname;
	private String email;
	private int age;
	private String username;
	private String password;
	
	private String imgPath = "resources/pictures/Default_Profile_Picture.jpg";
	
	private UserTiers tier;
	
	
	public User(String name, String surname, String email, int age, String username, String password, UserTiers tier) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.age = age;
		this.username = username;
		this.password = password;
		this.tier = tier;
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


	public String getImgPath() {
		return imgPath;
	}


	public void setPpImg(String imgPath) {
		this.imgPath = imgPath;
	}


	public UserTiers getTier() {
		return tier;
	}


	public void setTier(UserTiers tier) {
		this.tier = tier;
	}	
}
