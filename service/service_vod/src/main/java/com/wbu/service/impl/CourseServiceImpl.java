package com.wbu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wbu.model.vod.Course;
import com.wbu.model.vod.CourseDescription;
import com.wbu.model.vod.Subject;
import com.wbu.model.vod.Teacher;
import com.wbu.service.CourseDescriptionService;
import com.wbu.service.CourseService;
import com.wbu.mapper.CourseMapper;
import com.wbu.service.SubjectService;
import com.wbu.service.TeacherService;
import com.wbu.vo.vod.CourseFormVo;
import com.wbu.vo.vod.CourseQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
* @author 11852
* @description 针对表【course(课程)】的数据库操作Service实现
* @createDate 2023-09-03 16:38:33
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final CourseDescriptionService courseDescriptionService;

    public CourseServiceImpl(TeacherService teacherService, SubjectService subjectService, CourseDescriptionService courseDescriptionService) {
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.courseDescriptionService = courseDescriptionService;
    }

    @Override
    public Map<String, Object> findPageCourse(Page<Course> coursePage, CourseQueryVo courseQueryVo) {
        //获取条件值
        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId();//二层分类
        Long subjectParentId = courseQueryVo.getSubjectParentId();//一层分类
        Long teacherId = courseQueryVo.getTeacherId();
        //判断条件是否为空，封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (!Objects.isNull(title)){
            wrapper.like("title",title);
        }
        if (!Objects.isNull(subjectId)){
            wrapper.like("subject_id",subjectId);
        }
        if (!Objects.isNull(subjectParentId)){
            wrapper.like("subject_parent_id",subjectParentId);
        }
        if (!Objects.isNull(teacherId)){
            wrapper.like("teacher_id",teacherId);
        }
        //调用方法实现条件查询分页
        Page<Course> page = baseMapper.selectPage(coursePage, wrapper);
        long totalCount = page.getTotal();
        long totalPage = page.getPages();
        List<Course> list = page.getRecords();

        //查询数据里面有几个id
        list.stream().forEach(this::getNameById);

        //封装数据
        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",list);
        return map;
    }

    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        //添加课程的基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.insert(course);
        //添加课程的描述信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        //设置课程id
        courseDescription.setCourseId(course.getId());
        courseDescriptionService.save(courseDescription);
        return course.getId();
    }

    @Override
    public CourseFormVo getCourseInfoById(Long id) {
        //课程基本信息
        Course course = baseMapper.selectById(id);
        if (Objects.isNull(course)){
            return null;
        }
        //课程描述信息
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course,courseFormVo);
        //封装描述信息
        if (courseDescription!=null){
            courseFormVo.setDescription(courseDescription.getDescription());
        }
        return courseFormVo;
    }

    @Override
    public void updateCourseId(CourseFormVo courseFormVo) {
        //修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.updateById(course);

        //修改课程描述信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescriptionService.updateById(courseDescription);

    }

    @Override
    public Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        return null;
    }

    @Override
    public Map<String, Object> getInfoById(Long courseId) {
        return null;
    }

    private Course getNameById(Course course) {
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if (Objects.nonNull(teacher)){
            String name = teacher.getName();
            course.getParam().put("teacherName",name);
        }

        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if (Objects.nonNull(subjectOne)){
            course.getParam().put("subjectParentTitle",subjectOne.getTitle());
        }

        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if (Objects.nonNull(subjectTwo)){
            course.getParam().put("subjectTitle",subjectTwo.getTitle());
        }
        return course;
    }
}




