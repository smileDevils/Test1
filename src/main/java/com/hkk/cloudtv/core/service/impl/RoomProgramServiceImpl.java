package com.hkk.cloudtv.core.service.impl;

import com.hkk.cloudtv.core.mapper.RoomProgramMapper;
import com.hkk.cloudtv.core.service.ILiveRoomService;
import com.hkk.cloudtv.core.service.IRoomProgramService;
import com.hkk.cloudtv.core.service.ISysConfigService;
import com.hkk.cloudtv.core.utils.CommUtils;
import com.hkk.cloudtv.core.utils.FileUtil;
import com.hkk.cloudtv.entity.LiveRoom;
import com.hkk.cloudtv.entity.RoomProgram;
import com.hkk.cloudtv.entity.SysConfig;
import com.hkk.cloudtv.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoomProgramServiceImpl implements IRoomProgramService {

    @Autowired
    private RoomProgramMapper roomProgramMapper;
    @Autowired
    private ILiveRoomService liveRoomService;
    @Autowired
    private ISysConfigService sysConfigService;

    @Override
    public RoomProgram program(Long id) {
        return this.roomProgramMapper.findObjByid(id);
    }

    @Override
    public List<RoomProgram> getRoomProgram(Map<String, Object> params) {
        return this.roomProgramMapper.findObjByPage(params);
    }

    @Override
    public int getAccountByTotal() {
        return this.roomProgramMapper.findAccountByTotal();
    }

    @Override
    public Object save(RoomProgram instance) {
        if(instance != null){
            if(StringUtils.isEmpty(instance.getTitle())){
                //return new Result(400, "The title cannot be empty");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                String formatDate  = sdf.format(new Date());
                instance.setTitle(formatDate);
            }
            // 时间判断
            if(instance.getBeginTime() == null){
                instance.setBeginTime(new Date());
            }
 /*           if(instance.getBeginTime() != null){
                if(instance.getEndTime() != null){
                    if(instance.getBeginTime().after(instance.getEndTime())){
                        return new Result(400, "The start time cannot be greater than the end time");
                    }
                }else{
                    return new Result(400, "Please select a end time");
                }
            }else{
                return new Result(400, "Please select a start time");
            }*/
           /* int flag = this.roomProgramMapper.creatRoomProgram(instance);
            if(flag == 1){
                return new Result(200, "Successfully");
            }else{
                return new Result(500, "Insert error");
            }*/
            try {
        /*        Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String df = sdf.format(date);
                String bindCode = df + CommUtils.randomString(6);// 推流码
                instance.setBindCode(bindCode);*/
                this.roomProgramMapper.insert(instance);
                return new Result(200, "Successfully");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(500, "Insert error");
            }
        }else{
            return new Result(400, "Params error");
        }
    }

    @Override
    public Object update(RoomProgram instance) {
           if(instance.getId() != null){
             /*  if(instance.getBeginTime() != null){
                   if(instance.getEndTime() != null){
                       if(instance.getBeginTime().after(instance.getEndTime())){
                           return new Result(400, "The start time cannot be greater than the end time");
                       }
                   }else{
                       return new Result(400, "Please select a end time");
                   }
               }else{
                   return new Result(400, "Please select a start time");
               }*/
               try {
                   this.roomProgramMapper.update(instance);
                   return new Result(200, "Successfully");
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }else{
               return new Result(400, "Please select what you want to update");
           }
        return null;
    }

    /**
     * 删除直播，并清空当前目录ts文件，修改当前目录权限
     * @param id
     * @return
     */
    @Override
    public int delete(Long id) {
        // 修改节目权限以及清楚多余ts文件
        RoomProgram roomProgram = this.roomProgramMapper.findObjByid(id);
        if(roomProgram != null){
            if(roomProgram.getStatus() == 1){
                LiveRoom liveRoom = this.liveRoomService.getObjById(roomProgram.getRoomId());
                if(liveRoom != null){
                    String bindCode = liveRoom.getBindCode();
                    SysConfig SysConfig = this.sysConfigService.findSysConfigList();
                    String path =  SysConfig.getPath() + bindCode;
               /* FileUtil.delFileTs(path);
                 String m3u8 = path + File.separator + "index.m3u8";*/
                    FileUtil.delFile(path);
                }
            }
        }
        return this.roomProgramMapper.delete(id);
    }

    @Override
    public Object programLiveRoom(RoomProgram instance) {
        if(instance != null){
            List<LiveRoom> liveRoomList = this.liveRoomService.findAllLiveRoom();
            boolean flag = true;
            LiveRoom room = null;
            if(liveRoomList.size() < 1){
                room = new LiveRoom();
                room.setAddTime(new Date());
                room.setTitle("默认直播间");
                room.setManager("admin");
                room.setInfo("默认直播间");
                room.setIsEnable(1);
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String df = sdf.format(date);
                String bindCode = df + CommUtils.randomString(6);// 推流码
                room.setBindCode(bindCode);
                //rtmp://lk.soarmall.com:1935/hls
                SysConfig SysConfig = this.sysConfigService.findSysConfigList();
                String rtmp = CommUtils.getRtmp(SysConfig.getIp(), bindCode);
                String obsRtmp = CommUtils.getObsRtmp(SysConfig.getIp());
                String path =  SysConfig.getPath() + File.separator + bindCode;
                room.setRtmp(rtmp);
                room.setObsRtmp(obsRtmp);
                try {
                    FileUtil.storeFile(path);
                    FileUtil.possessor(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    this.liveRoomService.save(room);
                } catch (Exception e) {
                    e.printStackTrace();
                    flag = false;
                }
            }else{
                room = liveRoomList.get(0);
            }
            if(true){
                if(StringUtils.isEmpty(instance.getTitle())){
                    //return new Result(400, "The title cannot be empty");
                    instance.setTitle(CommUtils.formatTime("yyyyMMddHHmmss",new Date()));
                }
                if(StringUtils.isEmpty(instance.getLecturer())){
                    //return new Result(400, "The title cannot be empty");
                    instance.setLecturer("Admin");
                }

                /*if(instance.getBeginTime().after(new Date())){
                    instance.setBeginTime(new Date());
                }*/
                instance.setBeginTime(new Date());

                /*if(instance.getBeginTime() != null){
                    if(null != instance.getEndTime()){
                        if(instance.getBeginTime().after(instance.getEndTime())){
                            return new Result(400, "The start time cannot be greater than the end time");
                        }
                    }else{
                        return new Result(400, "Please select a end time");
                    }
                }else{
                    return new Result(400, "Please select a start time"); }*/
                try {
                    //Date date = new Date();
                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    //String df = sdf.format(date);
                    //String bindCode = df + CommUtils.randomString(6);// 推流码
                    //instance.setBindCode(bindCode);
                    instance.setRoomId(room.getId());
                    instance.setAddTime(new Date());
                    //rtmp://lk.soarmall.com:1935/hls
               /*     List<SysConfig> sysConfigList = this.sysConfigService.findSysConfigList();
                    String rtmp = "rtmp://" + sysConfigList.get(0).getIp() + ":1935" + "/hls/" + bindCode;
                    instance.setRtmp(rtmp);*/
                    this.roomProgramMapper.insert(instance);
                    return new Result(200, "Successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Result(500, "Insert error");
                }
            }else{
                return new Result(500, "Create error");
            }
        }else{
            return new Result(500, "Create error");
        }
    }

    @Override
    public List<RoomProgram> findObjByCondition(Map<String, Object> params) {
        return this.roomProgramMapper.condition(params);
    }

    @Override
    public List<RoomProgram> liveStatus(Map<String, Object> params) {
        return this.roomProgramMapper.livestatus(params);
    }

}
