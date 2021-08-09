package com.seclib.htbp.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seclib.htbp.model.hosp.Hospital;
import com.seclib.htbp.model.hosp.HospitalSet;

import java.util.Map;

public interface HospitalService {
    void save(Map<String, Object> paramMap);
}
