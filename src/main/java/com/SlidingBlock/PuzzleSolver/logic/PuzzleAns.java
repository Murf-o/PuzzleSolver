package com.SlidingBlock.PuzzleSolver.logic;

import java.util.ArrayList;

public class PuzzleAns {
	private ArrayList<String> moveListAns;
	private ArrayList<ArrayList<Piece>> puzzleAns;
	
	public PuzzleAns(ArrayList<ArrayList<Piece>> puzzleAns, ArrayList<String> moveListAns) {
		this.moveListAns = moveListAns;
		this.puzzleAns = puzzleAns;
	}
	
	public ArrayList<String> getMoveListAns() {
		return this.moveListAns;
	}
	
	public ArrayList<ArrayList<Piece>> getPuzzleAns() {
		return this.puzzleAns;
	}
	
}
