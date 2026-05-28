import com.yewmun.sudoku.Board
import com.yewmun.sudoku.SudokuGenerator
fun main(args: Array<String>) {
    val removeCells = try {
        args[0].toInt()
    } catch (e: Exception) {
        20
    }
    val board = SudokuGenerator(9).generate(removeCells)
    //val board = Board.generate() // hard coded board

    println("Welcome to Kotlin Sudoku!")
    println("Commands:")
    println("  set row col value  - Example: set A 3 9")
    println("  clear row col      - Example: clear A 3")
    println("  hint               - Example: hint")
    println("  check              - Example: check")
    println("  quit               - Exit the game")
    println()

    while (true) {
        board.draw()

        if (board.isComplete()) {
            println("Congratulations! You completed the board.")
            break
        }

        print("Enter command: ")
        val input = readlnOrNull()?.trim() ?: break

        if (input.equals("quit", ignoreCase = true)) {
            println("Goodbye!")
            break
        }

        board.handleCommand(input)
        println()
    }
}

