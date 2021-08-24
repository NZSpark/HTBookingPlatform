package com.seclib.htbp.hosp.controller

import com.seclib.htbp.common.result.Result
import com.seclib.htbp.hosp.service.ScheduleService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/admin/hosp/department") //@CrossOrigin
class ScheduleController {
    @Autowired
    private val scheduleService: ScheduleService? = null
    @ApiOperation(value = "get Schedule Rule.")
    @GetMapping("getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    fun getScheduleRule(
        @PathVariable page: Long?,
        @PathVariable limit: Long?,
        @PathVariable hoscode: String?,
        @PathVariable depcode: String?
    ): Result<*> {
        val map = scheduleService!!.getScheduleRule(page, limit, hoscode, depcode)
        return Result.ok(map)
    }

    @ApiOperation(value = "get Schedule Detail")
    @GetMapping("getScheduleDetail/{hoscode}/{depcode}/{workDate}")
    fun getScheduleDetail(
        @PathVariable hoscode: String?,
        @PathVariable depcode: String?,
        @PathVariable workDate: String?
    ): Result<*> {
        val list = scheduleService!!.getScheduleDetail(hoscode, depcode, workDate)
        return Result.ok(list)
    }
}