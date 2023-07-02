package com.SlidingBlock.PuzzleSolver.api;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="puzzles")
public class Puzzle {
	@Id
	@SequenceGenerator(
        name="puzzleId_sequence",
        sequenceName =  "puzzleId_sequence",
        allocationSize = 1
    )
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "puzzleId_sequence"
	)
	@Column(name="id")
	private Long id;

	@Column(name="initial_grid", length=3000)
	private String initialGrid;

	@Column(name="final_grid", length=3000)
	private String finalGrid;

	@Column(name="moves", length=3000)
	private String moves;
	
	@Column(name="warnings", length=3000)
	private String warnings;
	
	@Column(name="solvable")
	private Boolean solvable;
	
	@Column(name="row_size")
	private Integer rowSize;
	
	@Column(name="column_size")
	private Integer columnSize;
	
	@Transient
	private ArrayList<String> errors;
	
	@Column(name="to_string", length=3000)
	private String toString;

	public Puzzle() {
	}
	
	//invalid puzzle
	public Puzzle(ArrayList<String> errors) {
		this.errors = errors;
	}
	
	public Puzzle(Long id, ArrayList<ArrayList<String>> initialGrid, ArrayList<ArrayList<String>> finalGrid, ArrayList<String> moves,
			ArrayList<String> warnings, Boolean solvable, Integer rowSize, Integer columnSize, String toString) {
		this.id = id;
		this.initialGrid = puzzleToString(initialGrid);
		this.finalGrid = puzzleToString(finalGrid);
		this.warnings = warnings.toString();
		this.solvable = solvable;
		this.rowSize = rowSize;
		this.columnSize = columnSize;
		this.errors = null;
		this.toString = toString;
		
		//moves
		this.moves = moves.toString();
		
	}
	
	
	//no solution constructor
	public Puzzle(ArrayList<ArrayList<String>> initialGrid, ArrayList<String> moves, ArrayList<String> warnings,
			Boolean solvable, Integer rowSize, Integer columnSize, String toString) {
		this.initialGrid = puzzleToString(initialGrid);
		this.warnings = warnings.toString();
		this.solvable = solvable;
		this.rowSize = rowSize;
		this.columnSize = columnSize;
		this.errors = null;
		this.toString = toString;

		
		this.moves = moves.toString();
	}

	public Puzzle(ArrayList<ArrayList<String>> initialGrid, ArrayList<ArrayList<String>> finalGrid, ArrayList<String> moves,
			ArrayList<String> warnings, Boolean solvable, Integer rowSize, Integer columnSize, String toString) {
		this.initialGrid = puzzleToString(initialGrid);
		this.finalGrid = puzzleToString(finalGrid);
		this.warnings = warnings.toString();
		this.solvable = solvable;
		this.rowSize = rowSize;
		this.columnSize = columnSize;
		this.errors = null;
		this.toString = toString;
		
		this.moves = moves.toString();
	}
	
	public Puzzle(PuzzleResponse puzzleR) {
		this.initialGrid = puzzleToString(puzzleR.getInitialGrid());
		this.finalGrid = puzzleToString(puzzleR.getFinalGrid());
		this.warnings = puzzleR.getWarnings().toString();
		this.solvable = puzzleR.getSolvable();
		this.rowSize = puzzleR.getRowSize();
		this.columnSize = puzzleR.getColumnSize();
		this.errors = null;
		this.toString = puzzleR.getToString().toString();
		
		this.moves = puzzleR.getMoves().toString();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInitialGrid() {
		return initialGrid;
	}

	public void setInitialGrid(String initialGrid) {
		this.initialGrid = initialGrid;
	}

	public String getFinalGrid() {
		return finalGrid;
	}

	public void setFinalGrid(String finalGrid) {
		this.finalGrid = finalGrid;
	}

	public String getMoves() {
		return moves;
	}

	public void setMoves(String moves) {
		this.moves = moves;
	}

	public String getWarnings() {
		return warnings;
	}

	public void setWarnings(String warnings) {
		this.warnings = warnings;
	}

	public Boolean isSolvable() {
		return solvable;
	}

	public void setSolvable(boolean solvable) {
		this.solvable = solvable;
	}

	public Integer getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public Integer getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int colSize) {
		this.columnSize = colSize;
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

	@Override
	public String toString() {
		return "Puzzle [id=" + id + ", initialGrid=" + initialGrid + ", finalGrid=" + finalGrid + ", moves=" + moves
				+ ", warnings=" + warnings + ", solvable=" + solvable + ", rowSize=" + rowSize + ", colSize=" + columnSize
				+ "]";
	}
	
	//converts grid to it's string equivalent
    public String puzzleToString(ArrayList<ArrayList<String>> grid){
    	if(grid == null) {return null;}
    	
    	int rowSize = grid.size();
    	int colSize = grid.get(0).size();
       String ans = "";
       String c;
       for(int i = 0; i < rowSize; i++){
         for(int j = 0; j < colSize; j++){
           if(grid.get(i).get(j).equals(".")){c = " ";}
           else  {c = grid.get(i).get(j);}
           ans += c;
         }
       }
       return ans;
     }
	
}
