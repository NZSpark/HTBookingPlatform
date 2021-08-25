package com.seclib.htbp.user.utils


import java.lang.Exception
import kotlin.Throws
import java.net.SocketTimeoutException
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.Consts
import kotlin.jvm.JvmOverloads
import org.apache.http.client.methods.HttpGet
import java.security.GeneralSecurityException
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.X509HostnameVerifier
import javax.net.ssl.SSLSession
import java.io.IOException
import javax.net.ssl.SSLException
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.commons.io.IOUtils
import org.apache.commons.lang.StringUtils
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.conn.ConnectTimeoutException
import org.apache.http.conn.ssl.TrustStrategy
import org.apache.http.entity.ContentType
import org.apache.http.ssl.SSLContextBuilder
import java.security.cert.X509Certificate
import java.util.ArrayList
import javax.net.ssl.SSLSocket

object HttpClientUtils {
    const val connTimeout = 10000
    const val readTimeout = 10000
    const val charset = "UTF-8"
    private var client: HttpClient? = null
    @Throws(ConnectTimeoutException::class, SocketTimeoutException::class, Exception::class)
    fun postParameters(url: String, parameterStr: String?): String {
        return post(url, parameterStr, "application/x-www-form-urlencoded", charset, connTimeout, readTimeout)
    }

    @Throws(ConnectTimeoutException::class, SocketTimeoutException::class, Exception::class)
    fun postParameters(
        url: String,
        parameterStr: String?,
        charset: String?,
        connTimeout: Int?,
        readTimeout: Int?
    ): String {
        return post(url, parameterStr, "application/x-www-form-urlencoded", charset, connTimeout, readTimeout)
    }

    @Throws(ConnectTimeoutException::class, SocketTimeoutException::class, Exception::class)
    fun postParameters(url: String, params: Map<String?, String?>?): String {
        return postForm(url, params, null, connTimeout, readTimeout)
    }

    @Throws(ConnectTimeoutException::class, SocketTimeoutException::class, Exception::class)
    fun postParameters(url: String, params: Map<String?, String?>?, connTimeout: Int?, readTimeout: Int?): String {
        return postForm(url, params, null, connTimeout, readTimeout)
    }


    @JvmName("get1")
    @Throws(Exception::class)
    fun get(url: String): String {
        return HttpClientUtils[url, null, null]
    }
    @JvmName("get1")
    @Throws(Exception::class)
    fun get(url: String, charset: String?): String {
        return HttpClientUtils[url, charset, connTimeout, readTimeout]
    }

