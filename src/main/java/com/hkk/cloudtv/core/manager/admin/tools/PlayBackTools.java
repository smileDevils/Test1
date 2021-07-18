package com.hkk.cloudtv.core.manager.admin.tools;

import com.hkk.cloudtv.core.service.IProgramPlaybackService;
import com.hkk.cloudtv.core.service.ISysConfigService;
import com.hkk.cloudtv.core.utils.CommUtils;
import com.hkk.cloudtv.core.video.VideoUtil;
import com.hkk.cloudtv.entity.ProgramPlayback;
import com.hkk.cloudtv.entity.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

@Component
public class PlayBackTools {


    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private IProgramPlaybackService programPlaybackService;

    /**
     * 创建回放
     * @param live
     * @param program
     * @param bindCode
     * @return
     */
    public boolean create(Long live, Long program, String bindCode){
        SysConfig sysconfig = this.sysConfigService.findSysConfigList();
        String path = sysconfig.getPath() + File.separator + bindCode;
        boolean flag =  VideoUtil.ConvertMp4(path);
        if(true){
            String currentDate = CommUtils.formatTime("yyyyMMddHHmmss", new Date());
            String playBack = path + File.separator + currentDate;
            String rtmp = CommUtils.getRtmp(sysconfig.getIp(), bindCode)+ File.separator + currentDate;;// 回放视频路径
            ProgramPlayback programPlayback = new ProgramPlayback();
            programPlayback.setAddTime(new Date());
            programPlayback.setPath(playBack);
            programPlayback.setRtmp(rtmp);
            programPlayback.setProgramId(program);
            programPlayback.setRoomId(live);
            try {
                this.programPlaybackService.save(programPlayback);  return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
