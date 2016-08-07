package com.polenta.driver;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;


public class PolentaResultSetTest {
	
	@Ignore
	@Test
	public void generalTest1() throws Exception {
		
		PolentaResultSet resultSet = new PolentaResultSet("NAME,AGE|NAME:PETER RICH,AGE:NULL|NAME:PAUL POOR,AGE:38|");
		
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.fields().contains("NAME"));
		assertTrue(resultSet.fields().contains("AGE"));
		
		assertTrue(resultSet.hasNext());
		assertFalse(resultSet.hasPrevious());
		
		assertEquals("PETER RICH", resultSet.get("NAME"));
		assertEquals("NULL", resultSet.get("AGE"));
		
		resultSet.next();
		
		assertFalse(resultSet.hasNext());
		assertTrue(resultSet.hasPrevious());
		
		assertEquals("PAUL POOR", resultSet.get("NAME"));
		assertEquals("38", resultSet.get("AGE"));
	}

	@Test
	public void generalTest2() throws Exception {
		
		PolentaResultSet resultSet = new PolentaResultSet("NAME|NAME:PETER RICH|NAME:PAUL POOR|");
		
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.fields().contains("NAME"));
		
		assertTrue(resultSet.hasNext());
		assertFalse(resultSet.hasPrevious());
		
		assertEquals("PETER RICH", resultSet.get("NAME"));
		
		resultSet.next();
		
		assertFalse(resultSet.hasNext());
		assertTrue(resultSet.hasPrevious());
		
		assertEquals("PAUL POOR", resultSet.get("NAME"));
	}

}
