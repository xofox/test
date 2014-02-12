package org.nbrc.mobile.helper;

import java.io.Serializable;
import java.util.Date;

public class JobItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private String compId;  //COMPID
	private String workAt; //GONG_ZUO_DD
	private String workAtCh; //GONG_ZU_DD_ZW
	private String cateIds;//GANG_WEI_LB
	private String cateId1;//GWLB1
	private String cateId2;//GWLB12
	private String cateId3;//GWLB13
	private String jId;//GANG_WEI_BH
	private String compName;//DAN_WEI_MC
	private String bussiness;//HANG_YE_LB
	private String jobName;//GANG_WEI_MC
	private String jobDescription;//GANG_WEI_MS
	private String salary;//ZUI_DI_XL
	private String compDescription;//DAN_WEI_JJ
	private String eduLevel;//XLMC
	private String workYear;//GONG_LING
	private String part;//SFJZ
	private Date endAt;//JIE_ZHI_RQ
	private Date pubAt;//FA_BU_SJ
	private String gender;//XING_BIE
	private Date modAt;//XIU_GAI_SJ
	/**
	 * @return the compId
	 */
	public String getCompId() {
		return compId;
	}
	/**
	 * @param compId the compId to set
	 */
	public void setCompId(String compId) {
		this.compId = compId;
	}
	/**
	 * @return the workAt
	 */
	public String getWorkAt() {
		return workAt;
	}
	/**
	 * @param workAt the workAt to set
	 */
	public void setWorkAt(String workAt) {
		this.workAt = workAt;
	}
	/**
	 * @return the workAtCh
	 */
	public String getWorkAtCh() {
		return workAtCh;
	}
	/**
	 * @param workAtCh the workAtCh to set
	 */
	public void setWorkAtCh(String workAtCh) {
		this.workAtCh = workAtCh;
	}
	/**
	 * @return the cateIds
	 */
	public String getCateIds() {
		return cateIds;
	}
	/**
	 * @param cateIds the cateIds to set
	 */
	public void setCateIds(String cateIds) {
		this.cateIds = cateIds;
	}
	/**
	 * @return the cateId1
	 */
	public String getCateId1() {
		return cateId1;
	}
	/**
	 * @param cateId1 the cateId1 to set
	 */
	public void setCateId1(String cateId1) {
		this.cateId1 = cateId1;
	}
	/**
	 * @return the cateId2
	 */
	public String getCateId2() {
		return cateId2;
	}
	/**
	 * @param cateId2 the cateId2 to set
	 */
	public void setCateId2(String cateId2) {
		this.cateId2 = cateId2;
	}
	/**
	 * @return the cateId3
	 */
	public String getCateId3() {
		return cateId3;
	}
	/**
	 * @param cateId3 the cateId3 to set
	 */
	public void setCateId3(String cateId3) {
		this.cateId3 = cateId3;
	}
	/**
	 * @return the jId
	 */
	public String getjId() {
		return jId;
	}
	/**
	 * @param jId the jId to set
	 */
	public void setjId(String jId) {
		this.jId = jId;
	}
	/**
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}
	/**
	 * @param compName the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}
	/**
	 * @return the bussiness
	 */
	public String getBussiness() {
		return bussiness;
	}
	/**
	 * @param bussiness the bussiness to set
	 */
	public void setBussiness(String bussiness) {
		this.bussiness = bussiness;
	}
	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * @return the jobDescription
	 */
	public String getJobDescription() {
		return jobDescription;
	}
	/**
	 * @param jobDescription the jobDescription to set
	 */
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	/**
	 * @return the salary
	 */
	public String getSalary() {
		return salary;
	}
	/**
	 * @param salary the salary to set
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}
	/**
	 * @return the compDescription
	 */
	public String getCompDescription() {
		return compDescription;
	}
	/**
	 * @param compDescription the compDescription to set
	 */
	public void setCompDescription(String compDescription) {
		this.compDescription = compDescription;
	}
	/**
	 * @return the eduLevel
	 */
	public String getEduLevel() {
		return eduLevel;
	}
	/**
	 * @param eduLevel the eduLevel to set
	 */
	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
	}
	/**
	 * @return the workYear
	 */
	public String getWorkYear() {
		return workYear;
	}
	/**
	 * @param workYear the workYear to set
	 */
	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}
	/**
	 * @return the part
	 */
	public String getPart() {
		return part;
	}
	/**
	 * @param part the part to set
	 */
	public void setPart(String part) {
		this.part = part;
	}
	/**
	 * @return the endAt
	 */
	public Date getEndAt() {
		return endAt;
	}
	/**
	 * @param endAt the endAt to set
	 */
	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}
	/**
	 * @return the pubAt
	 */
	public Date getPubAt() {
		return pubAt;
	}
	/**
	 * @param pubAt the pubAt to set
	 */
	public void setPubAt(Date pubAt) {
		this.pubAt = pubAt;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the modAt
	 */
	public Date getModAt() {
		return modAt;
	}
	/**
	 * @param modAt the modAt to set
	 */
	public void setModAt(Date modAt) {
		this.modAt = modAt;
	}
	
}
