package com.hkk.cloudtv.core.manager.view.action;

import com.hkk.cloudtv.core.service.ILiveRoomService;
import com.hkk.cloudtv.core.service.IRoomProgramService;
import com.hkk.cloudtv.core.service.ISysConfigService;
import com.hkk.cloudtv.entity.LiveRoom;
import com.hkk.cloudtv.entity.RoomProgram;
import com.hkk.cloudtv.entity.SysConfig;
import com.hkk.cloudtv.vo.Result;
import net.bytebuddy.asm.Advice;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/program")
public class RoomProgramViewAction {

    @Autowired
    private IRoomProgramService roomProgramService;
    @Autowired
    private ILiveRoomService liveRoomService;
    @Autowired
    private ISysConfigService sysConfigService;

    @RequestMapping("/list")
    public Object webProgram(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<String, Object>();
        Map params = new HashMap();
        params.put("status", 1);
        List<RoomProgram> roomPrograms = this.roomProgramService.findObjByCondition(params);
        if(roomPrograms != null && roomPrograms.size() > 0){
            LiveRoom liveRoom = this.liveRoomService.getObjById(roomPrograms.get(0).getRoomId());
            String bindCode = "";
            String rtmp = "";

            if(liveRoom != null){
                bindCode = liveRoom.getBindCode();
                rtmp = liveRoom.getRtmp();
                map.put("live", liveRoom.getLive());
            }
            map.put("roomProgram", roomPrograms.get(0));
            map.put("rtmp", rtmp + "/index.m3u8");

            map.put("status", roomPrograms.get(0).getStatus());
        }else{
            // 默认查询唯一直播间
            List<LiveRoom> liveRoomList = this.liveRoomService.findAllLiveRoom();
            if(liveRoomList.size() > 0){
                LiveRoom liveRoom = liveRoomList.get(0);
                String bindCode = "";
                String rtmp = "";
                if(liveRoom != null){
                    bindCode = liveRoom.getBindCode();
                    rtmp = liveRoom.getRtmp();
                    map.put("live", liveRoom.getLive());
                }
                map.put("rtmp", rtmp + "/index.m3u8");
            }
        }
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        map.put("title", sysConfig.getTitle());
        return new Result(200, "Successfully", map);
    }

    @RequestMapping("/liveStatus")
    public Object liveStatus(){
        Map params = new HashMap();
        params.put("status", 1);
        List<RoomProgram> roomProgramList = this.roomProgramService.liveStatus(params);
        Map map = new HashMap();
        if(roomProgramList.size() > 0){
            RoomProgram obj = roomProgramList.get(0);
            map.put("id", obj.getId());
            map.put("status", obj.getStatus());
            //map.put("live", obj.getLive());
        }
        return new Result(200, "Successfully", map);
    }
}
