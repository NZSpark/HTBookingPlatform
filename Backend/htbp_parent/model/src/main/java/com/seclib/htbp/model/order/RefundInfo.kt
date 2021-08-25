package com.seclib.htbp.model.order

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.seclib.htbp.model.base.BaseEntity
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Data
import java.math.BigDecimal
import java.util.*

/**
 *
 *
 * RefundInfo
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "RefundInfo")
@TableName("refund_info")
class RefundInfo : BaseEntity() {
    @ApiModelProperty(value = "对外业务编号")
    @TableField("out_trade_no")
    var outTradeNo: String? = null

    @ApiModelProperty(value = "订单编号")
    @TableField("order_id")
    var orderId: Long? = null

    @ApiModelProperty(value = "支付类型（微信 支付宝）")
    @TableField("payment_type")
    var paymentType: Int? = null

    @ApiModelProperty(value = "交易编号")
    @TableField("trade_no")
    var tradeNo: String? = null

    @ApiModelProperty(value = "退款金额")
    @TableField("total_amount")
    var totalAmount: BigDecimal? = null

    @ApiModelProperty(value = "交易内容")
    @TableField("subject")
    var subject: String? = null

    @ApiModelProperty(value = "退款状态")
    @TableField("refund_status")
    var refundStatus: Int? = null

    @ApiModelProperty(value = "回调时间")
    @TableField("callback_time")
    var callbackTime: Date? = null

    @ApiModelProperty(value = "回调信息")
    @TableField("callback_content")
    var callbackContent: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}