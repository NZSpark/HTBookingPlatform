package com.seclib.htbp.order.service;

import java.util.Map;

public interface WeChatService {
    Map createNative(Long orderId);

    Map<String, String> queryPayStatus(Long orderId);

    /***
     * 退款
     * @param orderId
     * @return
     */
    Boolean refund(Long orderId);


}
