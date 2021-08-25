package com.seclib.htbp.vo.hosp

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data
import java.util.*

/**
 *
 *
 * RegisterRule
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "可预约排班规则数据")
class BookingScheduleRuleVo {
    @ApiModelProperty(value = "可预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    var workDate: Date? = null

    @ApiModelProperty(value = "可预约日期")
    @JsonFormat(pattern = "MM月dd日")
    var workDateMd //方便页面使用
            : Date? = null

    @ApiModelProperty(value = "周几")
    var dayOfWeek: String? = null

    @ApiModelProperty(value = "就诊医生人数")
    var docCount: Int? = null

    @ApiModelProperty(value = "科室可预约数")
    var reservedNumber: Int? = null

    @ApiModelProperty(value = "科室剩余预约数")
    var availableNumber: Int? = null

    @ApiModelProperty(value = "状态 0：正常 1：即将放号 -1：当天已停止挂号")
    var status: Int? = null
}