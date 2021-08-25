package com.seclib.htbp.vo.user

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

@Data
@ApiModel(description = "注册对象")
class RegisterVo {
    @ApiModelProperty(value = "手机号")
    var mobile: String? = null

    @ApiModelProperty(value = "密码")
    var password: String? = null

    @ApiModelProperty(value = "验证码")
    var code: String? = null
}