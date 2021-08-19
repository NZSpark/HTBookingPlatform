package com.seclib.htbp.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seclib.htbp.model.hosp.HospitalSet;
import com.seclib.htbp.vo.order.SignInfoVo;

public interface HospitalSetService extends IService<HospitalSet> {

    String getSignKey(String hoscode);
    //获取医院签名信息
    SignInfoVo getSignInfoVo(String hoscode);

}
