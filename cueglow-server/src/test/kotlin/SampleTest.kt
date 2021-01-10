import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SampleTest {

    @Test
    fun foo() {
        Assertions.assertTrue(true)
    }
}