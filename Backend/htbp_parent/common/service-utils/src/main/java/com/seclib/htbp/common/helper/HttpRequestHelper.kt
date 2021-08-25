package com.seclib.htbp.common.helper

import com.alibaba.fastjson.JSONObject
import com.seclib.htbp.common.utils.HttpUtil
import com.seclib.htbp.common.utils.MD5
import lombok.extern.slf4j.Slf4j
import org.reflections.Reflections.log
import kotlin.jvm.JvmStatic
import java.lang.Exception
import java.lang.StringBuilder
import java.util.*

@Slf4j
object HttpRequestHelper {
    @JvmStatic
    fun main(args: Array<String>) {
        val paramMap: MutableMap<String, Any?> = HashMap()
        paramMap["d"] = "4"
        paramMap["b"] = "2"
        paramMap["c"] = "3"
        paramMap["a"] = "1"
        paramMap["timestamp"] = timestamp
        log?.info(getSign(paramMap, "111111111"))
    }

    /**
     *
     * @param paramMap
     * @return
     */
    fun switchMap(paramMap: Map<String, Array<String>>): MutableMap<String, Any?> {
        val resultMap: MutableMap<String, Any?> = HashMap()
        for ((key, value) in paramMap) {
            resultMap[key] = value[0]
        }
        return resultMap
    }

    /**
     * 请求数据获取签名
     * @param paramMap
     * @param signKey
     * @return
     */
    fun getSign(paramMap: MutableMap<String, Any?>, signKey: String?): String {
        if (paramMap.containsKey("sign")) {
            paramMap.remove("sign")
        }
        val sorted = TreeMap(paramMap)
        val str = StringBuilder()
        for ((_, value) in sorted) {
            str.append(value).append("|")
        }
        str.append(signKey)
        log?.info("加密前：$str")
        val md5Str = MD5.encrypt(str.toString())
        log?.info("加密后：$md5Str")
        return md5Str
    }

    /**
     * 签名校验
     * @param paramMap
     * @param signKey
     * @return
     */
    fun isSignEquals(paramMap: MutableMap<String, Any?>, signKey: String?): Boolean {
        val sign = paramMap["sign"] as String?
        val md5Str = getSign(paramMap, signKey)
        return sign == md5Str
    }

    /**
     * 获取时间戳
     * @return
     */
    val timestamp: Long
        get() = Date().time

    /**
     * 封装同步请求
     * @param paramMap
     * @param url
     * @return
     */
    fun sendRequest(paramMap: Map<String, Any?>, url: String): JSONObject {
        var result: String? = ""
        try {
            //封装post参数
            val postdata = StringBuilder()
            for ((key, value) in paramMap) {
                postdata.append(key).append("=")
                    .append(value).append("&")
            }
            log?.info(String.format("--> 发送请求：post data %1s", postdata))
            val reqData = postdata.toString().toByteArray(charset("utf-8"))
            val respdata = HttpUtil.doPost(url, reqData)
            result = respdata?.let { String(it) }
            log?.info(String.format("--> 应答结果：result data %1s", result))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return JSONObject.parseObject(result)
    }
}