package com.yewmun.sudoku

import kotlin.math.sqrt
import kotlin.random.Random

class SudokuGenerator(private val size: Int = 9) {
    private val board = Array(size) { IntArray(size) { 0 } }
    private val fixed = Array(size) { BooleanArray(size) { true } }
    private val answers = mutableMapOf<Pair<Int, Int>, Int>()
    private val sqrtSize = sqrt(size.toDouble()).toInt()

    fun generate(cellsToRemove: Int): Board {
        fillDiagonal()
        fillRemaining(0, sqrtSize)
        removeKDigits(cellsToRemove)
        return Board(board, fixed, answers)
    }

    private fun fillDiagonal() {
        for (i in 0 until size step sqrtSize) {
            fillBox(i, i)
        }
    }

    private fun fillBox(row: Int, col: Int) {
        var num: Int
        for (i in 0 until sqrtSize) {
            for (j in 0 until sqrtSize) {
                do {
                    num = Random.nextInt(1, size + 1)
                } while (!isSafeInBox(row, col, num))
                board[row + i][col + j] = num
            }
        }
    }

    private fun isSafeInBox(rowStart: Int, colStart: Int, num: Int): Boolean {
        for (i in 0 until sqrtSize) {
            for (j in 0 until sqrtSize) {
                if (board[rowStart + i][colStart + j] == num) return false
            }
        }
        return true
    }

    private fun isSafe(i: Int, j: Int, num: Int): Boolean {
        return board[i].none { it == num } && // Row check
                (0 until size).none { board[it][j] == num } && // Column check
                isSafeInBox(i - i % sqrtSize, j - j % sqrtSize, num) // Box check
    }

    private fun fillRemaining(i: Int, j: Int): Boolean {
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
            if (isSafe(row, col, num)) {
                board[row][col] = num
                if (fillRemaining(row, col + 1)) return true
                board[row][col] = 0
            }
        }
        return false
    }

    private fun removeKDigits(k: Int) {
        var count = k
        while (count != 0) {
            val cellId = Random.nextInt(0, size * size)
            val i = cellId / size
            val j = cellId % size
            if (board[i][j] != 0) {
                answers[Pair(i, j)] = board[i][j]
                board[i][j] = 0
                fixed[i][j] = false
                count--
            }
        }
    }
}
