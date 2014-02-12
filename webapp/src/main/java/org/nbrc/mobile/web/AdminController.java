package org.nbrc.mobile.web;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nbrc.mobile.helper.AjaxResult;
import org.nbrc.mobile.helper.ConfigUtil;
import org.nbrc.mobile.helper.Pagenation;
import org.nbrc.mobile.helper.fileVo;
import org.nbrc.mobile.helper.UploadUtil;
import org.nbrc.mobile.interceptor.WebConstants;
import org.nbrc.mobile.model.Admin;
import org.nbrc.mobile.model.Ads;
import org.nbrc.mobile.model.Channel;
import org.nbrc.mobile.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController extends BaseController {

	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/west")
	public String west(ModelMap model){
		return "west";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(ModelMap model){
		return "login";
	}
	
	//登录
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login (Admin admin, HttpSession session,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Admin a = adminService.login(admin);
		if (a != null) {
			session = request.getSession();
			session.setAttribute(WebConstants.SESSION_KEY, a);
			redirectAttributes.addFlashAttribute("admin",a);
			return "welcome";
		} else {
			redirectAttributes.addFlashAttribute("message","用户名或密码错误！");
			return "redirect:/login";
		}
	}
	
	//注册
	@ResponseBody
	@RequestMapping(value="/reg",method=RequestMethod.POST)
	public AjaxResult reg (Admin admin){
		AjaxResult result = new AjaxResult();
		try {
			adminService.reg(admin);
			result.setSuccess(true);
			result.setMessage("注册成功！");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	//修改密码
	@ResponseBody
	@RequestMapping(value="/editPwd",method=RequestMethod.POST)
	public AjaxResult editPwd (Admin admin){
		AjaxResult result = new AjaxResult();
		if(adminService.editPwd(admin)){
			result.setSuccess(true);
			result.setMessage("修改成功！");
		}else{
			result.setSuccess(false);
			result.setMessage("修改失败！");
		}
		return result;
	}
	
	//退出登录
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		session.removeAttribute(WebConstants.SESSION_KEY);
		return "redirect:/login";
	}
	
	@RequestMapping(value="/channel",method=RequestMethod.GET)
	public String channel(ModelMap model){
		return "channel/channel";
	}
	
	//频道分页
	@ResponseBody
	@RequestMapping(value="/channelGrid",method=RequestMethod.POST)
	public Pagenation<Channel> channelGrid(Channel c,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="20") int rows){
		return adminService.channelGrid(page, rows, c);
	}
	
	//跳转到添加频道页面
	@RequestMapping("/addChannelPage")
	public String addChannelPage(){
		return "channel/channelAdd";
	}
	
	//添加频道
	@ResponseBody
	@RequestMapping(value="/addChannel",method=RequestMethod.POST)
	public AjaxResult addChannel (Channel c){
		AjaxResult result = new AjaxResult();
		try {
			adminService.addChannel(c);
			result.setSuccess(true);
			result.setMessage("添加成功！");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	//跳转到频道修改页面
	@RequestMapping(value="/editChannelPage",method=RequestMethod.POST)
	public String editChannelPage(HttpServletRequest request,String id) {
		Channel channel = adminService.getChannel(id); 
		request.setAttribute("c", channel);
		return "channel/channelEdit";
	}
	
	//编辑频道
	@ResponseBody
	@RequestMapping(value="/editChannel",method=RequestMethod.POST)
	public AjaxResult editChannel (Channel c){
		AjaxResult result = new AjaxResult();
		try {
			adminService.editChannel(c);
			result.setSuccess(true);
			result.setMessage("修改成功！");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/advert",method=RequestMethod.GET)
	public String advert(ModelMap model){
		return "advert/advert";
	}
	
	//广告分页
	@ResponseBody
	@RequestMapping(value="/adverGrid",method=RequestMethod.POST)
	public Pagenation<Ads> adverGrid(Ads a,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="20") int rows){
		return adminService.adverGrid(page, rows,a) ;
	}
	
	//跳转到添加广告页面
	@RequestMapping("/addAdvertPage")
	public String addAdvertPage(){
		return "advert/advertAdd";
	}
	
	//添加广告
	@ResponseBody
	@RequestMapping(value="/addAdvert",method=RequestMethod.POST)
	public AjaxResult addAdvert (@RequestParam CommonsMultipartFile file,Ads a,HttpServletRequest request){
		
		AjaxResult result = UploadUtil.upload(file, "图片", ConfigUtil.get("maxFileSize"), ConfigUtil.get("image"), ConfigUtil.get("imageSavePath"), request);
		if(result.isSuccess()){
			fileVo v = (fileVo) result.getObj();
			a.setAdImage(v.getRealUrl());
			a.setAddDate(new Date());
			adminService.addAdvert(a);
			//System.out.println(request.getSession().getServletContext().getRealPath("/"+ConfigUtil.get("imageSavePath")+"/"));
		}
		
		return result;
	}
	
	//跳转到频道修改页面
	@RequestMapping(value="/editAdvertPage",method=RequestMethod.POST)
	public String editAdvertPage(HttpServletRequest request,String id) {
		Ads ad = adminService.getAdvert(id);
		request.setAttribute("a", ad);
		return "advert/advertEdit";
	}
	
	//编辑广告
	@ResponseBody
	@RequestMapping(value="/editAdvert",method=RequestMethod.POST)
	public AjaxResult editAdvert (Ads a){
		AjaxResult result = new AjaxResult();
		boolean flag = adminService.editAdvert(a);
		if(flag){
			result.setSuccess(true);
			result.setMessage("修改成功！");
		}else{
			result.setSuccess(false);
			result.setMessage("修改失败！");
		}
		return result;
	}
	
	//删除广告
	@ResponseBody
	@RequestMapping(value="/deleAdvert",method=RequestMethod.POST)
	public AjaxResult deleAdvert(String id,HttpServletRequest request){
		AjaxResult result = new AjaxResult();
		if(id!=null){
			Ads advert = adminService.getAdvert(id);
			String url = advert.getAdImage();
			String str = url.lastIndexOf("/") >= 0 ? url.substring(url.lastIndexOf("/") - 8) : "";
			String realpath = request.getSession().getServletContext().getRealPath("/"+ConfigUtil.get("imageSavePath")+"/"+str.substring(0,str.lastIndexOf("/"))+"/"+str.substring(str.lastIndexOf("/")+1));
			File file = new File(realpath);
			if(file.exists()){
				file.delete();
				adminService.deleteAdvert(id);
				result.setSuccess(true);
				result.setMessage("删除成功！");
			}else{
				result.setSuccess(false);
				result.setMessage("删除失败，广告文件不存在！");
			}
		}
		return result;
	}	
}
