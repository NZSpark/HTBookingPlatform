package com.seclib.htbp.model.order

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import com.seclib.htbp.model.base.BaseEntity
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Data
import java.math.BigDecimal
import java.util.*

/**
 *
 *
 * Order
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "Order")
@TableName("order_info")
class OrderInfo : BaseEntity() {
    @ApiModelProperty(value = "userId")
    @TableField("user_id")
    var userId: Long? = null

    @ApiModelProperty(value = "订单交易号")
    @TableField("out_trade_no")
    var outTradeNo: String? = null

    @ApiModelProperty(value = "医院编号")
    @TableField("hoscode")
    var hoscode: String? = null

    @ApiModelProperty(value = "医院名称")
    @TableField("hosname")
    var hosname: String? = null

    @ApiModelProperty(value = "科室编号")
    @TableField("depcode")
    var depcode: String? = null

    @ApiModelProperty(value = "科室名称")
    @TableField("depname")
    var depname: String? = null

    @ApiModelProperty(value = "排班id")
    @TableField("hos_schedule_id")
    var hosScheduleId: String? = null

    @ApiModelProperty(value = "医生职称")
    @TableField("title")
    var title: String? = null

    @ApiModelProperty(value = "安排日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("reserve_date")
    var reserveDate: Date? = null

    @ApiModelProperty(value = "安排时间（0：上午 1：下午）")
    @TableField("reserve_time")
    var reserveTime: Int? = null

    @ApiModelProperty(value = "就诊人id")
    @TableField("patient_id")
    var patientId: Long? = null

    @ApiModelProperty(value = "就诊人名称")
    @TableField("patient_name")
    var patientName: String? = null

    @ApiModelProperty(value = "就诊人手机")
    @TableField("patient_phone")
    var patientPhone: String? = null

    @ApiModelProperty(value = "预约记录唯一标识（医院预约记录主键）")
    @TableField("hos_record_id")
    var hosRecordId: String? = null

    @ApiModelProperty(value = "预约号序")
    @TableField("number")
    var number: Int? = null

    @ApiModelProperty(value = "建议取号时间")
    @TableField("fetch_time")
    var fetchTime: String? = null

    @ApiModelProperty(value = "取号地点")
    @TableField("fetch_address")
    var fetchAddress: String? = null

    @ApiModelProperty(value = "医事服务费")
    @TableField("amount")
    var amount: BigDecimal? = null

    @ApiModelProperty(value = "退号时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @TableField("quit_time")
    var quitTime: Date? = null

    @ApiModelProperty(value = "订单状态")
    @TableField("order_status")
    var orderStatus: Int? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}