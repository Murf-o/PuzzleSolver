# PuzzleSolver
Maven Project, React JS, and Java Spring Boot.

This program is able to solve your sliding block puzzle, in the fastest way possible!

Algorithm used to achieve this: Breadth-First-Search.

A user is able to create a puzzle using the front-end or is able to import their own. The rules on how to play are displayed on the front-end The back-end serves requests from the front-end.

Client folder contains code for the front-end, created using React JS. To run it, run "npm start" in a terminal inside the my-app folder.

The src folder contains all java related things, such as the spring-boot application and the logic of the game. To run the server, you can simply run PuzzleSolverApplication.java or you can convert it to a jar file using maven commands and then run the jar file.

using maven commands:

mvn package
java -jar target\PuzzleSolver-0.0.1-SNAPSHOT.jar
Important Note: The server saves data to a MySQL database. To connect to your table, go to the resources folder and into the application.properties file-- insert your username and password, make necessary changes (port etc) There is one table named "puzzles" with columns as follow: -Id: bigint -initial_grid: varchar(3000) -final_grid: varchar(3000) -moves: varchar(3000) -warnings: varchar(3000) -solvable: bit(1) -row_size: int -column_size: int -to_string: varchar(3000)

You don't need the database to run the server, so you are free to remove it. Thank you!

Author: Sebastian Barroso
