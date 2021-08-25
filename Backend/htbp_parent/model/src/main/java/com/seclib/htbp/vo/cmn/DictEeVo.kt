package com.seclib.htbp.vo.cmn

import com.alibaba.excel.annotation.ExcelProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.vo.hosp.DepartmentVo
import java.math.BigDecimal
import com.seclib.htbp.vo.msm.MsmVo
import lombok.Data

/**
 *
 *
 * Dict
 *
 *
 * @author qy
 */
@Data
class DictEeVo {
    @ExcelProperty(value = ["id"], index = 0)
    var id: Long? = null

    @ExcelProperty(value = ["上级id"], index = 1)
    var parentId: Long? = null

    @ExcelProperty(value = ["名称"], index = 2)
    var name: String? = null

    @ExcelProperty(value = ["值"], index = 3)
    var value: String? = null

    @ExcelProperty(value = ["编码"], index = 4)
    var dictCode: String? = null
}