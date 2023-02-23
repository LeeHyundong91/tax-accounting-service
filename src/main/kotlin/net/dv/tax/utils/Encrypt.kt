package net.dv.tax.utils

import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.util.Base64

@Component
class Encrypt {

    private val salt: ByteArray = "dr.village.net".toByteArray(Charsets.UTF_8)
    fun encodeToBase64(key: String): String = MessageDigest.getInstance("SHA-256")
        .run {
            update(key.toByteArray(Charsets.UTF_8))
            update(salt)
            digest()
        }.let {
            Base64.getEncoder().encodeToString(it)
        }

}