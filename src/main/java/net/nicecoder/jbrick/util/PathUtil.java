package net.nicecoder.jbrick.util;


public class PathUtil {
	
	private static String SUB_FILE_NAME = "[^\\\\/]*+$";
	
	private static String SUB_TO_FILE_NAME = ".*?([^\\\\/]*$)";
	
	public static String subFileName(String path){
		return path.replaceAll(SUB_FILE_NAME, "");
	}
	
	public static String subToFileName(String path){
		return path.replaceAll(SUB_TO_FILE_NAME, "$1");
	}
	
	public static void main(String[] args){
		String f = "C:\\Users\\Administrator\\Desktop\\ooo\\o.o";
		String l = "/usr/bin/c_da.profile";
		System.out.println(subFileName(f));
		System.out.println(subToFileName(f));
		System.out.println(subFileName(l));
		System.out.println(subToFileName(l));
	}
}
