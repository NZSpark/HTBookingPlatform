package com.seclib.htbp.vo.user

import com.baomidou.mybatisplus.annotation.TableField
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

@Data
@ApiModel(description = "会员认证对象")
class UserAuthVo {
    @ApiModelProperty(value = "用户姓名")
    @TableField("name")
    var name: String? = null

    @ApiModelProperty(value = "证件类型")
    @TableField("certificates_type")
    var certificatesType: String? = null

    @ApiModelProperty(value = "证件编号")
    @TableField("certificates_no")
    var certificatesNo: String? = null

    @ApiModelProperty(value = "证件路径")
    @TableField("certificates_url")
    var certificatesUrl: String? = null
}