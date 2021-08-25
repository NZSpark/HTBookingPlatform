package com.seclib.htbp.vo.hosp

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

@Data
@ApiModel(description = "Department")
class DepartmentVo {
    @ApiModelProperty(value = "科室编号")
    var depcode: String? = null

    @ApiModelProperty(value = "科室名称")
    var depname: String? = null

    @ApiModelProperty(value = "下级节点")
    var children: List<DepartmentVo>? = null
}