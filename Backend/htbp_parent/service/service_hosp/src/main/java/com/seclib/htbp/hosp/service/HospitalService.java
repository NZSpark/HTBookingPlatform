package com.seclib.htbp.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seclib.htbp.model.hosp.Hospital;
import com.seclib.htbp.model.hosp.HospitalSet;
import com.seclib.htbp.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface HospitalService {
    void save(Map<String, Object> paramMap);

    Hospital getByHoscode(String hoscode);

    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);
}
