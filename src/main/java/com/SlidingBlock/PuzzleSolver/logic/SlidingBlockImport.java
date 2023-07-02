package com.SlidingBlock.PuzzleSolver.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.SlidingBlock.PuzzleSolver.api.PuzzleResponse;

public class SlidingBlockImport {
	
	public PuzzleResponse solvePuzzle(String puzzle) {
		
		//for puzzle Object
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<String> warnings = new ArrayList<String>();
		
		boolean firstLine = false; //used to read size of grid
		boolean secondLine = false;
		String line;
		int colSize = 0, rowSize = 0;
		int blockRow, blockCol;
		int blockWidth, blockHeight;
		String movement;
		int handler;
		
		//used to determine name of pieces
		String numNames[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y"};
		int ind = 0;
		//object used for game
		Solver game = null; 
		 
		Scanner scan = new Scanner(puzzle);
		while (scan.hasNextLine()) {
			  
			 line = scan.nextLine();
			 if(line.isBlank()) {break;}	//change to continue??
			Scanner iss = new Scanner(line);
		    //System.out.println(line);    // this is to verify data input and should not be part of final code
		    
		    if(!firstLine){ //read grid sizes
		      firstLine = true;
		      rowSize =iss.nextInt();
		      colSize = iss.nextInt();
		      //validate size
		      //NEED TO FIX RETURN VALUE
		      if(rowSize <= 0)  {errors.add("Error: Number of rows must be greater than zero"); iss.close();  return new PuzzleResponse(errors);}
		      if(colSize <= 0)  {errors.add("Error: Number of columns must be greater than zero"); iss.close();  return new PuzzleResponse(errors);}
		      game = new Solver(rowSize, colSize);
		    }////////////////////

		    else if(!secondLine){  //read position of goal piece
		      secondLine = true;
		      try {
		      blockRow = iss.nextInt();
		      blockCol = iss.nextInt();
		      blockWidth = iss.nextInt();
		      blockHeight = iss.nextInt();
		      movement = iss.next();
		      }catch(Exception e) {
		    	  iss.close();
		    	  return null;
		      }

		      handler = game.add("Z", blockRow-1, blockCol-1, blockWidth, blockHeight, movement);
		      //if invalid piece, set secondLine to false -- next piece becomes goal piece
		      if(handler == -1)  {
		        warnings.add("Warning: Piece with starting position of " + blockRow + ", " + blockCol + " falls outside of grid"); 
		        secondLine = false;
		      }
		      else if (handler == -2){ //movement not valid
		        warnings.add("Warning: Piece with starting position of " + blockRow + ", " + blockCol + " has invalid movement");
		        secondLine = false;
		      }
		      else if(handler == -3){  //if overlaps with other space
		        warnings.add("Warning: Piece with starting position of " + blockRow + ", " + blockCol + " overlaps with another piece");
		        secondLine = false;
		      }
		    }////////////////
		    else{ //read position of other pieces
			  if(ind >= 60){  //too many pieces
			    warnings.add("Warning: grid has exceeded max number of blocks (60)");
			    continue;
			  }
			 
		      blockRow = iss.nextInt();
		      blockCol = iss.nextInt();
		      blockWidth = iss.nextInt();
		      blockHeight = iss.nextInt();
		      movement = iss.next();
		      handler = game.add(numNames[ind++], blockRow-1, blockCol-1, blockWidth, blockHeight, movement);
		      //if invalid piece, set secondLine to false -- next piece becomes goal piece
		      if(handler == -1)  {
		        warnings.add("Warning: Piece with starting position of " + blockRow + ", " + blockCol + " falls outside of grid"); 
		        --ind;
		      }
		      else if (handler == -2){ //movement not valid
		        warnings.add("Warning: Piece with starting position of " + blockRow + ", " + blockCol + " has invalid movement");
		        --ind;
		      }
		      else if(handler == -3){  //if overlaps with other space
		        warnings.add("Warning: Piece with starting position of " + blockRow + ", " + blockCol + " overlaps with another piece");
		        --ind;
		      }
		    }
		    //close scanner
			iss.close();
		  }
		 //close main scanner
		 scan.close();
		 
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
			// System.out.println("no solution found");
			 return new PuzzleResponse(initialGrid, moves, warnings, false, rowSize, colSize, toString);
		 }
		 //store moves and final grid
		 ArrayList<String> moves = ans.getMoveListAns();
		 moves.add(0, "This puzzle is solvable in " + game.getTotalMoves() + " steps");
		// printGrid(ans.getPuzzleAns());
		// System.out.println("FINAL GRID: ");
		 ArrayList<ArrayList<String>> finalGrid = convertGrid(ans.getPuzzleAns());
		 
		 
		 
		 for(String str : moves) {
			 System.out.println(str);
		 }
		 
		 return new PuzzleResponse(initialGrid, finalGrid, moves, warnings, true, rowSize, colSize, toString);
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
}
