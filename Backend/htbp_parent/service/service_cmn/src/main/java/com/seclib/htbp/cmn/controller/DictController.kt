package com.seclib.htbp.cmn.controller


import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.cmn.service.DictService
import com.seclib.htbp.common.result.Result
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PathVariable

@Api(tags = ["Dictionary configuration."])
@RestController
@RequestMapping("/admin/cmn/dict") //@CrossOrigin
class DictController {
    @Autowired
    private val dictService: DictService? = null
    @PostMapping("importData")
    fun importDict(file: MultipartFile?): Result<*> {
        dictService!!.importDictData(file)
        return Result.ok<Any>()
    }

    @GetMapping("exportData")
    fun exportDict(response: HttpServletResponse?): Result<*> {
        dictService!!.exportDictData(response)
        return Result.ok<Any>()
    }

    @GetMapping("findChildData/{id}")
    fun findChildData(@PathVariable id: Long?): Result<*> {
        val list = dictService!!.findChildData(id)
        return Result.ok(list)
    }

    @GetMapping("getName/{dictCode}/{value}")
    fun getName(@PathVariable dictCode: String?, @PathVariable value: String?): String? {
        return dictService!!.getDictName(dictCode, value)
    }

    @GetMapping("getName/{value}")
    fun getName(@PathVariable value: String?): String? {
        return dictService!!.getDictName("", value)
    }

    @GetMapping("findByDictCode/{dictCode}")
    fun findByDictCode(@PathVariable dictCode: String?): Result<*> {
        val dictList = dictService!!.findByDictCode(dictCode)
        return Result.ok(dictList)
    }
}