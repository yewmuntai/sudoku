package com.yewmun.sudoku.command

import TestUtil
import spock.lang.Specification
import spock.lang.Subject

class CheckCommandExecutorTest extends Specification {
    @Subject CheckCommandExecutor executor = new CheckCommandExecutor()

    def "get command"() {
        when:
        def result = executor.command

        then:
        assert result == "check"
    }

    def "execute - no change"() {
        given:
        def board = TestUtil.generate()
        def parts = ["CHECK"]

        when:
        def result = executor.execute(parts, board)

        then:
        assert result == "All answered cells are correct."
    }

    def "execute - valid change"() {
        given:
        def board = TestUtil.generate()
        def parts = ["CHECK"]
        board.values[4][2] = 6

        when:
        def result = executor.execute(parts, board)

        then:
        assert result == "All answered cells are correct."
    }

    def "execute - invalid change"() {
        given:
        def board = TestUtil.generate()
        def parts = ["CHECK"]
        board.values[4][2] = 7

        when:
        def result = executor.execute(parts, board)

        then:
        assert result == null
    }
}
