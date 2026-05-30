package com.yewmun.sudoku.command

import com.yewmun.sudoku.ExitException
import TestUtil
import spock.lang.Specification
import spock.lang.Subject

class QuitCommandExecutorTest extends Specification {
    @Subject
    QuitCommandExecutor executor = new QuitCommandExecutor()

    def "get command"() {
        when:
        def result = executor.command

        then:
        assert result == "quit"
    }

    def "execute - no change"() {
        given:
        def board = TestUtil.generate()
        def parts = ["QUIT"]

        when:
        executor.execute(parts, board)

        then:
        def ex = thrown(ExitException)
        assert ex
    }
}
