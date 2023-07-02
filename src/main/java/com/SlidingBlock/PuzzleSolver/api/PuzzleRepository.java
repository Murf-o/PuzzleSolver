package com.SlidingBlock.PuzzleSolver.api;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle, Long>{
	
	Optional<Puzzle> findPuzzleByToString(String toString);
}
