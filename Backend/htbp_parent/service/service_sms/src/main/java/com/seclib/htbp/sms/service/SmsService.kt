package com.seclib.htbp.sms.service;

import com.seclib.htbp.vo.msm.MsmVo;

public interface SmsService {
    boolean send(String phone, String code);
    boolean send(MsmVo msmVo);
}
