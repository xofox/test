package org.nbrc.mobile.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nbrc.mobile.helper.AjaxResult;
import org.nbrc.mobile.helper.ConfigUtil;
import org.nbrc.mobile.helper.Pagenation;
import org.nbrc.mobile.helper.fileVo;
import org.nbrc.mobile.helper.Tree;
import org.nbrc.mobile.helper.UploadUtil;
import org.nbrc.mobile.model.Attaches;
import org.nbrc.mobile.model.News;
import org.nbrc.mobile.service.AdminService;
import org.nbrc.mobile.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class DocController extends BaseController {

	@Autowired
	private DocService docService;
	
	@Autowired
	private AdminService adminService;
	
	@ResponseBody
	@RequestMapping("/tree")
	public List<Tree> tree(){
		List<Tree> lt = new ArrayList<Tree>();
		
		Tree tree = new Tree();
		tree.setText("频道管理");
		tree.setIconCls("database_gear");
		Map<String, Object> attr = new HashMap<String, Object>();
		attr.put("url", "/channel");
		tree.setAttributes(attr);
		
		lt.add(tree);
		
		Tree tree1 = new Tree();
		tree1.setText("文档发布");
		tree1.setIconCls("tux");
		Map<String, Object> attr1 = new HashMap<String, Object>();
		attr1.put("url", "/doc");
		tree1.setAttributes(attr1);
		
		lt.add(tree1);
		
		Tree tree2 = new Tree();
		tree2.setText("广告管理");
		tree2.setIconCls("status_online");
		Map<String, Object> attr2 = new HashMap<String, Object>();
		attr2.put("url", "/advert");
		tree2.setAttributes(attr2);
		
		lt.add(tree2);
		
		return lt;
	}
	
	@RequestMapping(value="/doc",method=RequestMethod.GET)
	public String doc(ModelMap model){
		return "doc/doc";
	}
	
	//文章分页
	@ResponseBody
	@RequestMapping(value="/docGrid",method=RequestMethod.POST)
	public Pagenation<News> docGrid(News doc,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="20") int rows){
		return docService.docGrid(page, rows, doc);
	}
	
	//跳转到添加文章页面
	@RequestMapping("/addDocPage")
	public String addDocPage(HttpServletRequest request){
		request.setAttribute("channelList", adminService.getChannelList());
		return "doc/docAdd";
	}
	
	//添加文章
	@ResponseBody
	@RequestMapping(value="/addDoc",method=RequestMethod.POST)
	public AjaxResult addDoc (News doc,String tags){
		AjaxResult result = new AjaxResult();
		try {
			//System.out.println(doc.getContent());
			//System.out.println(tags);
			docService.add(doc, tags);
			result.setSuccess(true);
			result.setMessage("添加成功！");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	//跳转到编辑文章页面
	@RequestMapping(value="/editDocPage",method=RequestMethod.POST)
	public String editDocPage(HttpServletRequest request,String id){
		request.setAttribute("channelList", adminService.getChannelList());
		request.setAttribute("n",docService.getNews(id));
		request.setAttribute("tags", docService.getNewsTags(id));
		return "doc/docEdit";
	}
	
	//编辑文章
	@ResponseBody
	@RequestMapping(value="/eidtDoc",method=RequestMethod.POST)
	public AjaxResult eidtDoc (News doc,String tags){
		AjaxResult result = new AjaxResult();
		try {
			docService.edit(doc, tags);
			result.setSuccess(true);
			result.setMessage("修改成功！");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	//跳转到附件列表页面
	@RequestMapping(value="/attachePage",method=RequestMethod.POST)
	public String attachePage(HttpServletRequest request,String id){
		request.setAttribute("docid",id);
		return "attache/attache";
	}
	
	//附件分页
	@ResponseBody
	@RequestMapping(value="/attacheGrid",method=RequestMethod.POST)
	public Pagenation<Attaches> attacheGrid(Attaches attache,String id,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="20") int rows){
		return docService.attacheGrid(page, rows, attache, id);
	}
	
	//附件上传
	@ResponseBody
	@RequestMapping(value="/addAttache",method=RequestMethod.POST)
	public AjaxResult addAttache (@RequestParam("file") CommonsMultipartFile file,Attaches attache,HttpServletRequest request){
		AjaxResult result = UploadUtil.upload(file, "文件", ConfigUtil.get("maxFileSize"), ConfigUtil.get("file"), ConfigUtil.get("fileSavePath"), request);
		if(result.isSuccess()){
			fileVo v = (fileVo) result.getObj();
			attache.setSize(v.getSize());
			attache.setTitle(v.getTitle());
			attache.setUrl(v.getRealUrl());
			docService.addAttache(attache);
		}
		return result;
	}
	
	//删除附件
	@ResponseBody
	@RequestMapping(value="/deleAttache",method=RequestMethod.POST)
	public AjaxResult deleAttache(String id,HttpServletRequest request){
		AjaxResult result = new AjaxResult();
		if(id!=null){
			Attaches attache = docService.getAttache(id);
			String url = attache.getUrl();
			String str = url.lastIndexOf("/") >= 0 ? url.substring(url.lastIndexOf("/") - 8) : "";
			String realpath = request.getSession().getServletContext().getRealPath("/"+ConfigUtil.get("fileSavePath")+"/"+str.substring(0,str.lastIndexOf("/"))+"/"+str.substring(str.lastIndexOf("/")+1));
			File file = new File(realpath);
			if(file.exists()){
				file.delete();
				docService.deleteAttache(id);
				result.setSuccess(true);
				result.setMessage("删除成功！");
			}else{
				result.setSuccess(false);
				result.setMessage("删除失败，附件不存在！");
			}
		}
		return result;
	}
}
