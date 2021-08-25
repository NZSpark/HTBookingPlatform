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
 * 用户
 *
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "用户")
@TableName("acl_user")
class User : BaseEntity() {
    @ApiModelProperty(value = "用户名")
    @TableField("username")
    var username: String? = null

    @ApiModelProperty(value = "密码")
    @TableField("password")
    var password: String? = null

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    var nickName: String? = null

    @ApiModelProperty(value = "用户头像")
    @TableField("salt")
    var salt: String? = null

    @ApiModelProperty(value = "用户签名")
    @TableField("token")
    var token: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}