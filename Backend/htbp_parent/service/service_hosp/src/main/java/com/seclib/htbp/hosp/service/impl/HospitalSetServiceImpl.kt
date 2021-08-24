package com.seclib.htbp.hosp.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.seclib.htbp.common.exception.HtbpException
import com.seclib.htbp.common.result.ResultCodeEnum
import com.seclib.htbp.hosp.mapper.HospitalSetMapper
import com.seclib.htbp.hosp.service.HospitalSetService
import com.seclib.htbp.model.hosp.HospitalSet
import com.seclib.htbp.vo.order.SignInfoVo
import org.springframework.stereotype.Service


@Service
open class HospitalSetServiceImpl : ServiceImpl<HospitalSetMapper?, HospitalSet?>(), HospitalSetService {
    override fun getSignKey(hoscode: String?): String? {
        val queryWrapper = QueryWrapper<HospitalSet>()
        queryWrapper.eq("hoscode", hoscode)
        val hospitalSet = baseMapper!!.selectOne(queryWrapper)
        return hospitalSet?.signKey
    }

    //获取医院签名信息
    override fun getSignInfoVo(hoscode: String?): SignInfoVo? {
        val wrapper = QueryWrapper<HospitalSet>()
        wrapper.eq("hoscode", hoscode)
        val hospitalSet = baseMapper!!.selectOne(wrapper)
            ?: throw HtbpException(ResultCodeEnum.HOSPITAL_OPEN)
        val signInfoVo = SignInfoVo()
        signInfoVo.apiUrl = hospitalSet.apiUrl
        signInfoVo.signKey = hospitalSet.signKey
        return signInfoVo
    }
}