package com.cd120.unicorn;

/**
 * @author wudaomen1@gmail.com 单例传值类，单例模式
 */
public class UserUtil {

	private static UserUtil loginUserData = new UserUtil();

	public static UserUtil getLoginUserData() {
		return loginUserData;
	}

	public String age = null;
	public String email = null;

	public Boolean flag = false;// 用户信息初始化标记
	public String gender = null;
	public String hospitalization_number = null;

	public String identify_number = null;

	public String password = null;

	public String Phone = null;
	// 用户信息
	public String pictureString = null;
	public String result = null;
	public String userid = null;
	public String username = null;
	private UserUtil() {
	}
	public String getAge() {
		return age;
	}

	public String getEmail() {
		return email;
	}

	public Boolean getFlag() {
		return flag;
	}

	public String getGender() {
		return gender;
	}

	public String getHospitalization_number() {
		return hospitalization_number;
	}

	public String getIdentify_number() {
		return identify_number;
	}

	public String getPassword() {
		return loginUserData.password;
	}

	public String getPhone() {
		return Phone;
	}

	public String getPictureString() {
		return pictureString;
	}

	public String getResult() {
		return loginUserData.result;
	}

	public String getUserid() {
		return loginUserData.userid;
	}

	public String getUsername() {
		return loginUserData.username;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setHospitalization_number(String hospitalization_number) {
		this.hospitalization_number = hospitalization_number;
	}

	public void setIdentify_number(String identify_number) {
		this.identify_number = identify_number;
	}

	public void setPassword(String password) {
		loginUserData.password = password;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public void setPictureString(String pictureString) {
		this.pictureString = pictureString;
	}

	public void setResult(String result) {
		loginUserData.result = result;
	}

	public void setUserid(String userid) {
		loginUserData.userid = userid;
	}

	public void setUsername(String username) {
		loginUserData.username = username;
	}

}
