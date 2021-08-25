package com.seclib.htbp.vo.user

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

@Data
@ApiModel(description = "登录对象")
class LoginVo {
    @ApiModelProperty(value = "openid")
    var openid: String? = null

    @ApiModelProperty(value = "手机号")
    var phone: String? = null

    @ApiModelProperty(value = "密码")
    var code: String? = null

    @ApiModelProperty(value = "IP")
    var ip: String? = null
}