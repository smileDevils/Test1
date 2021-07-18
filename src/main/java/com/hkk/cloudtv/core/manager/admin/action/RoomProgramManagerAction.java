package com.hkk.cloudtv.core.manager.admin.action;

import com.hkk.cloudtv.core.manager.admin.tools.PlayBackTools;
import com.hkk.cloudtv.core.service.ILiveRoomService;
import com.hkk.cloudtv.core.service.IProgramPlaybackService;
import com.hkk.cloudtv.core.service.IRoomProgramService;
import com.hkk.cloudtv.core.service.ISysConfigService;
import com.hkk.cloudtv.core.utils.CommUtils;
import com.hkk.cloudtv.core.utils.FileUtil;
import com.hkk.cloudtv.core.video.ChangeVideo;
import com.hkk.cloudtv.core.video.VideoUtil;
import com.hkk.cloudtv.entity.LiveRoom;
import com.hkk.cloudtv.entity.ProgramPlayback;
import com.hkk.cloudtv.entity.RoomProgram;
import com.hkk.cloudtv.entity.SysConfig;
import com.hkk.cloudtv.vo.Result;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: RoomProgramManagerAction.java
 * </p>
 *
 * <p>
 * Description: 直播节目管理控制器；负责创建直播节目，多个节目可对应一个直播间，也可一个直播对应一个直播间；两种模式；
 * </p>
 */
@RestController
@RequestMapping("/admin/program")
public class RoomProgramManagerAction {

    @Autowired
    private IRoomProgramService roomProgramService;
    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private ILiveRoomService liveRoomService;
    @Autowired
    private IProgramPlaybackService programPlaybackService;
    @Autowired
    private PlayBackTools playBackTools;

    @RequestMapping("/list")
    @RequiresPermissions("ADMIN_PROGRAM_LIST")
    public Object program(HttpServletRequest request, HttpServletResponse response, Integer currentPage, Integer pageSize) {
        Map map = new HashMap();
        if (currentPage == null || currentPage.equals("")) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize.equals("")) {
            pageSize = 15;
        }
        int totalRow = this.roomProgramService.getAccountByTotal();// 获取总数
        if(totalRow > 0){
            int tatolPages = totalRow / currentPage;
            int left = totalRow % pageSize;
            if (left > 0) {
                tatolPages += 1;
            }
            int startRow = (currentPage - 1) * pageSize;// 起始行
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("startRow", startRow);
            params.put("pageSize", pageSize);
            List<RoomProgram> roomProgramList = this.roomProgramService.getRoomProgram(params);
            map.put("pageSize", pageSize);
            map.put("totalRow",totalRow);
            map.put("currentPage", currentPage);
            map.put("list", roomProgramList);
            // map.put("totalPages", totalPages);
            map.put("pageSize", roomProgramList.size());
        }

        return new Result(200, "Successfully", map);
    }

    /**
     * 直播节目添加
     * @param request
     * @param response
     * @param roomprogram
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions("ADMIN_PROGRAM_SAVE")
    public Object save(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) RoomProgram roomprogram) {
        return this.roomProgramService.save(roomprogram);
    }

    @RequestMapping("/update")
    @RequiresPermissions("ADMIN_PROGRAM_UPDATE")
    public Object update(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) RoomProgram instance){
        return this.roomProgramService.update(instance);
    }

    @DeleteMapping("/delete")
    @RequiresPermissions("ADMIN_PROGRAM_DELETE")
    public Object delete(Integer id){
        if(id != null && !id.equals("")){
            try {
                this.roomProgramService.delete(Long.valueOf(id));
                return new Result(200, "Successfully");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(500, e.getMessage());
            }
        }else{
            return new Result(400, "Parameter is null");
        }
    }

    /**
     * 创建直播节目-默认创建直播间
     * @param response
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/liveroom", method = RequestMethod.POST)
    @RequiresPermissions("ADMIN_PROGRAM_LIVEROOM")
    public Object ProgramLiveRoom(HttpServletResponse response, HttpServletRequest request, @RequestBody(required = false) RoomProgram roomProgram){

        return this.roomProgramService.programLiveRoom(roomProgram);
    }

    /**
     * 开启关闭直播
     * @param
     * @return
     */
    @RequestMapping("/change")
    public Object changeRoomProgram(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        RoomProgram obj = this.roomProgramService.program(Long.valueOf(id));
        if(obj != null){ // off
            LiveRoom liveRoom = this.liveRoomService.getObjById(obj.getRoomId());
            SysConfig sysconfig = this.sysConfigService.findSysConfigList();
            String path = sysconfig.getPath() + File.separator + liveRoom.getBindCode();
            if (obj.getStatus() == 1) {
                obj.setStatus(0);
                 boolean flag = this.playBackTools.create(liveRoom.getId(), obj.getId(), liveRoom.getBindCode());
                if(flag){
                    obj.setPlayback(1);
                }
                // 修改目录权限
                FileUtil.storeFile(path);
                this.roomProgramService.update(obj);
            }else{
                Map params = new HashMap();
                params.put("status", 1);
                List<RoomProgram> roomPrograms = this.roomProgramService.findObjByCondition(params);
                if(roomPrograms != null && roomPrograms.size()>0){
                    return new Result(202,"Please shut down other live streams first");
                }
                obj.setStatus(1);// 开启直播
                this.roomProgramService.update(obj);
                FileUtil.storeFileOpen(path);
            }
            return new Result(200, "Successfully");
        }else{
            return new Result(400, "Param error");
        }
    }

}
