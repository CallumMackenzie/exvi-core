import com.camackenzie.exvi.core.model.ExviSerializer
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlin.test.Test
import kotlin.test.assertEquals


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
        val esc2 = ExviSerializer.fromJson<EncodedStringCache>(json)
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