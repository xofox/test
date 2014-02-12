package org.nbrc.mobile.model;

import java.io.Serializable;

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
 * @description 新闻标签对应表
 */
@Entity
@Table(name="mob_news_tag")
public class NewsTag implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="Id",length=40)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="org.hibernate.id.UUIDGenerator")
	private String id;	//	Varchar2(32)	主键，uuid
	@Column(name="Tag",length=100)
	private String tagName; // Varchar2(100) 标签名
	@ManyToOne
	@JoinColumn(name="Docid")
	private News news;	//Varchar2(32)	所属新闻
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}

}
