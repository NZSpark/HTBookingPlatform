package com.seclib.htbp.model.hosp

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.model.base.BaseMongoEntity
import com.seclib.htbp.model.hosp.BookingRule
import lombok.Data
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*

/**
 *
 *
 * Schedule
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "Schedule")
@Document("Schedule")
class Schedule : BaseMongoEntity() {
    @ApiModelProperty(value = "医院编号")
    @Indexed //普通索引
    var hoscode: String? = null

    @ApiModelProperty(value = "科室编号")
    @Indexed //普通索引
    var depcode: String? = null

    @ApiModelProperty(value = "职称")
    var title: String? = null

    @ApiModelProperty(value = "医生名称")
    var docname: String? = null

    @ApiModelProperty(value = "擅长技能")
    var skill: String? = null

    @ApiModelProperty(value = "排班日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    var workDate: Date? = null

    @ApiModelProperty(value = "排班时间（0：上午 1：下午）")
    var workTime: Int? = null

    @ApiModelProperty(value = "可预约数")
    var reservedNumber: Int? = null

    @ApiModelProperty(value = "剩余预约数")
    var availableNumber: Int? = null

    @ApiModelProperty(value = "挂号费")
    var amount: BigDecimal? = null

    @ApiModelProperty(value = "排班状态（-1：停诊 0：停约 1：可约）")
    var status: Int? = null

    @ApiModelProperty(value = "排班编号（医院自己的排班主键）")
    @Indexed //普通索引
    var hosScheduleId: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}