package com.mentor.firstdemo.model;

import java.io.Serializable;

public class Reply implements Serializable{

	private String guid;
	private String text;
	private String userid;
	private String username;
	private String useravstar;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUseravstar() {
		return useravstar;
	}
	public void setUseravstar(String useravstar) {
		this.useravstar = useravstar;
	}
	
}
