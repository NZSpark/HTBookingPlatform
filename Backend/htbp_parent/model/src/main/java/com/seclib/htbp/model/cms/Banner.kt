package com.seclib.htbp.model.cms

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.seclib.htbp.model.base.BaseEntity
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Data

/**
 *
 *
 * 首页Banner实体
 *
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "首页Banner实体")
@TableName("banner")
class Banner : BaseEntity() {
    @ApiModelProperty(value = "标题")
    @TableField("title")
    var title: String? = null

    @ApiModelProperty(value = "图片地址")
    @TableField("image_url")
    var imageUrl: String? = null

    @ApiModelProperty(value = "链接地址")
    @TableField("link_url")
    var linkUrl: String? = null

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    var sort: Int? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}