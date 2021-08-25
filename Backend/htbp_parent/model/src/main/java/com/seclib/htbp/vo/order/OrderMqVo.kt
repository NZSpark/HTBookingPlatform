package com.seclib.htbp.vo.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

@Data
@ApiModel(description = "OrderMqVo")
class OrderMqVo {
    @ApiModelProperty(value = "可预约数")
    var reservedNumber: Int? = null

    @ApiModelProperty(value = "剩余预约数")
    var availableNumber: Int? = null

    @ApiModelProperty(value = "排班id")
    var scheduleId: String? = null

    @ApiModelProperty(value = "短信实体")
    var msmVo: MsmVo? = null
}