package com.maxd.controller.vpn;

import com.maxd.upload.LoginServer;
import com.maxd.utils.Config;
import com.maxd.utils.FastJsonUtils;
import com.maxd.utils.Utils;
import com.maxd.model.OutVpnInfo;
import com.maxd.model.VpnInfo;
import com.maxd.service.vpn.IVpnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/vpn")
public class VpnController {
    @Autowired
    private IVpnService iVpnService;
    private List<OutVpnInfo> customs;

    @RequestMapping(value = "/{id}")
    @ResponseBody
    public VpnInfo getVpnInfo(@PathVariable Integer id) {
        return iVpnService.findOne(Long.valueOf(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addVpnInfo(@RequestBody VpnInfo vpnInfo) {
        if (iVpnService.findByOu(vpnInfo.getSchoolId()).size() > 0) {
            return "很抱歉，单个学校最多只能添加一个VPN服务信息";
        } else {
            iVpnService.saveBody(vpnInfo);
            return "success";
        }
    }

    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public void uUpdate(@RequestBody VpnInfo vpnInfo) {
        iVpnService.saveBody(vpnInfo);
    }

    @RequestMapping(value = "/delete/{id}")
    public void deleteVpnInfo(@PathVariable int id) {
        iVpnService.delete(id);
    }

    @RequestMapping(value = "/delete/all")
    public void deleteAllVpnInfo() {
        List<VpnInfo> all = iVpnService.findAll();
        for (VpnInfo b : all) {
            iVpnService.delete(b.getId());
        }
    }

    @RequestMapping(value = "/")
    @ResponseBody
    public List<VpnInfo> getVpnInfoList() {
        return iVpnService.findAll();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public VpnInfo updateVpnIno(@RequestBody VpnInfo vpnInfo) {
        iVpnService.updateBody(vpnInfo);
        return vpnInfo;
    }

    @RequestMapping(value = "/out", method = RequestMethod.GET)
    @ResponseBody
    public List<OutVpnInfo> findOutList() {
        List<VpnInfo> list = iVpnService.findAll();
        customs = new ArrayList<>();
        for (VpnInfo custom : list) {
            OutVpnInfo alert1 = new OutVpnInfo();
            alert1.setPort(custom.getPort());
            alert1.setSchoolId(custom.getSchoolId());
            alert1.setServerAddress(custom.getServerAddress());
            alert1.setUpdateAddress(custom.getUpdateAddress());
            alert1.setVpnPsk(custom.getVpnPsk());
            customs.add(alert1);
        }
        return customs;
    }

    @RequestMapping(value = "/add/all", method = RequestMethod.POST)
    @ResponseBody
    public String addAllVpn(@RequestBody List<OutVpnInfo> outVpnInfos) {
        for (OutVpnInfo u : outVpnInfos) {
            if (iVpnService.findByOu(u.getSchoolId()).size() > 0) {
                return "很抱歉，单个学校最多只能添加一个VPN服务信息,是否要清理之前所有学校数据？";
            } else {
                Date d = new Date();//获取时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");//转换格式
                VpnInfo updateInfo = new VpnInfo();
                updateInfo.setSchoolId(u.getSchoolId());
                updateInfo.setPort(u.getPort());
                updateInfo.setSchoolName(Utils.getName(u.getSchoolId()));
                updateInfo.setServerAddress(u.getServerAddress());
                updateInfo.setUpdateAddress(u.getUpdateAddress());
                updateInfo.setVpnPsk(String.valueOf(u.getVpnPsk()));
                updateInfo.setTime(sdf.format(d));
                iVpnService.saveBody(updateInfo);
            }

        }
        return "success";

    }

    @RequestMapping(value = "/server/write", method = RequestMethod.GET)
    @ResponseBody
    public String writeBodyInfoToServer() {
        try {
            List<OutVpnInfo> outList = findOutList();
            if (outList.size() != 0) {
                String jsonString = FastJsonUtils.toJSONString(outList);
                File copyFile = new File(Config.writePath + "/" + Config.Vpnkey1 + ".json");
                boolean b = LoginServer.writeTxtFile(jsonString, copyFile);
                if (b) {
                    LoginServer.login(LoginServer.getProperties(Config.Vpnkey1 + ".json"), false);
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
            LoginServer.login(LoginServer.getProperties(Config.Vpnkey1 + ".json"), false);
            File copyFile = new File(Config.writePath + "/" + Config.Vpnkey1 + ".json");
            String s = LoginServer.readTxtFile(copyFile);
            if (s.contains("[") && s.contains("]")) {
                List<OutVpnInfo> imageBanners = FastJsonUtils.toList(s, OutVpnInfo.class);
                return addAllVpn(imageBanners);
            } else {
                return "导入文件数据格式不正确，请尝试手动导入";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/list/{ou}")
    @ResponseBody
    public List<VpnInfo> getBodyByOu(@PathVariable String ou) {
        if (ou.equals("all")) {
            return iVpnService.findAll();
        } else {
            return iVpnService.findByOu(ou);
        }
    }
}
