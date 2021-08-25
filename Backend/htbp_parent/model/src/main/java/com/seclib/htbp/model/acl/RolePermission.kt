package com.seclib.htbp.model.acl

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.seclib.htbp.model.base.BaseEntity
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Data

/**
 *
 *
 * 角色权限
 *
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "角色权限")
@TableName("acl_role_permission")
class RolePermission : BaseEntity() {
    @ApiModelProperty(value = "roleid")
    @TableField("role_id")
    var roleId: Long? = null

    @ApiModelProperty(value = "permissionId")
    @TableField("permission_id")
    var permissionId: Long? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}