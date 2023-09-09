package com.wbu.controller;

import com.wbu.result.Result;
import com.wbu.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @auther 11852
 * @create 2023/9/9
 */
@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/admin/vod/file")
public class FileUploadController {
    private final FileService fileService;

    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file){
        String url = fileService.upload(file);
        return Result.success(url);
    }
}
