package com.seclib.htbp.hosp.controller;

import com.seclib.htbp.common.exception.HtbpException;
import com.seclib.htbp.common.result.Result;
import com.seclib.htbp.common.result.ResultCodeEnum;
import com.seclib.htbp.common.utils.MD5;
import com.seclib.htbp.common.utils.helper.HttpRequestHelper;
import com.seclib.htbp.hosp.repository.HospitalRepository;
import com.seclib.htbp.hosp.service.HospitalService;
import com.seclib.htbp.hosp.service.HospitalSetService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalSetService hospitalSetService;

    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request){
        Map<String, String[]> requstMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requstMap);

        String  logoData = (String) paramMap.get("logoData");
        logoData = logoData.replace(" ","+");
        paramMap.put("logoData",logoData);

        String hospSign = (String)paramMap.get("sign");
        String hoscode = (String)paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey);

        if(!hospSign.equals(signKeyMD5)){
            throw new HtbpException(ResultCodeEnum.SIGN_ERROR);
        }


        hospitalService.save(paramMap);
        return Result.ok();
    }
}
