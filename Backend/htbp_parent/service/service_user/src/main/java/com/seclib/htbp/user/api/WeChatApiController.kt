package com.seclib.htbp.user.api

import com.alibaba.fastjson.JSONObject
import com.seclib.htbp.common.helper.JwtHelper.createToken
import com.seclib.htbp.common.result.Result.Companion.ok
import org.springframework.web.bind.annotation.RequestMapping
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.user.service.UserInfoService
import org.springframework.web.bind.annotation.GetMapping
import com.seclib.htbp.common.exception.HtbpException
import java.lang.StringBuffer
import com.seclib.htbp.user.utils.ConstantWxPropertiesUtils
import java.lang.Exception
import java.util.HashMap
import org.springframework.web.bind.annotation.ResponseBody
import java.io.UnsupportedEncodingException
import com.seclib.htbp.common.result.Result
import com.seclib.htbp.common.result.ResultCodeEnum
import com.seclib.htbp.model.user.UserInfo
import com.seclib.htbp.user.utils.HttpClientUtils.get
import org.apache.commons.lang.StringUtils
import org.reflections.Reflections.log
import org.springframework.stereotype.Controller
import java.net.URLEncoder

@Controller //no response data.
@RequestMapping("/api/ucenter/wx")
@Slf4j
class WeChatApiController {
    @Autowired
    private val userInfoService: UserInfoService? = null
    @GetMapping("callback")
    fun callback(code: String, state: String): String? {
        //get certificial code
//        String code = "011Ii6000q9NfM1dT4100xtYqd2Ii60S" ;
//        String state = "1629182602530" ;
        // code+ id + secret +url -> access token
        // %s placeholder
        //获取授权临时票据
        println("微信授权服务器回调。。。。。。")
        println("state = $state")
        println("code = $code")
        if (StringUtils.isEmpty(state) || StringUtils.isEmpty(code)) {
            throw HtbpException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR)
        }

        //使用code和appid以及appscrect换取access_token
        val baseAccessTokenUrl = StringBuffer()
            .append("https://api.weixin.qq.com/sns/oauth2/access_token")
            .append("?appid=%s")
            .append("&secret=%s")
            .append("&code=%s")
            .append("&grant_type=authorization_code")
        val accessTokenUrl = String.format(
            baseAccessTokenUrl.toString(),
            ConstantWxPropertiesUtils.Companion.WX_OPEN_APP_ID,
            ConstantWxPropertiesUtils.Companion.WX_OPEN_APP_SECRET,
            code
        )
        var result: String? = null
        try {
            result = get(accessTokenUrl)
        } catch (e: Exception) {
            throw HtbpException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD)
        }
        println("使用code换取的access_token结果 = $result")
        val resultJson = JSONObject.parseObject(result)
        if (resultJson.getString("errcode") != null) {
            log?.error("获取access_token失败：" + resultJson.getString("errcode") + resultJson.getString("errmsg"))
            throw HtbpException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD)
        }
        val accessToken = resultJson.getString("access_token")
        val openId = resultJson.getString("openid")
        log?.info(accessToken)
        log?.info(openId)
        var userInfo = userInfoService!!.selectWxInfoOpenId(openId)
        if (userInfo == null) {
            //根据access_token获取微信用户的基本信息
            //先根据openid进行数据库查询
            // UserInfo userInfo = userInfoService.getByOpenid(openId);
            // 如果没有查到用户信息,那么调用微信个人信息获取的接口
            // if(null == userInfo){
            //如果查询到个人信息，那么直接进行登录
            //使用access_token换取受保护的资源：微信的个人信息
            val baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s"
            val userInfoUrl = String.format(baseUserInfoUrl, accessToken, openId)
            var resultUserInfo: String? = null
            try {
                resultUserInfo = get(userInfoUrl)
            } catch (e: Exception) {
                throw HtbpException(ResultCodeEnum.FETCH_USERINFO_ERROR)
            }
            println("使用access_token获取用户信息的结果 = $resultUserInfo")
            val resultUserInfoJson = JSONObject.parseObject(resultUserInfo)
            if (resultUserInfoJson.getString("errcode") != null) {
                log?.error(
                    "获取用户信息失败：" + resultUserInfoJson.getString("errcode") + resultUserInfoJson.getString(
                        "errmsg"
                    )
                )
                throw HtbpException(ResultCodeEnum.FETCH_USERINFO_ERROR)
            }

            //解析用户信息
            val nickname = resultUserInfoJson.getString("nickname")
            val headimgurl = resultUserInfoJson.getString("headimgurl")
            userInfo = UserInfo()
            userInfo.openid = openId
            userInfo.nickName =nickname
            userInfo.status = 1
            userInfoService.save(userInfo)
        }
        return try {
            val map = mutableMapOf<String,String?>()
            var name: String? = userInfo.name
            if (StringUtils.isEmpty(name)) {
                name = userInfo.nickName
            }
            if (StringUtils.isEmpty(name)) {
                name = userInfo.phone
            }
            map["name"] = name
            if (StringUtils.isEmpty(userInfo.phone)) {
                map["openid"] = userInfo.openid
            } else {
                map["openid"] = ""
            }
            val token = createToken(userInfo.id, name)
            map["token"] = token
            "redirect:" + ConstantWxPropertiesUtils.Companion.HTBP_BASE_URL + "/wechat/callback?token=" + map["token"] + "&openid=" + map["openid"] + "&name=" + URLEncoder.encode(
                map["name"] as String?,
                "utf-8"
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        // return "";
    }

    //1.generate QR code
    @GetMapping("getLoginParam")
    @ResponseBody //have response data
    fun genQrConnect(): Result<*>? {
        return try {
            val map: MutableMap<String, Any?> = HashMap()
            map["appid"] = ConstantWxPropertiesUtils.Companion.WX_OPEN_APP_ID
            map["scope"] = "snsapi_login"
            map["redirect_uri"] = URLEncoder.encode(ConstantWxPropertiesUtils.Companion.WX_OPEN_REDIRECT_URL, "utf-8")
            map["state"] = System.currentTimeMillis().toString() + ""
            ok<Map<String, Any?>>(map)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            //return Result.fail();
            null
        }
    } //
}