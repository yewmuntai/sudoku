package com.yewmun.sudoku.command

import com.yewmun.sudoku.BoardData
import com.yewmun.sudoku.CommandExecutor
import com.yewmun.sudoku.ExitException

open class QuitCommandExecutor: CommandExecutor {
    override fun execute(parts: List<String>, board: BoardData): String? {
        throw ExitException()
    }

    override fun getCommand(): String = "quit"
}