import com.camackenzie.exvi.core.util.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.serialization.json.*
import kotlinx.serialization.*


class TestEncodedStringCache {

    @Test
    fun testEncodeDecode() {
        val esc = testStr.cached()
        assertEquals(testStr, esc.get())
    }

    @Test
    fun testFromEncoded() {
        val esc = testStr.cached()
        val encoded = esc.getEncoded()
        val esc2 = EncodedStringCache.fromEncoded(encoded)
        assertEquals(esc, esc2)
        assertEquals(testStr, esc.get(), esc2.get())
    }

    @Test
    fun testSerializeDeserialize() {
        val esc = testStr.cached()
        val json = esc.toJson()
        val esc2 = Json.decodeFromString<EncodedStringCache>(json)
        assertEquals(esc, esc2)
        assertEquals(testStr, esc.get(), esc2.get())
    }

    @Test
    fun testEquality() {
        val esc = testStr.cached()
        val esc2 = testStr.cached()
        assertEquals(esc, esc2)
        assertEquals(testStr, esc.get(), esc2.get())
    }

    @Test
    fun testSet() {
        val og = testStr
        val new = "@*())MD)(MWDKOSNMDANODSNDIDSNIDJHIAD"
        val esc = og.cached()
        assertEquals(og, esc.get())
        esc.set(new)
        assertEquals(new, esc.get())
    }

}