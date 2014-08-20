package net.nicecoder.jbrick.model;

import java.util.Date;
import java.util.Map;

public class Category {
	
	private String name;
	private String english;
	private Date createAt;
	private Date updateAt;
	private Map<String,Post> posts;//key=>title,value=>post
	
	
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
