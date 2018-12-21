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


import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

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
