package com.yewmun.sudoku.command

import com.yewmun.sudoku.BoardData
import com.yewmun.sudoku.CommandExecutor
import com.yewmun.sudoku.ROW_LABELS

open class HintCommandExecutor: CommandExecutor {
    override fun execute(parts: List<String>, board: BoardData): String? {
        val unanswered = board.answers.keys.filter {
            board.values[it.first][it.second] == 0
        }.toTypedArray()

        val hint = unanswered.random()
        val row = ROW_LABELS.getOrNull(hint.first)!! // will never be null. NPE at line 10 will happen 1st.
        return "Hint: cell $row ${hint.second + 1} = ${board.answers[hint]}"
    }

    override fun getCommand() = "hint"
}