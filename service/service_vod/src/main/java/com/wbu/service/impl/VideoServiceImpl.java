package com.wbu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wbu.model.vod.Video;
import com.wbu.service.VideoService;
import com.wbu.mapper.VideoMapper;
import org.springframework.stereotype.Service;

/**
* @author 11852
* @description 针对表【video(课程视频)】的数据库操作Service实现
* @createDate 2023-09-03 16:40:08
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{

}




