package net.nicecoder.jbrick.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.nicecoder.jbrick.util.GeneratorWalker;
import net.nicecoder.jbrick.util.PathUtil;
import net.nicecoder.jbrick.util.PostWalker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.ho.yaml.Yaml;

public class App {

	private String name;
	private String author;
	private String version;
	private Map<String,String> meta;
	
	//需要生成的文件后缀
	private static IOFileFilter genFilter = new SuffixFileFilter(new String[]{".ftl",".html",".md",".htm",".markdown"}, IOCase.INSENSITIVE);
	
	private Date createAt;
	private Date updateAt;
	
	private Map<String,Post> posts = new HashMap<String,Post>();//key=>title,value=>post
	private Map<String,Tag> tags = new HashMap<String,Tag>();//key=>name,value=>tag
	private Map<String,Category> categories = new HashMap<String,Category>();//key=>name,value=>category

	private String absRootDir;
	private String postsDir = "_posts";
	private String outputDir = "_site";
	
	/**
	 * 读取配置文件
	 * <br><i>at 2014年7月12日下午4:36:36</i>
	 * @author lichee
	 * @see <a href="http://nicecoder.net">nicecoder.net</a>
	 * @param path
	 */
	@SuppressWarnings("unchecked")
	public void load(String path){
		try {
			File config = new File(path);
			meta = (Map<String, String>) Yaml.load(config);
			setMeta(meta);
			setAuthor(meta.get("author"));
			setName(meta.get("name"));
			setVersion(meta.get("version"));
			
			//路径设置
			absRootDir = PathUtil.subFileName(config.getAbsolutePath());
			postsDir = absRootDir+postsDir;
			outputDir = absRootDir+outputDir;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * 生成html
	 * <br><i>at 2014年7月12日下午4:36:36</i>
	 * @author lichee
	 * @see <a href="http://nicecoder.net">nicecoder.net</a>
	 */
	public void generate(){

		//读取各种信息
		loadPosts();//读取post信息到app实例中
		parseTags();//解析出析post的tags
		parseCategories();//解析出析post的分类
		
		try {
			//清理输出目录
			File out = new File(outputDir);
			if(out.exists() && out.isDirectory()){
				FileUtils.cleanDirectory(out);
			}else{
				if(out.isFile()) out.delete();
				out.mkdir();
			}
			//创建循环输出器，生成html
			GeneratorWalker generator = new GeneratorWalker(this);
			generator.execute();
			
			//生成完毕，拷贝其他文件到目标目录
			FileUtils.copyDirectory(
					new File(absRootDir),
					new File(outputDir), 
					FileFilterUtils.and(FileFilterUtils.notFileFilter(App.genFilter),
							FileFilterUtils.notFileFilter(new PrefixFileFilter("_"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 启动server服务
	 * <br><i>at 2014年7月12日下午4:36:36</i>
	 * @author lichee
	 * @see <a href="http://nicecoder.net">nicecoder.net</a>
	 */
	public void server(int port,int mode){
		
	}
	
	
	
	/**
	 * 读取post信息
	 * <br><i>at 2014年7月12日下午4:36:36</i>
	 * @author lichee
	 * @see <a href="http://nicecoder.net">nicecoder.net</a>
	 */
	private void loadPosts(){
		PostWalker pw = new PostWalker(
				HiddenFileFilter.VISIBLE,
				genFilter
		);
		
		try {
			Collection<Post> rs = pw.loadPosts(new File(postsDir));
			posts = new HashMap<String,Post>();
			for(Post p : rs){
				posts.put(p.getTitle(), p);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解析出析post的tags
	 * <br><i>at 2014年7月12日下午4:36:36</i>
	 * @author lichee
	 * @see <a href="http://nicecoder.net">nicecoder.net</a>
	 */
	private void parseTags() {
		for(Post p : posts.values()){
			String tg = p.getMeta().get("tags");
			String tgs[] = null;
			if(null==tg){
			}else{
				tgs = tg.split(",");
				for(String t:tgs){
					Tag tmp = null;
					if(!tags.containsKey(t)){//
						tmp = new Tag();
						tmp.setName(t);
						tags.put(t, tmp);
					};
					tmp = tags.get(t);
					if(!tmp.getPosts().containsKey(p.getTitle())){
						tmp.getPosts().put(p.getTitle(), p);
					}
				}
			}
		}
	}

	/**
	 * 解析出析post的Categories
	 * <br><i>at 2014年7月12日下午4:36:36</i>
	 * @author lichee
	 * @see <a href="http://nicecoder.net">nicecoder.net</a>
	 */
	private void parseCategories() {
		for(Post p : posts.values()){
			String tg = p.getMeta().get("categories");
			String tgs[] = null;
			if(null==tg){
			}else{
				tgs = tg.split(",");
				for(String t:tgs){
					Category tmp = null;
					if(!categories.containsKey(t)){//
						tmp = new Category();
						tmp.setName(t);
						categories.put(t, tmp);
					};
					tmp = categories.get(t);
					if(!tmp.getPosts().containsKey(p.getTitle())){
						tmp.getPosts().put(p.getTitle(), p);
					}
				}
			}
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Map<String, String> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, String> meta) {
		this.meta = meta;
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
	public Map<String, Tag> getTags() {
		return tags;
	}
	public void setTags(Map<String, Tag> tags) {
		this.tags = tags;
	}
	public Map<String, Category> getCategories() {
		return categories;
	}
	public void setCategories(Map<String, Category> categories) {
		this.categories = categories;
	}

	public String getAbsRootDir() {
		return absRootDir;
	}

	public void setAbsRootDir(String absRootDir) {
		this.absRootDir = absRootDir;
	}

	public String getPostsDir() {
		return postsDir;
	}

	public void setPostsDir(String postsDir) {
		this.postsDir = postsDir;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}



	public static IOFileFilter getGenFilter() {
		return genFilter;
	}



	public static void setGenFilter(IOFileFilter genFilter) {
		App.genFilter = genFilter;
	}
	
	
	
}
