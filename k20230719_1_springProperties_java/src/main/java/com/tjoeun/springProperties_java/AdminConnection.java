package com.tjoeun.springProperties_java;

// AdminConnection 클래스의 bean이 생성되는 순간 admin.properties 파일의 내용을 읽어서 필드에 저장한다. 
public class AdminConnection {
	
	private String adminId; 
	private String adminPw;
	private String sub_adminId; 
	private String sub_adminPw;
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAdminPw() {
		return adminPw;
	}
	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}
	
	public String getSub_adminId() {
		return sub_adminId;
	}
	public void setSub_adminId(String sub_adminId) {
		this.sub_adminId = sub_adminId;
	}
	public String getSub_adminPw() {
		return sub_adminPw;
	}
	public void setSub_adminPw(String sub_adminPw) {
		this.sub_adminPw = sub_adminPw;
	}
	@Override
	public String toString() {
		return "AdminConnection [adminId=" + adminId + ", adminPw=" + adminPw + ", sub_adminId=" + sub_adminId
				+ ", sub_adminPw=" + sub_adminPw + "]";
	}
	
	
	
	
	
}
