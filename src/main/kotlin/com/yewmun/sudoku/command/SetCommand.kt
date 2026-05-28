package com.yewmun.sudoku.command

import com.yewmun.sudoku.Board
import com.yewmun.sudoku.Command

class SetCommand: Command {
    override fun execute(
        parts: List<String>,
        board: Board
    ): String? {
        val move = parseMove(parts) ?: return null

        if (board.fixedValues[move.row][move.col]) {
            println("You cannot change a fixed puzzle cell.")
            return null
        }

        val previousValue = board.values[move.row][move.col]
        board.values[move.row][move.col] = 0
        val isValid = when {
            !isValidMove(move.row, move.col, board) -> {
                println("Invalid move. That cell is a fixed value.")
                false
            }
            !isValidInRow(move.row, move.value, board) -> {
                println("Invalid move. That value already exists in the row.")
                false
            }
            !isValidInColumn(move.col, move.value, board) -> {
                println("Invalid move. That value already exists in the column.")
                false
            }
            !isValidInBox(move.row, move.col, move.value, board) -> {
                println("Invalid move. That value already exists in the 3x3 box.")
                false
            }
            else -> {
                true
            }
        }
        if (!isValid) {
            board.values[move.row][move.col] = previousValue
            return null
        }

        board.values[move.row][move.col] = move.value
        return "Move accepted."
    }

    private fun parseMove(parts: List<String>): Move? {
        if (parts.size != 4) {
            println("Invalid command. Usage: set row col value")
            return null
        }

        val row = parts[1]
        val col = parts[2].toIntOrNull()
        val value = parts[3].toIntOrNull()

        if (row.length != 1 || col == null || value == null) {
            println("Row must be alphabet, column and value must be numbers.")
            return null
        }

        if (Board.ROW_LABELS.indexOf(row) < 0 || col !in 1..9 || value !in 1..9) {
            println("Row must be A-J skip I, column and value must be numbers from 1 to 9.")
            return null
        }

        return Move(
            row = Board.ROW_LABELS.indexOf(row),
            col = col - 1,
            value = value
        )
    }

    private fun isValidMove(
        row: Int,
        col: Int,
        board: Board
    ): Boolean {
        return !board.fixedValues[row][col]
    }

    private fun isValidInRow(
        row: Int,
        value: Int,
        board: Board
    ) = board.values[row].none { it == value }

    private fun isValidInColumn(
        col: Int,
        value: Int,
        board: Board
    ) = board.values.none { it[col] == value }

    private fun isValidInBox(
        row: Int,
        col: Int,
        value: Int,
        board: Board
    ): Boolean {
        val boxStartRow = row / 3 * 3
        val boxStartCol = col / 3 * 3

        for (r in boxStartRow until boxStartRow + 3) {
            for (c in boxStartCol until boxStartCol + 3) {
                if (board.values[r][c] == value) {
                    return false
                }
            }
        }

        return true
    }
}

data class Move(
    val row: Int,
    val col: Int,
    val value: Int
)
