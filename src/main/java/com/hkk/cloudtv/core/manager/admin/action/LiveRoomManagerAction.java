package com.hkk.cloudtv.core.manager.admin.action;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.hkk.cloudtv.core.POJO.QueryFilter;
import com.hkk.cloudtv.core.service.ILiveRoomService;
import com.hkk.cloudtv.core.service.IProgramPlaybackService;
import com.hkk.cloudtv.core.service.IRoomProgramService;
import com.hkk.cloudtv.core.service.ISysConfigService;
import com.hkk.cloudtv.core.utils.CommUtils;
import com.hkk.cloudtv.core.utils.FileUtil;
import com.hkk.cloudtv.core.video.ChangeVideo;
import com.hkk.cloudtv.entity.LiveRoom;
import com.hkk.cloudtv.entity.ProgramPlayback;
import com.hkk.cloudtv.entity.RoomProgram;
import com.hkk.cloudtv.entity.SysConfig;
import com.hkk.cloudtv.vo.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Title：LiveRoomManagerAction.java
 * </p>
 *
 * <p>
 *  Description: 直播间管理控制器；
 * </p>
 *
 * <p>
 *  author: pers hkk
 * </p>
 */
@RestController
@RequestMapping(value = "/admin/room/")
public class LiveRoomManagerAction {

    @Autowired
    private ILiveRoomService liveRoomService;
    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private IRoomProgramService roomProgramService;
    @Autowired
    private IProgramPlaybackService programPlaybackService;

    /**
     * 直播间列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "list")
    @RequiresPermissions("ADMIN_ROOM_LIST")
    public Object rooms(HttpServletRequest request, HttpServletResponse response, Integer currentPage, Integer pageSize,String search) {
        Map map = new HashMap();
        // 分页查询
        if(currentPage == null || currentPage.equals("")){
            currentPage = 1;
        }
        if(pageSize == null || pageSize.equals("")){
            pageSize = 2;
        }
        int totalRow = this.liveRoomService.findAccountByTotal();// 查询总数
        if(totalRow > 0){
            int totalPages = totalRow / pageSize;// 总页数
            int left = totalRow % pageSize;
            if(left > 0){
                totalPages += 1;
            }
            // 判断用户是否手动修改页码
            if(currentPage > totalPages){
                currentPage = totalPages;
            }
            // 起始行
            int startRow = (currentPage - 1) * pageSize;
            // 封装分页参数
            Map<String ,Object> params = new HashMap();
            params.put("startRow", startRow);
            params.put("pageSize", pageSize);
            List<LiveRoom> liveRooms = this.liveRoomService.findByPager(params);
            map.put("totalPages", totalPages);
            map.put("totalRow",totalRow);
            map.put("pageSize", liveRooms.size());
            map.put("list", liveRooms);
        }
        //List<LiveRoom> liveRooms = this.liveRoomService.findAllLiveRoom();
        return new Result(200, "Successfully", map);
    }

    /**
     * 创建直播间
     * @param request
     * @param response
     * @param room
     * @return
     */
    @RequestMapping(value = "save")
    @RequiresPermissions("ADMIN_ROOM_SAVE")
    public Object creatRoot(HttpServletRequest request, HttpServletResponse response, LiveRoom room) {
        if (room != null) {
            room.setAddTime(new Date());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String df = sdf.format(date);
            String bindCode = df + CommUtils.randomString(6);// 推流码
            room.setBindCode(bindCode);
            int flag = this.liveRoomService.save(room);
            if(flag == 1){
                return new Result(200, "Successfully");
            }
        }
        return new Result(400,"Parameter Error");
    }

