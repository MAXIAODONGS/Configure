package com.xinlu.controller.update;

import com.xinlu.model.OutCustom;
import com.xinlu.model.OutUpdateInfo;
import com.xinlu.model.UpdateInfo;
import com.xinlu.service.update.IUpdateService;
import com.xinlu.upload.LoginServer;
import com.xinlu.utils.Config;
import com.xinlu.utils.FastJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/update")
public class UpdateController {
    @Autowired
    private IUpdateService iUpdateService;
    private List<OutUpdateInfo> updateInfos;

    @RequestMapping(value = "/{id}")
    @ResponseBody
    public UpdateInfo getUpdate(@PathVariable Integer id) {
        return iUpdateService.findOne(Long.valueOf(id));
    }

    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public String addUpdate(@RequestBody UpdateInfo updateInfo) {
        if (iUpdateService.findByApp(updateInfo.getApp()).size() > 1) {
            return "很抱歉,单个客户端最多只能添加两个设备升级信息";
        } else {
            iUpdateService.saveBody(updateInfo);
            return "success";
        }
    }

    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public void uUpdate(@RequestBody UpdateInfo updateInfo) {
        iUpdateService.saveBody(updateInfo);
    }

    @RequestMapping(value = "/delete/all")
    public void deleteAllUpdateInfo() {
        List<UpdateInfo> all = iUpdateService.findAll();
        for (UpdateInfo b : all) {
            iUpdateService.delete(b.getId());
        }
    }

    @RequestMapping(value = "/add/all", method = RequestMethod.POST)
    @ResponseBody
    public String addAllUpdate(@RequestBody List<OutUpdateInfo> updateInfos) {
        for (OutUpdateInfo u : updateInfos) {
            if (iUpdateService.findByApp(u.getApp()).size() > 1) {
                return "很抱歉，单个客户端最多只能添加两个设备升级信息，是否要清理之前所有学校数据？";
            } else {
                Date d = new Date();//获取时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");//转换格式
                UpdateInfo updateInfo = new UpdateInfo();
                switch (u.getApp()) {
                    case "WeNet校园":
                        updateInfo.setApp("WeNet");
                        break;
                    case "WeNet欧亚":
                        updateInfo.setApp("WeNetOY");
                        break;
                    default:
                        updateInfo.setApp(u.getApp());
                        break;
                }
                updateInfo.setPhoneWare(u.getPhoneWare());
                updateInfo.setDescription(u.getDescription());
                updateInfo.setDownload_url(u.getDownload_url());
                updateInfo.setNewVersion(u.getNewVersion());
                updateInfo.setPlan_alert(String.valueOf(u.getPlan_alert()));
                updateInfo.setType(String.valueOf(u.getType()));
                updateInfo.setSchoolName(u.getApp());
                updateInfo.setTime(sdf.format(d));
                iUpdateService.saveBody(updateInfo);
            }
        }
        return "success";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public UpdateInfo upUpdate(@PathVariable UpdateInfo updateInfo) {
        iUpdateService.updateBody(updateInfo);
        return updateInfo;
    }

    @RequestMapping(value = "/delete/{id}")
    public void deleteUpdate(@PathVariable int id) {
        iUpdateService.delete(id);
    }

    @RequestMapping(value = "/")
    @ResponseBody
    public List<UpdateInfo> getUpdateList() {
        return iUpdateService.findAll();
    }

    @RequestMapping(value = "/list/{app}")
    @ResponseBody
    public List<UpdateInfo> getBodyByApp(@PathVariable String app) {
        if (app.equals("all")) {
            return iUpdateService.findAll();
        } else {
            return iUpdateService.findByApp(app);
        }
    }

    @RequestMapping(value = "/out", method = RequestMethod.GET)
    @ResponseBody
    public List<OutUpdateInfo> findOutList() {
        List<UpdateInfo> list = iUpdateService.findAll();
        updateInfos = new ArrayList<>();
        for (UpdateInfo updateInfo : list) {
            OutUpdateInfo alert1 = new OutUpdateInfo();
            if (updateInfo.getSchoolName().contains("VPN")) {
                alert1.setApp("VPN");
            } else {
                alert1.setApp(updateInfo.getSchoolName());
            }
            alert1.setDescription(updateInfo.getDescription());
            alert1.setDownload_url(updateInfo.getDownload_url());
            alert1.setNewVersion(updateInfo.getNewVersion());
            alert1.setPlan_alert(updateInfo.getPlan_alert());
            alert1.setPhoneWare(updateInfo.getPhoneWare());
            alert1.setType(Integer.valueOf(updateInfo.getType()));
            updateInfos.add(alert1);
        }
        return updateInfos;

    }

    @RequestMapping(value = "/find/page/{page}/{size}/{app}")
    @ResponseBody
    public Page<UpdateInfo> getUpdateByAppPage(@PathVariable String app, @PathVariable Integer page,
                                               @PathVariable int size) {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "id"));
        return iUpdateService.find(app, pageable);
    }

    @RequestMapping(value = "/server/write", method = RequestMethod.GET)
    @ResponseBody
    public String writeBodyInfoToServer() {
        try {
            List<OutUpdateInfo> outList = findOutList();
            if (outList.size() != 0) {
                String jsonString = FastJsonUtils.toJSONString(outList);
                File copyFile = new File(Config.writePath + "/" + Config.Updatekey + ".json");
                boolean b = LoginServer.writeTxtFile(jsonString, copyFile);
                if (b) {
                    LoginServer.login(LoginServer.getProperties(Config.Updatekey + ".json"), false);
                } else {
                    return "文件本地存储失败";
                }

            } else {
                return "暂无数据";
            }


        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "远程部署成功";
    }

    @RequestMapping(value = "/server/read", method = RequestMethod.GET)
    @ResponseBody
    public String getBodyInfoFromServer() {
        try {
            LoginServer.login(LoginServer.getProperties(Config.Updatekey + ".json"), false);
            File copyFile = new File(Config.writePath + "/" + Config.Updatekey + ".json");
            String s = LoginServer.readTxtFile(copyFile);
            System.out.println(s);
            if (s.contains("[") && s.contains("]")) {
                List<OutUpdateInfo> imageBanners = FastJsonUtils.toList(s, OutUpdateInfo.class);
                return addAllUpdate(imageBanners);
            } else {
                return "导入文件数据格式不正确，请尝试手动导入";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
