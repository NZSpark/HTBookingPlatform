package com.seclib.htbp.user.api;


import com.seclib.htbp.common.result.Result;
import com.seclib.htbp.user.utils.ConstantWxPropertiesUtils;
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
public class WeixinApiController {
    @GetMapping("callback")
    public String callback(@PathVariable String code,@PathVariable String state){

        return "";

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
