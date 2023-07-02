package com.SlidingBlock.PuzzleSolver.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;  

public class slidingBlock {

	public static void main(String[] args) {
		
		 if ( args.length != 2 ) { // argc should be 2 for correct execution
			 					// We print argv[0] assuming it is the program name
		    System.out.println("need two arguments for program to run properly");
		    return;
		  }
			 
		  // We assume argv[1] is a filename to open
	 	  File file = new File(args[1]);
	 	  
	 	 // Always check to see if file opening succeeded
		  if ( !file.exists() ) {
		    System.out.println("Could not open file: " + args[1]);
		    return;
		  }
	 	  
	 	  Scanner the_file;
		try {
			the_file = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not open file: " + args[1]);
		    return;
		}
	 	 

		  System.out.println("Welcome to Sliding Block Puzzle");
		  System.out.println("Using data from puzzle: " + args[1]);
		  
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
		  

		  // read in input file and set up initial puzzle configuration
		  while (the_file.hasNextLine()) {
			  
			 line = the_file.nextLine();
			  
			Scanner iss = new Scanner(line);
		    //iStringstream iss(line);
		    System.out.println(line);    // this is to verify data input and should not be part of final code
		    
		    if(!firstLine){ //read grid sizes
		      firstLine = true;
		      rowSize =iss.nextInt();
		      colSize = iss.nextInt();
		      //validate size
		      if(rowSize <= 0)  {System.out.println("Error: Number of rows must be greater than zero"); iss.close();  return;}
		      if(colSize <= 0)  {System.out.println("Error: Number of columns must be greater than zero"); iss.close();  return;}
		      game = new Solver(rowSize, colSize);
		    }////////////////////

		    else if(!secondLine){  //read position of goal piece
		      secondLine = true;
		      blockRow = iss.nextInt();
		      blockCol = iss.nextInt();
		      blockWidth = iss.nextInt();
		      blockHeight = iss.nextInt();
		      movement = iss.next();

		      handler = game.add("Z", blockRow-1, blockCol-1, blockWidth, blockHeight, movement);
		      //if invalid piece, set secondLine to false -- next piece becomes goal piece
		      if(handler == -1)  {
		        System.out.println("Warning: Piece with starting position of " + blockRow + ", " + blockCol + " falls outside of grid"); 
		        secondLine = false;
		      }
		      else if (handler == -2){ //movement not valid
		        System.out.println("Warning: Piece with starting position of " + blockRow + ", " + blockCol + " has invalid movement");
		        secondLine = false;
		      }
		      else if(handler == -3){  //if overlaps with other space
		        System.out.println("Warning: Piece with starting position of " + blockRow + ", " + blockCol + " overlaps with another piece");
		        secondLine = false;
		      }
		    }////////////////
		    else{ //read position of other pieces
			  if(ind >= 60){  //too many pieces
			    System.out.println("Warning: grid has exceeded max number of blocks (60)");
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
		        System.out.println("Warning: Piece with starting position of " + blockRow + ", " + blockCol + " falls outside of grid"); 
		        --ind;
		      }
		      else if (handler == -2){ //movement not valid
		        System.out.println("Warning: Piece with starting position of " + blockRow + ", " + blockCol + " has invalid movement" );
		        --ind;
		      }
		      else if(handler == -3){  //if overlaps with other space
		        System.out.println("Warning: Piece with starting position of " + blockRow + ", " + blockCol + " overlaps with another piece" );
		        --ind;
		      }
		    }
		    //close scanner
			iss.close();
		  }
		  
		  

		  //  print out initial puzzle configuration
		  System.out.println("\nInitial puzzle configuration:\n");
		  for(int i = 0; i < colSize + 2; i++){
		    System.out.print("*");
		  }
		  System.out.println();
		  for(int i = 0; i < rowSize; i++){
		    System.out.print("*");
		    for(int j = 0; j < colSize; j++){
		      if(game.getGrid().get(i).get(j) == null){System.out.print(".");}
		      else{System.out.print(game.getGrid().get(i).get(j).getName());}
		    }
		    System.out.println("*");
		  }

		  for(int i = 0; i < colSize + 2; i++){
		    System.out.print("*");
		  }
		  System.out.println();
		  
		  //  find solution if one exists
		  ArrayList<String> moves;
		  ArrayList<ArrayList<Piece>> grid;
		  PuzzleAns ans = game.solvePuzzle();
		  //if no solution
		  if(ans == null) {
			  System.out.println("This puzzle has no solution"); 
			  the_file.close(); 
			  return;
		  }
		  grid = ans.getPuzzleAns();
		  moves = ans.getMoveListAns();
			  
		  if(grid.isEmpty())  {}
		  
		  System.out.println("This puzzle is solvable in " + game.getTotalMoves() + " steps");
		  for(String str: moves){
		    System.out.println(str);
		  }

		  System.out.println("Final puzzle configuration: ");
		  for(int i = 0; i < colSize + 2; i++){
		    System.out.print("*");
		  }
		  System.out.println();
		  for(int i = 0; i < rowSize; i++){
		    System.out.print("*");
		    for(int j = 0; j < colSize; j++){
		      if(grid.get(i).get(j) == null){System.out.print(".");}
		      else{System.out.print(grid.get(i).get(j).getName());}
		    }
		    System.out.println("*");
		  }
		  for(int i = 0; i < colSize + 2; i++){
		    System.out.print("*");
		  }
		  System.out.println();
		  //close folder
		  the_file.close();
		  return;
		}

}
