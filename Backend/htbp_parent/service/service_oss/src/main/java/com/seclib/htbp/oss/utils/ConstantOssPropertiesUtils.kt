package com.seclib.htbp.oss.utils

import org.springframework.beans.factory.InitializingBean
import kotlin.Throws
import java.lang.Exception
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ConstantOssPropertiesUtils : InitializingBean {
    @Value("\${aliyun.oss.endpoint}")
    private val endpoint: String? = null

    @Value("\${aliyun.oss.accessKeyId}")
    private val accessKeyId: String? = null

    @Value("\${aliyun.oss.secret}")
    private val secret: String? = null

    @Value("\${aliyun.oss.bucket}")
    private val bucket: String? = null

    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        ENDPOINT = endpoint ?: ""
        ACCESS_KEY_ID = accessKeyId ?: ""
        SECRET = secret ?: ""
        BUCKET = bucket ?: ""
    }

    companion object {
        var ENDPOINT = ""
        var ACCESS_KEY_ID = ""
        var SECRET = ""
        var BUCKET = ""
    }
}