    /**
     * 发送一个 Post 请求, 使用指定的字符集编码.
     *
     * @param url
     * @param body RequestBody
     * @param mimeType 例如 application/xml "application/x-www-form-urlencoded" a=1&b=2&c=3
     * @param charset 编码
     * @param connTimeout 建立链接超时时间,毫秒.
     * @param readTimeout 响应超时时间,毫秒.
     * @return ResponseBody, 使用指定的字符集编码.
     * @throws ConnectTimeoutException 建立链接超时异常
     * @throws SocketTimeoutException  响应超时
     * @throws Exception
     */
    @Throws(ConnectTimeoutException::class, SocketTimeoutException::class, Exception::class)
    fun post(
        url: String,
        body: String?,
        mimeType: String?,
        charset: String?,
        connTimeout: Int?,
        readTimeout: Int?
    ): String {
        var client: HttpClient? = null
        val post = HttpPost(url)
        var result = ""
        try {
            if (StringUtils.isNotBlank(body)) {
                val entity: HttpEntity = StringEntity(body, ContentType.create(mimeType, charset))
                post.entity = entity
            }
            // 设置参数
            val customReqConf = RequestConfig.custom()
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout)
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout)
            }
            post.config = customReqConf.build()
            val res: HttpResponse
            if (url.startsWith("https")) {
                // 执行 Https 请求.
                client = createSSLInsecureClient()
                res = client.execute(post)
            } else {
                // 执行 Http 请求.
                client = HttpClientUtils.client
                res = client!!.execute(post)
            }
            result = IOUtils.toString(res.entity.content, charset)
        } finally {
            post.releaseConnection()
            if (url.startsWith("https") && client != null && client is CloseableHttpClient) {
                client.close()
            }
        }
        return result
    }

    /**
     * 提交form表单
     *
     * @param url
     * @param params
     * @param connTimeout
     * @param readTimeout
     * @return
     * @throws ConnectTimeoutException
     * @throws SocketTimeoutException
     * @throws Exception
     */
    @Throws(ConnectTimeoutException::class, SocketTimeoutException::class, Exception::class)
    fun postForm(
        url: String,
        params: Map<String?, String?>?,
        headers: Map<String?, String?>?,
        connTimeout: Int?,
        readTimeout: Int?
    ): String {
        var client: HttpClient? = null
        val post = HttpPost(url)
        return try {
            if (params != null && !params.isEmpty()) {
                val formParams: MutableList<NameValuePair> = ArrayList()
                val entrySet: Set<Map.Entry<String?, String?>> = params.entries
                for (entry in entrySet) {
                    formParams.add(BasicNameValuePair(entry.key, entry.value))
                }
                val entity = UrlEncodedFormEntity(formParams, Consts.UTF_8)
                post.entity = entity
            }
            if (headers != null && !headers.isEmpty()) {
                for ((key, value) in headers) {
                    post.addHeader(key, value)
                }
            }
            // 设置参数
            val customReqConf = RequestConfig.custom()
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout)
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout)
            }
            post.config = customReqConf.build()
            var res: HttpResponse? = null
            if (url.startsWith("https")) {
                // 执行 Https 请求.
                client = createSSLInsecureClient()
                res = client.execute(post)
            } else {
                // 执行 Http 请求.
                client = HttpClientUtils.client
                res = client!!.execute(post)
            }
            IOUtils.toString(res!!.entity.content, "UTF-8")
        } finally {
            post.releaseConnection()
            if (url.startsWith("https") && client != null && client is CloseableHttpClient) {
                client.close()
            }
        }
    }

    /**
     * 发送一个 GET 请求
     */
    @JvmOverloads
    @Throws(ConnectTimeoutException::class, SocketTimeoutException::class, Exception::class)
    operator fun get(
        url: String,
        charset: String? = this.charset,
        connTimeout: Int? = null,
        readTimeout: Int? = null
    ): String {
        var client: HttpClient? = null
        val get = HttpGet(url)
        var result = ""
        try {
            // 设置参数
            val customReqConf = RequestConfig.custom()
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout)
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout)
            }
            get.config = customReqConf.build()
            var res: HttpResponse? = null
            if (url.startsWith("https")) {
                // 执行 Https 请求.
                client = createSSLInsecureClient()
                res = client.execute(get)
            } else {
                // 执行 Http 请求.
                client = HttpClientUtils.client
                res = client!!.execute(get)
            }
            result = IOUtils.toString(res!!.entity.content, charset)
        } finally {
            get.releaseConnection()
            if (url.startsWith("https") && client != null && client is CloseableHttpClient) {
                client.close()
            }
        }
        return result
    }

    /**
     * 从 response 里获取 charset
     */
    private fun getCharsetFromResponse(ressponse: HttpResponse): String? {
        // Content-Type:text/html; charset=GBK
        if (ressponse.entity != null && ressponse.entity.contentType != null && ressponse.entity.contentType.value != null) {
            val contentType = ressponse.entity.contentType.value
            if (contentType.contains("charset=")) {
                return contentType.substring(contentType.indexOf("charset=") + 8)
            }
        }
        return null
    }

    /**
     * 创建 SSL连接
     * @return
     * @throws GeneralSecurityException
     */
    @Throws(GeneralSecurityException::class)
    private fun createSSLInsecureClient(): CloseableHttpClient {
        return try {
            val sslContext =
                SSLContextBuilder().loadTrustMaterial(null, TrustStrategy { chain, authType -> true }).build()
            val sslsf = SSLConnectionSocketFactory(sslContext, object : X509HostnameVerifier {
                override fun verify(arg0: String, arg1: SSLSession): Boolean {
                    return true
                }

                @Throws(IOException::class)
                override fun verify(host: String, ssl: SSLSocket) {
                }

                @Throws(SSLException::class)
                override fun verify(host: String, cert: X509Certificate) {
                }

                @Throws(SSLException::class)
                override fun verify(
                    host: String, cns: Array<String>,
                    subjectAlts: Array<String>
                ) {
                }
            })
            HttpClients.custom().setSSLSocketFactory(sslsf).build()
        } catch (e: GeneralSecurityException) {
            throw e
        }
    }

    init {
        val cm = PoolingHttpClientConnectionManager()
        cm.maxTotal = 128
        cm.defaultMaxPerRoute = 128
        client = HttpClients.custom().setConnectionManager(cm).build()
    }
}