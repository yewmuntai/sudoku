package com.yewmun.sudoku

import com.yewmun.sudoku.command.QuitCommandExecutor
import spock.lang.Specification
import spock.lang.Subject

class SudokuControllerTest extends Specification {
    @Subject
    SudokuController controller

    CommandControl commandControl

    def setup() {
        commandControl = Mock(CommandControl)
        controller = new SudokuController(9, commandControl)
    }

    def "generate board"() {
        when:
        def result = controller.generate(removeCount)

        then:
        assert result.answers.size() == removeCount

        def numberOfFixed = result.fixedValues.collect {
            it.findAll { it == false }
        }.flatten().size()
        assert numberOfFixed == removeCount

        def zeroes = result.values.collect {
            it.findAll { it == 0 }
        }.flatten().size()
        assert zeroes ==removeCount

        assert result.values.size() == 9

        def solved = result.values
        result.answers.each {
            result.values[it.key.first][it.key.second] = it.value
        }

        def unfixed = result.fixedValues.collect {
            it.findAll { it == false }
        }.flatten().size()
        assert unfixed == removeCount

        solved.each {  int[] row ->
            assert row.toSet().size() == 9
        }
        0..8.each {colIdx ->
            def col = solved.collect { it[colIdx] }.toSet()
            assert col.size() == 9
        }

        for (int i=0; i<9; i+=3) {
            for (int x=0; x<9; x+=3) {
                def box = new HashSet()
                for (int y=0; y<3; y++) {
                    for (int z=0; z<3; z++) {
                        box.add(result.values[i+y][x+z])
                    }
                }
                assert box.size() == 9
            }
        }

        where:
        removeCount << [8, 9, 10]
    }

    def "print board"() {
        given:
        def board = TestUtil.generate()
        def outputBuffer = TestUtil.swapOutSystemOut()

        when:
        controller.draw(board)

        then:
        def output = outputBuffer.toString().split("\n")
        assert output.size() == 14
        assert output[0]  == "    1 2 3   4 5 6   7 8 9"
        assert output[1]  == "  +-------+-------+-------+"
        assert output[2]  == "A | 5 3 \u001B[32m. \u001B[0m| \u001B[32m. \u001B[0m7 \u001B[32m. \u001B[0m| \u001B[32m. . . \u001B[0m| "
        assert output[3]  == "B | 6 \u001B[32m. . \u001B[0m| 1 9 5 | \u001B[32m. . . \u001B[0m| "
        assert output[4]  == "C | \u001B[32m. \u001B[0m9 8 | \u001B[32m. . . \u001B[0m| \u001B[32m. \u001B[0m6 \u001B[32m. \u001B[0m| "
        assert output[5]  == "  +-------+-------+-------+"
        assert output[6]  == "D | 8 \u001B[32m. . \u001B[0m| \u001B[32m. \u001B[0m6 \u001B[32m. \u001B[0m| \u001B[32m. . \u001B[0m3 | "
        assert output[7]  == "E | 4 \u001B[32m. . \u001B[0m| 8 \u001B[32m. \u001B[0m3 | \u001B[32m. . \u001B[0m1 | "
        assert output[8]  == "F | 7 \u001B[32m. . \u001B[0m| \u001B[32m. \u001B[0m2 \u001B[32m. \u001B[0m| \u001B[32m. . \u001B[0m6 | "
        assert output[9]  == "  +-------+-------+-------+"
        assert output[10] == "G | \u001B[32m. \u001B[0m6 \u001B[32m. \u001B[0m| \u001B[32m. . . \u001B[0m| 2 8 \u001B[32m. \u001B[0m| "
        assert output[11] == "H | \u001B[32m. . . \u001B[0m| 4 1 9 | \u001B[32m. . \u001B[0m5 | "
        assert output[12] == "J | \u001B[32m. . . \u001B[0m| \u001B[32m. \u001B[0m8 \u001B[32m. \u001B[0m| \u001B[32m. \u001B[0m7 9 | "
        assert output[13] == "  +-------+-------+-------+"

        cleanup:
        TestUtil.swapBackSystemOut()
    }

    def "is complete - false"() {
        given:
        def board = TestUtil.generate()

        when:
        def result = controller.isComplete(board)

        then:
        assert !result
    }

    def "is complete - true"() {
        given:
        def board = TestUtil.generate()
        board.answers.each {
            def pos = it.key
            board.values[pos.first][pos.second] = it.value
        }

        when:
        def result = controller.isComplete(board)

        then:
        assert result
    }

    def "handle command - normal"() {
        given:
        def board = TestUtil.generate()
        def outputBuffer = TestUtil.swapOutSystemOut()

        when:
        controller.handleCommand(input, board)

        then:
        1 * commandControl.execute(parts, _) >> cmdMsg
        0 *_
        def output = outputBuffer.toString()
        assert output == printed

        cleanup:
        TestUtil.swapBackSystemOut()

        where:
        input       | parts                     | cmdMsg        | printed
        "set a 1 2" | ["SET", "A", "1", "2"]    | "message 1"   | "message 1\n"
        "clear a 1" | ["CLEAR", "A", "1"]       | null          | "Invalid command. Try: set row col value, clear row col, hint, check or quit.\n"

    }

    def "handle command - exit"() {
        given:
        def board = TestUtil.generate()
        def parts = ["QUIT"]

        when:
        controller.handleCommand("quit", board)

        then:
        1 * commandControl.execute(parts, board) >> {
            new QuitCommandExecutor().execute(parts, board)
        }
        0 *_

        def ex = thrown(ExitException)
        assert ex
    }
}
