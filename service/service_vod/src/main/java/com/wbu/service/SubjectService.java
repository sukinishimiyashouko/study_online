package com.wbu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.model.vod.Subject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author 11852
* @description 针对表【subject(课程科目)】的数据库操作Service
* @createDate 2023-09-03 14:30:13
*/
public interface SubjectService extends IService<Subject> {

    List<Subject> selectSubjectList(Long id);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);
}
