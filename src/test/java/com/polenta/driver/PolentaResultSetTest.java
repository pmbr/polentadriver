package com.polenta.driver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class PolentaResultSetTest {

	@Test
	public void generalTest1() throws Exception {
		List<String> fields = new LinkedList<String>();
		fields.add("NAME");
		fields.add("AGE");
		
		Map<String, Object> row1 = new HashMap<String, Object>();
		row1.put("NAME", "PETER RICH");
		row1.put("AGE", null);
		
		Map<String, Object> row2 = new HashMap<String, Object>();
		row2.put("NAME", "PAUL POOR");
		row2.put("AGE", "38");
		
		List<Map<String, Object>> rows = new LinkedList<Map<String,Object>>();
		rows.add(row1);
		rows.add(row2);
		
		PolentaResultSet resultSet = new PolentaResultSet(fields, rows);
		
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.fields().contains("NAME"));
		assertTrue(resultSet.fields().contains("AGE"));
		
		assertTrue(resultSet.hasNext());
		assertFalse(resultSet.hasPrevious());
		
		assertEquals("PETER RICH", resultSet.get("NAME"));
		assertEquals(null, resultSet.get("AGE"));
		
		resultSet.next();
		
		assertFalse(resultSet.hasNext());
		assertTrue(resultSet.hasPrevious());
		
		assertEquals("PAUL POOR", resultSet.get("NAME"));
		assertEquals("38", resultSet.get("AGE"));
	}

	@Test
	public void generalTest2() throws Exception {
		
		List<String> fields = new LinkedList<String>();
		fields.add("NAME");

		Map<String, Object> row1 = new HashMap<String, Object>();
		row1.put("NAME", "PETER RICH");

		Map<String, Object> row2 = new HashMap<String, Object>();
		row2.put("NAME", "PAUL POOR");
		
		List<Map<String, Object>> rows = new LinkedList<Map<String,Object>>();
		rows.add(row1);
		rows.add(row2);

		PolentaResultSet resultSet = new PolentaResultSet(fields, rows);
		
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
