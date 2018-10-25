package com.saylorsolutions.util.mapping_utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class FieldUtilsTest {
	@Test
	public void testObjectFieldValueMap() {
		SourceValueMap fieldValueMap = FieldUtils.getFieldValueMap(new ObjectFieldValues());

		assertEquals("publicStr", fieldValueMap.get("publicStr"));
		assertEquals("name", fieldValueMap.get("name"));
		assertTrue(fieldValueMap.get("boolObj", Boolean.class));
		assertFalse(fieldValueMap.get("boolPrim", Boolean.class));
		assertEquals(new Integer(5), fieldValueMap.get("intObj", Integer.class));
		assertSame(5, fieldValueMap.get("intObj", Integer.class));
		assertEquals(new Integer(10), fieldValueMap.get("intPrim", Integer.class));
		assertNotSame(10, fieldValueMap.get("intPrim", Integer.class));
	}

	@Test
	public void testObjectFieldMap() {
		Set<String> fieldNames = FieldUtils.getAllFields(new ObjectFieldValues()).keySet();
		String[] expectedFields = new String[] { "publicStr", "name", "boolObj", "boolPrim", "intObj", "intPrim" };

		fieldNames.containsAll(Arrays.asList(expectedFields));
	}
}

class ObjectFieldValues {
	public String publicStr = "publicStr";

	private String name = "name";
	private Boolean boolObj = true;
	private boolean boolPrim = false;
	private Integer intObj = 5;
	private int intPrim = 10;
	public String getName() {
		return name;
	}
	public Boolean getBoolObj() {
		return boolObj;
	}
	public void setBoolObj(Boolean boolObj) {
		this.boolObj = boolObj;
	}
	public boolean isBoolPrim() {
		return boolPrim;
	}
	public void setBoolPrim(boolean boolPrim) {
		this.boolPrim = boolPrim;
	}
	public Integer getIntObj() {
		return intObj;
	}
	public void setIntObj(Integer intObj) {
		this.intObj = intObj;
	}
	public int getIntPrim() {
		return intPrim;
	}
	public void setIntPrim(int intPrim) {
		this.intPrim = intPrim;
	}
}