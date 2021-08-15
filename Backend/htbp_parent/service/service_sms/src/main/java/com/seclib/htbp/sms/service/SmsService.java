package com.seclib.htbp.sms.service;

public interface SmsService {
    boolean send(String phone, String code);
}
