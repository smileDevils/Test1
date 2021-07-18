package com.hkk.cloudtv.core.video;

import com.hkk.cloudtv.core.service.IProgramPlaybackService;
import com.hkk.cloudtv.core.service.ISysConfigService;
import com.hkk.cloudtv.core.utils.CommUtils;
import com.hkk.cloudtv.core.utils.FileUtil;
import com.hkk.cloudtv.entity.ProgramPlayback;
import com.hkk.cloudtv.entity.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * Title: VideoUtil.java
 * </p>
 *
 * <p>
 * Descriprion: 视频工具类；负责合并ts文件，转mp4;
 * </p>
 */
@Component
public class VideoUtil {

    /**
     * 转Mp4
     *
     * @param path 源文件地址
     *             playBack 生成回放文件地址
     */
    public static boolean ConvertMp4(String path) {
        String currentDate = CommUtils.formatTime("yyyyMMddHHmmss", new Date());
        String playBack = path + File.separator + currentDate;
        boolean merge = false;
        try {
            // 合并ts 文件
            merge = FileUtil.merge(path, playBack);
            if (merge) {
                String read = playBack + File.separator + "merge.ts";
                String writer = playBack + File.separator + "temp.mp4";
                // linux
                boolean convert = ChangeVideo.convert(read, writer);  // 转mp4
                if (convert) {
                    // 转换完成，删除merge文件
                    FileUtil.delFile(read);
                }
                return convert;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
