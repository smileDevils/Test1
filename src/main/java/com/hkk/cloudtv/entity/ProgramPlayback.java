package com.hkk.cloudtv.entity;

import com.hkk.cloudtv.core.domain.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 *     Title: ProgramPlayback.java
 * </p>
 *
 * <p>
 *     Description: 视频回放管理类
 * </p>
 *
 * <author>
 *     HKK
 * </author>
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProgramPlayback extends IdEntity {

    private String title;// 回放信息标题

    private Long RoomId;// 直播间Id

    private Long programId;// 直播节目Id

    private String path;// 回放路径

    private Integer create;// 创建回放 0: 创建成功 1：创建失败

    private String rtmp;//

}
