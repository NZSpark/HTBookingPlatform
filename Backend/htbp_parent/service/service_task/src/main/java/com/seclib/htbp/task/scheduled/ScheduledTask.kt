package com.seclib.htbp.task.scheduled

import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.common.service.RabbitService
import org.springframework.scheduling.annotation.Scheduled
import com.seclib.htbp.common.constant.MqConst
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class ScheduledTask {
    @Autowired
    private val rabbitService: RabbitService? = null

    /**
     * 每天8点执行 提醒就诊
     */
    //@Scheduled(cron = "0 0 1 * * ?")
    //30s
    @Scheduled(cron = "0/30 * * * * ?")
    fun task1() {
        rabbitService!!.sendMessage(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_TASK_8, "")
    }
}