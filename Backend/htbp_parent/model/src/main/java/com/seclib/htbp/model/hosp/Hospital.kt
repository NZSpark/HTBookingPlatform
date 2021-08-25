package com.seclib.htbp.model.hosp

import com.alibaba.fastjson.JSONObject
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
 * Hospital
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "Hospital")
@Document("Hospital")
class Hospital : BaseMongoEntity() {
    @ApiModelProperty(value = "医院编号")
    @Indexed(unique = true) //唯一索引
    var hoscode: String? = null

    @ApiModelProperty(value = "医院名称")
    @Indexed //普通索引
    var hosname: String? = null

    @ApiModelProperty(value = "医院类型")
    var hostype: String? = null

    @ApiModelProperty(value = "省code")
    var provinceCode: String? = null

    @ApiModelProperty(value = "市code")
    var cityCode: String? = null

    @ApiModelProperty(value = "区code")
    var districtCode: String? = null

    @ApiModelProperty(value = "详情地址")
    var address: String? = null

    @ApiModelProperty(value = "医院logo")
    var logoData: String? = null

    @ApiModelProperty(value = "医院简介")
    var intro: String? = null

    @ApiModelProperty(value = "坐车路线")
    var route: String? = null

    @ApiModelProperty(value = "状态 0：未上线 1：已上线")
    var status: Int? = null

    //预约规则
    @ApiModelProperty(value = "预约规则")
    var bookingRule: BookingRule? = null
    fun setBookingRule(bookingRule: String?) {
        this.bookingRule = JSONObject.parseObject(bookingRule, BookingRule::class.java)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}