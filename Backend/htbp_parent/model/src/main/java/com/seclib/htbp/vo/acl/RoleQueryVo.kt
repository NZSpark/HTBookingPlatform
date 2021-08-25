package com.seclib.htbp.vo.acl

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Data
import java.io.Serializable

/**
 *
 *
 * 角色查询实体
 *
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "角色查询实体")
class RoleQueryVo : Serializable {
    @ApiModelProperty(value = "角色名称")
    var roleName: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}