package com.seclib.htbp.hosp.controller;

import com.seclib.htbp.common.exception.HtbpException;
import com.seclib.htbp.common.result.Result;
import com.seclib.htbp.common.result.ResultCodeEnum;
import com.seclib.htbp.common.utils.MD5;
import com.seclib.htbp.common.utils.helper.HttpRequestHelper;
import com.seclib.htbp.hosp.repository.HospitalRepository;
import com.seclib.htbp.hosp.service.DepartmentService;
import com.seclib.htbp.hosp.service.HospitalService;
import com.seclib.htbp.hosp.service.HospitalSetService;
import com.seclib.htbp.hosp.service.ScheduleService;
import com.seclib.htbp.model.hosp.Department;
import com.seclib.htbp.model.hosp.Hospital;
import com.seclib.htbp.model.hosp.Schedule;
import com.seclib.htbp.vo.hosp.DepartmentQueryVo;
import com.seclib.htbp.vo.hosp.ScheduleQueryVo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
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

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request) {
        Map<String, String[]> requstMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        String hospSign = (String)paramMap.get("sign");
        String hoscode = (String)paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey);

        if(StringUtils.isEmpty(hoscode)) {
            throw new HtbpException(ResultCodeEnum.PARAM_ERROR);
        }

        if(!hospSign.equals(signKeyMD5)){
            throw new HtbpException(ResultCodeEnum.SIGN_ERROR);
        }

        String hosScheduleId = (String)paramMap.get("hosScheduleId");

        scheduleService.remove(hoscode,hosScheduleId);

        return Result.ok();
    }

    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request) {
        Map<String, String[]> requstMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        String hoscode = (String)paramMap.get("hoscode");
        String depcode = (String)paramMap.get("depcode");
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));

        if(StringUtils.isEmpty(hoscode)) {
            throw new HtbpException(ResultCodeEnum.PARAM_ERROR);
        }

        String hospSign = (String)paramMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey);

        if(!hospSign.equals(signKeyMD5)){
            throw new HtbpException(ResultCodeEnum.SIGN_ERROR);
        }

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        Page<Schedule> pageModel = scheduleService.selectPage(page , limit, scheduleQueryVo);
        return Result.ok(pageModel);
    }


    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        Map<String, String[]> requstMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requstMap);

        String hospSign = (String)paramMap.get("sign");
        String hoscode = (String)paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey);

        if(!hospSign.equals(signKeyMD5)){
            throw new HtbpException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.save(paramMap);

        return Result.ok();

    }



    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        Map<String, String[]> requstMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requstMap);
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");

        String hospSign = (String)paramMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey);

        if(!hospSign.equals(signKeyMD5)) {
            throw new HtbpException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.remove(hoscode,depcode);
        return Result.ok();
    }
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request){
        Map<String, String[]> requstMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requstMap);
        String hoscode = (String)paramMap.get("hoscode");

        Integer page = StringUtils.isEmpty(paramMap.get("page"))? 1 : Integer.parseInt((String) paramMap.get("page"));
        Integer limit = StringUtils.isEmpty(paramMap.get("limit"))? 1 : Integer.parseInt((String) paramMap.get("limit"));

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);

        Page<Department> pageModel =  departmentService.findPageDepartment(page,limit,departmentQueryVo);
        return Result.ok(pageModel);
    }

    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        Map<String, String[]> requstMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requstMap);

        String hospSign = (String)paramMap.get("sign");
        String hoscode = (String)paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey);

        if(!hospSign.equals(signKeyMD5)){
            throw new HtbpException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.save(paramMap);

        return Result.ok();

    }

    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request){
        Map<String, String[]> requstMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requstMap);

        String hospSign = (String)paramMap.get("sign");
        String hoscode = (String)paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey);

        if(!hospSign.equals(signKeyMD5)){
            throw new HtbpException(ResultCodeEnum.SIGN_ERROR);
        }

        Hospital hospital = hospitalService.getByHoscode(hoscode);

        return Result.ok(hospital);

    }

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
