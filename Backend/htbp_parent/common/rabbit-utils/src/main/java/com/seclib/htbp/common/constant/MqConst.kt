package com.seclib.htbp.common.constant

object MqConst {
    /**
     * 预约下单
     */
    const val EXCHANGE_DIRECT_ORDER = "exchange.direct.order"
    const val ROUTING_ORDER = "order"

    //队列
    const val QUEUE_ORDER = "queue.order"

    /**
     * 短信
     */
    const val EXCHANGE_DIRECT_MSM = "exchange.direct.msm"
    const val ROUTING_MSM_ITEM = "msm.item"

    //队列
    const val QUEUE_MSM_ITEM = "queue.msm.item"
    const val EXCHANGE_DIRECT_TASK = "exchange.direct.task"
    const val ROUTING_TASK_8 = "task.8"

    //队列
    const val QUEUE_TASK_8 = "queue.task.8"
}