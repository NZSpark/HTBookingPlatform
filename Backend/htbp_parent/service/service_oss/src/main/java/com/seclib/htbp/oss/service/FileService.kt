package com.seclib.htbp.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String upload(MultipartFile multipartFile);
}
