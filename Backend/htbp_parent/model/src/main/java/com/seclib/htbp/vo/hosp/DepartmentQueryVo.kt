package com.seclib.htbp.vo.hosp

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

@Data
@ApiModel(description = "Department")
class DepartmentQueryVo {
    @ApiModelProperty(value = "医院编号")
    var hoscode: String? = null

    @ApiModelProperty(value = "科室编号")
    var depcode: String? = null

    @ApiModelProperty(value = "科室名称")
    var depname: String? = null

    @ApiModelProperty(value = "大科室编号")
    var bigcode: String? = null

    @ApiModelProperty(value = "大科室名称")
    var bigname: String? = null
}