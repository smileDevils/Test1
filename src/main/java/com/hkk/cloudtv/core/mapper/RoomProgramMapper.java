package com.hkk.cloudtv.core.mapper;

import com.hkk.cloudtv.entity.RoomProgram;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoomProgramMapper {


    /**
     * 根据id查询RoomProgram对象
     * @param id
     * @return
     */
    RoomProgram findObjByid(Long id);

    /**
     * Limit分页查询
     * @param params
     * @return
     */
    List<RoomProgram> findObjByPage(Map<String, Object> params);

    RoomProgram findProgram(Object o);
    /**
     * 查询总数
     * @return
     */
    int findAccountByTotal();

    /**
     * 创建一个RoomProgram对象
     * @param instance
     * @return
     */
    int insert(RoomProgram instance);

    /**
     * 更新一个RoomProgram对象
     * @param instance
     * @return
     */
    int update(RoomProgram instance);

    /**
     * 删除一个RoomProgram对象
     * @param id
     * @return
     */
    int delete(Long id);


    /**
     * 根据条件查询RoomProgram对象
     * @param params
     * @return
     */
    List<RoomProgram> condition(Map<String, Object> params);

    /**
     * 提供前端定时查询直播状态
     * 后期改为websorcket
     * @param params
     * @return
     */
    List<RoomProgram> livestatus(Map<String, Object> params);
}
