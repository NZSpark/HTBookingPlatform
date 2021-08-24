package com.seclib.htbp.hosp.controller

import com.seclib.htbp.common.result.Result
import com.seclib.htbp.hosp.service.HospitalService
import com.seclib.htbp.vo.hosp.HospitalQueryVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/admin/hosp/hospital") //@CrossOrigin
class HospitalController {
    @Autowired
    private val hospitalService: HospitalService? = null
    @GetMapping("list/{page}/{limit}")
    fun listHosp(@PathVariable page: Int?, @PathVariable limit: Int?, hospitalQueryVo: HospitalQueryVo?): Result<*> {
        val pageModel = hospitalService!!.selectHospPage(page, limit, hospitalQueryVo)
        return Result.ok(pageModel)
    }

    @GetMapping("updateHospStatus/{id}/{status}")
    fun updateHospStatus(@PathVariable id: String?, @PathVariable status: Int?): Result<*> {
        hospitalService!!.updateStatus(id, status)
        return Result.ok<Any>()
    }

    @GetMapping("showHospitalDetail/{id}")
    fun showHospitalDetail(@PathVariable id: String?): Result<*> {
        val result = hospitalService!!.getHospById(id)
        return Result.ok(result)
    }
}