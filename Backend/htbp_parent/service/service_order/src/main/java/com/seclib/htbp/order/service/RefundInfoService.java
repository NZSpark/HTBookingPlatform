package com.seclib.htbp.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seclib.htbp.model.order.PaymentInfo;
import com.seclib.htbp.model.order.RefundInfo;

public interface RefundInfoService extends IService<RefundInfo> {
    /**
     * 保存退款记录
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);
}
