import com.yewmun.sudoku.ExitException
import com.yewmun.sudoku.SudokuController

fun main(args: Array<String>) {
    var removeCellIdx = 0
    val isTest = if (args.size > 0 && args[0] == "test") {
        removeCellIdx++
        true
    } else {
        false
    }

    val removeCells = try {
        args[removeCellIdx].toInt()
    } catch (e: Exception) {
        20
    }
    val controller = SudokuController(9)

    val board = if (isTest) {
        SudokuController.makeBoard()
    } else {
        controller.generate(removeCells)
    }

    println("Welcome to Kotlin Sudoku!")
    println("Commands:")
    println("  set row col value  - Example: set A 3 9")
    println("  clear row col      - Example: clear A 3")
    println("  hint               - Example: hint")
    println("  check              - Example: check")
    println("  quit               - Exit the game")
    println()

    while (true) {
        controller.draw(board)

        if (controller.isComplete(board)) {
            println("Congratulations! You completed the board.")
            break
        }

        print("Enter command: ")
        val input = readlnOrNull()?.trim() ?: break

        try {
            controller.handleCommand(input, board)
        }catch (exit: ExitException) {
            println("Goodbye!")
            break
        }catch (e: Exception) {
            println("Error has occurred. Try again.")
            // log exception
        }
        println()
    }
}

