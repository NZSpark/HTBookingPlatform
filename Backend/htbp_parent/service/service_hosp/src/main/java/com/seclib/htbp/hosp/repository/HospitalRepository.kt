package com.seclib.htbp.hosp.repository

import org.springframework.data.mongodb.repository.MongoRepository
import com.seclib.htbp.model.hosp.Hospital

interface HospitalRepository : MongoRepository<Hospital?, String?> {
    fun getHospitalByHoscode(hoscode: String?): Hospital?
    fun findHospitalByHosnameLike(hosname: String?): List<Hospital?>?
}