package com.wbu.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @auther 11852
 * @create 2023/9/9
 */
public interface FileService {
    String upload(MultipartFile file);

}
