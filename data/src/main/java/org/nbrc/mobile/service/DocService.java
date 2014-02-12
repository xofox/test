package org.nbrc.mobile.service;

import java.util.List;

import org.nbrc.mobile.helper.Pagenation;
import org.nbrc.mobile.model.Attaches;
import org.nbrc.mobile.model.News;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DocService {

	/**
	 * 新闻分页
	 * @param page
	 * @param pagesize
	 * @param doc
	 * @return
	 */
	public Pagenation<News> docGrid(int page ,int pagesize,News doc);
	
	/**
	 * 添加新闻，同时添加标签
	 * @param doc
	 * @param tags
	 */
	public void add(News doc ,String tags) throws Exception;
	
	/**
	 * 修改新闻
	 * @param doc
	 */
	public void edit(News doc,String tags);
	
	/**
	 * 获取新闻
	 * @param id
	 * @return
	 */
	public News getNews(String id);
	
	/**
	 * 获取新闻标签
	 * @param id
	 * @return
	 */
	public String getNewsTags(String id);
	
	/**
	 * 附件分页
	 * @param page
	 * @param pagesize
	 * @param Attaches
	 * @return
	 */
	public Pagenation<Attaches> attacheGrid(int page ,int pagesize,Attaches attache,String docid);
	
	/**
	 * 添加附件
	 * @param attache
	 */
	public void addAttache(Attaches attache);
	
	/**
	 * 获取附件
	 * @param id
	 * @return
	 */
	public Attaches getAttache(String id);
	
	/**
	 * 删除附件
	 * @param attacheId
	 * @return
	 */
	public boolean deleteAttache(String attacheId);
	
	/**
	 * 取前5条新闻
	 * @param channelId
	 * @return
	 */
	public List<News> getNewsList(String channelId);
	
	/**
	 * 添加点击数，同时显示新闻的详细信息
	 * @param newsId
	 * @return
	 */
	public News getDocDetails(String newsId,String ua);
	
	/**
	 * 前台web频道新闻分页
	 * @param channelId
	 * @param page
	 * @param pagesize
	 * @return
	 */
	public Pagenation<News> pageDocs(String channelId,int page ,int pagesize);
}
