package com.seclib.htbp.model.user

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.seclib.htbp.model.base.BaseEntity
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Data

/**
 *
 *
 * UserLoginRecord
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "用户登录日志")
@TableName("user_login_record")
class UserLoginRecord : BaseEntity() {
    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    var userId: Long? = null

    @ApiModelProperty(value = "ip")
    @TableField("ip")
    var ip: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}