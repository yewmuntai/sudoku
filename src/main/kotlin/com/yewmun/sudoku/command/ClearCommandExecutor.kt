package com.yewmun.sudoku.command

import com.yewmun.sudoku.BoardData
import com.yewmun.sudoku.CommandExecutor
import com.yewmun.sudoku.MoveData
import com.yewmun.sudoku.ROW_LABELS

open class ClearCommandExecutor: CommandExecutor {
    override fun execute (
        parts: List<String>,
        board: BoardData
    ): String? {
        val clear = parseClear(parts) ?: return null

        val rowIndex = clear.row
        val colIndex = clear.col

        if (!board.isAnswerCell(rowIndex, colIndex)) {
            println("You cannot clear a fixed puzzle cell.")
            return null
        }

        board.moveHistory.add(MoveData(rowIndex, colIndex, board.values[rowIndex][colIndex]))
        board.values[rowIndex][colIndex] = 0
        return "Cell ${parts[1]} ${parts[2]} cleared."
    }

    private fun parseClear(parts: List<String>): ClearData? {
        if (parts.size != 3) {
            println("Invalid command. Usage: clear row col")
            return null
        }

        val row = parts[1]
        val col = parts[2].toIntOrNull()

        if (row.length != 1 || col == null) {
            println("Row must be alphabet, column must be numbers.")
            return null
        }

        if (ROW_LABELS.indexOf(row) < 0 || col !in 1..9) {
            println("Row must be A-J skip I, column must be numbers from 1 to 9.")
            return null
        }

        return ClearData(
            row = ROW_LABELS.indexOf(row),
            col = col - 1
        )
    }

    override fun getCommand() = "clear"
}

data class ClearData (
    val row: Int,
    val col: Int,
)
