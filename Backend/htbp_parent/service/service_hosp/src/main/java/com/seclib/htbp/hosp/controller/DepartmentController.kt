package com.seclib.htbp.hosp.controller

import com.seclib.htbp.hosp.service.DepartmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.seclib.htbp.common.result.Result;

@RestController
@RequestMapping("/admin/hosp/department") //@CrossOrigin
class DepartmentController {
    @Autowired
    private val departmentService: DepartmentService? = null
    @GetMapping("getDeptList/{hoscode}")
    fun getDeptList(@PathVariable hoscode: String?): Result<*> {
        val list = departmentService!!.findDeptTree(hoscode)
        return Result.ok(list)
    }
}