package org.nbrc.mobile.helper;

import java.io.Serializable;
import java.util.Map;

public class CodeMappers implements Serializable {
	private static final long serialVersionUID = -1137102583155449145L;
	private Map<String, CodeMapping> mapping;

	public Map<String, CodeMapping> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String, CodeMapping> mapping) {
		this.mapping = mapping;
	}
}
