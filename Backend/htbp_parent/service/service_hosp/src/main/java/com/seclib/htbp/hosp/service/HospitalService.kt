package com.seclib.htbp.hosp.service

import com.seclib.htbp.model.hosp.Hospital
import com.seclib.htbp.vo.hosp.HospitalQueryVo
import org.springframework.data.domain.Page

interface HospitalService {
    fun save(paramMap: Map<String?, Any?>?)
    fun getByHoscode(hoscode: String?): Hospital?
    fun selectHospPage(page: Int?, limit: Int?, hospitalQueryVo: HospitalQueryVo?): Page<Hospital?>?
    fun updateStatus(id: String?, status: Int?)
    fun getHospById(id: String?): Map<String?, Any?>?
    fun getHospName(hoscode: String?): String?
    fun findByHosName(hosname: String?): List<Hospital?>?
    fun item(hoscode: String?): Map<String?, Any?>?
}