package com.seclib.htbp.model.base

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableLogic
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import lombok.Data
import java.io.Serializable
import java.util.*

@Data
open class BaseEntity : Serializable {
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    val id: Long? = null

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    var createTime: Date? = null

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    var updateTime: Date? = null

    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    @TableLogic
    @TableField("is_deleted")
    var isDeleted: Int? = null

    @ApiModelProperty(value = "其他参数")
    @TableField(exist = false)
    var param  = mutableMapOf<String,Any>()
}