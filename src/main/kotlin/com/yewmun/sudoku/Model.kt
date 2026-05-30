package com.yewmun.sudoku

data class BoardData (
    internal val values: Array<IntArray>,
    internal val fixedValues: Array<BooleanArray>,
    internal val answers: Map<Pair<Int, Int>, Int> = emptyMap()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BoardData

        if (!values.contentDeepEquals(other.values)) return false
        if (!fixedValues.contentDeepEquals(other.fixedValues)) return false
        if (answers != other.answers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = values.contentDeepHashCode()
        result = 31 * result + fixedValues.contentDeepHashCode()
        result = 31 * result + answers.hashCode()
        return result
    }
}

const val ROW_LABELS = "ABCDEFGHJ"
