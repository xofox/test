package org.nbrc.mobile.so;

import org.springframework.util.StringUtils;

public class PersonSearchObject {
	private String key;
	private String edulevel;
	private String workyear;
	private String bwt;
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the edulevel
	 */
	public String getEdulevel() {
		return edulevel;
	}
	/**
	 * @param edulevel the edulevel to set
	 */
	public void setEdulevel(String edulevel) {
		this.edulevel = edulevel;
	}
	/**
	 * @return the workyear
	 */
	public String getWorkyear() {
		return workyear;
	}
	/**
	 * @param workyear the workyear to set
	 */
	public void setWorkyear(String workyear) {
		this.workyear = workyear;
	}
	/**
	 * @return the bwt
	 */
	public String getBwt() {
		return bwt;
	}
	/**
	 * @param bwt the bwt to set
	 */
	public void setBwt(String bwt) {
		this.bwt = bwt;
	}
	public String makeQueryString() {
		StringBuilder sb = new StringBuilder();
		if(!StringUtils.hasText(this.key)){
			this.key="*";
		}
		sb.append("(");
		sb.append("(BZ:").append(this.key).append(")^2").append("OR (TRS:").append(this.key).append(")^2 ");
		sb.append(" OR (ZYMC:").append((this.key)).append(")^4");
		sb.append(")");
		if(StringUtils.hasText(this.edulevel)){
			sb.append(" AND XLBH:[").append(this.edulevel).append(" TO *]");
		}
		if(StringUtils.hasText(this.workyear)){
			sb.append(" AND GL:[").append(this.workyear).append(" TO *]");
		}
		if(StringUtils.hasText(this.bwt)){
			sb.append(" AND ZHSXSJ:[NOW/DAY-").append(this.bwt).append("DAY TO NOW/DAY+1DAY]");
		}
		return sb.toString();
	}
	
}
