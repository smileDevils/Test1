package com.hkk.cloudtv.entity;

import com.hkk.cloudtv.core.domain.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 *     Title: LiveRoom.java
 * </p>
 *
 * <p>
 *     Description: 直播间管理类；
 * </p>
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LiveRoom extends IdEntity{
   /* private Long id;// 直播间ID

    private Date addTime;// 直播间创建时间

    private int deleteStatus;
*/
    private String title;// 直播间名称

    private String manager;// 直播间管理人

    private String managerPassword;// 直播间密码

    private String info;// 直播间简介

    private String telphone;// 直播间联系电话

    private Integer isEnable;// 是否禁用直播间 默认0：关闭 1：开启

    private int live;// 视频流默认0：停止 1：开启

    private String logoImg;// 直播间封面图片

    private Integer onLine;// 在线人数

    private Integer maxOnline;// 直播间最大在线人数

    private Integer istalk;// 直播间是否禁言  默认0：否 1：是

    private Integer isVip;// 是否游客观看 默认0：否 1：是

    private String pushAddress;// 推流地址

    private String bindCode;// 推流码

    private String rtmp;// rtmp 地址

    private String obsRtmp;// 推流地址

    private String flv;// flv 地址

    private String websocketFlv;// websocket 地址

    private Long ItemId;// 栏目管理：java、php、python、语文、数学

    private Long deptId;// 部门ID 直播间所属部门

    private Integer isPlayback;// 是否开启回放

 public String getTitle() {
  return title;
 }

 public void setTitle(String title) {
  this.title = title;
 }

 public String getManager() {
  return manager;
 }

 public void setManager(String manager) {
  this.manager = manager;
 }

 public String getManagerPassword() {
  return managerPassword;
 }

 public void setManagerPassword(String managerPassword) {
  this.managerPassword = managerPassword;
 }

 public String getInfo() {
  return info;
 }

 public void setInfo(String info) {
  this.info = info;
 }

 public String getTelphone() {
  return telphone;
 }

 public void setTelphone(String telphone) {
  this.telphone = telphone;
 }

 public Integer getIsEnable() {
  return isEnable;
 }

 public void setIsEnable(Integer isEnable) {
  this.isEnable = isEnable;
 }

 public String getLogoImg() {
  return logoImg;
 }

 public void setLogoImg(String logoImg) {
  this.logoImg = logoImg;
 }

 public Integer getOnLine() {
  return onLine;
 }

 public void setOnLine(Integer onLine) {
  this.onLine = onLine;
 }

 public Integer getMaxOnline() {
  return maxOnline;
 }

 public void setMaxOnline(Integer maxOnline) {
  this.maxOnline = maxOnline;
 }

 public Integer getIstalk() {
  return istalk;
 }

 public void setIstalk(Integer istalk) {
  this.istalk = istalk;
 }

 public Integer getIsVip() {
  return isVip;
 }

 public void setIsVip(Integer isVip) {
  this.isVip = isVip;
 }

 public String getPushAddress() {
  return pushAddress;
 }

 public void setPushAddress(String pushAddress) {
  this.pushAddress = pushAddress;
 }

 public String getBindCode() {
  return bindCode;
 }

 public void setBindCode(String bindCode) {
  this.bindCode = bindCode;
 }

 public String getRtmp() {
  return rtmp;
 }

 public void setRtmp(String rtmp) {
  this.rtmp = rtmp;
 }

 public String getFlv() {
  return flv;
 }

 public void setFlv(String flv) {
  this.flv = flv;
 }

 public String getWebsocketFlv() {
  return websocketFlv;
 }

 public void setWebsocketFlv(String websocketFlv) {
  this.websocketFlv = websocketFlv;
 }

 public Long getItemId() {
  return ItemId;
 }

 public void setItemId(Long itemId) {
  ItemId = itemId;
 }

 public Long getDeptId() {
  return deptId;
 }

 public void setDeptId(Long deptId) {
  this.deptId = deptId;
 }

 public Integer getIsPlayback() {
  return isPlayback;
 }

 public void setIsPlayback(Integer isPlayback) {
  this.isPlayback = isPlayback;
 }
}
