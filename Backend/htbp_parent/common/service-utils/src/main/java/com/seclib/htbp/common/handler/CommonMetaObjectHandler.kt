package com.seclib.htbp.common.handler

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.util.*

@Component
class CommonMetaObjectHandler : MetaObjectHandler {
    override fun insertFill(metaObject: MetaObject) {
        this.setFieldValByName("createTime", Date(), metaObject)
        this.setFieldValByName("updateTime", Date(), metaObject)
    }

    override fun updateFill(metaObject: MetaObject) {
        this.setFieldValByName("updateTime", Date(), metaObject)
    }
}