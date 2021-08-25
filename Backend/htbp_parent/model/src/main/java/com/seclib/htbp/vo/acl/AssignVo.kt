package com.seclib.htbp.vo.acl

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

@Data
class AssignVo {
    var roleId: Long? = null
    var permissionId: Array<Long> = arrayOf<Long>()
}