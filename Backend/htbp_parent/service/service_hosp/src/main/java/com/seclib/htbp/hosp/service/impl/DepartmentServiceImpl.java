package com.seclib.htbp.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.seclib.htbp.hosp.repository.DepartmentRepository;
import com.seclib.htbp.hosp.service.DepartmentService;
import com.seclib.htbp.model.hosp.Department;
import com.seclib.htbp.vo.hosp.DepartmentQueryVo;
import com.seclib.htbp.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        List<DepartmentVo> result = new ArrayList<>();

        Department departmentQuery = new Department();
        departmentQuery.setHoscode(hoscode);

        Example<Department> example = Example.of(departmentQuery);
        List<Department> departmentList = departmentRepository.findAll(example);

        Map<String, List<Department>> departmentMap = departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));
        for(Map.Entry<String,List<Department>> entry: departmentMap.entrySet()){
            String bigcode = entry.getKey();
            List<Department> departmentByBigCodeList = entry.getValue();
            DepartmentVo departmentVo = new DepartmentVo();
            departmentVo.setDepcode(bigcode);
            departmentVo.setDepname(departmentByBigCodeList.get(0).getBigname());

            List<DepartmentVo> children = new ArrayList<>();
            for(Department department: departmentByBigCodeList){
                DepartmentVo childDepartmentVo = new DepartmentVo();
                childDepartmentVo.setDepcode(department.getDepcode());
                childDepartmentVo.setDepname(department.getDepname());
                children.add(childDepartmentVo);
            }
            departmentVo.setChildren(children);
            result.add(departmentVo);
        }

        return result;
    }
}
