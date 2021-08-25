package com.seclib.htbp.model.user

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import com.seclib.htbp.model.base.BaseEntity
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Data
import java.util.*

/**
 *
 *
 * Patient
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "Patient")
@TableName("patient")
class Patient : BaseEntity() {
    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    var userId: Long? = null

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    var name: String? = null

    @ApiModelProperty(value = "证件类型")
    @TableField("certificates_type")
    var certificatesType: String? = null

    @ApiModelProperty(value = "证件编号")
    @TableField("certificates_no")
    var certificatesNo: String? = null

    @ApiModelProperty(value = "性别")
    @TableField("sex")
    var sex: Int? = null

    @ApiModelProperty(value = "出生年月")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("birthdate")
    var birthdate: Date? = null

    @ApiModelProperty(value = "手机")
    @TableField("phone")
    var phone: String? = null

    @ApiModelProperty(value = "是否结婚")
    @TableField("is_marry")
    var isMarry: Int? = null

    @ApiModelProperty(value = "省code")
    @TableField("province_code")
    var provinceCode: String? = null

    @ApiModelProperty(value = "市code")
    @TableField("city_code")
    var cityCode: String? = null

    @ApiModelProperty(value = "区code")
    @TableField("district_code")
    var districtCode: String? = null

    @ApiModelProperty(value = "详情地址")
    @TableField("address")
    var address: String? = null

    @ApiModelProperty(value = "联系人姓名")
    @TableField("contacts_name")
    var contactsName: String? = null

    @ApiModelProperty(value = "联系人证件类型")
    @TableField("contacts_certificates_type")
    var contactsCertificatesType: String? = null

    @ApiModelProperty(value = "联系人证件号")
    @TableField("contacts_certificates_no")
    var contactsCertificatesNo: String? = null

    @ApiModelProperty(value = "联系人手机")
    @TableField("contacts_phone")
    var contactsPhone: String? = null

    @ApiModelProperty(value = "是否有医保")
    @TableField("is_insure")
    var isInsure: Int? = null

    @ApiModelProperty(value = "就诊卡")
    @TableField("card_no")
    var cardNo: String? = null

    @ApiModelProperty(value = "状态（0：默认 1：已认证）")
    @TableField("status")
    var status: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}