package org.nbrc.mobile.service;

import java.util.List;

import org.nbrc.mobile.helper.Pagenation;
import org.nbrc.mobile.model.Admin;
import org.nbrc.mobile.model.Ads;
import org.nbrc.mobile.model.Channel;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminService {
	/**
	 * 管理员登录
	 * @param admin
	 * @return
	 */
	public Admin login(Admin admin);
	/**
	 * 管理员注册
	 * @param admin
	 * @throws Exception
	 */
	public void reg(Admin admin) throws Exception;
	
	/**
	 * 修改密码
	 * @param admin
	 * @return
	 */
	public boolean editPwd(Admin admin);
	
	/**
	 * 频道分页
	 * @param page
	 * @param pagesize
	 * @param c
	 * @return
	 */
	public Pagenation<Channel> channelGrid(int page ,int pagesize,Channel c);
	
	/**
	 * 频道列表
	 * @return
	 */
	public List<Channel> getChannelList();
	
	/**
	 * 添加频道
	 * @param Channel
	 */
	public void addChannel(Channel c) throws Exception;
	
	/**
	 * 获取频道
	 * @param id
	 * @return Channel
	 */
	public Channel getChannel(String id);
	
	/**
	 * 编辑频道
	 * @param c
	 * @throws Exception
	 */
	public void editChannel(Channel c) throws Exception;
	
	/**
	 * 广告分页
	 * @param page
	 * @param pagesize
	 * @param a
	 * @return
	 */
	public Pagenation<Ads> adverGrid(int page ,int pagesize,Ads a);
	
	/**
	 * 添加广告
	 * @param a
	 */
	public void addAdvert(Ads a);
	
	/**
	 * 获取广告
	 * @param id
	 * @return Ads
	 */
	public Ads getAdvert(String id);
	
	/**
	 * 编辑广告
	 * @param a
	 * @throws Exception
	 */
	public boolean editAdvert(Ads a);
	
	/**
	 * 删除广告
	 * @param advertId
	 * @return
	 */
	public boolean deleteAdvert(String advertId);
	
	/**
	 * 有效期内的广告列表
	 * @return
	 */
	public List<Ads> getAdsList();
	
	/**
	 * 添加广告点击数，同时显示广告的详细信息
	 * @param id
	 * @return
	 */
	public Ads getAdsDetails(String id,String ua);
	
	/**
	 * 添加岗位搜索记录
	 */
	public void addKeyWords(String keyName,String ua,String ip);
	
	/**
	 * 添加人才搜索记录
	 */
	public void addKeyPersonWords(String keyName,String ua,String ip);
}
