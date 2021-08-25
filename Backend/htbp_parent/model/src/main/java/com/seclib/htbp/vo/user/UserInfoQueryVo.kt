package com.seclib.htbp.vo.user

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

@Data
@ApiModel(description = "会员搜索对象")
class UserInfoQueryVo {
    @ApiModelProperty(value = "关键字")
    var keyword: String? = null

    @ApiModelProperty(value = "状态")
    var status: Int? = null

    @ApiModelProperty(value = "认证状态")
    var authStatus: Int? = null

    @ApiModelProperty(value = "创建时间")
    var createTimeBegin: String? = null

    @ApiModelProperty(value = "创建时间")
    var createTimeEnd: String? = null
}