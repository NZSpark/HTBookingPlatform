package com.seclib.htbp.order.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.fasterxml.jackson.databind.AnnotationIntrospector.pair
import com.seclib.htbp.order.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.user.client.PatientFeignClient
import com.seclib.htbp.hosp.client.HospitalFeignClient
import com.seclib.htbp.common.service.RabbitService
import com.seclib.htbp.order.service.WeChatService
import com.seclib.htbp.common.exception.HtbpException
import com.seclib.htbp.enums.OrderStatusEnum
import com.seclib.htbp.vo.order.OrderMqVo
import com.seclib.htbp.vo.msm.MsmVo
import com.seclib.htbp.common.constant.MqConst
import com.seclib.htbp.vo.order.OrderQueryVo
import com.seclib.htbp.vo.order.OrderCountQueryVo
import com.seclib.htbp.vo.order.OrderCountVo
import java.util.stream.Collectors
import com.seclib.htbp.common.helper.HttpRequestHelper
import com.seclib.htbp.common.result.ResultCodeEnum
import com.seclib.htbp.model.order.OrderInfo
import com.seclib.htbp.order.mapper.OrderInfoMapper
import org.joda.time.DateTime
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.util.*

@Service
open class OrderServiceImpl : ServiceImpl<OrderInfoMapper?, OrderInfo?>(), OrderService {
    @Autowired
    private val patientFeignClient: PatientFeignClient? = null

    @Autowired
    private val hospitalFeignClient: HospitalFeignClient? = null

    @Autowired
    private val rabbitService: RabbitService? = null

    @Autowired
    private val weChatService: WeChatService? = null

    //保存订单
    override fun saveOrder(scheduleId: String, patientId: Long): Long? {
        val patient = patientFeignClient!!.getPatient(patientId)
            ?: throw HtbpException(ResultCodeEnum.PARAM_ERROR)
        val scheduleOrderVo = hospitalFeignClient!!.getScheduleOrderVo(scheduleId)
            ?: throw HtbpException(ResultCodeEnum.PARAM_ERROR)
        //当前时间不可以预约
        if (DateTime(scheduleOrderVo.startTime).isAfterNow
            || DateTime(scheduleOrderVo.endTime).isBeforeNow
        ) {
            throw HtbpException(ResultCodeEnum.TIME_NO)
        }
        val signInfoVo = hospitalFeignClient.getSignInfoVo(scheduleOrderVo.hoscode)
        if (null == scheduleOrderVo) {
            throw HtbpException(ResultCodeEnum.PARAM_ERROR)
        }
        if (scheduleOrderVo.availableNumber!! <= 0) {
            throw HtbpException(ResultCodeEnum.NUMBER_NO)
        }
        val orderInfo = OrderInfo()
        BeanUtils.copyProperties(
            scheduleOrderVo,
            orderInfo
        ) //here orderInfo.HosScheduleId <- scheduleOrderVo.HosScheduleId
        val outTradeNo = System.currentTimeMillis().toString() + "" + Random().nextInt(100)
        orderInfo.outTradeNo = outTradeNo
        //        orderInfo.setScheduleId(scheduleId);//only hos_record_id in table.
        orderInfo.userId = patient.userId
        orderInfo.patientId = patientId
        orderInfo.patientName = patient.name
        orderInfo.patientPhone = patient.phone
        orderInfo.orderStatus = OrderStatusEnum.UNPAID.status
        save(orderInfo)
        val paramMap = mutableMapOf<String, Any?>()
        paramMap["hoscode"] = orderInfo.hoscode
        paramMap["depcode"] = orderInfo.depcode
        //        paramMap.put("hosScheduleId",orderInfo.getScheduleId());
        paramMap["hosScheduleId"] = orderInfo.hosScheduleId
        paramMap["reserveDate"] = DateTime(orderInfo.reserveDate).toString("yyyy-MM-dd")
        paramMap["reserveTime"] = orderInfo.reserveTime
        paramMap["amount"] = orderInfo.amount
        paramMap["name"] = patient.name
        paramMap["certificatesType"] = patient.certificatesType
        paramMap["certificatesNo"] = patient.certificatesNo
        paramMap["sex"] = patient.sex
        paramMap["birthdate"] = patient.birthdate
        paramMap["phone"] = patient.phone
        paramMap["isMarry"] = patient.isMarry
        paramMap["provinceCode"] = patient.provinceCode
        paramMap["cityCode"] = patient.cityCode
        paramMap["districtCode"] = patient.districtCode
        paramMap["address"] = patient.address
        //联系人
        paramMap["contactsName"] = patient.contactsName
        paramMap["contactsCertificatesType"] = patient.contactsCertificatesType
        paramMap["contactsCertificatesNo"] = patient.contactsCertificatesNo
        paramMap["contactsPhone"] = patient.contactsPhone
        paramMap["timestamp"] = HttpRequestHelper.timestamp
        val sign = HttpRequestHelper.getSign(paramMap, signInfoVo!!.signKey)
        paramMap["sign"] = sign
        val result = HttpRequestHelper.sendRequest(paramMap, signInfoVo.apiUrl + "/order/submitOrder")
        if (result.getInteger("code") == 200) {
            val jsonObject = result.getJSONObject("data")
            //预约记录唯一标识（医院预约记录主键）
            val hosRecordId = jsonObject.getString("hosRecordId")
            //预约序号
            val number = jsonObject.getInteger("number")
            //取号时间
            val fetchTime = jsonObject.getString("fetchTime")
            //取号地址
            val fetchAddress = jsonObject.getString("fetchAddress")
            //更新订单
            orderInfo.hosRecordId = hosRecordId
            orderInfo.number = number
            orderInfo.fetchTime = fetchTime
            orderInfo.fetchAddress = fetchAddress
            baseMapper!!.updateById(orderInfo)
            //排班可预约数
            val reservedNumber = jsonObject.getInteger("reservedNumber")
            //排班剩余预约数
            val availableNumber = jsonObject.getInteger("availableNumber")

            //发送mq信息更新号源和短信通知
            val orderMqVo = OrderMqVo()
            orderMqVo.scheduleId = scheduleId
            orderMqVo.reservedNumber = reservedNumber
            orderMqVo.availableNumber = availableNumber

            //短信提示
            val msmVo = MsmVo()
            msmVo.phone = orderInfo.patientPhone
            val reserveDate = (DateTime(orderInfo.reserveDate).toString("yyyy-MM-dd")
                    + if (orderInfo.reserveTime == 0) "上午" else "下午")
            val param = mutableMapOf<String, Any?>(
                    Pair<String,Any?>("title", orderInfo.hosname + "|" + orderInfo.depname + "|" + orderInfo.title),
                    Pair<String,Any?>("amount", orderInfo.amount),
                    Pair<String,Any?>("reserveDate", reserveDate),
                    Pair<String,Any?>("name", orderInfo.patientName),
                    Pair<String,Any?>("quitTime", DateTime(orderInfo.quitTime).toString("yyyy-MM-dd HH:mm"))
            )
            msmVo.param = param
            orderMqVo.msmVo = msmVo
            rabbitService!!.sendMessage(MqConst.EXCHANGE_DIRECT_ORDER, MqConst.ROUTING_ORDER, orderMqVo)
        } else {
            throw HtbpException(result.getString("message"), ResultCodeEnum.FAIL.code)
        }
        return orderInfo.id
    }

