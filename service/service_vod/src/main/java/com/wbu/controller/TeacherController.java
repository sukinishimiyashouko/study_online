package com.wbu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.model.vod.Teacher;
import com.wbu.result.Result;
import com.wbu.service.TeacherService;
import com.wbu.vo.vod.TeacherQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * @auther 11852
 * @create 2023/9/1
 */
@Validated
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping("/admin/vod/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    /**
     * 查询所有讲师
     * @return
     */
    @ApiOperation("查询所有讲师")
    @GetMapping("/findAll")
    public Result<List<Teacher>> findAllTeacher(){
        List<Teacher> list = teacherService.list();
        return Result.success(list);
    }
    /**
     * 逻辑删除讲师
     */
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/remove/{id}")
    public Result<Boolean> removeTeacher(
            @NotNull(message = "id不能为空")
            @ApiParam(name = "id",value = "ID",required = true)
            @PathVariable Long id){
        boolean isSuccess = teacherService.removeById(id);
        return Result.success(isSuccess);
    }

    /**
     * 条件分页查询
     */
    @ApiOperation("条件分页查询")
    @PostMapping("/findQueryPage/{current}/{limit}")
    public Result findPage(@PathVariable Long current,
                           @PathVariable Long limit,
                           @RequestBody(required = false) TeacherQueryVo teacherQueryVo){
        Page<Teacher> pageParam = new Page<>(current,limit);
        //判断teacherQueryVO对象是否为空
        if (teacherQueryVo == null){
            //查询所有
            IPage<Teacher> pageModel = teacherService.page(pageParam,null);
            return Result.success(pageModel);
        }else {
            //获取条件值，进行非空判断，条件封装
            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();
            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            if (Objects.nonNull(name)){
                wrapper.like("name",name);
            }
            if (Objects.nonNull(level)){
                wrapper.eq("level",level);
            }
            if (Objects.nonNull(joinDateBegin)){
                wrapper.ge("join_date",joinDateBegin);
            }
            if (Objects.nonNull(joinDateEnd)){
                wrapper.le("join_date",joinDateEnd);
            }
            IPage<Teacher> pageModel = teacherService.page(pageParam,wrapper);
            return Result.success(pageModel);
        }
    }
    /**
     * 添加讲师
     */
    @ApiOperation("添加讲师")
    @PostMapping("/saveTeacher")
    public Result<Boolean> saveTeacher(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        return Result.success(save);
    }

    /**
     * 修改-根据id查询
     */
    @ApiOperation("根据id查询")
    @GetMapping("/getTeacher/{id}")
    public Result<Teacher> getTeacher(@PathVariable Long id){
        Teacher teacher = teacherService.getById(id);
        return Result.success(teacher);
    }
    /**
     * 修改-最终修改
     */
    @ApiOperation("修改最终实现")
    @PostMapping("/updateTeacher")
    public Result<Boolean> updateTeacher(@RequestBody Teacher teacher){
        boolean flag = teacherService.updateById(teacher);
        return Result.success(flag);
    }

    /**
     * 批量删除讲师
     */
    @ApiOperation("批量删除讲师")
    @DeleteMapping("/removeBatch")
    public Result<Boolean> removeBatch(@RequestBody List<Long> idList){
        boolean b = teacherService.removeByIds(idList);
        return Result.success(b);
    }
}
