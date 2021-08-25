package com.seclib.htbp.vo.hosp

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data
import java.io.Serializable

@Data
@ApiModel(description = "Hospital")
class HospitalQueryVo : Serializable {
    @ApiModelProperty(value = "医院编号")
    var hoscode: String? = null

    @ApiModelProperty(value = "医院名称")
    var hosname: String? = null

    @ApiModelProperty(value = "医院类型")
    var hostype: String? = null

    @ApiModelProperty(value = "省code")
    var provinceCode: String? = null

    @ApiModelProperty(value = "市code")
    var cityCode: String? = null

    @ApiModelProperty(value = "区code")
    var districtCode: String? = null

    @ApiModelProperty(value = "状态")
    var status: Int? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}