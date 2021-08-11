package com.seclib.htbp.hosp.service;

import com.seclib.htbp.model.hosp.Schedule;
import com.seclib.htbp.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ScheduleService {
    void save(Map<String, Object> paramMap);
    Page<Schedule> selectPage(Integer page, Integer limit, ScheduleQueryVo scheduleQueryVo);

    void remove(String hoscode, String hosScheduleId);

    Map<String, Object> getScheduleRule(Long page, Long limit, String hoscode, String depcode);
}
