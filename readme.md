This application runs on java 21. Though it's supposed to work on all os with jvm installed, it's only been tested on Mac.

To build, run
**./gradlew jar**

the build jar is also in this repo for convenience.

To run built app, run  
**java -jar build/libs/kotlin-sudoku-cli-1.0.jar**

you can have a optional parameter to specify the difficulty of the puzzle. It is the number of cells to remove from the board. If there are no parameters, the default is 20.

This jar file is also included in this repository for convenience.

To build and run, run  
**./gradlew installDist ./build/install/kotlin-sudoku-cli/bin/kotlin-sudoku-cli**

The main class Main, which will use SudokuController to generate a sudoku puzzle, and control the actions done on the board. The BoardData object stores the board data.
The Command classes are used to execute the commands. This makes it easy to add new commands if needed.

To run tests and code coverage run   
**./gradlew test**
There is 1 integration test and some unit tests. The tests are written in Groovy using SPOCK framework.

To see a code coverage report, open **build/reports/jacoco/test/html/index.html** in a browser.
The most recent report is added to this repository for convenience.