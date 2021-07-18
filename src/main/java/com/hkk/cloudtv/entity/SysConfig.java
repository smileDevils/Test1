package com.hkk.cloudtv.entity;

import com.hkk.cloudtv.core.domain.IdEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 *     Title：系统配置
 * </p>
 *
 * <p>
 *     Description: 系统配置管理类；系统基础配置。
 * </p>
 *
 * <author>
 *     HKK
 * </author>
 */

/*@EqualsAndHashCode //实现equals()和hashCode()
@ToString  //实现toString()*/
/*@Cleanup  //关闭流
@Synchronized //对象上同步
@SneakyThrows //抛出异常*/
@NoArgsConstructor  //注解在类上；为类提供一个无参的构造方法
@AllArgsConstructor  //注解在类上；为类提供一个全参的构造方法
@Data  //注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
@Setter  //可用在类或属性上；为属性提供 setting 方法
@Getter  //可用在类或属性上；为属性提供 getting 方法
public class SysConfig extends IdEntity {

    private String title;// 网站标题

    private String ip;

    private String rtmp;//  rtmp://lk.soarmall.com:1935/hls

    private String path;// 视频上传路径 /www/wwwroot/lk.soarmall.com/hls

    private String temp;// 转mp4文件名称

}
