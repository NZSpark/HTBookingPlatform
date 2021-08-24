package com.seclib.htbp.hosp.repository

import org.springframework.data.mongodb.repository.MongoRepository
import com.seclib.htbp.model.hosp.Schedule
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ScheduleRepository : MongoRepository<Schedule?, String?> {
    fun getScheduleByHoscodeAndHosScheduleId(hoscode: String?, hosScheduleId: String?): Schedule?
    fun findScheduleByHoscodeAndDepcodeAndWorkDate(hoscode: String?, depcode: String?, toDate: Date?): List<Schedule?>?
    fun getScheduleById(scheduleId: String?): Schedule?
}