package com.seclib.htbp.order.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.seclib.htbp.model.order.OrderInfo
import com.seclib.htbp.vo.order.OrderCountQueryVo
import com.seclib.htbp.vo.order.OrderCountVo
import com.seclib.htbp.model.order.PaymentInfo
import com.seclib.htbp.model.order.RefundInfo
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface OrderInfoMapper : BaseMapper<OrderInfo?> {
    fun selectOrderCount(@Param("vo") orderCountQueryVo: OrderCountQueryVo?): List<OrderCountVo?>?
}