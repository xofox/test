package org.nbrc.mobile.helper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadUtil {
	public static AjaxResult upload(CommonsMultipartFile file,String str,String size,String extType,String path,HttpServletRequest request){
		AjaxResult result = new AjaxResult();
		
		if(file.isEmpty()){ 
			result.setSuccess(false);
			result.setMessage("请选择"+str+"！");
			return result;
		}else{
			String fileName = file.getOriginalFilename();
			String fileext = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
			
			long maxSize = Long.parseLong(size);
			
			if(file.getSize()<0 || file.getSize()>maxSize){
				result.setSuccess(false);
				result.setMessage("上传"+str+"大小超过限制！(允许最大[" + maxSize + "]字节，您上传了[" + file.getSize() + "]字节)");
				return result;
			}
			
			if(!Arrays.<String> asList(extType.split(",")).contains(fileext)){
				result.setSuccess(false);
				result.setMessage("上传"+str+"扩展名是不允许的扩展名。\n只允许" + ConfigUtil.get("file") + "格式！");
				return result;
			}
			
			//生成以日期为名的文件夹
            String DateSuffix = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String realpath = request.getSession().getServletContext().getRealPath("/"+path+"/"+DateSuffix + "/");
           
            String realUrl = request.getContextPath()+"/"+path+"/"+DateSuffix + "/";
            
            File dir = new File(realpath);  
            if(!dir.exists() || !dir.isDirectory()){
    			dir.mkdirs();			
    		}
            //文件重命名
            int random = (int) (Math.random() * 10000);
            Long  time = System.currentTimeMillis();
            String fileNewName = time.toString() + random +"." + fileext;
            
            try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realpath, fileNewName));  
				fileVo t = new fileVo();
				t.setRealUrl(realUrl + fileNewName);
				t.setSize((int) file.getSize());
				t.setTitle(fileNewName);
				result.setSuccess(true);
				result.setObj(t);
				result.setMessage("添加成功！");
			} catch (Exception e) {
				result.setMessage(e.getMessage());
			}
		}
		
		return result;
	}
}



/*if(file.isEmpty()){ 
result.setSuccess(false);
result.setMessage("请选择文件！");
return result;
}else{
String fileName = file.getOriginalFilename();
String fileext = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();

long maxSize = Long.parseLong(ConfigUtil.get("maxFileSize"));

if(file.getSize()<0 || file.getSize()>maxSize){
	result.setSuccess(false);
	result.setMessage("上传文件大小超过限制！(允许最大[" + maxSize + "]字节，您上传了[" + file.getSize() + "]字节)");
	return result;
}

if(!Arrays.<String> asList(ConfigUtil.get("file").split(",")).contains(fileext)){
	result.setSuccess(false);
	result.setMessage("上传文件扩展名是不允许的扩展名。\n只允许" + ConfigUtil.get("file") + "格式！");
	return result;
}

//生成以日期为名的文件夹
String DateSuffix = new SimpleDateFormat("yyyyMMdd").format(new Date());
String realpath = request.getSession().getServletContext().getRealPath("/"+ConfigUtil.get("fileSavePath")+"/"+DateSuffix + "/");

String realUrl = request.getContextPath()+"/"+ConfigUtil.get("fileSavePath")+"/"+DateSuffix + "/";

File dir = new File(realpath);  
if(!dir.exists() || !dir.isDirectory()){
	dir.mkdirs();			
}
//文件重命名
int random = (int) (Math.random() * 10000);
Long  time = System.currentTimeMillis();
String fileNewName = time.toString() + random +"." + fileext;

try {
	result.setSuccess(true);
	result.setMessage("添加成功！");
	FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realpath, fileNewName));  
	attache.setSize((int) file.getSize());
	attache.setTitle(fileNewName);
	attache.setUrl(realUrl + fileNewName);
	docService.addAttache(attache);
	result.setSuccess(true);
	result.setMessage("添加成功！");
} catch (Exception e) {
	result.setMessage(e.getMessage());
}
}*/