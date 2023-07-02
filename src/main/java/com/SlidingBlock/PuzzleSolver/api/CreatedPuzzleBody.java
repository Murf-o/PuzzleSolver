package com.SlidingBlock.PuzzleSolver.api;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CreatedPuzzleBody {
	private Integer rowSize;
	
	private Integer colSize;
	
	private ArrayList<ArrayList<String>> grid;
	
	private String goalBlock; 
	
	private Map<String, String> movementMap;
	
	public CreatedPuzzleBody() {}

	public CreatedPuzzleBody(Integer rowSize, Integer colSize, ArrayList<ArrayList<String>> grid, String goalBlock, Map<String, String> movementMap) {
		this.rowSize = rowSize;
		this.colSize = colSize;
		this.grid = grid;
		this.goalBlock = goalBlock;
		this.movementMap = movementMap;
	}

	public Integer getRowSize() {
		return rowSize;
	}

	public void setRowSize(Integer rowSize) {
		this.rowSize = rowSize;
	}

	public Integer getColSize() {
		return colSize;
	}

	public void setColSize(Integer colSize) {
		this.colSize = colSize;
	}

	public ArrayList<ArrayList<String>> getGrid() {
		return grid;
	}

	public void setGrid(ArrayList<ArrayList<String>> grid) {
		this.grid = grid;
	}
	

	public String getGoalBlock() {
		return goalBlock;
	}

	public void setGoalBlock(String goalBlock) {
		this.goalBlock = goalBlock;
	}

	public Map<String, String> getMovementMap() {
		return movementMap;
	}

	public void setMovementMap(Map<String, String> movementMap) {
		this.movementMap = movementMap;
	}

	@Override
	public String toString() {
		return "CreatedPuzzleBody [rowSize=" + rowSize + ", colSize=" + colSize + ", grid=" + grid + ", goalBlock="
				+ goalBlock + ", movementMap=" + movementMap + "]";
	}
	
	

	
	
}
