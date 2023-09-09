package com.wbu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.model.vod.Course;
import com.wbu.result.Result;
import com.wbu.service.CourseService;
import com.wbu.vo.vod.CourseFormVo;
import com.wbu.vo.vod.CourseQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @auther 11852
 * @create 2023/9/3
 */
@RestController
@RequestMapping("/admin/vod/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //添加课程基本信息表
    @ApiOperation("添加课程基本信息表")
    @PostMapping("/save")
    public Result save(@RequestBody CourseFormVo courseFormVo){
        Long courseId = courseService.saveCourseInfo(courseFormVo);
        return Result.success(courseId);
    }
    /**
     * 点播课堂列表
     */
    @ApiOperation("点播课程列表")
    @GetMapping("/{page}/{limit}")
    public Result courseList(@PathVariable Long page,
                             @PathVariable Long limit,
                             CourseQueryVo courseQueryVo){
        Page<Course> coursePage = new Page<>(page,limit);
        Map<String,Object> map = courseService.findPageCourse(coursePage,courseQueryVo);
        return Result.success(map);
    }
    /**
     * 根据id获取课程信息
     */
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id){
        CourseFormVo courseFormVo = courseService.getCourseInfoById(id);
        return Result.success(courseFormVo);
    }

    /**
     * 修改课程信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody CourseFormVo courseFormVo){
        courseService.updateCourseId(courseFormVo);
        return Result.success(null);
    }
}
