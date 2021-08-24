package com.seclib.htbp.hosp.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import com.seclib.htbp.vo.hosp.ScheduleOrderVo
import com.seclib.htbp.vo.order.SignInfoVo
import org.springframework.stereotype.Repository

@FeignClient(value = "service-hosp")
@Repository
interface HospitalFeignClient {
    /**
     * 根据排班id获取预约下单数据
     */
    @GetMapping("/api/hosp/hospital/inner/getScheduleOrderVo/{scheduleId}")
    fun getScheduleOrderVo(@PathVariable("scheduleId") scheduleId: String?): ScheduleOrderVo?

    /**
     * 获取医院签名信息
     */
    @GetMapping("/api/hosp/hospital/inner/getSignInfoVo/{hoscode}")
    fun getSignInfoVo(@PathVariable("hoscode") hoscode: String?): SignInfoVo?
}