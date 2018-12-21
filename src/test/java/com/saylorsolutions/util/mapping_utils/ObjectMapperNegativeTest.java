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

import org.junit.jupiter.api.Test;

import com.saylorsolutions.util.mapping_utils.annotation.MappingSourceName;
import com.saylorsolutions.util.mapping_utils.exception.MappingException;

public class ObjectMapperNegativeTest {
	@Test
	public void expectMappingExceptionNoConstructor() {
		assertThrows(MappingException.class, () -> {
			A a = new A();
			B b = ObjectMapper.createTarget(a, B.class);
		});
	}

	@Test
	public void expectNoExceptionWrongType() {
		C c = new C();
		A a = ObjectMapper.createTarget(c, A.class);
		assertEquals(0, a.getA());
	}

	@Test
	public void expectIllegalArgumentExceptionNullSource() {
		assertThrows(IllegalArgumentException.class, () -> {
			A a = ObjectMapper.createTarget((B)null, A.class);
		});
	}

	@Test
	public void expectIllegalArgumentExceptionNullClass() {
		assertThrows(IllegalArgumentException.class, () -> {
			A a = ObjectMapper.createTarget(new B(5), (Class<A>)null);
		});
	}
}

class A {
	@MappingSourceName("badIntValue")
	private int a;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}
}

class B extends A {
	public B(int a) {
		super.setA(a);
	}
}

class C {
	private boolean badIntValue;
}
