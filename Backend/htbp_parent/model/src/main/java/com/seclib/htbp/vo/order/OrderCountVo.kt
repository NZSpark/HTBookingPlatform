package com.seclib.htbp.vo.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

@Data
@ApiModel(description = "OrderCountVo")
class OrderCountVo {
    @ApiModelProperty(value = "安排日期")
    var reserveDate: String? = null

    @ApiModelProperty(value = "预约单数")
    var count: Int? = null
}