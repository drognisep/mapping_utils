package com.saylorsolutions.util.mapping_utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.saylorsolutions.util.mapping_utils.annotation.MappingSourceName;

public class ObjectMapperTest {
	@MappingSourceName("publicStr")
	private String a;
	@MappingSourceName("name")
	private String b;
	@MappingSourceName("boolObj")
	private boolean c;
	@MappingSourceName("boolPrim")
	private boolean d;
	@MappingSourceName("intObj")
	private int e;
	@MappingSourceName("intPrim")
	private int f;

	@Test
	public void testCreateTarget() throws NoSuchFieldException, SecurityException {
		ObjectSourceValues2 source = new ObjectSourceValues2();
		ObjectMapperTest test = ObjectMapper.createTarget(source, ObjectMapperTest.class);

		assertEquals("publicStr", test.getA());
		assertFalse(test.getClass().getDeclaredField("a").isAccessible());
		assertEquals("name", test.getB());
		assertFalse(test.getClass().getDeclaredField("b").isAccessible());
		assertTrue(test.isC());
		assertFalse(test.getClass().getDeclaredField("d").isAccessible());
		assertFalse(test.isD());
		assertFalse(test.getClass().getDeclaredField("d").isAccessible());
		assertEquals(5, test.getE());
		assertSame(5, test.getE());
		assertFalse(test.getClass().getDeclaredField("e").isAccessible());
		assertEquals(10, test.getF());
		assertSame(10, test.getF());
		assertFalse(test.getClass().getDeclaredField("f").isAccessible());
	}

	@Test
	public void testUpdateTargetObjectT() throws NoSuchFieldException, SecurityException {
		ObjectSourceValues2 source = new ObjectSourceValues2();
		ObjectMapperTest test = ObjectMapper.updateTarget(source, new ObjectMapperTest());

		assertEquals("publicStr", test.getA());
		assertFalse(test.getClass().getDeclaredField("a").isAccessible());
		assertEquals("name", test.getB());
		assertFalse(test.getClass().getDeclaredField("b").isAccessible());
		assertTrue(test.isC());
		assertFalse(test.getClass().getDeclaredField("d").isAccessible());
		assertFalse(test.isD());
		assertFalse(test.getClass().getDeclaredField("d").isAccessible());
		assertEquals(5, test.getE());
		assertSame(5, test.getE());
		assertFalse(test.getClass().getDeclaredField("e").isAccessible());
		assertEquals(10, test.getF());
		assertSame(10, test.getF());
		assertFalse(test.getClass().getDeclaredField("f").isAccessible());
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public boolean isC() {
		return c;
	}

	public void setC(boolean c) {
		this.c = c;
	}

	public boolean isD() {
		return d;
	}

	public void setD(boolean d) {
		this.d = d;
	}

	public int getE() {
		return e;
	}

	public void setE(int e) {
		this.e = e;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

}

class ObjectSourceValues2 {
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