package ru.garfid.kotlinspringbase.secure.logic

import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.codec.Hex
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object JwtService {
    private const val secret = "temporaryExampleSecret"
    private const val header: String = "{\"typ\":\"JWT\", \"typ\":\"JWT\", \"cty\":\"JWT\", \"alg\":\"HS256\"}"

    fun makeTokenString(login: String): String {
        val now = 0/*System.currentTimeMillis() / 1000L*/
        val payload: String = "{\"exp\":\"" + (now + 18000).toString() + "\", \"iat\":\"" + now.toString() + "\", \"user\":\"" + login + "\"}"

        val signature = hmacSHA256(secret, toBase64(header) + "." + toBase64(payload))

        val token = toBase64(header) + "." +
                toBase64(payload) + "." +
                toBase64(signature)

        return token
    }

    fun checkToken(token: String): String? {
        val tokenParts: List<String> = token.split(".")

        val tokenHeader: String = fromBase64(tokenParts[0])
        val tokenPayload: String = fromBase64(tokenParts[1])
        val signature: String = fromBase64(tokenParts[2])
        val targetSignature: String = hmacSHA256(secret, toBase64(tokenHeader) + "." + toBase64(tokenPayload))

        if (signature == targetSignature) {
            return "yay"
        } else {
            throw BadCredentialsException("Bad token")
        }
    }

    fun toBase64(source: String): String {
        return Base64.encodeBase64String(source.toByteArray())
    }

    fun fromBase64(source: String): String =
        String(Base64.decodeBase64(source))

    fun hash(payload: String): String = hash(payload.toByteArray())

    fun hash(payload: ByteArray): String = try {
        String(Hex.encode(MessageDigest.getInstance("SHA-256").digest(payload)))
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException(e)
    }

    fun hmacSHA256(key: String, data: String): String = try {
        val algorithm = "HmacSHA256"
        Mac.getInstance(algorithm).run {
            init(SecretKeySpec(key.toByteArray(), algorithm))
            String(doFinal(data.toByteArray(charset("UTF8"))))
        }
    } catch (e: Exception) {
        throw RuntimeException("Could not run HMAC SHA256", e)
    }
}

object AwsHmacSha256 {

}