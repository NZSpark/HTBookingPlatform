package com.seclib.htbp.vo.hosp

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data
import java.util.*

@Data
@ApiModel(description = "Schedule")
class ScheduleOrderVo {
    @ApiModelProperty(value = "医院编号")
    var hoscode: String? = null

    @ApiModelProperty(value = "医院名称")
    var hosname: String? = null

    @ApiModelProperty(value = "科室编号")
    var depcode: String? = null

    @ApiModelProperty(value = "科室名称")
    var depname: String? = null

    @ApiModelProperty(value = "排班编号（医院自己的排班主键）")
    var hosScheduleId: String? = null

    @ApiModelProperty(value = "医生职称")
    var title: String? = null

    @ApiModelProperty(value = "安排日期")
    var reserveDate: Date? = null

    @ApiModelProperty(value = "剩余预约数")
    var availableNumber: Int? = null

    @ApiModelProperty(value = "安排时间（0：上午 1：下午）")
    var reserveTime: Int? = null

    @ApiModelProperty(value = "医事服务费")
    var amount: BigDecimal? = null

    @ApiModelProperty(value = "退号时间")
    var quitTime: Date? = null

    @ApiModelProperty(value = "挂号开始时间")
    var startTime: Date? = null

    @ApiModelProperty(value = "挂号结束时间")
    var endTime: Date? = null

    @ApiModelProperty(value = "当天停止挂号时间")
    var stopTime: Date? = null
}