package com.saylorsolutions.util.mapping_utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import com.saylorsolutions.util.mapping_utils.annotation.MappingSourceName;
import com.saylorsolutions.util.mapping_utils.exception.MappingException;

/**
 * Maps an object's fields from source to target or vice versa, using standard
 * getters and setters based on annotated fields.
 * 
 * @author Doug Saylor
 *
 * @param <S> Source object type.
 * @param <T> Target object type.
 */
public class ObjectMapper {
	public static <T> T createTarget(Object source, Class<T> targetType) {
		T instance = null;
		try {
			instance = targetType.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new MappingException(String.format("Class '%s' does not have a public, no-arg constructor", targetType.getName()), e);
		}

		return updateTarget(source, instance);
	}
	public static <T> T updateTarget(Object source, T target) {
		Map<String, Field> sourceFields = FieldMapper.getAllFields(source);
		Map<String, Field> targetFields = FieldMapper.getAllFields(target, f -> {
			return f.isAnnotationPresent(MappingSourceName.class);
		});

		for(Entry<String, Field> e : targetFields.entrySet()) {
			// Get targetField and setter name
			Field targetField = e.getValue();
			String targetFieldSetter = getFieldSetter(targetField);

			// Get sourceField and getter name
			Field sourceField = sourceFields.get(targetField.getAnnotation(MappingSourceName.class).value());
			String sourceFieldGetter = getFieldGetter(sourceField);

			try {
				if(targetField.getType().isAssignableFrom(sourceField.getType())) {
					Method getter = null;
					Method setter = null;
					Object value = null;
					try {
						getter = source.getClass().getMethod(sourceFieldGetter);
					} catch (NoSuchMethodException | SecurityException e1) {
						throw new MappingException("Unable to locate public getter '" + sourceFieldGetter + "' for class '" + source.getClass() + "'");
					}
					try {
						setter = target.getClass().getMethod(targetFieldSetter, new Class<?>[] { sourceField.getType() });
					} catch (NoSuchMethodException | SecurityException e1) {
						throw new MappingException("Unable to locate public setter '" + targetFieldSetter + "' for class '" + target.getClass() + "'");
					}
					try {
						value = getter.invoke(source);
						setter.invoke(target, value);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
						throw new MappingException("Unable to set the mapped value: " + e1.getMessage());
					}
				} else {
					throw new MappingException(String.format("Cannot assign instance of type '%s' to a field of type '%s'",
							sourceField.getType(), targetField.getType()));
				}
			} catch (MappingException mx) {
				System.err.println(String.format("Caught exception mapping %s to %s, skipping", sourceField.getName(),
						targetField.getName()));
				continue;
			}
		}
		return target;
	}

	public static String getFieldSetter(Field field) {
		return "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
	}
	public static String getFieldGetter(Field field) {
		String targetFieldSetter = "";
		if (field.getType() == boolean.class || field.getType() == Boolean.class) {
			targetFieldSetter = "is" + field.getName().toUpperCase().substring(0, 1)
					+ field.getName().substring(1);
		} else {
			targetFieldSetter = "get" + field.getName().toUpperCase().substring(0, 1)
					+ field.getName().substring(1);
		}
		return targetFieldSetter;
	}
}
