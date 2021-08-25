package com.seclib.htbp.model.base

import io.swagger.annotations.ApiModelProperty
import lombok.Data
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import java.io.Serializable
import java.util.*

@Data
open class BaseMongoEntity : Serializable {
    @ApiModelProperty(value = "id")
    @Id
    var id: String? = null

    @ApiModelProperty(value = "创建时间")
    var createTime: Date? = null

    @ApiModelProperty(value = "更新时间")
    var updateTime: Date? = null

    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    var isDeleted: Int? = null

    @ApiModelProperty(value = "其他参数")
    @Transient //被该注解标注的，将不会被录入到数据库中。只作为普通的javaBean属性
    var param = mutableMapOf<String,Any?>()
}