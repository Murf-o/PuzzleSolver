package com.SlidingBlock.PuzzleSolver.api;

import org.springframework.stereotype.Component;

@Component
public class ImportBody {
	
	private String data;
	
	public ImportBody() {}

	public ImportBody(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ImportBody [data=" + data + "]";
	}
	
	
}
