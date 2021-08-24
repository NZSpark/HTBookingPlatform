package com.seclib.htbp.hosp.service

import com.seclib.htbp.vo.hosp.DepartmentQueryVo
import com.seclib.htbp.model.hosp.Department
import com.seclib.htbp.vo.hosp.DepartmentVo
import org.springframework.data.domain.Page

interface DepartmentService {
    fun save(paramMap: Map<String?, Any?>?)
    fun findPageDepartment(page: Int?, limit: Int?, departmentQueryVo: DepartmentQueryVo?): Page<Department?>?
    fun remove(hoscode: String?, depcode: String?)
    fun findDeptTree(hoscode: String?): List<DepartmentVo?>?
    fun getDepName(hoscode: String?, depcode: String?): String?
    fun getDepartment(hoscode: String?, depcode: String?): Department?
}