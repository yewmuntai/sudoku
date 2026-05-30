import com.yewmun.sudoku.BoardData
import com.yewmun.sudoku.SudokuController
import kotlin.Pair

class TestUtil {
    private static PrintStream originalOut = null
    private static InputStream originalIn = null

    static ByteArrayOutputStream swapOutSystemOut() {
        def outputBuffer = new ByteArrayOutputStream()
        originalOut = System.out
        System.out = new PrintStream(outputBuffer)
        return outputBuffer
    }

    static void swapBackSystemOut() {
        System.out = originalOut
    }

    static setSystemInput(String input) {
        originalIn = System.in
        def inputBuffer = new ByteArrayInputStream(input.getBytes())
        System.in = inputBuffer
    }

    static void restoreSystemIn() {
        System.in = originalIn
    }

    static BoardData generate() {
        SudokuController.makeBoard()
    }

}
