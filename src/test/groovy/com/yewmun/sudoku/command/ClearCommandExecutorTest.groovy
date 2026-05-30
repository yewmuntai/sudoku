package com.yewmun.sudoku.command

import TestUtil
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class ClearCommandExecutorTest extends Specification {
    @Subject
    ClearCommandExecutor executor = new ClearCommandExecutor()

    def "get command"() {
        when:
        def result = executor.command

        then:
        assert result == "clear"
    }

    def "execute - success"() {
        given:
        def board = TestUtil.generate()
        board.values[0][2] = 4
        def parts = ["CLEAR", "A", "3"]

        when:
        def result = executor.execute(parts, board)

        then:
        assert board.values[0][2] == 0
        assert result == "Cell A 3 cleared."
    }

    def "execute - no answer yet"() {
        given:
        def board = TestUtil.generate()
        def parts = ["CLEAR", "A", "3"]

        when:
        def result = executor.execute(parts, board)

        then:
        assert board.values[0][2] == 0
        assert result == "Cell A 3 cleared."
    }

    def "execute - clear fixed cell"() {
        def board = TestUtil.generate()
        def parts = ["CLEAR", "A", "2"]

        when:
        def result = executor.execute(parts, board)

        then:
        assert result == null
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
                ["CLEAR", "A", "2", "1"],
                ["CLEAR", "A"],
                ["CLEAR", "1", "2"],
                ["CLEAR", "A", "A"]
        ]
    }
}
