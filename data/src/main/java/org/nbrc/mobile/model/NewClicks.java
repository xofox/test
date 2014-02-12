package org.nbrc.mobile.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * @author zhuangsj
 * @description 新闻点击表
 */
@Entity
@Table(name="mob_doc_click")
public class NewClicks implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="org.hibernate.id.UUIDGenerator")
	@Column(name="Id",length=40)
	private String id;	//Varchar2(32)	主键，uuid
	@ManyToOne
	@JoinColumn(name="Docid")
	private News news;	//Varchar2(32)	所属新闻
	@Column(name="Clickat")
	private Date clickAt; //点击时间
	@Column(name="Ua",length=500)
	private String ua;	//Varchar2(500) USER-AGENT
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	public Date getClickAt() {
		return clickAt;
	}
	public void setClickAt(Date clickAt) {
		this.clickAt = clickAt;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}
	
}
