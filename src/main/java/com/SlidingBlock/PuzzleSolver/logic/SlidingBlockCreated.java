package com.SlidingBlock.PuzzleSolver.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.SlidingBlock.PuzzleSolver.api.CreatedPuzzleBody;
import com.SlidingBlock.PuzzleSolver.api.PuzzleResponse;

public class SlidingBlockCreated {
	public PuzzleResponse solvePuzzle(CreatedPuzzleBody puzzleBody) {
		
		int rowSize = puzzleBody.getRowSize();
		int colSize = puzzleBody.getColSize();
		String goalBlock = puzzleBody.getGoalBlock();
		Map<String, String> movementMap = puzzleBody.getMovementMap();
		Solver game = new Solver(rowSize, colSize);
		
		ArrayList<ArrayList<String>> grid = puzzleBody.getGrid();
		Set<String> pieces = new HashSet<String>();
		pieces.add("0");
		
		int size = grid.size();
		for(int i = 0; i < size; ++i) {
			int arrSize = grid.get(i).size();
			for(int j = 0; j < arrSize; ++j) {
				String piece = grid.get(i).get(j);
				//System.out.println("Piece: " + piece);
				
				if(!pieces.contains(piece)) {
					pieces.add(piece);
					int height = 0;
					int width = 0;
					int row = i;
					int col = j;
					//get piece height
					for(int k = i; k < size; ++k) {
						if(grid.get(k).get(j).equals(piece))
							++height;
						else
							break;
					}
					//get piece width
					for(int k = j; k < arrSize; ++k) {
						if(grid.get(i).get(k).equals(piece))
							++width;
						else
							break;
					}
					String movement = movementMap.get(piece);
					if(piece.equals(goalBlock)) {piece= "Z"; System.out.println("goalblock: " +goalBlock);}
					
				//	System.out.println("Piece height: " + height + " width: " + width + " movement: " + movement);
					game.add(piece, row, col, width, height, movement);
				}
			}
		}//end of for loops
		
		//initialGrid 
		 ArrayList<ArrayList<String>> initialGrid = convertGrid(game.getGrid());
		// printGrid(game.getGrid());
		 
		//get initialGrid toString
		 String toString = toString(game.getGrid());
		 
		//solve the puzzle
		 PuzzleAns ans = game.solvePuzzle();
		 
		 
		 if(ans == null) {//if no solution
			 ArrayList<String> moves = new ArrayList<String>();
			 moves.add("This puzzle has no solution");
			 System.out.println("no solution found");
			 return new PuzzleResponse(initialGrid, moves, new ArrayList<String>(), false, rowSize, colSize, toString);
		 }
		 
		//store moves and final grid
		 ArrayList<String> moves = ans.getMoveListAns();
		 moves.add(0, "This puzzle is solvable in " + game.getTotalMoves() + " steps");
		 ArrayList<ArrayList<String>> finalGrid = convertGrid(ans.getPuzzleAns());
		// System.out.println("FINAL GRID: ");
		// printGrid(game.getGrid());
		 
			/*
			 * for(String str : moves) { System.out.println(str); }
			 */
		 
		 return new PuzzleResponse(initialGrid, finalGrid, moves, new ArrayList<String>(), true, rowSize, colSize, toString);
	}
	
	
	// used to help debug, prints grid out
    void printGrid(ArrayList<ArrayList<Piece>> g)
    {
    	int rowSize = g.size();
    	int colSize = g.get(0).size();

      for (int i = 0; i < rowSize; i++)
      {
        for (int j = 0; j < colSize; j++)
        {
          if (g.get(i).get(j) == null)
          {
          	System.out.print(".");
          }
          else
          {
          	System.out.print(g.get(i).get(j).getName());
          }
        }
        System.out.println();
      }
    }
    
    public ArrayList<ArrayList<String>> convertGrid(ArrayList<ArrayList<Piece>> grid){
		ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
		int rSize = grid.size();
		int cSize = grid.get(0).size();
		String name;
		for(int i = 0; i < rSize; ++i) {
			ret.add(new ArrayList<String>());
			for(int j = 0; j < cSize; ++j) {
				if (grid.get(i).get(j) == null) {name =".";}
				
				else		{name = grid.get(i).get(j).getName();}
				
				ret.get(i).add(name);
			}
		}
		return ret;
	}
  //converts grid to it's string equivalent
    public String toString(ArrayList<ArrayList<Piece>> grid){
       int rowSize = grid.size();
       int colSize= grid.get(0).size();
       String ans = "";
       String c;
       for(int i = 0; i < rowSize; i++){
         for(int j = 0; j < colSize; j++){
           if(grid.get(i).get(j) == null){c = " ";}
           else  {c = grid.get(i).get(j).getName();}
           ans += c;
         }
       }
       return ans;
     }
}
