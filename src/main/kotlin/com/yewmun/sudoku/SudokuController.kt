package com.yewmun.sudoku

import kotlin.math.sqrt
import kotlin.random.Random

class SudokuController(
    private val size: Int = 9,
    private val commandControl: CommandControl = CommandControl()
) {
    fun generate(cellsToRemove: Int): BoardData {
        val values = Array(size) { IntArray(size) { 0 } }
        val answers = mutableMapOf<Pair<Int, Int>, Int>()
        val sqrtSize = sqrt(size.toDouble()).toInt()

        fillDiagonal(sqrtSize, values)
        fillRemaining(0, sqrtSize, sqrtSize, values)
        removeKDigits(cellsToRemove, values, answers)

        return BoardData(values, answers)
    }

    fun draw(board: BoardData) {
        println("    1 2 3   4 5 6   7 8 9")
        println("  +-------+-------+-------+")
        var isGreen = false

        for (row in 0 until 9) {
            print("${ROW_LABELS[row]} | ")

            for (col in 0 until 9) {
                val value = board.values[row][col].let {
                    if (it == 0) "." else it
                }
                val displayValue = if (board.isAnswerCell(row, col)) {
                    if (isGreen) {
                        value.toString()
                    } else {
                        isGreen = true
                        "$GREEN_TEXT$value"
                    }
                } else {
                    if (isGreen) {
                        isGreen = false
                        "$RESET_TEXT$value"
                    } else {
                        value.toString()
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

    fun isComplete(board: BoardData) = board.values.all {
        it.none { value -> value == 0 }
    }

    fun handleCommand(
        input: String,
        board: BoardData
    ) {
        val parts = input.uppercase().split(Regex("\\s+"))

        parts.firstOrNull()?.let {
            val s = commandControl.execute(parts, board)
            s
        }?.let {
            println(it)
            it
        }?: println("Invalid command. Try: set row col value, clear row col, hint, check or quit.")
    }

    private fun fillDiagonal(sqrtSize: Int, values: Array<IntArray>) {
        for (i in 0 until size step sqrtSize) {
            fillBox(i, i, sqrtSize, values)
        }
    }

    private fun fillBox(row: Int, col: Int, sqrtSize: Int, values: Array<IntArray>) {
        var num: Int
        for (i in 0 until sqrtSize) {
            for (j in 0 until sqrtSize) {
                do {
                    num = Random.nextInt(1, size + 1)
                } while (!isSafeInBox(row, col, num, sqrtSize, values))
                values[row + i][col + j] = num
            }
        }
    }

    private fun isSafeInBox(rowStart: Int, colStart: Int, num: Int, sqrtSize: Int, values: Array<IntArray>): Boolean {
        for (i in 0 until sqrtSize) {
            for (j in 0 until sqrtSize) {
                if (values[rowStart + i][colStart + j] == num) return false
            }
        }
        return true
    }

    private fun isSafe(i: Int, j: Int, num: Int, sqrtSize: Int, values: Array<IntArray>): Boolean {
        return values[i].none { it == num } && // Row check
                (0 until size).none { values[it][j] == num } && // Column check
                isSafeInBox(i - i % sqrtSize, j - j % sqrtSize, num, sqrtSize, values) // Box check
    }

    private fun fillRemaining(i: Int, j: Int, sqrtSize: Int, values: Array<IntArray>): Boolean {
        var row = i
        var col = j
        if (col >= size && row < size - 1) {
            row += 1
            col = 0
        }
        if (row >= size && col >= size) return true
        if (row < sqrtSize && col < sqrtSize) col = sqrtSize
        else if (row < size - sqrtSize && col == (row / sqrtSize) * sqrtSize) col += sqrtSize
        else if (row >= size - sqrtSize && col == size - sqrtSize) {
            row += 1
            col = 0
            if (row >= size) return true
        }

        for (num in 1..size) {
            if (isSafe(row, col, num, sqrtSize, values)) {
                values[row][col] = num
                if (fillRemaining(row, col + 1, sqrtSize, values)) return true
                values[row][col] = 0
            }
        }
        return false
    }

    private fun removeKDigits(k: Int, values: Array<IntArray>, answers: MutableMap<Pair<Int, Int>, Int>) {
        var count = k
        while (count != 0) {
            val cellId = Random.nextInt(0, size * size)
            val i = cellId / size
            val j = cellId % size
            if (values[i][j] != 0) {
                answers[Pair(i, j)] = values[i][j]
                values[i][j] = 0
                count--
            }
        }
    }

    companion object {
        private const val GREEN_TEXT = "\u001B[32m"
        private const val RESET_TEXT = "\u001B[0m"

        @JvmStatic
        fun makeBoard(): BoardData {
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

            val answers = mapOf(
                Pair(0, 2) to 4,Pair(0, 3) to 6,Pair(0, 5) to 8,Pair(0, 6) to 9,Pair(0, 7) to 1,Pair(0, 8) to 2,
                Pair(1, 1) to 7,Pair(1, 2) to 2,Pair(1, 6) to 3,Pair(1, 7) to 4,Pair(1, 8) to 8,
                Pair(2, 0) to 1,Pair(2, 3) to 3,Pair(2, 4) to 4,Pair(2, 5) to 2,Pair(2, 6) to 5,Pair(2, 8) to 7,
                Pair(3, 1) to 5,Pair(3, 2) to 9,Pair(3, 3) to 7,Pair(3, 5) to 1,Pair(3, 6) to 4,Pair(3, 7) to 2,
                Pair(4, 1) to 2,Pair(4, 2) to 6,Pair(4, 4) to 5,Pair(4, 6) to 7,Pair(4, 7) to 9,
                Pair(5, 1) to 1,Pair(5, 2) to 3,Pair(5, 3) to 9,Pair(5, 5) to 4,Pair(5, 6) to 8,Pair(5, 7) to 5,
                Pair(6, 0) to 9,Pair(6, 2) to 1,Pair(6, 3) to 5,Pair(6, 4) to 3,Pair(6, 5) to 7,Pair(6, 8) to 4,
                Pair(7, 0) to 2,Pair(7, 1) to 8,Pair(7, 2) to 7,Pair(7, 6) to 6,Pair(7, 7) to 3,
                Pair(8, 0) to 3,Pair(8, 1) to 4,Pair(8, 2) to 5,Pair(8, 3) to 2,Pair(8, 5) to 6,Pair(8, 6) to 1
            )
            return BoardData(puzzle, answers)
        }
    }
}
