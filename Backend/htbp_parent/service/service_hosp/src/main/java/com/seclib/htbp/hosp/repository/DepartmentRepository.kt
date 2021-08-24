package com.seclib.htbp.hosp.repository

import org.springframework.data.mongodb.repository.MongoRepository
import com.seclib.htbp.model.hosp.Department
import org.springframework.stereotype.Repository

@Repository
interface DepartmentRepository : MongoRepository<Department?, String?> {
    fun getDepartmentByHoscodeAndDepcode(hoscode: String?, depcode: String?): Department?
}