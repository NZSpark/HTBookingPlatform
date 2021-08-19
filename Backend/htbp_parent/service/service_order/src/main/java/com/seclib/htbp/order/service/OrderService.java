package com.seclib.htbp.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seclib.htbp.model.order.OrderInfo;

public interface OrderService extends IService<OrderInfo> {
    //保存订单
    Long saveOrder(String scheduleId, Long patientId);
}
