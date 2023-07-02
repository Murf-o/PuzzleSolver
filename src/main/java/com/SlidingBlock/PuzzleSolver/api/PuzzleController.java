package com.SlidingBlock.PuzzleSolver.api;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="api/murf/puzzlesolver")
public class PuzzleController {
	
	private final PuzzleService puzzleService;

	@Autowired
	public PuzzleController(PuzzleService puzzleService) {
		this.puzzleService = puzzleService;
	}
	
//	@PostMapping
//	public Puzzle handlePuzzle(@RequestBody Puzzle p) {
//		return this.puzzleService.handlePuzzle(p.getInitialGrid());
//	}
	
	//receive 2D array along with row size and col size
	@PostMapping("createdPuzzle")
	public PuzzleResponse handleCreatedPuzzle(@RequestBody CreatedPuzzleBody puzzleBody) {
		return this.puzzleService.handleCreatedPuzzle(puzzleBody);
	}
	
	//receive raw text file for puzzle -- not in array style
	@PostMapping("import")
	public PuzzleResponse handleImportPuzzle(@RequestBody ImportBody puzzleBody) {
		
		return this.puzzleService.handleImportedPuzzle(puzzleBody.getData());
	}
	
}
