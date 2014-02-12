package org.nbrc.mobile.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {
	 
	 public static String toHtml(String str) {                  
		 if (str == null)                          
			 return "";                  
//		 str = str.replaceAll("&", "&amp;");                  
//		 str = str.replaceAll("<", "&lt;");                  
//		 str = str.replaceAll(">", "&gt;");                  
//		 str = str.replaceAll("\"", "&quot;");                  
//		 str = str.replaceAll("\r\n", "\n");                  
//		 str = str.replaceAll("\n", "<br/>");                  
//		 str = str.replaceAll("\t", " &nbsp;&nbsp;&nbsp;");                 
//		 str = str.replaceAll("  ", " &nbsp;");  
		 
		 str = str.replaceAll("&amp;","&");                  
		 str = str.replaceAll("&lt;","<");                  
		 str = str.replaceAll("&gt;",">");                  
		 str = str.replaceAll("&quot;","\"");                  
		 str = str.replaceAll( "\n","\r\n");                  
		 str = str.replaceAll("<br/>","\n");                  
		 str = str.replaceAll(" &nbsp;&nbsp;&nbsp;","\t");                 
		 str = str.replaceAll(" &nbsp;","  ");  
		 
		 return str;          
	}
	
    /**           
     * 正则表达式去除字符串中除了htmlTag标签之外的的HTML标签，。
     * @param str          
     * 		待去除html标签的字符串           
     * @param htmlTags           
     *		排除的html标签关键字，多个关键字用|分割。例如：IMG|BR|P           
     * @return 处理后的字符串          
     */ 	
	public static String regExNoHTML(String str, String htmlTags) {                  
		if (str == null) {                          
			return str;                  
		}                  
		Pattern p = Pattern.compile("<(?!/?(" + htmlTags + "))[^>]*>");                  
		Matcher m = p.matcher(str);                  
		return m.replaceAll("");          
	}
	 /**          
	  * 正则表达式去除字符串中所有的HTML标签          
	  * @param str           
	  *            待处理的字符串          
	  * @return 返回无HTML标签的字符串           
	  */  
	public static String regExNoHTML(String str) {                  
		if (str == null) {                          
			return str;                  
		}                  
		Pattern p = Pattern.compile("<.+?>");                  
		Matcher m = p.matcher(str);                  
		return m.replaceAll("");          
	}
}
