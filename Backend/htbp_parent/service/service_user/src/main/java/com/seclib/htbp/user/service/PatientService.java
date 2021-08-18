package com.seclib.htbp.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seclib.htbp.model.user.Patient;

import java.util.List;

public interface PatientService extends IService<Patient> {
    List<Patient> findAllByUserId(Long userId);

    Patient getPatientId(Long id);
}
