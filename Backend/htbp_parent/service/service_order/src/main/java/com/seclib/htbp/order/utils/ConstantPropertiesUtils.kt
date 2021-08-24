package com.seclib.htbp.order.utils

import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import kotlin.Throws
import java.lang.Exception

@Component
class ConstantPropertiesUtils : InitializingBean {
    @Value("\${weixin.pay.appid}")
    private var appid = ""

    @Value("\${weixin.pay.partner}")
    private var partner = ""

    @Value("\${weixin.pay.partnerkey}")
    private var partnerkey = ""

    @Value("\${weixin.pay.cert}")
    private var cert = ""

    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        APPID = appid
        PARTNER = partner
        PARTNERKEY = partnerkey
        CERT = cert
    }

    companion object {
        var APPID = ""
        var PARTNER = ""
        var PARTNERKEY = ""
        var CERT = ""
    }
}