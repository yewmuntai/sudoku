package com.yewmun.sudoku.command

import com.yewmun.sudoku.BoardData
import com.yewmun.sudoku.CommandExecutor
import com.yewmun.sudoku.ROW_LABELS

open class CheckCommandExecutor: CommandExecutor {
    override fun execute(parts: List<String>, board: BoardData): String? {
        board.answers.keys.forEach { cell ->
            val boardValue = board.values[cell.first][cell.second]
            if (boardValue != 0 && boardValue != board.answers[cell]) {
                val row = ROW_LABELS.getOrNull(cell.first) // will never be null. NPE at line 11 will happen 1st.
                println("Cell $row ${cell.second + 1} is incorrect.")
                return null
            }
        }
        return "All answered cells are correct."
    }

    override fun getCommand() = "check"
}