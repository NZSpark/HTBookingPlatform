package com.seclib.htbp.common.helper

import io.jsonwebtoken.*
import org.springframework.util.StringUtils
import java.util.*
import kotlin.jvm.JvmStatic

object JwtHelper {
    private const val tokenExpiration = (24 * 60 * 60 * 1000 //Expired time.
            ).toLong()
    private const val tokenSignKey = "123456" //Key
    @JvmStatic
    fun createToken(userId: Long?, userName: String?): String {
        return Jwts.builder()
            .setSubject("HTBP-USER")
            .setExpiration(Date(System.currentTimeMillis() + tokenExpiration))
            .claim("userId", userId)
            .claim("userName", userName)
            .signWith(SignatureAlgorithm.HS512, tokenSignKey)
            .compressWith(CompressionCodecs.GZIP)
            .compact()
    }

    @JvmStatic
    fun getUserId(token: String?): Long? {
        if (StringUtils.isEmpty(token)) return null
        val claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token)
        val claims = claimsJws.body
        val userId = claims["userId"] as Int?
        return userId!!.toLong()
    }

    @JvmStatic
    fun getUserName(token: String?): String? {
        if (StringUtils.isEmpty(token)) return ""
        val claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token)
        val claims = claimsJws.body
        return claims["userName"] as String?
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val token = createToken(1L, "55")
        println(token)
        println(getUserId(token))
        println(getUserName(token))
    }
}