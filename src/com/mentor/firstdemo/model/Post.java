package com.mentor.firstdemo.model;

import java.io.Serializable;

public class Post implements Serializable{
	private String id;
	private String guid;
	private String text;
	private String reply_count;
	private String userid;
	private String username;
	private String useravstar;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getReply_count() {
		return reply_count;
	}
	public void setReply_count(String reply_count) {
		this.reply_count = reply_count;
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
