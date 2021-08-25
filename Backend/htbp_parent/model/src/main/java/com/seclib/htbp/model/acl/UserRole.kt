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
 * 用户角色
 *
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "用户角色")
@TableName("acl_user_role")
class UserRole : BaseEntity() {
    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    var roleId: Long? = null

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    var userId: Long? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}