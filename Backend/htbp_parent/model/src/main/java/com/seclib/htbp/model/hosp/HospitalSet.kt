package com.seclib.htbp.model.hosp

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.seclib.htbp.model.base.BaseEntity
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.model.base.BaseMongoEntity
import com.seclib.htbp.model.hosp.BookingRule
import lombok.Data
import java.math.BigDecimal

/**
 *
 *
 * HospitalSet
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "医院设置")
@TableName("hospital_set")
class HospitalSet : BaseEntity() {
    @ApiModelProperty(value = "医院名称")
    @TableField("hosname")
    var hosname: String? = null

    @ApiModelProperty(value = "医院编号")
    @TableField("hoscode")
    var hoscode: String? = null

    @ApiModelProperty(value = "api基础路径")
    @TableField("api_url")
    var apiUrl: String? = null

    @ApiModelProperty(value = "签名秘钥")
    @TableField("sign_key")
    var signKey: String? = null

    @ApiModelProperty(value = "联系人姓名")
    @TableField("contacts_name")
    var contactsName: String? = null

    @ApiModelProperty(value = "联系人手机")
    @TableField("contacts_phone")
    var contactsPhone: String? = null

    @ApiModelProperty(value = "状态")
    @TableField("status")
    var status: Int? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}