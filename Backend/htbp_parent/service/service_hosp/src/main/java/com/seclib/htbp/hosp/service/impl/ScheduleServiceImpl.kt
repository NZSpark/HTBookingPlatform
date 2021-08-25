package com.seclib.htbp.hosp.service.impl

import com.alibaba.fastjson.JSONObject
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils
import com.baomidou.mybatisplus.extension.plugins.pagination.Page

import com.seclib.htbp.hosp.service.DepartmentService
import org.springframework.beans.factory.annotation.Autowired

import java.util.stream.Collectors
import com.seclib.htbp.common.exception.HtbpException
import com.seclib.htbp.common.result.ResultCodeEnum
import com.seclib.htbp.hosp.service.ScheduleService
import com.seclib.htbp.hosp.repository.ScheduleRepository
import com.seclib.htbp.hosp.service.HospitalService
import org.springframework.data.mongodb.core.MongoTemplate
import com.seclib.htbp.vo.hosp.ScheduleQueryVo

import com.seclib.htbp.vo.hosp.BookingScheduleRuleVo
import java.lang.Exception
import com.seclib.htbp.vo.hosp.ScheduleOrderVo
import com.seclib.htbp.model.hosp.BookingRule
import com.seclib.htbp.model.hosp.Schedule
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.format.DateTimeFormat
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.*
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
open class ScheduleServiceImpl : ScheduleService {
    @Autowired
    private val scheduleRepository: ScheduleRepository? = null

    @Autowired
    private val mongoTemplate: MongoTemplate? = null

    @Autowired
    private val hospitalService: HospitalService? = null

    @Autowired
    private val departmentService: DepartmentService? = null
    override fun selectPage(
        page: Int?,
        limit: Int?,
        scheduleQueryVo: ScheduleQueryVo?
    ): org.springframework.data.domain.Page<Schedule?>? {
        val sort =
            Sort.by(Sort.Direction.DESC, "createTime")
        val pageable: Pageable = PageRequest.of(page!! - 1, limit!!, sort)
        val schedule = Schedule()
        BeanUtils.copyProperties(scheduleQueryVo, schedule)
        schedule.isDeleted = 0
        val matcher = ExampleMatcher.matching() //构建对象
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
            .withIgnoreCase(true) //改变默认大小写忽略方式：忽略大小写

        //创建实例
        val example =
            Example.of(schedule, matcher)
        return scheduleRepository!!.findAll(example, pageable)
    }

