package com.seclib.htbp.hosp.controller;

import com.seclib.htbp.common.result.Result;
import com.seclib.htbp.hosp.service.DepartmentService;
import com.seclib.htbp.vo.hosp.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hosp/department")
//@CrossOrigin
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode){
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);

        return Result.ok(list);
    }

}