    /**
     * 删除直播间
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "delete")
    @RequiresPermissions("ADMIN_ROOM_DELETE")
    public Object delete(HttpServletRequest request, HttpServletResponse response, Integer id ){
        if(id != null || !id.equals("")){
            int flag = this.liveRoomService.delete(Long.valueOf(id));
            if(flag == 1){
                return new Result(200, "Succesfully");
            }else{
                return new Result(500, "Delete Error");
            }
        }else{
            return new Result(400, "Parameter error");
        }
    }

    /**
     * 更新直播间
     * @param room
     * @return
     */
    @RequestMapping("update")
    @RequiresPermissions("ADMIN_ROOM_UPDATE")
    public Object update(LiveRoom room){
        try {
            this.liveRoomService.update(room);
            return new Result(200, "Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(500, "Update error");
        }
    }

    /**
     * 直播间 on/off
     * @return
     */
    @PutMapping("/change")
    @RequiresPermissions("ADMIN_ROOM_CHANGE")
    public Object changeLiveRoom(HttpServletRequest request, HttpServletResponse response, Integer programId) {
        String id = request.getParameter("property");
        LiveRoom liveRoom = this.liveRoomService.getObjById(Long.valueOf(id));
        if (liveRoom != null) {
            try {
                if (liveRoom.getIsEnable() == 0) {
                    liveRoom.setIsEnable(1);
                    // 合并ts文件、转mp4
                    SysConfig sysconfig = this.sysConfigService.findSysConfigList();
                    String path = sysconfig.getPath() + File.separator +liveRoom.getBindCode();
                    String currentDate = CommUtils.formatTime("yyyyMMddhhmmss", new Date());
                    String playBack = path + File.separator + currentDate;
                    String playBackPath = liveRoom.getRtmp() + File.separator + currentDate;
                    boolean flag = FileUtil.merge(path, playBack);
                    if(flag){
                        // 转mp4
                        String read = playBack + File.separator+ "merge.ts";
                        String writer = playBack + File.separator+ "temp.mp4";

                        // linux
                       boolean convert = ChangeVideo.convert(read, writer);
                        // windows
                       /*
                        boolean convert = ChangeVideo.convert(read,
                                writer);*/
                        //创建回放信息
                        ProgramPlayback programPlayback = new ProgramPlayback();
                        programPlayback.setAddTime(new Date());
                        programPlayback.setRoomId(liveRoom.getId());
                        programPlayback.setPath(playBack);
                        programPlayback.setRtmp(playBackPath);
                        programPlayback.setCreate(convert == true ? 0 : 1);
                        RoomProgram roomProgram = this.roomProgramService.program(Long.valueOf(programId));
                        if (roomProgram != null) {
                            programPlayback.setProgramId(roomProgram.getRoomId());
                        }
                        this.programPlaybackService.save(programPlayback);
                    }
                } else {
                    liveRoom.setIsEnable(0);
                }
                this.liveRoomService.update(liveRoom);
                return new Result(200, "Successfully");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(500, "update Error");
            }
        }else{
            return new Result(400, "Params error");
        }
    }

    /**
     * pageHelper 分页
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("/allLiveRoom")
    public Object queryAllLiveRoom(Integer currentPage, Integer pageSize){
        Map map = new HashMap();
        if(currentPage == null || pageSize.equals("")){
            currentPage = 1;
        }
        if(pageSize == null || pageSize.equals("")){
            pageSize = 15;
        }
        List<LiveRoom> liveRoomList = this.liveRoomService.queryLiveRooms(currentPage, pageSize);
        if(liveRoomList.size() > 0){
            map.put("currentPage", currentPage);
            map.put("pageSize", liveRoomList.size());
            map.put("liveRoomList", liveRoomList);
        }
        return new Result(200, "Successfully", map);
    }

    /**
     * {"op":{"order_id":"like","user_name":"like","goods_name":"like","addTime":"range"},
     * "filter":{"addTime":["2021-05-16 00:00:00","2021-05-20 00:00:00"],
     * "order_id":"3","user_name":"3","order_cat":5,"goods_name":"3"},
     * "limit":10,"offset":1}
     */
    /**
     * pageHelper 分页
     * @param query
     * @return
     */
    @RequestMapping("/liverooms")
    public Object liveroom(String query){
        Map map = new HashMap();
        int currentPage = 1;
        int pageSize = 15;
        if(query != null && !query.equals("")){
            QueryFilter queryFilter = new QueryFilter();
            JSONObject jo =  JSONObject.parseObject(query);
            queryFilter.setWildcard((JSONObject) jo.get("filter"));
            queryFilter.setFiltrate((JSONObject)jo.get("op"));
            queryFilter.setCurrentPage(Integer.parseInt(jo.get("limit").toString()));
            queryFilter.setPageSize(Integer.parseInt(jo.get("offset").toString()));
        }

        List<LiveRoom> liveRoomList = this.liveRoomService.queryLiveRooms(currentPage, pageSize);
        map.put("currentPage", currentPage);
        map.put("pageSize", liveRoomList.size());
        map.put("liveRoomList", liveRoomList);
        return new Result(200, "Successfully", map);
    }


}
