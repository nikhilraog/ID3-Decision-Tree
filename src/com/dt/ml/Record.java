package com.dt.ml;

import java.util.Arrays;

public class Record {
	
	public String[]  record_row;
	
	public String class_label;
	
	public Record(int num_features){
		
		record_row = new String[num_features];
	}
	
	public String[] getRecord_row() {
		return record_row;
	}
	public void setRecord_row(String[] record_row) {
		this.record_row = record_row;
	}
	public String getClass_label() {
		return class_label;
	}
	public void setClass_label(String class_label) {
		this.class_label = class_label;
	}
	
	
	@Override
	public String toString() {
		return "Record [record_row=" + Arrays.toString(record_row)
				+ ", class_label=" + class_label + "]";
	}
	
	
	
}
