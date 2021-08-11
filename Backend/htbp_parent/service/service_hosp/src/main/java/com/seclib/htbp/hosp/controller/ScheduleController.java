package com.seclib.htbp.hosp.controller;

import com.seclib.htbp.common.result.Result;
import com.seclib.htbp.hosp.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/department")
@CrossOrigin
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation(value = "get Schedule Rule.")
    @GetMapping("getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getScheduleRule(@PathVariable Long page,
                                  @PathVariable Long limit,
                                  @PathVariable String hoscode,
                                  @PathVariable String depcode){

        Map<String,Object> map = scheduleService.getScheduleRule(page,limit,hoscode,depcode);

        return Result.ok(map);
    }


}
