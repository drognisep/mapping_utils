/**
   Copyright 2018 Saylor Solutions

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.saylorsolutions.util.mapping_utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

public class FieldUtilsNegativeTest {
	@Test(expected = IllegalArgumentException.class)
	public void expecteIllegalArgumentNullClazz() {
		FieldUtils.getAllFields((Class<?>)null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void expecteIllegalArgumentNullClazzGoodPred() {
		FieldUtils.getAllFields((Class<?>)null, f -> true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void expecteIllegalArgumentGoodClazzNullPred() {
		FieldUtils.getAllFields(SomeClass.class, (Predicate<Field>)null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void expecteIllegalArgumentNullObjGoodPred() {
		FieldUtils.getAllFields((SomeClass)null, f -> true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void expecteIllegalArgumentGoodObjNullPred() {
		FieldUtils.getAllFields(new SomeClass(), (Predicate<Field>)null);
	}

	@Test
	public void testGetters() {
		List<String> expectedGetters = Arrays.asList(new String[] { "getA", "getB", "getC", "getD", "isE" });
		List<String> getters = Arrays.stream(SomeClass.class.getFields()).map(f -> FieldUtils.getFieldGetter(f)).collect(Collectors.toList());
		getters.containsAll(expectedGetters);
	}

	@Test
	public void testSetters() {
		List<String> expectedSetters = Arrays.asList(new String[] { "setA", "setB", "setC", "setD", "setE" });
		List<String> setters = Arrays.stream(SomeClass.class.getFields()).map(f -> FieldUtils.getFieldSetter(f)).collect(Collectors.toList());
		setters.containsAll(expectedSetters);
	}
}

class SomeClass {
	private int a;
	private long b;
	private short c;
	private float d;
	private boolean e;
}