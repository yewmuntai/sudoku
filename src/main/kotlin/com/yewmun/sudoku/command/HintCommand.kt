package com.yewmun.sudoku.command

import com.yewmun.sudoku.Board
import com.yewmun.sudoku.Command

class HintCommand: Command {
    override fun execute(parts: List<String>, board: Board): String? {
        val unanswered = board.answers.keys.filter {
            board.values[it.first][it.second] == 0
        }.toTypedArray()

        val hint = unanswered.random()
        val row = Board.ROW_LABELS.getOrNull(hint.first)?:let {
            println("Hint error.")
            return null
        }
        return "Hint: cell $row ${hint.second + 1} = ${board.answers[hint]}"
    }
}