package com.seclib.htbp.hosp.controller.api

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.hosp.service.HospitalSetService
import com.seclib.htbp.hosp.service.DepartmentService
import com.seclib.htbp.hosp.service.ScheduleService
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpServletRequest
import com.seclib.htbp.common.exception.HtbpException
import com.seclib.htbp.common.helper.HttpRequestHelper
import com.seclib.htbp.common.result.Result
import com.seclib.htbp.common.result.ResultCodeEnum
import com.seclib.htbp.hosp.service.HospitalService
import com.seclib.htbp.vo.hosp.ScheduleQueryVo
import com.seclib.htbp.vo.hosp.DepartmentQueryVo
import org.springframework.util.StringUtils

@RestController
@RequestMapping("/api/hosp") //@CrossOrigin
class ApiController {
    @Autowired
    private val hospitalService: HospitalService? = null

    @Autowired
    private val hospitalSetService: HospitalSetService? = null

    @Autowired
    private val departmentService: DepartmentService? = null

    @Autowired
    private val scheduleService: ScheduleService? = null
    @PostMapping("schedule/remove")
    fun removeSchedule(request: HttpServletRequest): Result<*> {
        val requstMap = request.parameterMap
        val paramMap = HttpRequestHelper.switchMap(request.parameterMap)
        val hospSign = paramMap["sign"] as String?
        val hoscode = paramMap["hoscode"] as String?
        val signKey = hospitalSetService!!.getSignKey(hoscode)
        val signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey)
        if (StringUtils.isEmpty(hoscode)) {
            throw HtbpException(ResultCodeEnum.PARAM_ERROR)
        }
        if (hospSign != signKeyMD5) {
            throw HtbpException(ResultCodeEnum.SIGN_ERROR)
        }
        val hosScheduleId = paramMap["hosScheduleId"] as String?
        scheduleService!!.remove(hoscode, hosScheduleId)
        return Result.ok<Any>()
    }

    @PostMapping("schedule/list")
    fun findSchedule(request: HttpServletRequest): Result<*> {
        val requstMap = request.parameterMap
        val paramMap = HttpRequestHelper.switchMap(request.parameterMap)
        val hoscode = paramMap!!["hoscode"] as String?
        val depcode = paramMap["depcode"] as String?
        val page = if (StringUtils.isEmpty(paramMap["page"])) 1 else paramMap["page"].toString().toInt ()
        val limit = if (StringUtils.isEmpty(paramMap["limit"])) 10 else paramMap["limit"].toString().toInt ()
        if (StringUtils.isEmpty(hoscode)) {
            throw HtbpException(ResultCodeEnum.PARAM_ERROR)
        }
        val hospSign = paramMap["sign"] as String?
        val signKey = hospitalSetService!!.getSignKey(hoscode)
        val signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey)
        if (hospSign != signKeyMD5) {
            throw HtbpException(ResultCodeEnum.SIGN_ERROR)
        }
        val scheduleQueryVo = ScheduleQueryVo()
        scheduleQueryVo.hoscode = hoscode
        scheduleQueryVo.depcode = depcode
        val pageModel = scheduleService!!.selectPage(page, limit, scheduleQueryVo)
        return Result.ok(pageModel)
    }

    @PostMapping("saveSchedule")
    fun saveSchedule(request: HttpServletRequest): Result<*>? {
        val requstMap = request.parameterMap
        val paramMap = HttpRequestHelper.switchMap(requstMap)
        val hospSign = paramMap!!["sign"] as String?
        val hoscode = paramMap["hoscode"] as String?
        val signKey = hospitalSetService!!.getSignKey(hoscode)
        val signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey)
        if (hospSign != signKeyMD5) {
            throw HtbpException(ResultCodeEnum.SIGN_ERROR)
        }
        scheduleService!!.save(paramMap)
        return Result.ok<Any?>()
    }

    @PostMapping("department/remove")
    fun removeDepartment(request: HttpServletRequest?): Result<*>? {
        val requstMap = request!!.parameterMap
        val paramMap = HttpRequestHelper.switchMap(requstMap)
        val hoscode = paramMap!!["hoscode"] as String?
        val depcode = paramMap["depcode"] as String?
        val hospSign = paramMap["sign"] as String?
        val signKey = hospitalSetService!!.getSignKey(hoscode)
        val signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey)
        if (hospSign != signKeyMD5) {
            throw HtbpException(ResultCodeEnum.SIGN_ERROR)
        }
        departmentService!!.remove(hoscode, depcode)
        return Result.ok<Any?>()
    }

    @PostMapping("department/list")
    fun findDepartment(request: HttpServletRequest?): Result<*>? {
        val requstMap = request!!.parameterMap
        val paramMap = HttpRequestHelper.switchMap(requstMap)
        val hoscode = paramMap!!["hoscode"] as String?
        val page = if (StringUtils.isEmpty(paramMap["page"])) 1 else paramMap["page"].toString().toInt ()
        val limit = if (StringUtils.isEmpty(paramMap["limit"])) 1 else paramMap["limit"].toString().toInt ()
        val departmentQueryVo = DepartmentQueryVo()
        departmentQueryVo.hoscode = hoscode
        val pageModel = departmentService!!.findPageDepartment(page, limit, departmentQueryVo)
        return Result.ok(pageModel)
    }

    @PostMapping("saveDepartment")
    fun saveDepartment(request: HttpServletRequest?): Result<*>? {
        val requstMap = request!!.parameterMap
        val paramMap = HttpRequestHelper.switchMap(requstMap)
        val hospSign = paramMap["sign"]
        val hoscode = paramMap["hoscode"].toString()
        val signKey = hospitalSetService!!.getSignKey(hoscode)
        val signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey)
        if (hospSign != signKeyMD5) {
            throw HtbpException(ResultCodeEnum.SIGN_ERROR)
        }
        departmentService!!.save(paramMap)
        return Result.ok<Any?>()
    }

    @PostMapping("hospital/show")
    fun getHospital(request: HttpServletRequest?): Result<*>? {
        val requstMap = request!!.parameterMap
        val paramMap = HttpRequestHelper.switchMap(requstMap)
        val hospSign = paramMap!!["sign"] as String?
        val hoscode = paramMap["hoscode"] as String?
        val signKey = hospitalSetService!!.getSignKey(hoscode)
        val signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey)
        if (hospSign != signKeyMD5) {
            throw HtbpException(ResultCodeEnum.SIGN_ERROR)
        }
        val hospital = hospitalService!!.getByHoscode(hoscode)
        return Result.ok(hospital)
    }

    @PostMapping("saveHospital")
    fun saveHosp(request: HttpServletRequest?): Result<*>? {
        val requstMap = request!!.parameterMap
        val paramMap = HttpRequestHelper.switchMap(requstMap)
        var logoData = paramMap!!["logoData"] as String?
        logoData = logoData!!.replace(" ", "+")
        paramMap["logoData"] = logoData
        val hospSign = paramMap["sign"].toString()
        val hoscode = paramMap["hoscode"].toString()
        val signKey = hospitalSetService!!.getSignKey(hoscode)
        val signKeyMD5 = HttpRequestHelper.getSign(paramMap, signKey)
        if (hospSign != signKeyMD5) {
            throw HtbpException(ResultCodeEnum.SIGN_ERROR)
        }
        hospitalService!!.save(paramMap)
        return Result.ok<Any?>()
    }
}