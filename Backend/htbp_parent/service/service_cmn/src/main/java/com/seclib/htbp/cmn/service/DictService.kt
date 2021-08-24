package com.seclib.htbp.cmn.service

import com.baomidou.mybatisplus.extension.service.IService
import com.seclib.htbp.model.cmn.Dict
import javax.servlet.http.HttpServletResponse
import org.springframework.web.multipart.MultipartFile

interface DictService : IService<Dict?> {
    fun findChildData(id: Long?): List<Dict?>?
    fun exportDictData(response: HttpServletResponse?)
    fun importDictData(file: MultipartFile?)
    fun getDictName(s: String?, value: String?): String?
    fun findByDictCode(dictCode: String?): List<Dict?>?
}