package com.yewmun.sudoku

import com.yewmun.sudoku.command.CheckCommandExecutor
import com.yewmun.sudoku.command.ClearCommandExecutor
import com.yewmun.sudoku.command.HintCommandExecutor
import com.yewmun.sudoku.command.QuitCommandExecutor
import com.yewmun.sudoku.command.SetCommandExecutor
import com.yewmun.sudoku.command.UndoCommandExecutor

interface CommandExecutor {
    fun execute(parts: List<String>, board: BoardData): String?
    fun getCommand(): String
}

open class CommandControl {
    private val commandMap = mutableMapOf<String, CommandExecutor>()
    init {
        add(SetCommandExecutor())
        add(ClearCommandExecutor())
        add(CheckCommandExecutor())
        add(HintCommandExecutor())
        add(QuitCommandExecutor())
        add(UndoCommandExecutor())
    }

    private fun add(command: CommandExecutor) {
        commandMap[command.getCommand().uppercase()] = command
    }

    open fun execute(parts: List<String>, board: BoardData): String? {
        val commandName = parts.firstOrNull() ?: return null
        val command = commandMap[commandName] ?: return null
        return command.execute(parts, board)
    }
}