package com.seclib.htbp.gateway.filter

import com.alibaba.fastjson.JSONObject
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.util.AntPathMatcher
import org.springframework.web.server.ServerWebExchange
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import reactor.core.publisher.Mono
import java.lang.Void
import com.seclib.htbp.common.helper.JwtHelper
import com.seclib.htbp.common.result.Result
import com.seclib.htbp.common.result.ResultCodeEnum
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.nio.charset.StandardCharsets

@Component
open class AuthGlobalFilter : GlobalFilter, Ordered {
    private val antPathMatcher = AntPathMatcher()
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val path = request.uri.path
        println("===$path")

        //内部服务接口，不允许外部访问
        if (antPathMatcher.match("/**/inner/**", path)) {
            val response = exchange.response
            return out(response, ResultCodeEnum.PERMISSION)
        }


        //api接口，异步请求，校验用户必须登录
        if (antPathMatcher.match("/api/**/auth/**", path)) {
            val userId = getUserId(request)
            if (StringUtils.isEmpty(userId)) {
                val response = exchange.response
                return out(response, ResultCodeEnum.LOGIN_AUTH)
            }
        }
        return chain.filter(exchange)
    }

    override fun getOrder(): Int {
        return 0
    }

    /**
     * api接口鉴权失败返回数据
     * @param response
     * @return
     */
    private fun out(response: ServerHttpResponse, resultCodeEnum: ResultCodeEnum): Mono<Void> {
        val result: Result<*> = Result.build<Any?>(null, resultCodeEnum)
        val bits = JSONObject.toJSONString(result).toByteArray(StandardCharsets.UTF_8)
        val buffer = response.bufferFactory().wrap(bits)
        //指定编码，否则在浏览器中会中文乱码
        response.headers.add("Content-Type", "application/json;charset=UTF-8")
        return response.writeWith(Mono.just(buffer))
    }

    /**
     * 获取当前登录用户id
     * @param request
     * @return
     */
    private fun getUserId(request: ServerHttpRequest): Long? {
        var token = ""
        val tokenList = request.headers["token"]
        if (null != tokenList) {
            token = tokenList[0]
        }
        return if (!StringUtils.isEmpty(token)) {
            JwtHelper.getUserId(token)
        } else null
    }
}