package com.seclib.htbp.user.utils


import java.lang.Exception
import kotlin.Throws
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ConstantWxPropertiesUtils : InitializingBean {
    @Value("\${wx.open.app_id}")
    private val appId: String? = null

    @Value("\${wx.open.app_secret}")
    private val appSecret: String? = null

    @Value("\${wx.open.redirect_url}")
    private val redirectUrl: String? = null

    @Value("\${htbp.baseUrl}")
    private val htbpBaseUrl: String? = null
    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        WX_OPEN_APP_ID = appId
        WX_OPEN_APP_SECRET = appSecret
        WX_OPEN_REDIRECT_URL = redirectUrl
        HTBP_BASE_URL = htbpBaseUrl
    }

    companion object {
        var WX_OPEN_APP_ID: String? = null
        var WX_OPEN_APP_SECRET: String? = null
        var WX_OPEN_REDIRECT_URL: String? = null
        var HTBP_BASE_URL: String? = null
    }
}