package com.wbu.controller;

import com.wbu.model.vod.Subject;
import com.wbu.result.Result;
import com.wbu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @auther 11852
 * @create 2023/9/3
 */
@RestController
@RequestMapping("/admin/vod/subject")
@Api("课程分类管理")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    /**
     * 课程分类列表
     * 懒加载，每次查询一层
     */
    @ApiOperation("课程分类列表")
    @GetMapping("/getChildSubject/{id}")
    public Result getChildSubject(@PathVariable Long id){
        List<Subject> list = subjectService.selectSubjectList(id);
        return Result.success(list);
    }

    /**
     * 课程分类导出
     */
    @ApiOperation("课程分类导出")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response){
        subjectService.exportData(response);
    }

    /**
     * 课程分类导入
     */
    @ApiOperation("课程分类导入")
    @PostMapping("/importData")
    public Result importData(MultipartFile file){
        subjectService.importData(file);
        return Result.success(null);
    }
}
