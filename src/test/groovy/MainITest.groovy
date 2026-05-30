import spock.lang.Specification
import spock.lang.Timeout

import java.util.concurrent.TimeUnit

class MainITest extends Specification {
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    def "run main"() {
        given:
        def outputBuffer = TestUtil.swapOutSystemOut()
        TestUtil.setSystemInput("set A 3 4\nquit\n")

        when:
        MainKt.main(["test"] as String[])

        then:
        def output = outputBuffer.toString().split("\n")
        assert output.size() == 39
        assert output[22] == "Enter command: Move accepted."
        assert output[38] == "Enter command: Goodbye!"


        cleanup:
        TestUtil.restoreSystemIn()
        TestUtil.swapBackSystemOut()
    }
}
