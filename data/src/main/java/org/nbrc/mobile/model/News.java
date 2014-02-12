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
 * @description 新闻表
 */
@Entity
@Table(name="mob_news")
public class News implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="Id",length=40)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="org.hibernate.id.UUIDGenerator")
	private String id;	//Varchar2(32)	主键，uuid
	@Column(name="Title",length=500)
	private String title;	//Varchar2(500)	标题
	@Column(name="Subtitle",length=500)
	private String subTitle;	//Varchar2(500)	子标题
	@Column(name="Content",columnDefinition="text")
	private String content;	//Clob	内容
	@Column(name="Pubat")	
	private Date pubat;	//Date 发布时间
	@Column(name="Modat")
	private Date modat;	//Date 修改时间
	@Column(name="Click")
	private Integer click; //Int 点击数
	@Column(name="Author",length=200)
	private String author;	//Varchar(200) 作者
	@ManyToOne
	@JoinColumn(name="Channel_id")
	private Channel channel;	//Varchar2(32)	所属频道
	@Column(name="News_source")
	private String source;	//Varchar2(50) 来源
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
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPubat() {
		return pubat;
	}
	public void setPubat(Date pubat) {
		this.pubat = pubat;
	}
	public Date getModat() {
		return modat;
	}
	public void setModat(Date modat) {
		this.modat = modat;
	}
	public Integer getClick() {
		return click;
	}
	public void setClick(Integer click) {
		this.click = click;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}	
}
