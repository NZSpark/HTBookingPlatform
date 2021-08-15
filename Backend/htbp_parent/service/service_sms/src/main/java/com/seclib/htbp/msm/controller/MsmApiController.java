package com.seclib.htbp.msm.controller;


import com.seclib.htbp.common.result.Result;
import com.seclib.htbp.msm.service.MsmService;
import com.seclib.htbp.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/api/msm")
public class MsmApiController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{phone}")
    public Result sendCode(@PathVariable String phone){

        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return Result.ok();
        }

        code = RandomUtil.getSixBitRandom();
        boolean isSend =  msmService.send(phone,code);

        if(isSend){
            redisTemplate.opsForValue().set(phone,code,2, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.fail().message("SMS send error.");
        }

    }

}
