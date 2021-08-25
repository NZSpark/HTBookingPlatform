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
 * UserInfo
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "UserInfo")
@TableName("user_info")
class UserInfo : BaseEntity() {

    @ApiModelProperty(value = "微信openid")
    @TableField("openid")
    var openid: String? = null

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    var nickName: String? = null

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    var phone: String? = null

    @ApiModelProperty(value = "用户姓名")
    @TableField("name")
    var name: String? = null

    @ApiModelProperty(value = "证件类型")
    @TableField("certificates_type")
    var certificatesType: String? = null

    @ApiModelProperty(value = "证件编号")
    @TableField("certificates_no")
    var certificatesNo: String? = null

    @ApiModelProperty(value = "证件路径")
    @TableField("certificates_url")
    var certificatesUrl: String? = null

    @ApiModelProperty(value = "认证状态（0：未认证 1：认证中 2：认证成功 -1：认证失败）")
    @TableField("auth_status")
    var authStatus: Int? = null

    @ApiModelProperty(value = "状态（0：锁定 1：正常）")
    @TableField("status")
    var status: Int? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}