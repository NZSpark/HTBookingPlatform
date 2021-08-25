package com.seclib.htbp.user.service

import com.baomidou.mybatisplus.extension.service.IService
import com.seclib.htbp.model.user.Patient

interface PatientService : IService<Patient?> {
    fun findAllByUserId(userId: Long?): List<Patient?>
    fun getPatientId(id: Long?): Patient
}