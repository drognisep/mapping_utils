package com.saylorsolutions.util.mapping_utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class FieldMapper {
	public static Map<String, Field> getAllFields(Object obj) {
		return getAllFields(obj, f -> true);
	}

	public static Map<String, Field> getAllFields(Object obj, Predicate<Field> pred) {
		if(obj == null) throw new IllegalArgumentException("Object parameter may not be null");
		return getAllFields(obj.getClass(), pred);
	}

	public static Map<String, Field> getAllFields(Class<?> clazz) {
		return getAllFields(clazz, f -> true);
	}

	public static Map<String, Field> getAllFields(Class<?> clazz, Predicate<Field> pred) {
		if(clazz == null) throw new IllegalArgumentException("Class parameter may not be null");
		HashMap<String, Field> map = new HashMap<>();
		for(Field f : clazz.getFields()) {
			if (pred.test(f)) {
				map.put(f.getName(), f);
			}
		}
		return map;
	}
}
