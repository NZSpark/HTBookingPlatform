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
 * 权限
 *
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "权限")
@TableName("acl_permission")
class Permission : BaseEntity() {
    @ApiModelProperty(value = "所属上级")
    @TableField("pid")
    var pid: Long? = null

    @ApiModelProperty(value = "名称")
    @TableField("name")
    var name: String? = null

    @ApiModelProperty(value = "类型(1:菜单,2:按钮)")
    @TableField("type")
    var type: Int? = null

    @ApiModelProperty(value = "权限值")
    @TableField("permission_value")
    var permissionValue: String? = null

    @ApiModelProperty(value = "路径")
    @TableField("path")
    var path: String? = null

    @ApiModelProperty(value = "component")
    @TableField("component")
    var component: String? = null

    @ApiModelProperty(value = "图标")
    @TableField("icon")
    var icon: String? = null

    @ApiModelProperty(value = "状态(0:禁止,1:正常)")
    @TableField("status")
    var status: Int? = null

    @ApiModelProperty(value = "层级")
    @TableField(exist = false)
    var level: Int? = null

    @ApiModelProperty(value = "下级")
    @TableField(exist = false)
    var children: List<Permission>? = null

    @ApiModelProperty(value = "是否选中")
    @TableField(exist = false)
    var isSelect = false

    companion object {
        private const val serialVersionUID = 1L
    }
}