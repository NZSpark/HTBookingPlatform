package com.seclib.htbp.user.api;

import com.alibaba.fastjson.JSONObject;
import com.seclib.htbp.common.helper.JwtHelper;
import com.seclib.htbp.model.user.UserInfo;
import com.seclib.htbp.user.service.UserInfoService;
import com.seclib.htbp.user.utils.ConstantWxPropertiesUtils;
import com.seclib.htbp.user.utils.HttpClientUtils;
import com.seclib.htbp.common.exception.HtbpException;
import com.seclib.htbp.common.result.Result;
import com.seclib.htbp.common.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller  //no response data.
@RequestMapping("/api/ucenter/wx")
@Slf4j
public class WeChatApiController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("callback")
    public String callback(String code,String state){
        //get certificial code
//        String code = "011Ii6000q9NfM1dT4100xtYqd2Ii60S" ;
//        String state = "1629182602530" ;
        // code+ id + secret +url -> access token
        // %s placeholder
        //获取授权临时票据
        System.out.println("微信授权服务器回调。。。。。。");
        System.out.println("state = " + state);
        System.out.println("code = " + code);

        if (StringUtils.isEmpty(state) || StringUtils.isEmpty(code)) {
            throw new HtbpException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }

        //使用code和appid以及appscrect换取access_token
        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");

        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantWxPropertiesUtils.WX_OPEN_APP_ID,
                ConstantWxPropertiesUtils.WX_OPEN_APP_SECRET,
                code);

        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            throw new HtbpException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }

        System.out.println("使用code换取的access_token结果 = " + result);

        JSONObject resultJson = JSONObject.parseObject(result);
        if(resultJson.getString("errcode") != null){
            log.error("获取access_token失败：" + resultJson.getString("errcode") + resultJson.getString("errmsg"));
            throw new HtbpException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }

        String accessToken = resultJson.getString("access_token");
        String openId = resultJson.getString("openid");
        log.info(accessToken);
        log.info(openId);

        UserInfo userInfo = userInfoService.selectWxInfoOpenId(openId);
        if(userInfo == null) {
            //根据access_token获取微信用户的基本信息
            //先根据openid进行数据库查询
            // UserInfo userInfo = userInfoService.getByOpenid(openId);
            // 如果没有查到用户信息,那么调用微信个人信息获取的接口
            // if(null == userInfo){
            //如果查询到个人信息，那么直接进行登录
            //使用access_token换取受保护的资源：微信的个人信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openId);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                throw new HtbpException(ResultCodeEnum.FETCH_USERINFO_ERROR);
            }
            System.out.println("使用access_token获取用户信息的结果 = " + resultUserInfo);

            JSONObject resultUserInfoJson = JSONObject.parseObject(resultUserInfo);
            if (resultUserInfoJson.getString("errcode") != null) {
            log.error("获取用户信息失败：" + resultUserInfoJson.getString("errcode") + resultUserInfoJson.getString("errmsg"));
                throw new HtbpException(ResultCodeEnum.FETCH_USERINFO_ERROR);
            }

            //解析用户信息
            String nickname = resultUserInfoJson.getString("nickname");
            String headimgurl = resultUserInfoJson.getString("headimgurl");

            userInfo = new UserInfo();
            userInfo.setOpenid(openId);
            userInfo.setNickName(nickname);
            userInfo.setStatus(1);
            userInfoService.save(userInfo);
        }

        try {
            Map<String, Object> map = new HashMap<>();
            String name = userInfo.getName();
            if (StringUtils.isEmpty(name)) {
                name = userInfo.getNickName();
            }
            if (StringUtils.isEmpty(name)) {
                name = userInfo.getPhone();
            }
            map.put("name", name);
            if (StringUtils.isEmpty(userInfo.getPhone())) {
                map.put("openid", userInfo.getOpenid());
            } else {
                map.put("openid", "");
            }
            String token = JwtHelper.createToken(userInfo.getId(), name);
            map.put("token", token);
            return "redirect:" + ConstantWxPropertiesUtils.HTBP_BASE_URL + "/wechat/callback?token=" + map.get("token") + "&openid=" + map.get("openid") + "&name=" + URLEncoder.encode((String) map.get("name"), "utf-8");
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // return "";
    }

    //1.generate QR code
    @GetMapping("getLoginParam")
    @ResponseBody  //have response data
    public Result genQrConnect(){
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("appid", ConstantWxPropertiesUtils.WX_OPEN_APP_ID);
            map.put("scope", "snsapi_login");
            map.put("redirect_uri", URLEncoder.encode(ConstantWxPropertiesUtils.WX_OPEN_REDIRECT_URL, "utf-8"));
            map.put("state", System.currentTimeMillis() + "");
            return Result.ok(map);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            //return Result.fail();
            return null;
        }
    }
    //
}
