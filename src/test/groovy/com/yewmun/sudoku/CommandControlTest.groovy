package com.yewmun.sudoku

import com.yewmun.sudoku.command.CheckCommandExecutor
import com.yewmun.sudoku.command.ClearCommandExecutor
import com.yewmun.sudoku.command.HintCommandExecutor
import com.yewmun.sudoku.command.QuitCommandExecutor
import com.yewmun.sudoku.command.SetCommandExecutor
import spock.lang.Specification
import spock.lang.Subject

class CommandControlTest extends Specification {
    @Subject
    CommandControl theControl = new CommandControl()

    def "add executors added"() {
        when:
        println "dummy"
        then:
        assert theControl.commandMap.size() == 5
        assert theControl.commandMap["CHECK"] instanceof CheckCommandExecutor
        assert theControl.commandMap["CLEAR"] instanceof ClearCommandExecutor
        assert theControl.commandMap["HINT"] instanceof HintCommandExecutor
        assert theControl.commandMap["QUIT"] instanceof QuitCommandExecutor
        assert theControl.commandMap["SET"] instanceof SetCommandExecutor
    }

    def "execute"() {
        given:
        def board = TestUtil.generate()

        theControl.commandMap["CHECK"] = Stub(CheckCommandExecutor)
        theControl.commandMap["CHECK"].execute(parts, board) >> "check123"

        theControl.commandMap["CLEAR"] = Stub(ClearCommandExecutor)
        theControl.commandMap["CLEAR"].execute(parts, board) >> "clear234"

        theControl.commandMap["HINT"] = Stub(HintCommandExecutor)
        theControl.commandMap["HINT"].execute(parts, board) >> "hint345"

        theControl.commandMap["QUIT"] = Stub(QuitCommandExecutor)
        theControl.commandMap["QUIT"].execute(parts, board) >> "quit456"

        theControl.commandMap["SET"] = Stub(SetCommandExecutor)
        theControl.commandMap["SET"].execute(parts, board) >> "set567"

        when:
        def result = theControl.execute(parts, board)

        then:
        0 *_
        assert theControl.commandMap.size() == 5 // to ensure the given setup did not add new executors
        assert result == msg

        where:
        parts                   | executor  | msg
        ["CHECK"]               | "CHECK"   | "check123"
        ["CLEAR", "a", "1"]     | "CLEAR"   | "clear234"
        ["HINT"]                | "HINT"    | "hint345"
        ["QUIT"]                | "QUIT"    | "quit456"
        ["SET", "a", "1", "2"]  | "SET"     | "set567"
        ["UNKNOWN"]             | null      | null
    }
}
