package com.saylorsolutions.util.mapping_utils;

import java.util.HashMap;

public class SourceValueMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 8861175757379055451L;

	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T> clazz) {
		Object obj = this.get(key);
		if(!clazz.isAssignableFrom(obj.getClass()) || obj == null) {
			return (T) null;
		}
		return (T) obj;
	}
}
