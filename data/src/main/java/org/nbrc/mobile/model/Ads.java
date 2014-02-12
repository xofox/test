package org.nbrc.mobile.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * @author zhuangsj
 * @description 广告表
 */
@Entity
@Table(name="mob_ads")
public class Ads implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="org.hibernate.id.UUIDGenerator")
	@Column(name="Id",length=40)
	private String id;	//Varchar2(32)	主键，uuid
	@Column(name="Adtitle",length=100)
	private String adTitle; //Varchar2(100) 广告名
	@Column(name="Adimage",length=200)
	private String adImage;  //Varchar2(200) 广告图片文件位置
	@Column(name="Startdate")
	private Date startDate; //广告开始时间
	@Column(name="Enddate")
	private Date endDate; //广告结束时间
	@Column(name="Click")
	private Integer click; //Int 点击数
	@Column(name="Adddate")
	private Date addDate; //广告发布时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdTitle() {
		return adTitle;
	}
	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}
	public String getAdImage() {
		return adImage;
	}
	public void setAdImage(String adImage) {
		this.adImage = adImage;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getClick() {
		return click;
	}
	public void setClick(Integer click) {
		this.click = click;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
}
