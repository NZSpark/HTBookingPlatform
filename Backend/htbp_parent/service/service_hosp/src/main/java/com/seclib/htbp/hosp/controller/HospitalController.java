package com.seclib.htbp.hosp.controller;

import com.seclib.htbp.common.result.Result;
import com.seclib.htbp.hosp.service.HospitalService;
import com.seclib.htbp.model.hosp.Hospital;
import com.seclib.htbp.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/hospital")
@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page, @PathVariable Integer limit, HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> pageModel = hospitalService.selectHospPage(page,limit,hospitalQueryVo);

        return Result.ok(pageModel);
    }

    @GetMapping("updateHospStatus/{id}/{status}")
    public  Result updateHospStatus(@PathVariable String id,@PathVariable Integer status){
        hospitalService.updateStatus(id,status);
        return Result.ok();
    }

    @GetMapping("showHospitalDetail/{id}")
    public Result showHospitalDetail(@PathVariable String id){
        Map<String,Object> result = hospitalService.getHospById(id);
        return Result.ok(result);
    }

}
