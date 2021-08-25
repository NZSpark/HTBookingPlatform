package com.seclib.htbp.vo.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data
import java.io.Serializable

/**
 *
 *
 * HospitalSet
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "签名信息")
class SignInfoVo : Serializable {
    @ApiModelProperty(value = "api基础路径")
    var apiUrl: String? = null

    @ApiModelProperty(value = "签名秘钥")
    var signKey: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}