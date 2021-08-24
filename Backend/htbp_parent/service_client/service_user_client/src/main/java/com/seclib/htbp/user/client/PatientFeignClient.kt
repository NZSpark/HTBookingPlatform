package com.seclib.htbp.user.client

import com.seclib.htbp.model.user.Patient
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(value = "service-user")
@Repository
interface PatientFeignClient {
    //获取就诊人
    @GetMapping("/api/user/patient/inner/get/{id}")
    fun getPatient(@PathVariable("id") id: Long?): Patient?
}