package com.seclib.htbp.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.seclib.htbp.cmn.client.DictFeignClient;
import com.seclib.htbp.hosp.repository.HospitalRepository;
import com.seclib.htbp.hosp.service.HospitalService;
import com.seclib.htbp.model.hosp.Hospital;
import com.seclib.htbp.vo.hosp.HospitalQueryVo;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public void save(Map<String, Object> paramMap) {

        String mapString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);

        if (hospitalExist != null) {
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
        } else {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
        }

        hospital.setUpdateTime(new Date());
        hospital.setIsDeleted(0);
        hospitalRepository.save(hospital);
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        return hospital;
    }

    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {

        Pageable pageable = PageRequest.of(page-1,limit);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo,hospital);

        Example<Hospital> example = Example.of(hospital,matcher);

        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);
        pages.getContent().stream().forEach(item -> {
            this.setHospitalHosType(item);
        });

        return pages;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);
    }

    @Override
    public  Map<String,Object> getHospById(String id) {
        Map<String,Object> result = new HashMap<>();
        Hospital hospital = this.setHospitalHosType( hospitalRepository.findById(id).get());
        result.put("hospital",hospital);
        result.put("bookingRule",hospital.getBookingRule());
        hospital.setBookingRule(null);

        return result;
    }

    @Override
    public String getHospName(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        if(hospital != null){
            return hospital.getHosname();
        }
        return null;
    }

    private Hospital setHospitalHosType(Hospital hospital) {
        String hostypeString = dictFeignClient.getName("hostype", hospital.getHostype());
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());

        hospital.getParam().put("hostypeString",hostypeString);
        hospital.getParam().put("fullAddress",provinceString + cityString + districtString);

        return hospital;
    }
}
