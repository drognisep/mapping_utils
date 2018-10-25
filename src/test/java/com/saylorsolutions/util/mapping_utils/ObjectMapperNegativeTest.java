package com.saylorsolutions.util.mapping_utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.saylorsolutions.util.mapping_utils.annotation.MappingSourceName;
import com.saylorsolutions.util.mapping_utils.exception.MappingException;

public class ObjectMapperNegativeTest {
	@Test(expected = MappingException.class)
	public void expectMappingExceptionNoConstructor() {
		A a = new A();
		B b = ObjectMapper.createTarget(a, B.class);
	}

	@Test
	public void expectNoExceptionWrongType() {
		C c = new C();
		A a = ObjectMapper.createTarget(c, A.class);
		assertEquals(0, a.getA());
	}

	@Test(expected = IllegalArgumentException.class)
	public void expectIllegalArgumentExceptionNullSource() {
		A a = ObjectMapper.createTarget((B)null, A.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void expectIllegalArgumentExceptionNullClass() {
		A a = ObjectMapper.createTarget(new B(5), (Class<A>)null);
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