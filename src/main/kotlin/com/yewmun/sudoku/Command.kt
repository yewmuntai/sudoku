package com.yewmun.sudoku

import com.yewmun.sudoku.command.CheckCommand
import com.yewmun.sudoku.command.ClearCommand
import com.yewmun.sudoku.command.HintCommand
import com.yewmun.sudoku.command.SetCommand

interface Command {
    fun execute(parts: List<String>, board: Board): String?
}

object CommandControl {
    private val commandMap = mutableMapOf<String, Command>()
    init {
        commandMap["SET"] = SetCommand()
        commandMap["CLEAR"] = ClearCommand()
        commandMap["CHECK"] = CheckCommand()
        commandMap["HINT"] = HintCommand()
    }

    internal fun add(name: String, command: Command) {
        commandMap[name] = command
    }

    fun execute(parts: List<String>, board: Board): String? {
        val commandName = parts.firstOrNull() ?: return null
        val command = commandMap[commandName] ?: return null
        return command.execute(parts, board)
    }
}