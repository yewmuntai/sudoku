To build, run
./gradlew jar

the build jar is also in this repo for convenience.

to run built app, run
java -jar build/libs/kotlin-sudoku-cli-1.0-SNAPSHOT.jar
you can have a optional parameter to specify the difficulty of the puzzle. It is the number of cells to remove from the board. If there are no parameters, the default is 20.

To build and run, run
./gradlew installDist ./build/install/kotlin-sudoku-cli/bin/kotlin-sudoku-cli

The main class Main, which will use SudokuGenerator to generate a sudoku puzzle, the Board object. The Board object stores the board data and print.
The Command classes are used to execute the commands. This makes it easy to add new commands if needed.