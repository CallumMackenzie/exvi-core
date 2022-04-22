import com.camackenzie.exvi.core.util.CryptographyUtils
import com.camackenzie.exvi.core.util.Identifiable
import com.camackenzie.exvi.core.util.RawIdentifiable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TestIdentifiable {

    @Test
    fun testGenerateId() {
        Identifiable.generateId()
    }

    @Test
    fun testRawIdentifiable() {
        val ida: Identifiable = RawIdentifiable(Identifiable.generateId())
        val idb: Identifiable = RawIdentifiable(ida.getIdentifier())
        assertEquals(ida, idb)

        val ida2 = ida.toRawIdentifiable()
        assertEquals(ida, ida2)
    }

    @Test
    fun testCheckIntersects() {
        val commonIds = List<Identifiable>(10) {
            RawIdentifiable(Identifiable.generateId())
        }
        val aIds = List<Identifiable>(5) {
            RawIdentifiable(Identifiable.generateId())
        }
        val bIds = List<Identifiable>(6) {
            RawIdentifiable(Identifiable.generateId())
        }
        val a = aIds + commonIds
        val b = bIds + commonIds

        Identifiable.intersectIndexed(
            a, b, onIntersect = { ae, ai, be, bi ->
                assertEquals(ae, be)
                assertEquals(a[ai], b[bi])
            }, onAOnly = { ae, ai ->
                assertFalse(b.contains(ae))
                assertTrue(a.contains(ae))
                assertEquals(ae, a[ai])
            }, onBOnly = { be, bi ->
                assertFalse(a.contains(be))
                assertTrue(b.contains(be))
                assertEquals(be, b[bi])
            }
        )
    }

    @Test
    fun test(){
        println(CryptographyUtils.encodeString("HELLO!"))
    }

}