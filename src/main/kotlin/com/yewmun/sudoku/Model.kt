package com.yewmun.sudoku

data class BoardData (
    internal val values: Array<IntArray>,
    internal val answers: Map<Pair<Int, Int>, Int> = emptyMap(),
) {
    internal val moveHistory = mutableListOf<MoveData>()

    fun isAnswerCell(row: Int, col: Int) = answers.containsKey(Pair(row, col))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BoardData

        if (!values.contentDeepEquals(other.values)) return false
        if (answers != other.answers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = values.contentDeepHashCode()
        result = 31 * result + answers.hashCode()
        return result
    }
}

const val ROW_LABELS = "ABCDEFGHJ"

data class MoveData(
    val row: Int,
    val col: Int,
    val previousValue: Int
)
