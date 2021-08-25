package com.seclib.htbp.oss.controller

import com.seclib.htbp.common.result.Result
import com.seclib.htbp.common.result.Result.Companion.ok
import com.seclib.htbp.oss.service.FileService
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping

@RestController
@RequestMapping("/api/oss/file")
class FileApiController {
    @Autowired
    private val fileService: FileService? = null
    @PostMapping("fileUpload")
    fun fileUpload(multipartFile: MultipartFile): Result<String> {
        val url = fileService!!.upload(multipartFile)
        return ok(url)
    }
}