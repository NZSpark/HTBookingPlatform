package com.seclib.htbp.hosp.service.impl

import com.alibaba.fastjson.JSONObject
import com.seclib.htbp.cmn.client.DictFeignClient
import com.seclib.htbp.hosp.repository.HospitalRepository
import com.seclib.htbp.hosp.service.HospitalService
import com.seclib.htbp.model.hosp.Hospital
import com.seclib.htbp.vo.hosp.HospitalQueryVo
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap

@Service
open class HospitalServiceImpl : HospitalService {
    @Autowired
    private val hospitalRepository: HospitalRepository? = null

    @Autowired
    private val dictFeignClient: DictFeignClient? = null
    override fun save(paramMap: Map<String?, Any?>?) {
        val mapString = JSONObject.toJSONString(paramMap)
        val hospital = JSONObject.parseObject(mapString, Hospital::class.java)
        val hoscode = hospital.hoscode
        val hospitalExist = hospitalRepository!!.getHospitalByHoscode(hoscode)
        if (hospitalExist != null) {
            hospital.status = hospitalExist.status
            hospital.createTime = hospitalExist.createTime
        } else {
            hospital.status = 0
            hospital.createTime = Date()
        }
        hospital.updateTime = Date()
        hospital.isDeleted = 0
        hospitalRepository.save(hospital)
    }

    override fun getByHoscode(hoscode: String?): Hospital? {
        return hospitalRepository!!.getHospitalByHoscode(hoscode)
    }

    override fun selectHospPage(page: Int?, limit: Int?, hospitalQueryVo: HospitalQueryVo?): Page<Hospital?>? {
        val pageable: Pageable = PageRequest.of(page!! - 1, limit!!)
        val matcher = ExampleMatcher.matching()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreCase()
        val hospital = Hospital()
        BeanUtils.copyProperties(hospitalQueryVo, hospital)
        val example = Example.of(hospital, matcher)
        val pages = hospitalRepository!!.findAll(example, pageable)
        pages.content.stream().forEach { item: Hospital -> setHospitalHosType(item) }
        return pages
    }

    override fun updateStatus(id: String?, status: Int?) {
        val hospital = hospitalRepository!!.findById(id).get()
        hospital.status = status
        hospital.updateTime = Date()
        hospitalRepository.save(hospital)
    }

    override fun getHospById(id: String?): Map<String?, Any?>? {
        val result: MutableMap<String?, Any?> = HashMap()
        val hospital = setHospitalHosType(hospitalRepository!!.findById(id).get())
        result["hospital"] = hospital
        result["bookingRule"] = hospital.bookingRule
        hospital.setBookingRule(null)
        return result
    }

    override fun getHospName(hoscode: String?): String? {
        val hospital = hospitalRepository!!.getHospitalByHoscode(hoscode)
        return hospital?.hosname
    }

    override fun findByHosName(hosname: String?): List<Hospital?>? {
        return hospitalRepository!!.findHospitalByHosnameLike(hosname)
    }

    override fun item(hoscode: String?): Map<String?, Any?>? {
        val result: MutableMap<String?, Any?> = HashMap()
        //医院详情
        val hospital = setHospitalHosType(getByHoscode(hoscode)!!)
        result["hospital"] = hospital
        //预约规则
        result["bookingRule"] = hospital.bookingRule
        //不需要重复返回
        hospital.setBookingRule(null)
        return result
    }

    private fun setHospitalHosType(hospital: Hospital): Hospital {
        val hostypeString = dictFeignClient!!.getName("hostype", hospital.hostype)
        val provinceString = dictFeignClient.getName(hospital.provinceCode)
        val cityString = dictFeignClient.getName(hospital.cityCode)
        val districtString = dictFeignClient.getName(hospital.districtCode)
        hospital.param["hostypeString"] = hostypeString
        hospital.param["fullAddress"] = provinceString + cityString + districtString
        return hospital
    }
}