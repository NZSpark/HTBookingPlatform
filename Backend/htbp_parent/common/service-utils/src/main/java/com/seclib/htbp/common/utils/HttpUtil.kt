package com.seclib.htbp.common.utils

import lombok.extern.slf4j.Slf4j
import org.reflections.Reflections.log
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import kotlin.Throws
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

/**
 *
 */
@Slf4j
object HttpUtil {
    const val POST = "POST"
    const val GET = "GET"
    const val CONN_TIMEOUT = 30000 // ms
    const val READ_TIMEOUT = 30000 // ms

    /**
     * post 方式发送http请求.
     *
     * @param strUrl
     * @param reqData
     * @return
     */
    fun doPost(strUrl: String?, reqData: ByteArray?): ByteArray? {
        return send(strUrl, POST, reqData)
    }

    /**
     * get方式发送http请求.
     *
     * @param strUrl
     * @return
     */
    fun doGet(strUrl: String?): ByteArray? {
        return send(strUrl, GET, null)
    }

    /**
     * @param strUrl
     * @param reqmethod
     * @param reqData
     * @return
     */
    fun send(strUrl: String?, reqmethod: String, reqData: ByteArray?): ByteArray? {
        return try {
            val url = URL(strUrl)
            val httpcon = url.openConnection() as HttpURLConnection
            httpcon.doOutput = true
            httpcon.doInput = true
            httpcon.useCaches = false
            httpcon.instanceFollowRedirects = true
            httpcon.connectTimeout = CONN_TIMEOUT
            httpcon.readTimeout = READ_TIMEOUT
            httpcon.requestMethod = reqmethod
            httpcon.connect()
            if (reqmethod.equals(POST, ignoreCase = true)) {
                val os = httpcon.outputStream
                os.write(reqData)
                os.flush()
                os.close()
            }
            val `in` = BufferedReader(InputStreamReader(httpcon.inputStream, "utf-8"))
            var inputLine: String?
            val bankXmlBuffer = StringBuilder()
            while (`in`.readLine().also { inputLine = it } != null) {
                bankXmlBuffer.append(inputLine)
            }
            `in`.close()
            httpcon.disconnect()
            bankXmlBuffer.toString().toByteArray()
        } catch (ex: Exception) {
            log?.error(ex.toString(), ex)
            null
        }
    }

    /**
     * 从输入流中读取数据
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun readInputStream(inStream: InputStream): ByteArray {
        val outStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len = 0
        while (inStream.read(buffer).also { len = it } != -1) {
            outStream.write(buffer, 0, len)
        }
        val data = outStream.toByteArray() // 网页的二进制数据
        outStream.close()
        inStream.close()
        return data
    }
}