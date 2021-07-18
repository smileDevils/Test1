package com.hkk.cloudtv.core.service.impl;

import com.hkk.cloudtv.core.mapper.ProgramPlaybackMapper;
import com.hkk.cloudtv.core.service.IProgramPlaybackService;
import com.hkk.cloudtv.entity.ProgramPlayback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProgramPlaybackServiceImpl implements IProgramPlaybackService {

    @Autowired
    private ProgramPlaybackMapper propramPlayBackMapper;

    @Override
    public int save(ProgramPlayback instance) {
        return this.propramPlayBackMapper.insert(instance);
    }
}
