package com.seclib.htbp.user.api

import com.seclib.htbp.common.utils.AuthContextHolder.getUserId
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.seclib.htbp.user.service.PatientService
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.DeleteMapping
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import com.seclib.htbp.common.result.Result
import com.seclib.htbp.model.user.Patient

@RestController
@RequestMapping("/api/user/patient")
class PatientApiController {
    @Autowired
    private val patientService: PatientService? = null

    //patient list
    @GetMapping("auth/findAll")
    fun findAll(request: HttpServletRequest?): Result<*> {
        val userId = getUserId(request!!)
        val patientList = patientService!!.findAllByUserId(userId)
        return Result.ok(patientList)
    }

    //添加就诊人
    @PostMapping("auth/save")
    fun savePatient(@RequestBody patient: Patient, request: HttpServletRequest?): Result<*> {
        //获取当前登录用户id
        val userId = getUserId(request!!)
        patient.userId = userId
        patientService!!.save(patient)
        return Result.ok<Any>()
    }

    //根据id获取就诊人信息
    @GetMapping("auth/get/{id}")
    fun getPatient(@PathVariable id: Long?): Result<*> {
        val patient = patientService!!.getPatientId(id)
        return Result.ok(patient)
    }

    //修改就诊人
    @PostMapping("auth/update")
    fun updatePatient(@RequestBody patient: Patient): Result<*> {
        patientService!!.updateById(patient)
        return Result.ok<Any>()
    }

    //删除就诊人
    @DeleteMapping("auth/remove/{id}")
    fun removePatient(@PathVariable id: Long?): Result<*> {
        patientService!!.removeById(id)
        return Result.ok<Any>()
    }

    @ApiOperation(value = "获取就诊人")
    @GetMapping("inner/get/{id}")
    fun getPatientOrder(
        @ApiParam(name = "id", value = "就诊人id", required = true) @PathVariable("id") id: Long?
    ): Patient? {
        return patientService!!.getById(id)
    }
}