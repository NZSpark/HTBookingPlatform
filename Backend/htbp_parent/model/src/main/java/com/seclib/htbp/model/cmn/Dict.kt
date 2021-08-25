package com.seclib.htbp.model.cmn

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Data
import java.util.*

/**
 *
 *
 * Dict
 *
 *
 * @author qy
 */
@Data
@ApiModel(description = "数据字典")
@TableName("dict")
class Dict {
    @ApiModelProperty(value = "id")
    var id: Long? = null

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
    var param: Map<String, Any> = HashMap()

    @ApiModelProperty(value = "上级id")
    @TableField("parent_id")
    var parentId: Long? = null

    @ApiModelProperty(value = "名称")
    @TableField("name")
    var name: String? = null

    @ApiModelProperty(value = "值")
    @TableField("value")
    var value: String? = null

    @ApiModelProperty(value = "编码")
    @TableField("dict_code")
    var dictCode: String? = null

    @ApiModelProperty(value = "是否包含子节点")
    @TableField(exist = false)
    var hasChildren = false

    companion object {
        private const val serialVersionUID = 1L
    }
}