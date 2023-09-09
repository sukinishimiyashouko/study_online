package com.wbu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wbu.model.vod.Chapter;
import com.wbu.service.ChapterService;
import com.wbu.mapper.ChapterMapper;
import org.springframework.stereotype.Service;

/**
* @author 11852
* @description 针对表【chapter(课程)】的数据库操作Service实现
* @createDate 2023-09-03 16:39:25
*/
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter>
    implements ChapterService{

}




