package com.wbu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wbu.mapper.SubjectMapper;
import com.wbu.model.vod.Subject;
import com.wbu.vo.vod.SubjectEeVo;
import com.wbu.vo.vod.SubjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @auther 11852
 * @create 2023/9/3
 */
@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {
    private final SubjectMapper subjectMapper;

    public SubjectListener(SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }

    //按行读取数据 从第二行开始读取
    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext analysisContext) {
        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectEeVo,subject);
        //添加
        subjectMapper.insert(subject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
