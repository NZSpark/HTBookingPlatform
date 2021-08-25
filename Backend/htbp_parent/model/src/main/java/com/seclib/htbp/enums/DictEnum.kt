package com.seclib.htbp.enums

import com.seclib.htbp.enums.AuthStatusEnum
import com.seclib.htbp.enums.OrderStatusEnum
import java.util.HashMap

enum class DictEnum(var dictCode: String, var msg: String) {
    HOSTYPE("Hostype", "医院等级"), CERTIFICATES_TYPE("CertificatesType", "证件类型");

}