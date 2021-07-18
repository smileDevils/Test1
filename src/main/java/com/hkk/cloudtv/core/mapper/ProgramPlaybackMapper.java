package com.hkk.cloudtv.core.mapper;

import com.hkk.cloudtv.entity.ProgramPlayback;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProgramPlaybackMapper {

    /**
     * 保存一个ProgramPlayback对象
     * @param instance
     * @return
     */
    int insert(ProgramPlayback instance);


}
