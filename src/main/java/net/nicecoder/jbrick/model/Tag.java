package net.nicecoder.jbrick.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Tag {
	private String name;
	private String english;
	private String color;
	private Date createAt;
	private Date updateAt;
	private Map<String,Post> posts=new HashMap<String,Post>();//key=>title,value=>post
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnglish() {
		return english;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	public Map<String, Post> getPosts() {
		return posts;
	}
	public void setPosts(Map<String, Post> posts) {
		this.posts = posts;
	}
	
	
}
