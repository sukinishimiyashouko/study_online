package com.wbu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wbu.model.vod.CourseDescription;
import com.wbu.service.CourseDescriptionService;
import com.wbu.mapper.CourseDescriptionMapper;
import org.springframework.stereotype.Service;

/**
* @author 11852
* @description 针对表【course_description(课程简介)】的数据库操作Service实现
* @createDate 2023-09-03 16:39:04
*/
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription>
    implements CourseDescriptionService{

}




