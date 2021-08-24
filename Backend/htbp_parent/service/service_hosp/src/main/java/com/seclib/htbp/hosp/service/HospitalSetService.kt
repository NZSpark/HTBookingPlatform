package com.seclib.htbp.hosp.service

import com.baomidou.mybatisplus.extension.service.IService
import com.seclib.htbp.model.hosp.HospitalSet
import com.seclib.htbp.vo.order.SignInfoVo

interface HospitalSetService : IService<HospitalSet?> {
    fun getSignKey(hoscode: String?): String?

    //获取医院签名信息
    fun getSignInfoVo(hoscode: String?): SignInfoVo?
}