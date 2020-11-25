package cz.pekostudio.camera.picker.utils

import java.security.MessageDigest

/**
 * Created by Lukas Urbanek on 14/08/2020.
 */

fun String.hash(type: String = "MD5"): String {
    fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
    return MessageDigest
        .getInstance(type)
        .digest(toByteArray()).toHex()
}