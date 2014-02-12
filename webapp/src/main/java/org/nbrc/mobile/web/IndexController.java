package org.nbrc.mobile.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nbrc.mobile.helper.CodeMappers;
import org.nbrc.mobile.helper.Pagenation;
import org.nbrc.mobile.model.Ads;
import org.nbrc.mobile.model.News;
import org.nbrc.mobile.service.AdminService;
import org.nbrc.mobile.service.DocService;
import org.nbrc.mobile.service.impl.SearchService;
import org.nbrc.mobile.so.PersonSearchObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

@Controller
public class IndexController extends BaseController {

	@Autowired
	private DocService docService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private SearchService svr;
	
	@Autowired
	private CodeMappers mapper;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("index"); 
		
		List<News> rdxw = docService.getNewsList("23e1f1bb-330f-4825-8232-06cbc5ba4596");
		List<News> sydw = docService.getNewsList("067d3e38-b499-46cd-8a37-3fcfb3fce312");
		List<News> zgjp=  docService.getNewsList("304b4f9c-b776-4d34-8f09-fa11eaec424a");
		//List<News> zphp = docService.getNewsList("4970989e-4c80-4eb6-a061-feb28e12d326");
		List<News> xyzp = docService.getNewsList("4c082302-f12c-4743-85f3-9d68c819ba97");
		List<News> zczx = docService.getNewsList("59963e84-cdd4-4fc1-a620-f4c6ef0237ca");
		List<News> wgcs = docService.getNewsList("9dca28d7-9387-43c4-b85e-110134b53cfe");
		//List<News> cyzp = docService.getNewsList("38ed2a48-382c-48ee-bf7c-6cbb88cfa980");
		
		List<Ads> ads = adminService.getAdsList();
		
		mav.addObject("rdxw",rdxw);
		mav.addObject("sydw", sydw);
		mav.addObject("zgjp", zgjp);
		//mav.addObject("zphp", zphp);
		mav.addObject("xyzp", xyzp);
		mav.addObject("zczx", zczx);
		mav.addObject("wgcs", wgcs);
		//mav.addObject("cyzp", cyzp);

		mav.addObject("ads", ads);
				
		//System.out.println(request.getSession().getServletContext().getRealPath("/"));
		
		return mav;
	}
	
	@RequestMapping("/getDocDetails")
	public ModelAndView getDocDetails(HttpServletRequest request,String id){
		
		ModelAndView mav = new ModelAndView("docDetails"); 
		String ua = request.getHeader("User-Agent");
		News doc  = docService.getDocDetails(id, ua);
		
		
		List<Ads> ads = adminService.getAdsList();
		
		//System.out.println(doc.getContent());
		String str = HtmlUtils.htmlUnescape(doc.getContent());
		//System.out.println(str);
		doc.setContent(str);
		mav.addObject("doc",doc);
		mav.addObject("ads", ads);
		
		return mav;
	}
	
	@RequestMapping(value="/pageDocs",method=RequestMethod.GET)
	public ModelAndView pageDocs(HttpServletRequest request,String id,@RequestParam int page,@RequestParam(defaultValue="20") int pagesize){
		
		ModelAndView mav = new ModelAndView("pageDocs"); 
		List<Ads> ads = adminService.getAdsList();
		
		Pagenation<News> p = docService.pageDocs(id, page, pagesize);
		
		mav.addObject("channel", adminService.getChannel(id));
		
		mav.addObject("ads",ads);
		mav.addObject("p",p);
		
		return mav;
	}
	
	@RequestMapping("/q")
	public ModelAndView query(
			@RequestParam(value="key",required=false,defaultValue="") String key,
			@RequestParam(value="more",required=false,defaultValue="") String more,
			@RequestParam(value="bwt",required=false,defaultValue="") String bwt,
			@RequestParam(value="pagesize",required=false,defaultValue="30") int pagesize,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("search/query");
		String p = request.getParameter("page");
		String keyWord = "";
		int page = 0;
		if(p!=null){
			try {
				keyWord = new String((HtmlUtils.htmlUnescape(key)).getBytes("iso-8859-1"),"utf-8");
				page = Integer.parseInt(p);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else{
			keyWord = key;
			page = 1; 
		}
		
		mav.addObject("key",keyWord);
		mav.addObject("more",more);
		mav.addObject("mapper",mapper);
		mav.addObject("result", svr.search(keyWord,more, bwt,page, pagesize));

		String ip = request.getHeader("X-Forwarded-For")!=null?request.getHeader("X-Forwarded-For"):request.getRemoteAddr();
		adminService.addKeyWords(keyWord, request.getHeader("User-Agent"),ip );
		return mav;
	}
	
	@RequestMapping("/pq")
	public ModelAndView queryPerson(PersonSearchObject pso,
			@RequestParam(value="pagesize",required=false,defaultValue="30") int pagesize,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("search/pquery");
		
		String p = request.getParameter("page");
		int page = 0;
		if(p!=null){
			try {
				pso.setKey(new String((HtmlUtils.htmlUnescape(pso.getKey())).getBytes("iso-8859-1"),"utf-8"));
				page = Integer.parseInt(p);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else{
			page = 1; 
		}
		
		mav.addObject("pso",pso);
		mav.addObject("result", svr.queryPerson(pso,page,pagesize));
		String ip = request.getHeader("X-Forwarded-For")!=null?request.getHeader("X-Forwarded-For"):request.getRemoteAddr();
		adminService.addKeyPersonWords(pso.getKey(),request.getHeader("User-Agent"), ip);
		return mav;
	}
}
