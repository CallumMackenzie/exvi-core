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
        val og = "SDHJNSadhjsad"
        val esc = og.cached()
        assertEquals(og, esc.get())
    }

    @Test
    fun testFromEncoded() {
        val og = "@(*#&283dkasnmdoASNDSNJDAND"
        val esc = og.cached()
        val esc2 = EncodedStringCache.fromEncoded(esc.getEncoded())
        assertEquals(esc, esc2)
        assertEquals(og, esc.get(), esc2.get())
    }

    @Test
    fun testSerializeDeserialize() {
        val og = "@*(#jIODJsndJNDJSANDjaND"
        val esc = og.cached()
        val json = esc.toJson()
        val esc2 = Json.decodeFromString<EncodedStringCache>(json)
        assertEquals(esc, esc2)
        assertEquals(og, esc.get(), esc2.get())
    }

    @Test
    fun testEquality() {
        val og = "293192DKJOSMOADMSMDo9290IUD()@#"
        val esc = og.cached()
        val esc2 = og.cached()
        assertEquals(esc, esc2)
        assertEquals(og, esc.get(), esc2.get())
    }

    @Test
    fun testSet() {
        val og = "@(@()dmkOSMdMDOAMDSD"
        val new = "@*())MD)(MWDKOSNMDANODSNDIDSNIDJHIAD"
        val esc = og.cached()
        assertEquals(og, esc.get())
        esc.set(new)
        assertEquals(new, esc.get())
    }

}