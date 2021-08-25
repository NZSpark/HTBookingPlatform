package com.seclib.htbp.model.hosp

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.model.base.BaseMongoEntity
import com.seclib.htbp.model.hosp.BookingRule
import lombok.Data
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

/**
 *
 *
 * Department
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "Department")
@Document("Department")
class Department : BaseMongoEntity() {
    @ApiModelProperty(value = "医院编号")
    @Indexed //普通索引
    var hoscode: String? = null

    @ApiModelProperty(value = "科室编号")
    @Indexed(unique = true) //唯一索引
    var depcode: String? = null

    @ApiModelProperty(value = "科室名称")
    var depname: String? = null

    @ApiModelProperty(value = "科室描述")
    var intro: String? = null

    @ApiModelProperty(value = "大科室编号")
    var bigcode: String? = null

    @ApiModelProperty(value = "大科室名称")
    var bigname: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}