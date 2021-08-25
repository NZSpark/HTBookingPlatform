package com.seclib.htbp.oss.controller;

import com.seclib.htbp.common.result.Result;
import com.seclib.htbp.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {

    @Autowired
    private FileService fileService;

    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile multipartFile){
        String url = fileService.upload(multipartFile);
        return Result.ok(url);
    }
}
