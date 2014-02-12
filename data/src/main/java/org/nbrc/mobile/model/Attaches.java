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
 * @description 附件表
 */
@Entity
@Table(name="mob_attaches")
public class Attaches implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="org.hibernate.id.UUIDGenerator")
	@Column(name="Id",length=40)
	private String id;	//Varchar2(32)	主键，uuid
	@Column(name="Title",length=100)
	private String title;	//Varchar2(100)	文件名
	@Column(name="url",length=100)
	private String url;	//Varchar2(100)	文件位置
	@Column(name="Size")
	private Integer size; //Int 文件大小
	@ManyToOne
	@JoinColumn(name="Docid")
	private News news;	//Varchar2(32)	所属新闻
	@Column(name="Seq")
	private Integer seq; //Int 序号，自动编号，可以由用户指定
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
}
