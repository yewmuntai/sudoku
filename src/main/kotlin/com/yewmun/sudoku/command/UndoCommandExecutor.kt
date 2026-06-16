package com.yewmun.sudoku.command

import com.yewmun.sudoku.BoardData
import com.yewmun.sudoku.CommandExecutor

open class UndoCommandExecutor: CommandExecutor {
    override fun execute(
        parts: List<String>,
        board: BoardData
    ): String? {
        if (board.moveHistory.isEmpty()) {
            println("No moves to undo.")
            return null
        }

        val lastMove = board.moveHistory.removeLast()

        board.values[lastMove.row][lastMove.col] = lastMove.previousValue

        return "Undo done."
    }

    override fun getCommand() = "undo"
}
