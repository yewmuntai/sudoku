package com.yewmun.sudoku.command

import TestUtil
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class SetCommandExecutorTest extends Specification {
    @Subject
    SetCommandExecutor executor = new SetCommandExecutor()

    def "get command"() {
        when:
        def result = executor.command

        then:
        assert result == "set"
    }

    def "execute - success"() {
        given:
        def board = TestUtil.generate()
        def parts = ["SET", "A", "3", value.toString()]

        when:
        def result = executor.execute(parts, board)

        then:
        assert board.values[0][2] == value
        assert result == "Move accepted."

        where:
        value << [4, 2] // 2 is not the correct answer, but it didn't break any validation
    }

    def "execute - number fail validation"() {
        def board = TestUtil.generate()
        def parts = ["SET", "A", col, value] as List<String>
        def outputBuffer = TestUtil.swapOutSystemOut()

        when:
        def result = executor.execute(parts, board)

        then:
        assert result == null
        def output = outputBuffer.toString()
        assert output == message

        cleanup:
        TestUtil.swapBackSystemOut()

        where:
        col | value   | message
        "2" | "3"     | "Invalid move. That cell is a fixed value.\n"
        "3" | "5"     | "Invalid move. That value already exists in the row.\n"
        "3" | "8"     | "Invalid move. That value already exists in the column.\n"
        "3" | "6"     | "Invalid move. That value already exists in the 3x3 box.\n"
    }

    @Unroll
    def "execute - invalid parameters"() {
        def board = TestUtil.generate()

        when:
        def result = executor.execute(parts, board)

        then:
        assert result == null

        where:
        parts << [
                ["SET", "A", "2", "1", "3"],
                ["SET", "A", "2"],
                ["SET", "1", "2", "1"],
                ["SET", "A", "A", "1"],
                ["SET", "A", "2", "A"]
        ]
    }
}
