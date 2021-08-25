package com.seclib.htbp.hosp.service


import com.seclib.htbp.model.hosp.Schedule
import com.seclib.htbp.vo.hosp.ScheduleQueryVo
import com.seclib.htbp.vo.hosp.ScheduleOrderVo
import org.springframework.data.domain.Page

interface ScheduleService {
    fun save(paramMap: MutableMap<String, Any?>?)
    fun selectPage(page: Int?, limit: Int?, scheduleQueryVo: ScheduleQueryVo?): Page<Schedule?>?
    fun remove(hoscode: String?, hosScheduleId: String?)
    fun getScheduleRule(page: Long?, limit: Long?, hoscode: String?, depcode: String?): Map<String?, Any?>?
    fun getScheduleDetail(hoscode: String?, depcode: String?, workDate: String?): List<Schedule?>?
    fun getBookingScheduleRule(page: Int?, limit: Int?, hoscode: String?, depcode: String?): Map<String?, Any?>?
    fun getById(id: String?): Schedule?

    //根据排班id获取预约下单数据
    fun getScheduleOrderVo(scheduleId: String?): ScheduleOrderVo?

    //for mq
    fun update(schedule: Schedule?)
}