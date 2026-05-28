To build, run
./gradlew jar

to run build file, run
java -jar build/libs/kotlin-sudoku-cli-1.0-SNAPSHOT.jar

To build and run, run
./gradlew installDist ./build/install/kotlin-sudoku-cli/bin/kotlin-sudoku-cli

The main class is SudokuGenerator, which will generate a sudoku puzzle, the Board object. The Board object stores the board data ad print.
The Command classes are used to execute the commands. This makes it easy to add new commands if needed.