    override fun remove(hoscode: String?, hosScheduleId: String?) {
        val scheduleExist = scheduleRepository!!.getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId)
        if (scheduleExist != null) {
            scheduleRepository.deleteById(scheduleExist.id)
        }
    }

    override fun getScheduleRule(page: Long?, limit: Long?, hoscode: String?, depcode: String?): Map<String?, Any?>? {
        //1. search by hoscode and depcoce
        val criteria = Criteria.where("hoscode").`is`(hoscode).and("depcode").`is`(depcode)

        //2. group by workdate
        //3. count
        //4. page
        val agg = Aggregation.newAggregation(
            Aggregation.match(criteria),
            Aggregation.group("workDate")
                .first("workDate").`as`("workDate")
                .count().`as`("docCount")
                .sum("reservedNumber").`as`("reservedNumber")
                .sum("availableNumber").`as`("availableNumber"),
            Aggregation.sort(Sort.Direction.DESC, "workDate"),
            Aggregation.skip((page!! - 1) * limit!!),
            Aggregation.limit(limit)
        )
        val aggResults = mongoTemplate!!.aggregate(agg, Schedule::class.java, BookingScheduleRuleVo::class.java)
        val bookingScheduleRuleList = aggResults.mappedResults
        val totalAgg = Aggregation.newAggregation(
            Aggregation.match(criteria),
            Aggregation.group("workDate")
        )
        val totalAggResults = mongoTemplate.aggregate(totalAgg, Schedule::class.java, BookingScheduleRuleVo::class.java)
        val total = totalAggResults.mappedResults.size

        //date to day
        for (bookingScheduleRuleVo in bookingScheduleRuleList) {
            val workDate = bookingScheduleRuleVo.workDate
            val dayOfWeek = getDayOfWeek(DateTime(workDate))
            bookingScheduleRuleVo.dayOfWeek = dayOfWeek
        }
        val result: MutableMap<String?, Any?> = HashMap()
        result["bookingScheduleRuleList"] = bookingScheduleRuleList
        result["total"] = total
        val hosName = hospitalService!!.getHospName(hoscode)
        val baseMap: MutableMap<String, String?> = HashMap()
        baseMap["hosName"] = hosName
        result["baseMap"] = baseMap
        return result
    }

    override fun getScheduleDetail(hoscode: String?, depcode: String?, workDate: String?): List<Schedule?>? {
        val scheduleList = scheduleRepository!!.findScheduleByHoscodeAndDepcodeAndWorkDate(
            hoscode,
            depcode,
            DateTime(workDate).toDate()
        )
        scheduleList?.stream()?.forEach { item: Schedule? ->
            if (item != null) {
                packSchedule(item)
            }
        }
        return scheduleList
    }

    override fun getById(id: String?): Schedule? {
        try {
            val schedule = scheduleRepository!!.findById(id).get()
            return packSchedule(schedule)
        } catch (e: Exception) {
//            e.printStackTrace();
        }
        return null
    }

    //根据排班id获取预约下单数据
    override fun getScheduleOrderVo(scheduleId: String?): ScheduleOrderVo? {
        val scheduleOrderVo = ScheduleOrderVo()
        //排班信息
        val schedule = scheduleRepository!!.getScheduleById(scheduleId)
            ?: throw HtbpException(ResultCodeEnum.PARAM_ERROR)

        //获取预约规则信息
        val hospital = hospitalService!!.getByHoscode(schedule.hoscode)
            ?: throw HtbpException(ResultCodeEnum.DATA_ERROR)
        val bookingRule = hospital.bookingRule
            ?: throw HtbpException(ResultCodeEnum.PARAM_ERROR)
        scheduleOrderVo.hoscode = schedule.hoscode
        scheduleOrderVo.hosname = hospitalService.getHospName(schedule.hoscode)
        scheduleOrderVo.depcode = schedule.depcode
        scheduleOrderVo.depname = departmentService!!.getDepName(schedule.hoscode, schedule.depcode)
        scheduleOrderVo.hosScheduleId = schedule.hosScheduleId
        scheduleOrderVo.availableNumber = schedule.availableNumber
        scheduleOrderVo.title = schedule.title
        scheduleOrderVo.reserveDate = schedule.workDate
        scheduleOrderVo.reserveTime = schedule.workTime
        scheduleOrderVo.amount = schedule.amount

        //退号截止天数（如：就诊前一天为-1，当天为0）
        val quitDay = bookingRule.quitDay
        val quitTime = getDateTime(DateTime(schedule.workDate).plusDays(quitDay!!).toDate(), bookingRule.quitTime!!)
        scheduleOrderVo.quitTime = quitTime.toDate()

        //预约开始时间
        val startTime = getDateTime(Date(), bookingRule.releaseTime!!)
        scheduleOrderVo.startTime = startTime.toDate()

        //预约截止时间
        val endTime = getDateTime(DateTime().plusDays(bookingRule.cycle!!).toDate(), bookingRule.stopTime!!)
        scheduleOrderVo.endTime = endTime.toDate()

        //当天停止挂号时间
        val stopTime = getDateTime(Date(), bookingRule.stopTime!!)
        scheduleOrderVo.startTime = startTime.toDate()
        return scheduleOrderVo
    }

    override fun update(schedule: Schedule?) {
        schedule!!.updateTime = Date()
        scheduleRepository!!.save(schedule)
    }

    override fun getBookingScheduleRule(
        page: Int?,
        limit: Int?,
        hoscode: String?,
        depcode: String?
    ): Map<String?, Any?>? {
        val result: MutableMap<String?, Any?> = HashMap()
        val hospital = hospitalService!!.getByHoscode(hoscode)
            ?: throw HtbpException(ResultCodeEnum.DATA_ERROR)
        val bookingRule = hospital.bookingRule ?: return null
        val iPage = getListDate(page, limit, bookingRule)
        val dateList: List<Date?> = iPage.records as List<Date?>
        val criteria =
            Criteria.where("hoscode").`is`(hoscode).and("depcode").`is`(depcode).and("workDate").`in`(dateList)
        val agg = Aggregation.newAggregation(
            Aggregation.match(criteria),
            Aggregation.group("workDate").first("workDate").`as`("workDate")
                .count().`as`("docCount")
                .sum("availableNumber").`as`("availableNumber")
                .sum("reservedNumber").`as`("reservedNumber")
        )
        val aggregateResult = mongoTemplate!!.aggregate(agg, Schedule::class.java, BookingScheduleRuleVo::class.java)
        val scheduleRuleVoList = aggregateResult.mappedResults
        var scheduleRuleVoMap: Map<Date?, BookingScheduleRuleVo?> = HashMap()
        if (!CollectionUtils.isEmpty(scheduleRuleVoList)) {
            scheduleRuleVoMap = scheduleRuleVoList.stream().collect(
                Collectors.toMap(
                    Function { obj: BookingScheduleRuleVo? -> obj!!.workDate },
                    Function { BookingScheduleRuleVo: BookingScheduleRuleVo? -> BookingScheduleRuleVo })
            )
        }
        val bookingScheduleRuleVoList: MutableList<BookingScheduleRuleVo> = ArrayList()
        var i = 0
        val len = dateList.size
        while (i < len) {
            val date = dateList[i]
            var bookingScheduleRuleVo = scheduleRuleVoMap[date]
            if (bookingScheduleRuleVo == null) {
                bookingScheduleRuleVo = BookingScheduleRuleVo()
                bookingScheduleRuleVo.docCount = 0
                bookingScheduleRuleVo.availableNumber = -1
            }
            bookingScheduleRuleVo.workDate = date
            bookingScheduleRuleVo.workDateMd = date
            val dayOfWeek = getDayOfWeek(DateTime(date))
            bookingScheduleRuleVo.dayOfWeek = dayOfWeek
            if (i == len - 1 && page!!.toLong() == iPage.pages) {
                bookingScheduleRuleVo.status = 1
            } else {
                bookingScheduleRuleVo.status = 0
            }
            //当天预约如果过了停号时间， 不能预约
            if (i == 0 && page == 1) {
                val stopTime = getDateTime(Date(), bookingRule.stopTime!!)
                if (stopTime.isBeforeNow) {
                    //停止预约
                    bookingScheduleRuleVo.status = -1
                }
            }
            bookingScheduleRuleVoList.add(bookingScheduleRuleVo)
            i++
        }
        result["bookingScheduleList"] = bookingScheduleRuleVoList
        result["total"] = iPage.total
        //其他基础数据
        val baseMap: MutableMap<String, String?> = HashMap()
        //医院名称
        baseMap["hosname"] = hospitalService.getHospName(hoscode)
        //科室
        val department = departmentService!!.getDepartment(hoscode, depcode)
        //大科室名称
        baseMap["bigname"] = department!!.bigname
        //科室名称
        baseMap["depname"] = department.depname
        baseMap["workDateString"] = DateTime().toString("yyyy年MM月")
        baseMap["releaseTime"] = bookingRule.releaseTime
        baseMap["stopTime"] = bookingRule.stopTime
        result["baseMap"] = baseMap
        return result
    }

    private fun getListDate(page: Int?, limit: Int?, bookingRule: BookingRule): IPage<*> {
        val releaseTime = getDateTime(Date(), bookingRule.releaseTime!!)
        var cycle = bookingRule.cycle ?: 0
        if (releaseTime.isBeforeNow) {
            cycle += 1
        }
        val dateList: MutableList<Date> = ArrayList()
        for (i in 0 until cycle) {
            val curDateTime = DateTime().plusDays(i)
            val dateString = curDateTime.toString("yyyy-MM-dd")
            dateList.add(DateTime(dateString).toDate())
        }
        val pageDateList: MutableList<Date> = ArrayList()
        val start = (page!! - 1) * limit!!
        var end = page * limit
        if (end > dateList.size) {
            end = dateList.size
        }
        for (i in start until end) {
            pageDateList.add(dateList[i])
        }
        val iPage: IPage<Date> = Page<Date>(page.toLong(), limit.toLong(), dateList.size.toLong())
        iPage.setRecords(pageDateList)
        return iPage
    }

    private fun packSchedule(schedule: Schedule): Schedule {
        schedule.param["hosname"] = hospitalService!!.getHospName(schedule.hoscode)
        schedule.param["depname"] = departmentService!!.getDepName(schedule.hoscode, schedule.depcode)
        schedule.param["dayOfWeek"] = getDayOfWeek(DateTime(schedule.workDate))
        return schedule
    }

    private fun getDateTime(date: Date, timeString: String): DateTime {
        val dateTimeString = DateTime(date).toString("yyyy-MM-dd") + " " + timeString
        return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").parseDateTime(dateTimeString)
    }

    /**
     * 根据日期获取周几数据
     * @param dateTime
     * @return
     */
    private fun getDayOfWeek(dateTime: DateTime): String {
        var dayOfWeek = ""
        when (dateTime.dayOfWeek) {
            DateTimeConstants.SUNDAY -> dayOfWeek = "周日"
            DateTimeConstants.MONDAY -> dayOfWeek = "周一"
            DateTimeConstants.TUESDAY -> dayOfWeek = "周二"
            DateTimeConstants.WEDNESDAY -> dayOfWeek = "周三"
            DateTimeConstants.THURSDAY -> dayOfWeek = "周四"
            DateTimeConstants.FRIDAY -> dayOfWeek = "周五"
            DateTimeConstants.SATURDAY -> dayOfWeek = "周六"
            else -> {
            }
        }
        return dayOfWeek
    }

    override fun save(paramMap: MutableMap<String, Any?>?) {
        val paramString = JSONObject.toJSONString(paramMap)
        val schedule = JSONObject.parseObject(paramString, Schedule::class.java)
        val scheduleExist =
            scheduleRepository!!.getScheduleByHoscodeAndHosScheduleId(schedule.hoscode, schedule.hosScheduleId)
        if (scheduleExist != null) {
            scheduleExist.updateTime = Date()
            scheduleExist.isDeleted = 0
            scheduleExist.status = 1
            scheduleRepository.save(scheduleExist)
        } else {
            schedule.createTime = Date()
            schedule.updateTime = Date()
            schedule.isDeleted = 0
            schedule.status = 1
            scheduleRepository.save(schedule)
        }
    }
}