package com.seclib.htbp.model.hosp

import com.alibaba.fastjson.JSONArray
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import com.seclib.htbp.model.base.BaseMongoEntity
import com.seclib.htbp.model.hosp.BookingRule
import lombok.Data
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.util.StringUtils
import java.math.BigDecimal

/**
 *
 *
 * RegisterRule
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "预约规则")
@Document("BookingRule")
class BookingRule {
    @ApiModelProperty(value = "预约周期")
    var cycle: Int? = null

    @ApiModelProperty(value = "放号时间")
    var releaseTime: String? = null

    @ApiModelProperty(value = "停挂时间")
    var stopTime: String? = null

    @ApiModelProperty(value = "退号截止天数（如：就诊前一天为-1，当天为0）")
    var quitDay: Int? = null

    @ApiModelProperty(value = "退号时间")
    var quitTime: String? = null

    @ApiModelProperty(value = "预约规则")
    private var rule: List<String>? = null

    /**
     *
     * @param rule
     */
    fun setRule(rule: String?) {
        if (!StringUtils.isEmpty(rule)) {
            this.rule = JSONArray.parseArray(rule, String::class.java)
        }
    }
}