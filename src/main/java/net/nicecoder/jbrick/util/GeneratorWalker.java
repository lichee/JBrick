package net.nicecoder.jbrick.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import net.nicecoder.jbrick.model.App;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * 用来生成html文件于outputDir下
 * 凡是没有以下划线"_"开头的都会被认为是模板文件输出到outputDir下
 * <br><i>at 2014年7月12日下午4:36:36</i>
 * @author lichee
 * @see <a href="http://nicecoder.net">nicecoder.net</a>
 */
public class GeneratorWalker extends DirectoryWalker<Object> {
	
	private Configuration cfg;
	private App app;
	
	
	public GeneratorWalker(App app){
		super(HiddenFileFilter.VISIBLE,App.getGenFilter(),-1);
		this.app = app;
		new File(app.getOutputDir());
		this.cfg = new Configuration();
		try {
			cfg.setDirectoryForTemplateLoading(new File(app.getAbsRootDir()));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setSharedVariable("app", app);
		} catch (IOException e) {
			//TODO
			e.printStackTrace();
		} catch (TemplateModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override  
    protected void handleFile(File file, int depth, Collection rs) {
		if(file.getName().startsWith("_")) return;//跳过下划线开头的文件
		Writer w = null;
		try {
			String relPath = file.getAbsolutePath().substring(app.getAbsRootDir().length());
			Template tp = cfg.getTemplate(relPath);
			File destFile = new File(app.getOutputDir(),relPath);
			System.out.println(destFile.getAbsolutePath());
			FileUtils.touch(destFile);
			w = new FileWriter(destFile);
			tp.process(null, w);
			w.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(w!=null) w.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	
	@SuppressWarnings("rawtypes")
	protected boolean handleDirectory(File dir, int depth, Collection rs) {
		if(dir.getName().startsWith("_")) {
			return false;//跳过下划线开头的目录
		}else{
			return true;
		}
	}
	
	public void execute(){
		try {
			walk(new File(app.getAbsRootDir()), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
