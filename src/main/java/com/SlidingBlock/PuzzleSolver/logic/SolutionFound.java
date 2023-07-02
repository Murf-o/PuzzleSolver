package com.SlidingBlock.PuzzleSolver.logic;


public class SolutionFound {
	private boolean solutionFound;
	
	public SolutionFound() {
		this.solutionFound = false;
	}
	
	public void solutionFound() {
		this.solutionFound = true;
	}
	
	public boolean getSolutionFound() {
		return this.solutionFound;
	}
}
