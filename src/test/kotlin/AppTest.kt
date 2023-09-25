import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AppTest {

    @Test
    fun testAddition() {
        val result = add(5, 3)
        assertEquals(8, result)
    }

    @Test
    fun testAdditionWithNegativeNumbers() {
        val result = add(-5, -3)
        assertEquals(-8, result)
    }
}