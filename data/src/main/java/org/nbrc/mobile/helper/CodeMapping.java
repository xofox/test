package org.nbrc.mobile.helper;

import java.io.Serializable;
import java.util.Map;

public class CodeMapping implements Serializable {
	private static final long serialVersionUID = -3191562958882622639L;
	private String code;
	private String name;
	private Map<String, String> codes;
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the codes
	 */
	public Map<String, String> getCodes() {
		return codes;
	}
	/**
	 * @param codes the codes to set
	 */
	public void setCodes(Map<String, String> codes) {
		this.codes = codes;
	}
	
}
