package com.yewmun.sudoku.command

import com.yewmun.sudoku.Board
import com.yewmun.sudoku.Command

class CheckCommand: Command {
    override fun execute(parts: List<String>, board: Board): String? {
        board.answers.keys.forEach { cell ->
            val boardValue = board.values[cell.first][cell.second]
            if (boardValue != 0 && boardValue != board.answers[cell]) {
                val row = Board.ROW_LABELS.getOrNull(cell.first)?:let {
                    println("Hint error.")
                    return null
                }
                println("Cell $row ${cell.second + 1} is incorrect.")
                return null
            }
        }
        return "All answered cells are correct."
    }
}