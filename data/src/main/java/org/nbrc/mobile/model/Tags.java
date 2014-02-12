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
 * @description 标签表
 */
@Entity
@Table(name="mob_tags")
public class Tags implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="Id",length=40)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="org.hibernate.id.UUIDGenerator")
	private String id;	//	Varchar2(32)	主键，uuid
	@Column(name="Tag",length=100)
	private String tagName; // Varchar2(100) 标签名
	@Column(name="Docs")
	private Integer docs; //Int 标签对应文章数
	@Column(name="Click")
	private Integer click; //Int 标签点击数
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
	public Integer getDocs() {
		return docs;
	}
	public void setDocs(Integer docs) {
		this.docs = docs;
	}
	public Integer getClick() {
		return click;
	}
	public void setClick(Integer click) {
		this.click = click;
	}
}
