package com.seclib.htbp.hosp.service.impl

import com.alibaba.fastjson.JSONObject

import com.seclib.htbp.hosp.service.DepartmentService
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.hosp.repository.DepartmentRepository
import com.seclib.htbp.model.hosp.Department
import com.seclib.htbp.vo.hosp.DepartmentQueryVo
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.util.stream.Collectors
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
open class DepartmentServiceImpl : DepartmentService {
    @Autowired
    private val departmentRepository: DepartmentRepository? = null
    override fun save(paramMap: Map<String?, Any?>?) {
        val paramString = JSONObject.toJSONString(paramMap)
        val department = JSONObject.parseObject(paramString, Department::class.java)
        val departmentExist =
            departmentRepository!!.getDepartmentByHoscodeAndDepcode(department.hoscode, department.depcode)
        if (departmentExist != null) {
            departmentExist.updateTime = Date()
            departmentExist.isDeleted = 0
            departmentRepository.save(departmentExist)
        } else {
            department.createTime = Date()
            department.updateTime = Date()
            department.isDeleted = 0
            departmentRepository.save(department)
        }
    }

    override fun findPageDepartment(
        page: Int?,
        limit: Int?,
        departmentQueryVo: DepartmentQueryVo?
    ): Page<Department?>? {
        val pageable: Pageable = PageRequest.of(page!! - 1, limit!!)
        val matcher = ExampleMatcher.matching()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreCase(true)
        val department = Department()
        BeanUtils.copyProperties(departmentQueryVo, department)
        val example =
            Example.of(department, matcher)
        return departmentRepository!!.findAll(example, pageable)
    }

    override fun remove(hoscode: String?, depcode: String?) {
        val department = departmentRepository!!.getDepartmentByHoscodeAndDepcode(hoscode, depcode)
        if (department != null) {
            departmentRepository.deleteById(department.id)
        }
    }

    override fun findDeptTree(hoscode: String?): List<DepartmentVo?>? {
        val result: MutableList<DepartmentVo?> = ArrayList()
        val departmentQuery = Department()
        departmentQuery.hoscode = hoscode
        val example = Example.of(departmentQuery)
        val departmentList = departmentRepository!!.findAll(example)
        val departmentMap = departmentList.stream().collect(
            Collectors.groupingBy(
                Function { obj: Department -> obj.bigcode })
        )
        for ((bigcode, departmentByBigCodeList) in departmentMap) {
            val departmentVo = DepartmentVo()
            departmentVo.depcode = bigcode
            departmentVo.depname = departmentByBigCodeList[0].bigname
            val children: MutableList<DepartmentVo> = ArrayList()
            for (department in departmentByBigCodeList) {
                val childDepartmentVo = DepartmentVo()
                childDepartmentVo.depcode = department.depcode
                childDepartmentVo.depname = department.depname
                children.add(childDepartmentVo)
            }
            departmentVo.children = children
            result.add(departmentVo)
        }
        return result
    }

    override fun getDepName(hoscode: String?, depcode: String?): String? {
        val department = departmentRepository!!.getDepartmentByHoscodeAndDepcode(hoscode, depcode)
        return department?.depname
    }

    override fun getDepartment(hoscode: String?, depcode: String?): Department? {
        return departmentRepository!!.getDepartmentByHoscodeAndDepcode(hoscode, depcode)
    }
}