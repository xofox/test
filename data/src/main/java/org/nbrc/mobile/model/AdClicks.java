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
 * @description 广告点击表
 */
@Entity
@Table(name="mob_ad_clicks")
public class AdClicks implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="Id",length=40)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="org.hibernate.id.UUIDGenerator")
	private String id;	//Varchar2(32)	主键，uuid
	@ManyToOne
	@JoinColumn(name="Adid")
	private Ads ad;  //Varchar2(32)	所属广告
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
	public Ads getAd() {
		return ad;
	}
	public void setAd(Ads ad) {
		this.ad = ad;
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
