package org.nbrc.mobile.service.impl;

import java.util.Date;
import java.util.List;

import org.nbrc.mobile.dao.AttachesDAO;
import org.nbrc.mobile.dao.NewClicksDAO;
import org.nbrc.mobile.dao.NewsDAO;
import org.nbrc.mobile.dao.NewsTagDAO;
import org.nbrc.mobile.dao.TagsDAO;
import org.nbrc.mobile.helper.Pagenation;
import org.nbrc.mobile.model.Attaches;
import org.nbrc.mobile.model.NewClicks;
import org.nbrc.mobile.model.News;
import org.nbrc.mobile.model.NewsTag;
import org.nbrc.mobile.model.Tags;
import org.nbrc.mobile.service.DocService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
public class DocServiceImpl implements DocService {

	@Autowired
	@Qualifier("newsDAOImpl")
	private NewsDAO newsDao;
	
	@Autowired
	@Qualifier("newsTagDAOImpl")
	private NewsTagDAO newsTagDao;
	
	@Autowired
	@Qualifier("tagsDAOImpl")
	private TagsDAO tagsDao;
	
	@Autowired
	@Qualifier("newClicksDAOImpl")
	private NewClicksDAO newClicksDao;
	
	@Autowired
	@Qualifier("attachesDAOImpl")
	private AttachesDAO attachesDao;

	//文章分页
	@Override
	public Pagenation<News> docGrid(int page, int pagesize, News doc) {
		Pagenation<News> p = new Pagenation<News>(page, pagesize);
		StringBuilder  sb = new StringBuilder();
		sb.append(" from News doc");
		if(doc!=null){
			if(doc.getTitle()!=null &&!"".equals(doc.getTitle())){
				sb.append(" where doc.title like '%").append(doc.getTitle()).append("%'");
			}
		}
		p.setRows(newsDao.page(sb.toString(), page, pagesize));
		p.setTotal(newsDao.count(sb.insert(0,"select count(*)  ").toString()));
		return p;
	}
	
	//添加新闻，同时添加标签
	@Override
	synchronized public void add(News doc, String tags) throws Exception {
		if (newsDao.count("select count(*) from News doc where doc.title = ?", doc.getTitle()) > 0) {
			throw new Exception("文章名已存在！");
		} else {
			//新增新闻
			News n = new News();
			BeanUtils.copyProperties(doc, n);
			//System.out.println(doc.getContent()); new String[]{"content"}
			//n.setContent(HtmlUtils.toHtml(doc.getContent()));
			//System.out.println(n.getContent());
			n.setPubat(new Date());
			n.setModat(new Date());
			newsDao.save(n);
			//插入该新闻的标签
			String[] str = tags.split(",");
			for(String tagName : str){
				NewsTag t = new NewsTag();
				t.setNews(n);
				t.setTagName(tagName);
				newsTagDao.save(t);	
				//查找标签表中是否有该标签，如果有文章数加1，没有则新增一条，文章数为1
				if (tagsDao.count("select count(*) from Tags t where t.tagName = ?",tagName) > 0) {
					Tags tag = tagsDao.query("from Tags t where t.tagName = ?", tagName).get(0);
					tag.setDocs(tag.getDocs()+1);
				}else{
					Tags tag  =new Tags();
					tag.setDocs(1);
					tag.setTagName(tagName);
					tagsDao.save(tag);
				}
			}
		}
	}

