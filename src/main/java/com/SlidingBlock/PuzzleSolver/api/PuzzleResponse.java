package com.SlidingBlock.PuzzleSolver.api;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;

//what we send back to the user

@Component
public class PuzzleResponse {

	private ArrayList<ArrayList<String>> initialGrid;

	private ArrayList<ArrayList<String>> finalGrid;

	private ArrayList<String> moves;
	
	private ArrayList<String> warnings;
	
	private Boolean solvable;
	
	private Integer rowSize;
	
	private Integer columnSize;
	
	private ArrayList<String> errors;
	
	private String toString;
	
	public PuzzleResponse() {}
	
	//no solution constructor
		public PuzzleResponse(ArrayList<ArrayList<String>> initialGrid, ArrayList<String> moves, ArrayList<String> warnings,
				Boolean solvable, Integer rowSize, Integer columnSize, String toString) {
			this.initialGrid = initialGrid;
			this.warnings = warnings;
			this.solvable = solvable;
			this.rowSize = rowSize;
			this.errors = null;
			this.columnSize = columnSize;
			this.toString = toString;
			this.moves = moves;
		}

	public PuzzleResponse(ArrayList<ArrayList<String>> initialGrid, ArrayList<ArrayList<String>> finalGrid,
			ArrayList<String> moves, ArrayList<String> warnings, Boolean solvable, Integer rowSize, Integer columnSize,
			String toString) {
		
		this.initialGrid = initialGrid;
		this.finalGrid = finalGrid;
		this.moves = moves;
		this.warnings = warnings;
		this.solvable = solvable;
		this.rowSize = rowSize;
		this.columnSize = columnSize;
		this.errors = null;
		this.toString = toString;
	}
	public PuzzleResponse(ArrayList<String> errors) {
		this.errors = errors;
	}

	public ArrayList<ArrayList<String>> getInitialGrid() {
		return initialGrid;
	}

	public void setInitialGrid(ArrayList<ArrayList<String>> initialGrid) {
		this.initialGrid = initialGrid;
	}

	public ArrayList<ArrayList<String>> getFinalGrid() {
		return finalGrid;
	}

	public void setFinalGrid(ArrayList<ArrayList<String>> finalGrid) {
		this.finalGrid = finalGrid;
	}

	public ArrayList<String> getMoves() {
		return moves;
	}

	public void setMoves(ArrayList<String> moves) {
		this.moves = moves;
	}

	public ArrayList<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(ArrayList<String> warnings) {
		this.warnings = warnings;
	}

	public Boolean getSolvable() {
		return solvable;
	}

	public void setSolvable(Boolean solvable) {
		this.solvable = solvable;
	}

	public Integer getRowSize() {
		return rowSize;
	}

	public void setRowSize(Integer rowSize) {
		this.rowSize = rowSize;
	}

	public Integer getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(Integer columnSize) {
		this.columnSize = columnSize;
	}

	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}

	public String getToString() {
		return toString;
	}

	public void setToString(String toString) {
		this.toString = toString;
	}

	
		
		
}
