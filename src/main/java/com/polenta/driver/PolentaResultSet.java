package com.polenta.driver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PolentaResultSet {

	private int current;
	private List<Map<String, Object>> rows;
	private List<String> fields;
	
	protected PolentaResultSet(String serverResponse) throws PolentaException {
		this.rows = new ArrayList<Map<String,Object>>();
		this.fields = new ArrayList<String>();
		current = 0;
		
		if (!serverResponse.equalsIgnoreCase("EMPTY_RESULT_SET")) {
			String[] lines = serverResponse.split("\\|");
			processFields(lines[0]);
			for (int i = 1; i < lines.length; i++) {
				processRow(lines[i]);
			}
		}
		
	}
	
	protected void processFields(String line) throws PolentaException {
		for (String field: line.split(",")) {
			this.fields.add(field);
		}
	}

	protected void processRow(String line) throws PolentaException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (String group: line.split(",")) {
			String[] subgroup = group.split(":");
			map.put(subgroup[0], subgroup[1]);
		}
		rows.add(map);
	}
	
	protected List<String> fields() {
		return this.fields;
	}

	public int size() throws PolentaException {
		checkIfInitialized();
		return this.rows.size();
	}
	
	public void first() throws PolentaException {
		checkIfInitialized();
		checkIfEmpty();
		this.current = 0;
	}

	public void last() throws PolentaException {
		checkIfInitialized();
		checkIfEmpty();
		this.current = this.rows.size() - 1;
	}

	public void next() throws PolentaException {
		checkIfInitialized();
		checkIfEmpty();
		if (this.current == (this.rows.size() -1)) {
			throw new PolentaException("ResultSet has reached last row.");
		}
		this.current++;
	}
	
	public void previous() throws PolentaException {
		checkIfInitialized();
		checkIfEmpty();
		if (this.current == 0) {
			throw new PolentaException("ResultSet has reached first row.");
		}
		this.current--;
	}
	
	public boolean hasNext() throws PolentaException {
		checkIfInitialized();
		checkIfEmpty();
		return (current != (this.rows.size() - 1));
	}
	
	public boolean hasPrevious() throws PolentaException {
		checkIfInitialized();
		checkIfEmpty();
		return (current != 0);
	}
	
	public boolean isEmpty() throws PolentaException {
		checkIfInitialized();
		return this.rows.size() == 0;
	}

	private void checkIfInitialized() throws PolentaException {
		if (this.rows == null) {
			throw new PolentaException("ResultSet not initialized.");
		}
	}
	
	private void checkIfEmpty() throws PolentaException {
		if (this.rows.size() == 0) {
			throw new PolentaException("ResultSet is empty.");
		}
	}
	
	public Object get(String field) throws PolentaException {
		return this.currentRow().get(field);
	}
	
	protected Map<String, Object> currentRow() throws PolentaException {
		checkIfInitialized();
		checkIfEmpty();
		return this.rows.get(this.current);
	}

	public String toString() {
		
		try {
			if (this.rows == null || this.fields == null || this.fields.isEmpty()) {
				return "";
			}
			StringBuilder sb = new StringBuilder("");

			sb.append("|=====|");
			for (int i = 0; i < this.fields.size(); i++) {
				sb.append("======================|");
			}
			sb.append("\n");

			sb.append("|   # |");
			for (String field: this.fields) {
				sb.append(" " + pad(field) + " |");
			}
			sb.append("\n");
			
			sb.append("|=====|");
			for (int i = 0; i < this.fields.size(); i++) {
				sb.append("======================|");
			}
			sb.append("\n");
			
			int c = 0;
			for (Map<String, Object> map: rows) {
				sb.append("| " + padIndex(++c) + " |");
				for (String field: this.fields) {
					sb.append(" " + pad(map.get(field).toString()) + " |");
				}
				sb.append("\n");
			}

			sb.append("|=====|");
			for (int i = 0; i < fields.size(); i++) {
				sb.append("======================|");
			}
			sb.append("\n");
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "xxx";
	}
	
	protected String pad(String input) {
		if (input.length() >= 20) {
			return input;
		}
		return String.format("%-20s", input);
	}
	
	protected String padIndex(int index) {
		return String.format("%3d", index);
	}

}
