package com.seclib.htbp.hosp.controller.api

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.hosp.service.HospitalSetService
import com.seclib.htbp.hosp.service.DepartmentService
import com.seclib.htbp.hosp.service.ScheduleService

import com.seclib.htbp.common.result.Result
import com.seclib.htbp.hosp.service.HospitalService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import com.seclib.htbp.vo.hosp.HospitalQueryVo
import io.swagger.annotations.ApiParam
import com.seclib.htbp.vo.hosp.ScheduleOrderVo
import com.seclib.htbp.vo.order.SignInfoVo

@RestController
@RequestMapping("/api/hosp/hospital") //@CrossOrigin
class HospApiController {
    @Autowired
    private val hospitalService: HospitalService? = null

    @Autowired
    var hospitalSetService: HospitalSetService? = null

    @Autowired
    private val departmentService: DepartmentService? = null

    @Autowired
    private val scheduleService: ScheduleService? = null
    @ApiOperation(value = "Query Hospital List")
    @GetMapping("findHospList/{page}/{limit}")
    fun findHospList(
        @PathVariable page: Int?,
        @PathVariable limit: Int?,
        hospitalQueryVo: HospitalQueryVo?
    ): Result<*>? {
        val hospitals = hospitalService!!.selectHospPage(page, limit, hospitalQueryVo)
        return Result.ok(hospitals)
    }

    @GetMapping("findByHosName/{hosname}")
    fun findByHosName(@PathVariable hosname: String?): Result<*>? {
        val hospitalList = hospitalService!!.findByHosName(hosname)
        return Result.ok(hospitalList)
    }

    @GetMapping("department/{hoscode}")
    fun index(@PathVariable hoscode: String?): Result<*>? {
        val list = departmentService!!.findDeptTree(hoscode)
        return Result.ok(list)
    }

    @GetMapping("findHospDetail/{hoscode}")
    fun item(@PathVariable hoscode: String?): Result<*>? {
        val map = hospitalService!!.item(hoscode)
        return Result.ok(map)
    }

    @ApiOperation(value = "???????????????????????????")
    @GetMapping("auth/getBookingScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    fun getBookingSchedule(
        @ApiParam(name = "page", value = "????????????", required = true) @PathVariable page: Int?,
        @ApiParam(name = "limit", value = "???????????????", required = true) @PathVariable limit: Int?,
        @ApiParam(name = "hoscode", value = "??????code", required = true) @PathVariable hoscode: String?,
        @ApiParam(name = "depcode", value = "??????code", required = true) @PathVariable depcode: String?
    ): Result<*>? {
        return Result.ok(
            scheduleService!!.getBookingScheduleRule(page, limit, hoscode, depcode)
        )
    }

    @ApiOperation(value = "??????????????????")
    @GetMapping("auth/findScheduleList/{hoscode}/{depcode}/{workDate}")
    fun findScheduleList(
        @ApiParam(name = "hoscode", value = "??????code", required = true) @PathVariable hoscode: String?,
        @ApiParam(name = "depcode", value = "??????code", required = true) @PathVariable depcode: String?,
        @ApiParam(name = "workDate", value = "????????????", required = true) @PathVariable workDate: String?
    ): Result<*>? {
        return Result.ok(
            scheduleService!!.getScheduleDetail(hoscode, depcode, workDate)
        )
    }

    @ApiOperation(value = "????????????id??????????????????")
    @GetMapping("getSchedule/{scheduleId}")
    fun getSchedule(
        @ApiParam(name = "scheduleId", value = "??????id", required = true) @PathVariable scheduleId: String?
    ): Result<*>? {
        return Result.ok(scheduleService!!.getById(scheduleId))
    }

    @ApiOperation(value = "????????????id????????????????????????")
    @GetMapping("inner/getScheduleOrderVo/{scheduleId}")
    fun getScheduleOrderVo(
        @ApiParam(name = "scheduleId", value = "??????id", required = true) @PathVariable("scheduleId") scheduleId: String?
    ): ScheduleOrderVo? {
        return scheduleService!!.getScheduleOrderVo(scheduleId)
    }

    @ApiOperation(value = "????????????????????????")
    @GetMapping("inner/getSignInfoVo/{hoscode}")
    fun getSignInfoVo(
        @ApiParam(name = "hoscode", value = "??????code", required = true) @PathVariable("hoscode") hoscode: String?
    ): SignInfoVo? {
        return hospitalSetService!!.getSignInfoVo(hoscode)
    }
}