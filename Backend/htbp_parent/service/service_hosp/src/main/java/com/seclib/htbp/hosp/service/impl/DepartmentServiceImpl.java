package com.seclib.htbp.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.seclib.htbp.hosp.repository.DepartmentRepository;
import com.seclib.htbp.hosp.service.DepartmentService;
import com.seclib.htbp.model.hosp.Department;
import com.seclib.htbp.vo.hosp.DepartmentQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        String paramString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(paramString,Department.class);

        Department departmentExist = departmentRepository.getDeparmentByHoscodeAndDepcode(department.getHoscode(),department.getDepcode());

        if(departmentExist != null){
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime( new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> findPageDepartment(Integer page, Integer limit, DepartmentQueryVo departmentQueryVo) {
        Pageable pageable = PageRequest.of(page -1 ,limit);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo,department);
        Example<Department> example = Example.of(department,matcher);
        Page<Department> all =  departmentRepository.findAll(example,pageable);
        return all;
    }

    @Override
    public void remove(String hoscode, String depcode) {
        Department department = departmentRepository.getDeparmentByHoscodeAndDepcode(hoscode,depcode);
        if(department != null) {
            departmentRepository.deleteById(department.getId());
        }
    }
}
