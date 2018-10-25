package com.saylorsolutions.util.mapping_utils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import com.saylorsolutions.util.mapping_utils.annotation.MappingSourceName;
import com.saylorsolutions.util.mapping_utils.exception.MappingException;

/**
 * Maps an object's fields from source to target or vice versa, using standard
 * getters and setters based on annotated fields.
 * 
 * @author Doug Saylor
 */
public class ObjectMapper {
	public static <T> T createTarget(Object source, Class<T> clazz) {
		return createTarget(FieldUtils.getFieldValueMap(source), clazz);
	}
	public static <T> T createTarget(SourceValueMap source, Class<T> clazz) {
		T instance = null;
		try {
			instance = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new MappingException(String.format("Class '%s' does not have a public, no-arg constructor", clazz.getName()), e);
		}

		return updateTarget(source, instance);
	}
	public static <T> T updateTarget(Object source, T target) {
		if (source != null) {
			return updateTarget(FieldUtils.getFieldValueMap(source), target);
		} else {
			throw new IllegalArgumentException("Object source parameter may not be null");
		}
	}
	public static <T> T updateTarget(SourceValueMap source, T target) {
		if(source == null) throw new IllegalArgumentException("FieldValueMap parameter may not be null");

		Map<String, Field> targetFields = FieldUtils.getAllFields(target, f -> {
			return f.isAnnotationPresent(MappingSourceName.class);
		});

		for(Entry<String, Field> e : targetFields.entrySet()) {
			// Get targetField and setter name
			Field targetField = e.getValue();

			// Get sourceField and getter name
			String mappingSourceName = targetField.getAnnotation(MappingSourceName.class).value();
			Object value = source.get(mappingSourceName);

			try {
				if(targetField.isAccessible()) {
					try {
						targetField.set(target, value);
					} catch (IllegalArgumentException | IllegalAccessException ex) {
						throw new MappingException("Unable to set the mapped value: " + ex.getMessage());
					}
				} else {
					try {
						targetField.setAccessible(true);
						targetField.set(target, value);
					} catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
						throw new MappingException("Unable to set the mapped value: " + ex.getMessage());
					} finally {
						targetField.setAccessible(false);
					}
				}
			} catch (MappingException mx) {
				System.err.println(String.format("Caught exception mapping '%s' to '%s': '%s', skipping", mappingSourceName,
						targetField.getName(), mx.getMessage()));
				continue;
			}
		}
		return target;
	}
}
