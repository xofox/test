package org.nbrc.mobile.service.impl;

import java.util.Date;
import java.util.List;

import org.nbrc.mobile.dao.AdClicksDAO;
import org.nbrc.mobile.dao.AdminDAO;
import org.nbrc.mobile.dao.AdsDAO;
import org.nbrc.mobile.dao.ChannelDAO;
import org.nbrc.mobile.dao.KeyPersonWordsDAO;
import org.nbrc.mobile.dao.KeyWordsDAO;
import org.nbrc.mobile.helper.MD5Util;
import org.nbrc.mobile.helper.Pagenation;
import org.nbrc.mobile.model.AdClicks;
import org.nbrc.mobile.model.Admin;
import org.nbrc.mobile.model.Ads;
import org.nbrc.mobile.model.Channel;
import org.nbrc.mobile.model.KeyPersonWords;
import org.nbrc.mobile.model.KeyWords;
import org.nbrc.mobile.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	@Qualifier("adminDAOImpl")
	private AdminDAO adminDao;
	
	@Autowired
	@Qualifier("channelDAOImpl")
	private ChannelDAO channelDao;
	
	@Autowired
	@Qualifier("adsDAOImpl")
	private AdsDAO adsDao;
	
	@Autowired
	@Qualifier("adClicksDAOImpl")
	private AdClicksDAO adclicksDao;
	
	@Autowired
	@Qualifier("keyWordsDAOImpl")
	private KeyWordsDAO keyWordsDao;
	
	@Autowired
	@Qualifier("keyPersonWordsDAOImpl")
	private KeyPersonWordsDAO keyPersonWordsDao;
	
	//登录
	@Override
	public Admin login(Admin admin) {
		Admin a = adminDao.queryObj("from Admin a where a.name = ? and a.pwd = ?", admin.getName(),MD5Util.md5(admin.getPwd()));
		if (a!= null) {
			BeanUtils.copyProperties(a, admin);
			return admin;
		}
		return null;
	}
	
	//注册
	@Override
	synchronized public void reg(Admin admin) throws Exception {
		if (adminDao.count("select count(*) from Admin a where a.name = ?", admin.getName()) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Admin a = new Admin();
			a.setName(admin.getName());
			a.setPwd(MD5Util.md5(admin.getPwd()));
			adminDao.save(a);
		}
	}

	//修改密码
	@Override
	public boolean editPwd(Admin admin) {
		boolean flag = false;
		if (admin != null && admin.getPwd() != null && !admin.getPwd().trim().equalsIgnoreCase("")) {
			Admin a = adminDao.get(admin.getId());
			a.setPwd(MD5Util.md5(admin.getPwd()));
			adminDao.update(a);
			flag = true;
		}
		return flag;
	}

	//频道分页
	@Override
	public Pagenation<Channel> channelGrid(int page, int pagesize, Channel c) {
		Pagenation<Channel> p = new Pagenation<Channel>(page, pagesize);
		StringBuilder  sb = new StringBuilder();
		sb.append(" from Channel c");
		if(c!=null){
			if(c.getTitle()!=null &&!"".equals(c.getTitle())){
				sb.append(" where c.title like '%").append(c.getTitle()).append("%'");
			}
		}
		p.setRows(channelDao.page(sb.toString(), page, pagesize));
		p.setTotal(channelDao.count(sb.insert(0,"select count(*)  ").toString()));
		return p;
	}
	
	//频道列表
	@Override
	public List<Channel> getChannelList(){
		return channelDao.query(" from Channel c ");
	}

	//添加频道
	@Override
	synchronized public void addChannel(Channel c) throws Exception {
		if (channelDao.count("select count(*) from Channel c where c.title = ?", c.getTitle()) > 0) {
			throw new Exception("频道名已存在！");
		} else {
			Channel channel = new Channel();
			BeanUtils.copyProperties(c, channel);
			channelDao.save(channel);
		}
	}

	//获取频道
	@Override
	public Channel getChannel(String id) {
		return channelDao.get(id);
	}

	//编辑频道
	@Override
	public void editChannel(Channel c) throws Exception {
		if (channelDao.count("select count(*) from Channel c where c.title = ?", c.getTitle()) > 0) {
			throw new Exception("频道名已存在！");
		} else {
			Channel channel = channelDao.get(c.getId());
			BeanUtils.copyProperties(c, channel);
			channelDao.update(channel);
		}
	}

	//广告分页
	@Override
	public Pagenation<Ads> adverGrid(int page, int pagesize, Ads a) {
		Pagenation<Ads> p = new Pagenation<Ads>(page, pagesize);
		StringBuilder  sb = new StringBuilder();
		sb.append(" from Ads a");
		if(a!=null){
			if(a.getAdTitle()!=null &&!"".equals(a.getAdTitle())){
				sb.append(" where a.adTitle like '%").append(a.getAdTitle()).append("%'");
			}
		}
		p.setRows(adsDao.page(sb.toString(), page, pagesize));
		p.setTotal(adsDao.count(sb.insert(0,"select count(*)  ").toString()));
		return p;
	}

	//添加广告
	@Override
	public void addAdvert(Ads a){
		Ads ad = new Ads();
		BeanUtils.copyProperties(a, ad);
		adsDao.save(ad);
	}

	//获取广告
	@Override
	public Ads getAdvert(String id) {
		return adsDao.get(id);
	}

	//编辑广告
	@Override
	public boolean editAdvert(Ads a) {
		boolean flag = false;
		Ads ad = adsDao.get(a.getId());
		if(ad!=null){
			BeanUtils.copyProperties(a, ad,new String[]{"addDate","click","adImage"});
			flag = true;
		}
		return flag;
	}

	//删除广告
	@Override
	public boolean deleteAdvert(String advertId) {
		boolean flag = false;
		if(advertId!=null)
		{
			adsDao.delete(adsDao.get(advertId));
			flag = true;
		}
		return flag;
	}
	
	//有效期内的广告列表
	@Override
	public List<Ads> getAdsList() {
		return adsDao.query("from Ads a where NOW() between a.startDate and a.endDate ");
	}

	//添加广告点击数，同时显示广告的详细信息
	@Override
	public Ads getAdsDetails(String id, String ua) {
		Ads ad = this.getAdvert(id);
		int num = ad.getClick()!=null ? ad.getClick()+1 : 1;
		ad.setClick(num);
		adsDao.update(ad);
		
		AdClicks click = new AdClicks();
		click.setAd(ad);
		click.setClickAt(new Date());
		click.setUa(ua);
		adclicksDao.save(click);
		
		return ad;
	}

	//添加搜索记录
	@Override
	public void addKeyWords(String keyName,String ua,String ip) {
		if (keyWordsDao.count("select count(*) from KeyWords k where k.keyName = ? and k.ip = ? and k.ua = ?", keyName,ip,ua) > 0) {
			
		}else{
			KeyWords key = new KeyWords();
			key.setKeyName(keyName);
			key.setUa(ua);
			key.setIp(ip);
			key.setSearchAt(new Date());
			keyWordsDao.save(key);
		}
	}

	//添加人才搜索记录
	@Override
	public void addKeyPersonWords(String keyName, String ua, String ip) {
		if (keyPersonWordsDao.count("select count(*) from KeyPersonWords k where k.keyName = ? and k.ip = ? and k.ua = ?", keyName,ip,ua) > 0) {
			
		}else{
			KeyPersonWords key = new KeyPersonWords();
			key.setKeyName(keyName);
			key.setUa(ua);
			key.setIp(ip);
			key.setSearchAt(new Date());	
			keyPersonWordsDao.save(key);
		}
	}
}
