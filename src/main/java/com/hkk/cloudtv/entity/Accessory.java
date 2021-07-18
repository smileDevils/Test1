package com.hkk.cloudtv.entity;

import com.hkk.cloudtv.core.domain.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 *     Title: Accessory.java
 * </p>
 *
 * <p>
 *     Description: 系统附件管理类，图片附件
 * </p>
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Accessory extends IdEntity {

    private String path;// 附件存放路径

    private String name;// 附件名称

    private String ext;// 附件扩展名，不包含点

    private String width;// 宽度

    private String height;// 高度

    private String size;// 附件大小

    private Long userId;// 附件对应的用户，精细化管理用户附件






}
