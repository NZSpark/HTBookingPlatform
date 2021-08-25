package com.seclib.htbp.common.utils

import java.security.NoSuchAlgorithmException
import java.lang.RuntimeException
import java.security.MessageDigest

object MD5 {
    fun encrypt(strSrc: String): String {
        return try {
            val hexChars = charArrayOf(
                '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'a', 'b', 'c', 'd', 'e', 'f'
            )
            var bytes = strSrc.toByteArray()
            val md = MessageDigest.getInstance("MD5")
            md.update(bytes)
            bytes = md.digest()
            val j = bytes.size
            val chars = CharArray(j * 2)
            var k = 0
            for (i in bytes.indices) {
                val b = bytes[i]
                chars[k++] = hexChars[b.toInt().ushr(4) and 0xf]
                chars[k++] = hexChars[b.toInt() and 0xf]
            }
            String(chars)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            throw RuntimeException("MD5加密出错！！+$e")
        }
    }
}