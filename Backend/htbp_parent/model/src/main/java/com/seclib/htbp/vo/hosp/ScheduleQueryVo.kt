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
class ScheduleQueryVo {
    @ApiModelProperty(value = "医院编号")
    var hoscode: String? = null

    @ApiModelProperty(value = "科室编号")
    var depcode: String? = null

    @ApiModelProperty(value = "医生编号")
    var doccode: String? = null

    @ApiModelProperty(value = "安排日期")
    var workDate: Date? = null

    @ApiModelProperty(value = "安排时间（0：上午 1：下午）")
    var workTime: Int? = null
}