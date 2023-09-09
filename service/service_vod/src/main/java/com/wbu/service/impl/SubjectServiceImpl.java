package com.wbu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.listener.SubjectListener;
import com.wbu.model.vod.Subject;
import com.wbu.service.SubjectService;
import com.wbu.mapper.SubjectMapper;
import com.wbu.vo.vod.SubjectEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
* @author 11852
* @description 针对表【subject(课程科目)】的数据库操作Service实现
* @createDate 2023-09-03 14:30:13
*/
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject>
    implements SubjectService{
    private final SubjectListener subjectListener;

    public SubjectServiceImpl(SubjectListener subjectListener) {
        this.subjectListener = subjectListener;
    }

    @Override
    public List<Subject> selectSubjectList(Long id) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        for (Subject subject : subjectList) {
            //获取subject的id值
            Long subjectId = subject.getId();
            //查询
            boolean isChild = this.isChildren(subjectId);
            subject.setHasChildren(isChild);
        }
        return subjectList;
    }

    private boolean isChildren(Long id) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        return count>0;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try{
            //设置下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition","attachment;filename="+fileName+".xlsx");

            //查询课程分类的所有数据
            List<Subject> subjectList = baseMapper.selectList(null);

            List<SubjectEeVo> list = new ArrayList<>();
            for (Subject subject : subjectList) {
                SubjectEeVo subjectEeVo = new SubjectEeVo();
                BeanUtils.copyProperties(subject,subjectEeVo);
                list.add(subjectEeVo);
            }
            //EasyExcel写操作
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet("课程分类")
                    .doWrite(list);
        }catch (Exception e){
            throw new RuntimeException("导出失败");
        }
    }

    @Override
    public void importData(MultipartFile file) {
        try{
            EasyExcel.read(file.getInputStream(), SubjectEeVo.class,subjectListener).sheet().doRead();
        }catch (Exception e){
            throw new RuntimeException("导入失败");
        }
    }
}




