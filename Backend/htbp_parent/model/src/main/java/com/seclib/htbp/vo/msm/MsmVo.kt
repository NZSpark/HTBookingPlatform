package com.seclib.htbp.vo.msm

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

@Data
@ApiModel(description = "短信实体")
class MsmVo {
    @ApiModelProperty(value = "phone")
    var phone: String? = null

    @ApiModelProperty(value = "短信模板code")
    var templateCode: String? = null

    @ApiModelProperty(value = "短信模板参数")
    var param: MutableMap<String, Any?>? = null
}