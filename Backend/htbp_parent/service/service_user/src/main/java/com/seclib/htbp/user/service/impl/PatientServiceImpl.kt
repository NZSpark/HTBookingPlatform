package com.seclib.htbp.user.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.user.service.PatientService
import com.seclib.htbp.user.mapper.PatientMapper
import com.seclib.htbp.cmn.client.DictFeignClient
import com.seclib.htbp.enums.DictEnum
import com.seclib.htbp.model.user.Patient
import org.springframework.stereotype.Service
import java.util.stream.Collectors.toMap

@Service
open class PatientServiceImpl : ServiceImpl<PatientMapper?, Patient?>(), PatientService {
    @Autowired
    private val dictFeignClient: DictFeignClient? = null
    override fun findAllByUserId(userId: Long?): List<Patient?> {
        //query list by id
        val queryWrapper = QueryWrapper<Patient>()
        queryWrapper.eq("user_id", userId)
        val patients = baseMapper!!.selectList(queryWrapper)
        patients.stream().forEach { item: Patient? -> packPatient(item!!) }
        return patients
    }

    override fun getPatientId(id: Long?): Patient {
        return packPatient(baseMapper!!.selectById(id)!!)
    }

    //embed strings into param
    private fun packPatient(patient: Patient): Patient {
        val certificatesTypeString =
            dictFeignClient!!.getName(DictEnum.CERTIFICATES_TYPE.dictCode, patient.certificatesType)
        val contactsCertificatesTypeString =
            dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.dictCode, patient.contactsCertificatesType)
        val provinceString = dictFeignClient.getName(patient.provinceCode)
        //市
        val cityString = dictFeignClient.getName(patient.cityCode)
        //区
        val districtString = dictFeignClient.getName(patient.districtCode)
        if (certificatesTypeString != null) {
            patient.param["certificatesTypeString"] = certificatesTypeString
        }
        if (contactsCertificatesTypeString != null) {
            patient.param["contactsCertificatesTypeString"] = contactsCertificatesTypeString
        }
        patient.param["fullAddress"] = provinceString + cityString + districtString + patient.address
        return patient
    }

}