package com.SlidingBlock.PuzzleSolver.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SlidingBlock.PuzzleSolver.logic.SlidingBlockImport;
import com.SlidingBlock.PuzzleSolver.logic.SlidingBlockCreated;



@Service
public class PuzzleService {
	
	private final PuzzleRepository puzzleRepository;

	@Autowired
	public PuzzleService(PuzzleRepository puzzleRepository) {
		this.puzzleRepository = puzzleRepository;
	}
	
//	public Puzzle handlePuzzle(ArrayList<ArrayList<String>> initialGrid) {
//		//check if puzzle has already been solved before hand here, then send that back
//		/***    Cool feature ^ implement later    ***/
//		
//		//empty puzzle
//		if(initialGrid.size() == 0) {return null;}
//		
//		int rowSize = initialGrid.size();
//		int colSize = initialGrid.get(0).size();
//		
//		
//	}
	
	public PuzzleResponse handleCreatedPuzzle(CreatedPuzzleBody puzzleBody) {
		SlidingBlockCreated solver = new SlidingBlockCreated();
		PuzzleResponse puzzleR = solver.solvePuzzle(puzzleBody);
		Optional<Puzzle> puzzleOpt = this.puzzleRepository.findPuzzleByToString(puzzleR.getToString());
		if(puzzleOpt.isEmpty()) {
			Puzzle puzzleToStore = new Puzzle(puzzleR);
			puzzleToStore = this.puzzleRepository.save(puzzleToStore);
			//System.out.println("SAVED");
		}
		//System.out.println("returning");
		return puzzleR;
	}
	
	public PuzzleResponse handleImportedPuzzle(String puzzleBody) {
		SlidingBlockImport solver = new SlidingBlockImport();
		PuzzleResponse puzzleR = solver.solvePuzzle(puzzleBody);
		if(puzzleR == null) {
			return null;
		}
		//if puzzle invalid
		if(puzzleR.getErrors() != null) {
			return puzzleR;
		}
		Optional<Puzzle> puzzleOpt = this.puzzleRepository.findPuzzleByToString(puzzleR.getToString());
		if(puzzleOpt.isEmpty()) {
			Puzzle puzzleToStore = new Puzzle(puzzleR);
			puzzleToStore = this.puzzleRepository.save(puzzleToStore);
			//System.out.println("SAVED");
		}
	//	System.out.println("returning");
		return puzzleR;
	}
	
}
