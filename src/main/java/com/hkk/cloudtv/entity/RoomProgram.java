package com.hkk.cloudtv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hkk.cloudtv.core.domain.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *     Title: RoomProgram.java
 * </p>
 *
 * <p>
 *     Description: 直播管理类；两种模式：一个直播对应一个直播间、多个直播对应一个直播间；
 *     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8) 时间戳 : 后台到前台时间格式保持一致的问题
 *     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
 * </p>
 *
 *  <author>
 *      HKK
 *  </author>
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoomProgram extends IdEntity {

    private String title;// 直播节目名称

    private String usernName;// 直播节目创建人

    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 时间戳 timezone = "GMT+8"*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;// 直播节目开始时间

    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;// 直播节目结束时间

    private int status;// 直播节目状态 0: 关闭 1：开启

    private int live;// 视频流默认0：停止 1：开启 deprecated

    private int type;// 直播节目类型

    private String info;// 直播节目简介

    private String poster;// 节目海报

    private int remark;//

    private int commmentNumber;// 评论数

    private int seeNumber;// 观看人数

    private int goodsNumber;// 点赞人数

    private Long RoomId;// 所属直播间

    private String lecturer;// 讲师

    private int loginNumber;// 签到人数

    private String logoImg;// 直播图片

 /*   private String pushAddress;// 推流地址

    private String bindCode;// 推流码

    private String rtmp;//*/

    private int playback;// 最近一次节目是否存在回放

}
