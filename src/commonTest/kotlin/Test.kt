import com.camackenzie.exvi.core.api.APIRequest
import com.camackenzie.exvi.core.api.RetrieveSaltRequest
import com.camackenzie.exvi.core.util.CryptographyUtils
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class Tests {

    @Test
    fun testAPIRequest() {
        println("API")
        APIRequest.requestAsync(
            "https://s36irvth41.execute-api.us-east-2.amazonaws.com/test/salt",
            RetrieveSaltRequest("callum")
        ) { result ->
            println(result.toJson())
        }
    }

    @Test
    fun testSerialize() {
        val og = RetrieveSaltRequest("callum")
        val serialized = og.toJson()
        val deserialized: RetrieveSaltRequest = Json.decodeFromString(serialized)
        assertEquals(og.username.get(), deserialized.username.get())
    }

    @Test
    fun testEncrypt() {
        val og = "dsaindsadhsa9d8sad8shds8dsd8s8ds"
        val encoded = CryptographyUtils.encodeString(og)
        val decoded = CryptographyUtils.decodeString(encoded)
        assertEquals(og, decoded)
    }
}