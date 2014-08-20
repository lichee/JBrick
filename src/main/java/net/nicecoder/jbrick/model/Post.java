package net.nicecoder.jbrick.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Post {
	private String name;//文件名称
	private String absPath;//绝对地址
	private String title;
	private String content;
	private Map<String,String> meta;
	private List<Tag> tags;
	private Category category;
	private Date createAt;
	private Date updateAt;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Map<String, String> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, String> meta) {
		this.meta = meta;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
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
	public String getAbsPath() {
		return absPath;
	}
	public void setAbsPath(String absPath) {
		this.absPath = absPath;
	}
	
}
