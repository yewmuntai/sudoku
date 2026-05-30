package com.yewmun.sudoku.command

import TestUtil
import spock.lang.Specification
import spock.lang.Subject

class HintCommandExecutorTest extends Specification {
    @Subject
    HintCommandExecutor executor = new HintCommandExecutor()

    def "get command"() {
        when:
        def result = executor.command

        then:
        assert result == "hint"
    }

    def "execute - many answers left, random get 1"() {
        given:
        def board = TestUtil.generate()
        def parts = ["HINT"]

        when:
        def result = executor.execute(parts, board)

        then:
        assert result.startsWith("Hint: cell ")
        assert result.length() == 18
    }

    def "execute - all answered, left 1"() {
        given:
        def board = TestUtil.generate()
        def first = true
        board.answers.each {
            if (first) {
                first = false
            } else {
                board.values[it.key.first][it.key.second] = it.value
            }
        }
        def parts = ["HINT"]

        when:
        def result = executor.execute(parts, board)

        then:
        assert result == "Hint: cell A 3 = 4"
    }
}
