import com.camackenzie.exvi.core.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

const val testStr =
    "1234567890-=`qwertyuiop[]\\asdfghjkl;'zxcvbnm,./QWERTYUIOPASDFGHJKL:\"ZXCVBNM<>?!@#$%^&*()_+"

class TestCryptographyUtils {

    @Test
    fun testEncryptAES() {
        CryptographyUtils.encryptAES("HELLO!")
    }

    @Test
    fun testDecryptAES() {
        val encrypted = CryptographyUtils.encryptAES(testStr)
        val decrypted = CryptographyUtils.decryptAES(encrypted)
        assertEquals(testStr, decrypted)
    }

    @Test
    fun testBase64() {
        val encoded = CryptographyUtils.encodeStringToBase64(testStr)
        val decoded = CryptographyUtils.decodeStringFromBase64(encoded)
        assertEquals(testStr, decoded)
    }

    @Test
    fun testRotationCipher() {
        val rotated = CryptographyUtils.applyRotationCipher(testStr, 2)
        val restored = CryptographyUtils.revertRotationCipher(rotated, 2)
        assertEquals(testStr, restored)
    }

    @Test
    fun testHashSHA256() {
        CryptographyUtils.hashSHA256(testStr)
    }

    @Test
    fun testGenerateSalt() {
        CryptographyUtils.generateSalt()
    }

    @Test
    fun testEncodeString() {
        val encoded = CryptographyUtils.encodeString(testStr)
        val decoded = CryptographyUtils.decodeString(encoded)
        assertEquals(testStr, decoded)
    }

    @Test
    fun testBytesBase64String() {
        val base64 = CryptographyUtils.encodeStringToBase64(testStr)
        val bytes = CryptographyUtils.bytesFromBase64String(base64)
        val original = CryptographyUtils.bytesToBase64String(bytes)
        assertEquals(base64, original)
    }

}