	//修改新闻
	@Override
	public void edit(News doc,String tags){
			News n = newsDao.get(doc.getId());
			//System.out.println(doc.getContent());
			BeanUtils.copyProperties(doc, n, new String[]{"pubat"});
			//n.setContent(HtmlUtils.toHtml(doc.getContent()));
			//System.out.println(n.getContent());
			n.setModat(new Date());
			newsDao.update(n);

			//删除原有的标签,同时对应的标签表数减1
			
			List<NewsTag> list = newsTagDao.query("from NewsTag t where  t.news.id = ?", doc.getId());
			for(NewsTag tt : list){
				Tags tag = tagsDao.query("from Tags t where t.tagName = ?", tt.getTagName()).get(0);
				if(tag != null){
					if(tag.getDocs()>0)
						tag.setDocs(tag.getDocs()-1);
				}
				tagsDao.update(tag);
				newsTagDao.delete(tt);
			}
				
			//插入该新闻的标签
			String[] str = tags.split(",");
			for(String tagName : str){
				NewsTag t = new NewsTag();
				t.setNews(n);
				t.setTagName(tagName);
				newsTagDao.save(t);	
				//查找标签表中是否有该标签，如果有文章数加1，没有则新增一条，文章数为1
				if (tagsDao.count("select count(*) from Tags t where t.tagName = ?",tagName) > 0) {
					Tags tag = tagsDao.query("from Tags t where t.tagName = ?", tagName).get(0);
					tag.setDocs(tag.getDocs()+1);
				}else{
					Tags tag  =new Tags();
					tag.setDocs(1);
					tag.setTagName(tagName);
					tagsDao.save(tag);
				}
			}
	}
		
	
	//获取新闻
	@Override
	public News getNews(String id) {
		return newsDao.get(id);
	}
	
	//获取新闻标签
	@Override
	public String getNewsTags(String id) {
		String str ="";
		List<NewsTag> list = newsTagDao.query("from NewsTag t where  t.news.id = ?", id);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				NewsTag t = list.get(i);
				if(i==0){
					str = str + t.getTagName();
				}else{
					str = str +","+t.getTagName();
				}
			}
		}
		return str;
	}

	//附件分页
	@Override
	public Pagenation<Attaches> attacheGrid(int page, int pagesize,Attaches attache,String docid) {
		Pagenation<Attaches> p = new Pagenation<Attaches>(page, pagesize);
		StringBuilder  sb = new StringBuilder();
		sb.append(" from Attaches a where a.news.id = ?");
		if(attache!=null){
			if(attache.getTitle()!=null &&!"".equals(attache.getTitle())){
				sb.append(" and a.title like '%").append(attache.getTitle()).append("%'");
			}
		}
		p.setRows(attachesDao.page(sb.toString(), page, pagesize,docid));
		p.setTotal(attachesDao.count(sb.insert(0,"select count(*)  ").toString(),docid));
		return p;
	}
	
	//添加附件
	@Override
	public void addAttache(Attaches attache) {
		Attaches a = new Attaches();
		BeanUtils.copyProperties(attache, a);
		int i = attachesDao.count("select count(*) from Attaches");
		if(i==0){
			a.setSeq(1);
		}else{
			a.setSeq(i+1);
		}
		attachesDao.save(a);
	}
	
	//获取附件
	@Override
	public Attaches getAttache(String id) {
		return attachesDao.get(id);
	}

	// 删除附件
	@Override
	public boolean deleteAttache(String attacheId) {
		boolean flag = false;
		if(attacheId!=null)
		{
			attachesDao.delete(attachesDao.get(attacheId));
			flag = true;
		}
		return flag;
	}

	//取前5条新闻
	@Override
	public List<News> getNewsList(String channelId) {
		return newsDao.page(" from News doc where doc.channel.id= ? order by doc.pubat desc ",0,5,channelId);
	}

	
	//添加点击数，同时显示新闻的详细信息
	@Override
	public News getDocDetails(String newsId,String ua) {
		News doc = this.getNews(newsId);
		int num = doc.getClick()!=null?doc.getClick()+1:1;
		doc.setClick(num);
		newsDao.update(doc);
		NewClicks click = new NewClicks();
		click.setClickAt(new Date());
		click.setNews(doc);
		click.setUa(ua);
		newClicksDao.save(click);
		return doc;
	}

	//前台web频道新闻分页
	@Override
	public Pagenation<News> pageDocs(String channelId, int page, int pagesize) {
		Pagenation<News> p = new Pagenation<News>(page, pagesize);
		StringBuilder  sb = new StringBuilder();
		sb.append(" from News doc where doc.channel.id = ? ");
		p.setRows(newsDao.page(sb.toString(), page, pagesize,channelId));
		p.setTotal(newsDao.count(sb.insert(0,"select count(*)  ").toString(),channelId));
		return p;
	}
	
}
