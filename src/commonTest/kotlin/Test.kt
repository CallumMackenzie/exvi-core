import com.camackenzie.exvi.core.api.APIRequest
import com.camackenzie.exvi.core.api.RetrieveSaltRequest
import kotlin.test.Test

@Test
fun testAPIRequest() {
    APIRequest.requestAsync(
        "https://s36irvth41.execute-api.us-east-2.amazonaws.com/test/salt",
        RetrieveSaltRequest("callum")
    ) { result ->
        println(result.toJson())
    }
}