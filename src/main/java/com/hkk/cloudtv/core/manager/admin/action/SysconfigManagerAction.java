package com.hkk.cloudtv.core.manager.admin.action;

import com.hkk.cloudtv.core.service.ISysConfigService;
import com.hkk.cloudtv.entity.SysConfig;
import com.hkk.cloudtv.vo.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//rtmp://lk.soarmall.com:1935/hls
@RestController
@RequestMapping("admin/config")
public class SysconfigManagerAction {

    @Autowired
    private ISysConfigService sysConfigService;

    @RequestMapping(value = "/list")
    @RequiresPermissions("ADMIN_SYSCONFIG_LIST")
    public Object sysConfig(HttpServletRequest request, HttpServletResponse response){
        SysConfig sysconfigList = this.sysConfigService.findSysConfigList();
        return sysconfigList;
    }

    @RequestMapping("/update")
    @RequiresPermissions("ADMIN_SYSCONFIG_UPDATE")
    public Object baseConfig(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) SysConfig sysconfig){
      if(sysconfig != null){
          try {
              this.sysConfigService.modify(sysconfig);
              return new Result(200, "Successfully");
          } catch (Exception e) {
              e.printStackTrace();
              return new Result(500, e.getMessage());
          }
      }
      return null;
    }
}
