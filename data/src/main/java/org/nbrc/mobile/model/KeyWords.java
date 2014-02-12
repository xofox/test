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
 * @description 检索企业关键字表
 */
@Entity
@Table(name="mob_keys")
public class KeyWords implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="Id",length=40)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="org.hibernate.id.UUIDGenerator")
	private String id;	//Varchar2(32)	主键，uuid
	@Column(name="KeyName",length=100)
	private String keyName; //Varchar2(100) 搜索关键字
	@Column(name="Searchat")
	private Date searchAt; //搜素时间
	@Column(name="Nums")
	private Integer nums; //Int 返回结果数
	@Column(name="Ua",length=500)
	private String ua;	//Varchar2(500) USER-AGENT
	@Column(name="Ip",length=20) //ip
	private String ip;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public Date getSearchAt() {
		return searchAt;
	}
	public void setSearchAt(Date searchAt) {
		this.searchAt = searchAt;
	}
	public Integer getNums() {
		return nums;
	}
	public void setNums(Integer nums) {
		this.nums = nums;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
