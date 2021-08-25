package com.seclib.htbp.oss.service

import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun upload(multipartFile: MultipartFile): String
}