    override fun getOrder(orderId: String?): OrderInfo? {
        val orderInfo = baseMapper!!.selectById(orderId)
        return orderInfo?.let { packOrderInfo(it) }
    }

    //订单列表（条件查询带分页）
    override fun selectPage(pageParam: Page<OrderInfo?>?, orderQueryVo: OrderQueryVo?): IPage<OrderInfo?>? {
        //orderQueryVo获取条件值
        val name = orderQueryVo?.keyword ?: "" //医院名称
        val patientId = orderQueryVo?.patientId ?: ""  //就诊人名称
        val orderStatus = orderQueryVo?.orderStatus ?: "" //订单状态
        val reserveDate = orderQueryVo?.reserveDate ?: ""  //安排时间
        val createTimeBegin = orderQueryVo?.createTimeBegin ?: ""
        val createTimeEnd = orderQueryVo?.createTimeEnd ?: ""
        //对条件值进行非空判断
        val wrapper = QueryWrapper<OrderInfo>()
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("hosname", name)
        }
        if (!StringUtils.isEmpty(patientId)) {
            wrapper.eq("patient_id", patientId)
        }
        if (!StringUtils.isEmpty(orderStatus)) {
            wrapper.eq("order_status", orderStatus)
        }
        if (!StringUtils.isEmpty(reserveDate)) {
            wrapper.ge("reserve_date", reserveDate)
        }
        if (!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time", createTimeBegin)
        }
        if (!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time", createTimeEnd)
        }
        //调用mapper的方法
        val pages: Page<OrderInfo?>? = baseMapper!!.selectPage(pageParam, wrapper)
        //编号变成对应值封装
        pages?.records?.stream()?.forEach { item: OrderInfo? -> packOrderInfo(item!!) }
        return pages
    }

    override fun show(orderId: Long): Map<String, Any> {
        val map: MutableMap<String, Any> = HashMap()
        val orderInfo = packOrderInfo(getById(orderId)!!)
        map["orderInfo"] = orderInfo
        val patient = patientFeignClient!!.getPatient(orderInfo.patientId)
        map["patient"] = patient!!
        return map
    }

    private fun packOrderInfo(orderInfo: OrderInfo): OrderInfo {
        orderInfo.param["orderStatusString"] = OrderStatusEnum.getStatusNameByStatus(orderInfo.orderStatus!!)
        return orderInfo
    }

    override fun cancelOrder(orderId: Long): Boolean {
        val orderInfo = getById(orderId)
        //当前时间大约退号时间，不能取消预约
        val quitTime = DateTime(orderInfo!!.quitTime)
        if (quitTime.isBeforeNow) {
            throw HtbpException(ResultCodeEnum.CANCEL_ORDER_NO)
        }
        val signInfoVo = hospitalFeignClient!!.getSignInfoVo(orderInfo.hoscode)
            ?: throw HtbpException(ResultCodeEnum.PARAM_ERROR)
        val reqMap: MutableMap<String, Any?> = HashMap()
        reqMap["hoscode"] = orderInfo.hoscode
        reqMap["hosRecordId"] = orderInfo.hosRecordId
        reqMap["timestamp"] = HttpRequestHelper.timestamp
        val sign = HttpRequestHelper.getSign(reqMap, signInfoVo.signKey)
        reqMap["sign"] = sign
        val result = HttpRequestHelper.sendRequest(reqMap, signInfoVo.apiUrl + "/order/updateCancelStatus")
        if (result.getInteger("code") != 200) {
            throw HtbpException(result.getString("message"), ResultCodeEnum.FAIL.code)
        } else {
//是否支付 退款
            if (orderInfo.orderStatus == OrderStatusEnum.PAID.status ) {
//已支付 退款
                val isRefund = weChatService!!.refund(orderId)
                if (!isRefund!!) {
                    throw HtbpException(ResultCodeEnum.CANCEL_ORDER_FAIL)
                }
            }
            //更改订单状态
            orderInfo.orderStatus = OrderStatusEnum.CANCLE.status
            updateById(orderInfo)

            val orderMqVo = OrderMqVo()
            //            orderMqVo.setScheduleId(orderInfo.getScheduleId());
            orderMqVo.scheduleId = orderInfo.hosScheduleId
            //短信提示
            val msmVo = MsmVo()
            msmVo.phone = orderInfo.patientPhone
            msmVo.templateCode = "SMS_194640722"
            val reserveDate =
                DateTime(orderInfo.reserveDate).toString("yyyy-MM-dd") + if (orderInfo.reserveTime == 0) "上午" else "下午"
            val param = mutableMapOf<String, Any?>(
                Pair<String,Any?>("title", orderInfo.hosname + "|" + orderInfo.depname + "|" + orderInfo.title),
                Pair<String,Any?>("reserveDate", reserveDate),
                Pair<String,Any?>("name", orderInfo.patientName)
            )
            msmVo.param = param
            orderMqVo.msmVo = msmVo
            rabbitService!!.sendMessage(MqConst.EXCHANGE_DIRECT_ORDER, MqConst.ROUTING_ORDER, orderMqVo)
        }
        return true
    }

    override fun patientTips() {
        val queryWrapper = QueryWrapper<OrderInfo>()
        queryWrapper.eq("reserve_date", DateTime().toString("yyyy-MM-dd"))
        queryWrapper.ne("order_status", OrderStatusEnum.CANCLE.status)
        val orderInfoList = baseMapper!!.selectList(queryWrapper)
        for (orderInfo in orderInfoList) {
            if(orderInfo == null) continue

            val msmVo = MsmVo()
            msmVo.phone = orderInfo.patientPhone
            val reserveDate =
                DateTime(orderInfo.reserveDate).toString("yyyy-MM-dd") + if (orderInfo.reserveTime == 0) "上午" else "下午"
            val param =  mutableMapOf<String, Any?>(
                Pair<String,Any?>("title", orderInfo.hosname + "|" + orderInfo.depname + "|" + orderInfo.title),
                Pair<String,Any?>("reserveDate", reserveDate),
                Pair<String,Any?>("name", orderInfo.patientName)
            )
            msmVo.param = param
            rabbitService!!.sendMessage(MqConst.EXCHANGE_DIRECT_MSM, MqConst.ROUTING_MSM_ITEM, msmVo)
        }
    }

    override fun getCountMap(orderCountQueryVo: OrderCountQueryVo): Map<String, Any> {
        val map: MutableMap<String, Any> = HashMap()
        val orderCountVoList = baseMapper!!.selectOrderCount(orderCountQueryVo)
        if ( orderCountVoList != null) {
            val dateList =
                orderCountVoList.stream().map{ obj: OrderCountVo? -> obj!!.reserveDate}.collect(Collectors.toList())
            //统计列表
            val countList = orderCountVoList.stream().map { obj: OrderCountVo? -> obj!!.count }.collect(Collectors.toList())
            map["dateList"] = dateList
            map["countList"] = countList
        }
        return map
    }
}
