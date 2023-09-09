package com.wbu.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.model.vod.Course;
import com.wbu.vo.vod.CourseFormVo;
import com.wbu.vo.vod.CourseQueryVo;

import java.util.Map;

/**
* @author 11852
* @description 针对表【course(课程)】的数据库操作Service
* @createDate 2023-09-03 16:38:33
*/
public interface CourseService extends IService<Course> {

    Map<String, Object> findPageCourse(Page<Course> coursePage, CourseQueryVo courseQueryVo);

    Long saveCourseInfo(CourseFormVo courseFormVo);

    CourseFormVo getCourseInfoById(Long id);

    void updateCourseId(CourseFormVo courseFormVo);

    Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    Map<String, Object> getInfoById(Long courseId);
}
