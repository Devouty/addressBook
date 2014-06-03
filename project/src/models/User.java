package models;

import java.util.UUID;

public class User {
	
	/*
	 * User.txt中信息排列顺序：
	 * 		uuid|groupUuid|userName|nativePlace|major|phoneNum|email|address
	 */
	
	private String uuid;
	private String groupUuid;
	private String userName;
	private String nativePlace;//籍贯
	private String major;//年级专业
	private String phoneNum;
	private String email;
	private String address;
	
	/*
	 * constructor
	 */
	public User() {
		this.uuid = UUID.randomUUID().toString();
	}
	
	public User(String groupUuid, String userName, String nativePlace,
			String major, String phoneNum,String email, String address) {
		this();
		this.groupUuid = groupUuid;
		this.userName = userName;
		this.nativePlace = nativePlace;
		this.major = major;
		this.phoneNum = phoneNum;
		this.email = email;
		this.address = address;
	}
	
	/*
	 * 从User.txt中载入信息时创建用户实例
	 */
	public User(String[] userInfo) {
		this.uuid = userInfo[0];
		this.groupUuid = userInfo[1];
		this.userName = userInfo[2];
		this.nativePlace = userInfo[3];
		this.major = userInfo[4];
		this.phoneNum = userInfo[5];
		this.email = userInfo[6];
		this.address = userInfo[7];
	}
	
	/*
	 * setter
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	
	public void setMajor(String major) {
		this.major = major;
	}
	 
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	/*
	 * getter
	 */
	public String getUuid() {
		return this.uuid;
	}
	
	public String getGroupUuid() {
		return this.groupUuid;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getNativePlace() {
		return this.nativePlace;
	}
	
	public String getMajor() {
		return this.major;
	}
	
	public String getPhoneNum() {
		return this.phoneNum;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getAddress() {
		return this.address;
	}
}
