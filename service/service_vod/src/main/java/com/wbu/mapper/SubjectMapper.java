package com.wbu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wbu.model.vod.Subject;
import org.springframework.stereotype.Repository;

/**
* @author 11852
* @description 针对表【subject(课程科目)】的数据库操作Mapper
* @createDate 2023-09-03 14:30:13
* @Entity com.wbu.pojo.Subject
*/
@Repository
public interface SubjectMapper extends BaseMapper<Subject> {

}




