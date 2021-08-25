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
 * 角色
 *
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "角色")
@TableName("acl_role")
class Role : BaseEntity() {
    @ApiModelProperty(value = "角色名称")
    @TableField("role_name")
    var roleName: String? = null

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    var remark: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}