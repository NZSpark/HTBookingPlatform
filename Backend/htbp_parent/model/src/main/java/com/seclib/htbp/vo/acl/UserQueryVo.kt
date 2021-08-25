package com.seclib.htbp.vo.acl

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
 * 用户查询实体
 *
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "用户查询实体")
class UserQueryVo : Serializable {
    @ApiModelProperty(value = "用户名")
    var username: String? = null

    @ApiModelProperty(value = "昵称")
    var nickName: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}