package com.hkk.cloudtv.core.service;

import com.hkk.cloudtv.entity.LiveRoom;
import com.hkk.cloudtv.entity.SysConfig;

import java.util.List;

public interface ISysConfigService {

    SysConfig findSysConfigList();

    int modify(SysConfig instance);
}
