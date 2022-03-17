import com.camackenzie.exvi.core.model.*
import kotlinx.datetime.Clock
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestUnitValues {

    @Test
    fun testUnitConversion() {
        val mu = 12.kilograms
        mu.asUnit(MassUnit.Pound)

        assertEquals(mu.unit, MassUnit.Pound)
        assertTrue(mu.inRangeOf(12.kilograms, 1.grams))

        var m2 = 12.grams
        m2 *= 1000
        assertTrue(m2.inRangeOf(12.kilograms, 1.grams))
    }

    @Test
    fun testUnits() {
        2.meters
        2.inches
        2.feet
        2.kilometers
        2.kilograms
        2.grams
        2.pounds
        2.seconds
        2.milliseconds
        2.hours
        2.days
        2.years
        2.minutes
    }

    @Test
    fun testTime() {
        val now = TimeUnit.now()
        val nowClock = Clock.System.now().toEpochMilliseconds()
        val nowSeconds = now.toUnit(TimeUnit.Millisecond).value
        assertTrue(abs(nowClock - nowSeconds) <= 100, "Times did not match")

        println(now.formatToDate())
    }

}