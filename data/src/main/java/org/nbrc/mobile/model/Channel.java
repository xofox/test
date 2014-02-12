package org.nbrc.mobile.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * @author zhuangsj
 * @description 新闻频道表
 */
@Entity
@Table(name="mob_channel")
public class Channel implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="Id",length=40)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="org.hibernate.id.UUIDGenerator")
	private String id;    //Varchar2(32)	主键，uuid
	@Column(name="Title",length=500)
	private String title;  //Varchar2(500)	标题
	@Column(name="Description",length=500)
	private String description; //Varchar2(500)	描述
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
