/*******************************************************************************
 * Copyright 2018 Saylor Solutions
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package com.saylorsolutions.util.mapping_utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.saylorsolutions.util.mapping_utils.exception.MappingException;

public class FieldUtils {
	public static Map<String, Field> getAllFields(Object obj) {
		return getAllFields(obj, f -> true);
	}

	public static Map<String, Field> getAllFields(Object obj, Predicate<Field> pred) {
		if (obj == null)
			throw new IllegalArgumentException("Object parameter may not be null");
		return getAllFields(obj.getClass(), pred);
	}

	public static Map<String, Field> getAllFields(Class<?> clazz) {
		return getAllFields(clazz, f -> true);
	}

	public static Map<String, Field> getAllFields(Class<?> clazz, Predicate<Field> pred) {
		if (clazz == null || pred == null)
			throw new IllegalArgumentException("Both Class and Predicate parameter must be non-null");
		HashMap<String, Field> map = new HashMap<>();
		for (Field f : clazz.getDeclaredFields()) {
			if (pred.test(f)) {
				map.put(f.getName(), f);
			}
		}
		return map;
	}

	public static SourceValueMap getFieldValueMap(Object obj) {
		return getFieldValueMap(obj, o -> true);
	}

	public static SourceValueMap getFieldValueMap(Object obj, Predicate<Object> pred) {
		if (obj == null)
			throw new IllegalArgumentException("Object parameter may not be null");
		SourceValueMap fieldValueMap = new SourceValueMap();
		for (Field f : obj.getClass().getDeclaredFields()) {
			if (pred.test(obj)) {
				try {
					Object value = null;
					if (!f.isAccessible()) {
						f.setAccessible(true);
						value = f.get(obj);
						f.setAccessible(false);
					} else {
						value = f.get(obj);
					}
					fieldValueMap.put(f.getName(), value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new MappingException("Failed to collect fieldValueMap: " + e.getMessage());
				}
			}
		}
		return fieldValueMap;
	}

	public static String getFieldGetter(Field field) {
		String targetFieldSetter = "";
		if (field.getType() == boolean.class || field.getType() == Boolean.class) {
			targetFieldSetter = "is" + field.getName().toUpperCase().substring(0, 1) + field.getName().substring(1);
		} else {
			targetFieldSetter = "get" + field.getName().toUpperCase().substring(0, 1) + field.getName().substring(1);
		}
		return targetFieldSetter;
	}

	public static String getFieldSetter(Field field) {
		return "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
	}
}
