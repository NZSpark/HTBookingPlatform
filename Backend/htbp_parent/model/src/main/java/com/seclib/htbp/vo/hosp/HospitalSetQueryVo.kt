package com.seclib.htbp.vo.hosp

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

@Data
class HospitalSetQueryVo {
    @ApiModelProperty(value = "医院名称")
    var hosname: String? = null

    @ApiModelProperty(value = "医院编号")
    var hoscode: String? = null
}