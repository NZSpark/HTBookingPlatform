package com.seclib.htbp.vo.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

@Data
@ApiModel(description = "Order")
class OrderQueryVo {
    @ApiModelProperty(value = "会员id")
    var userId: Long? = null

    @ApiModelProperty(value = "订单交易号")
    var outTradeNo: String? = null

    @ApiModelProperty(value = "就诊人id")
    var patientId: Long? = null

    @ApiModelProperty(value = "就诊人")
    var patientName: String? = null

    @ApiModelProperty(value = "医院名称")
    var keyword: String? = null

    @ApiModelProperty(value = "订单状态")
    var orderStatus: String? = null

    @ApiModelProperty(value = "安排日期")
    var reserveDate: String? = null

    @ApiModelProperty(value = "创建时间")
    var createTimeBegin: String? = null
    var createTimeEnd: String? = null
}