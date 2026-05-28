package com.yewmun.sudoku

import java.util.Locale.getDefault

class Board(
    internal val values: Array<IntArray>,
    internal val fixedValues: Array<BooleanArray>,
    internal val answers: Map<Pair<Int, Int>, Int> = emptyMap()
) {

    fun draw() {
        println("    1 2 3   4 5 6   7 8 9")
        println("  +-------+-------+-------+")
        var isGreen = false

        for (row in 0 until 9) {
            print("${ROW_LABELS[row]} | ")

            for (col in 0 until 9) {
                val value = values[row][col].let {
                    if (it == 0) "." else it
                }
                val displayValue = if (fixedValues[row][col]) {
                    if (isGreen) {
                        isGreen = false
                        "$RESET_TEXT$value"
                    } else {
                        value.toString()
                    }
                } else {
                    if (isGreen) {
                        value.toString()
                    } else {
                        isGreen = true
                        "$GREEN_TEXT$value"
                    }
                }
                print("$displayValue ")

                if ((col + 1) % 3 == 0) {
                    if (isGreen) {
                        isGreen = false
                        print("$RESET_TEXT| ")
                    } else {
                        print("| ")
                    }
                }
            }

            println()

            if ((row + 1) % 3 == 0) {
                println("  +-------+-------+-------+")
            }
        }
    }

    fun isComplete() = values.all {
        it.none { value -> value == 0 }
    }

    fun handleCommand(
        input: String,
    ) {
        val parts = input.uppercase(getDefault()).split(Regex("\\s+"))

        parts.firstOrNull()?.let {
            CommandControl.execute(parts, this)
        }?.let {
            println(it)
            it
        }?: println("Invalid command. Try: set row col value, clear row col, hint, check or quit.")
    }

    companion object {
        const val ROW_LABELS = "ABCDEFGHJ"
        private const val GREEN_TEXT = "\u001B[32m"
        private const val RESET_TEXT = "\u001B[0m"

        fun generate(): Board {
            val puzzle = arrayOf(
                intArrayOf(5, 3, 0, 0, 7, 0, 0, 0, 0),
                intArrayOf(6, 0, 0, 1, 9, 5, 0, 0, 0),
                intArrayOf(0, 9, 8, 0, 0, 0, 0, 6, 0),
                intArrayOf(8, 0, 0, 0, 6, 0, 0, 0, 3),
                intArrayOf(4, 0, 0, 8, 0, 3, 0, 0, 1),
                intArrayOf(7, 0, 0, 0, 2, 0, 0, 0, 6),
                intArrayOf(0, 6, 0, 0, 0, 0, 2, 8, 0),
                intArrayOf(0, 0, 0, 4, 1, 9, 0, 0, 5),
                intArrayOf(0, 0, 0, 0, 8, 0, 0, 7, 9)
            )
            val fixedValues = Array(9) { row ->
                BooleanArray(9) { col -> puzzle[row][col] != 0 }
            }
            val answers = mapOf(
                Pair(0, 2) to 4, Pair(0, 3) to 6, Pair(0, 5) to 8, Pair(0, 6) to 9, Pair(0, 7) to 1, Pair(0, 8) to 2,
                Pair(1, 1) to 7, Pair(1, 2) to 2, Pair(1, 6) to 3, Pair(1, 7) to 4, Pair(1, 8) to 8,
                Pair(2, 0) to 1, Pair(2, 3) to 3, Pair(2, 4) to 4, Pair(2, 5) to 2, Pair(2, 6) to 5, Pair(2, 8) to 7,
                Pair(3, 1) to 5, Pair(3, 2) to 9, Pair(3, 3) to 7, Pair(3, 5) to 1, Pair(3, 6) to 4, Pair(3, 7) to 2,
                Pair(4, 1) to 2, Pair(4, 2) to 6, Pair(4, 4) to 5, Pair(4, 6) to 7, Pair(4, 7) to 9,
                Pair(5, 1) to 1, Pair(5, 2) to 3, Pair(5, 3) to 9, Pair(5, 5) to 4, Pair(5, 6) to 8, Pair(5, 7) to 5,
                Pair(6, 0) to 9, Pair(6, 2) to 1, Pair(6, 3) to 5, Pair(6, 4) to 3, Pair(6, 5) to 7, Pair(6, 8) to 4,
                Pair(7, 0) to 2, Pair(7, 1) to 8, Pair(7, 2) to 7, Pair(7, 6) to 6, Pair(7, 7) to 3,
                Pair(8, 0) to 3, Pair(8, 1) to 4, Pair(8, 2) to 5, Pair(8, 3) to 2, Pair(8, 5) to 6, Pair(8, 6) to 1
            )
            return Board(puzzle, fixedValues, answers)
        }
    }
}