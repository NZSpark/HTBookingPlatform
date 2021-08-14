package com.seclib.htbp.hosp.controller;

import com.seclib.htbp.common.result.Result;
import com.seclib.htbp.hosp.service.ScheduleService;
import com.seclib.htbp.model.hosp.Schedule;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/department")
//@CrossOrigin
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

    @ApiOperation(value = "get Schedule Detail")
    @GetMapping("getScheduleDetail/{hoscode}/{depcode}/{workDate}")
    public Result getScheduleDetail(@PathVariable String hoscode,
                                    @PathVariable String depcode,
                                    @PathVariable String workDate){
        List<Schedule> list = scheduleService.getScheduleDetail(hoscode,depcode,workDate);
        return Result.ok(list);

    }

}
