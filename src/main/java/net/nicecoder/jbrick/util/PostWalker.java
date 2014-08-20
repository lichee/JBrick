package net.nicecoder.jbrick.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.nicecoder.jbrick.model.Post;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.ho.yaml.Yaml;
import org.pegdown.PegDownProcessor;

public class PostWalker extends DirectoryWalker<Post> {
	
	private static final PegDownProcessor md = new PegDownProcessor();
	
	public PostWalker(IOFileFilter visible, IOFileFilter suffixFileFilter) {
		super(visible,suffixFileFilter,-1);
	}

	public List<Post> loadPosts(File absPostDir) throws IOException{
		List<Post> rs = new ArrayList<Post>();
		walk(absPostDir,rs);
		return rs;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override  
    protected void handleFile(File file, int depth, Collection rs) {
		try {
			List<String> lines = FileUtils.readLines(file);
			if(!lines.get(0).startsWith("---")){//没有“---”头就
				return;
			}else{
				StringBuffer ymlHead=new StringBuffer();
				StringBuffer postContent=new StringBuffer();
				boolean isHead = true;//
				for(int i=1;i<lines.size();i++){
					if(isHead){
						if(!lines.get(i).startsWith("---")){
							ymlHead.append(lines.get(i));
							ymlHead.append(System.getProperty("line.separator"));
						}else{
							isHead = false;
							continue;
						}
					}else{
						postContent.append(lines.get(i));
						ymlHead.append(System.getProperty("line.separator"));
					}
				}
				if(isHead) {
					return;
				}//全篇没有找到“---”的另一头，不做操作
				else{
					Post post = new Post();
					post.setMeta((Map<String, String>) Yaml.load(ymlHead.toString()));
					post.setTitle(post.getMeta().get("title"));
					post.setName(file.getName());
					if(file.getName().endsWith(".md")||file.getName().endsWith(".markdown")){
						post.setContent(md.markdownToHtml(postContent.toString()));
					}else{
						post.setContent(postContent.toString());
					}
					post.setAbsPath(file.getAbsolutePath());
					rs.add(post);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
}
