package com.maxd.service.alert;

import com.maxd.model.*;
import com.maxd.redis.RedisHandler;
import com.maxd.respository.alert.AlertJpaRepository;
import com.maxd.respository.alert.AlertRepository;
import com.maxd.upload.LoginServer;
import com.maxd.utils.Config;
import com.maxd.utils.FastJsonUtils;
import com.maxd.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 轮播图的service实现类
 */
@Service
public class AlertServiceImpl implements IAlertService {
    @Autowired
    RedisHandler redisHandler;
    @Autowired
    private AlertJpaRepository alertJpaRepository;
    @Autowired
    private AlertRepository alertRepository;
    private List<Alert> alerts = new ArrayList<>();

    @Override
    public List<Alert> findAll() {
        List<Alert> redis = getRedis("all");
        if (redis.size() == 0) {
            return putRedis();
        } else {
            return redis;
        }
    }

    @Override
    public void saveBody(Alert alert) {
        try {
            alertJpaRepository.save(alert);
        } catch (Exception e) {
            e.printStackTrace();
        }
        putRedis();
    }

    @Override
    public Alert findOne(long id) {
        return alertJpaRepository.findOne(id);
    }

    @Override
    public void delete(long id) {
        try {
            alertJpaRepository.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        putRedis();
    }

    @Override
    public List<Alert> findByOu(String ou) {
        List<Alert> redis = getRedis(ou);
        if (redis.size() == 0) {
            return alertRepository.findById(ou);
        } else {
            return redis;
        }
    }


    @Override
    public Page<Alert> find(String app, Pageable pageable) {
        return alertRepository.find(app, pageable);
    }

    @Override
    @Transactional
    public void updateBody(Alert alert) {
        Alert alert1 = alertJpaRepository.findOne(alert.getId());
        BeanUtils.copyProperties(alert, alert1);
    }

    @Override
    public void deleteAllAlert() {
        try {
            List<Alert> all = alertJpaRepository.findAll();
            for (Alert anAll : all) {
                alertJpaRepository.delete(anAll.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        putRedis();
    }

    @Override
    public String addAllAlert(List<OutAlert> alerts) {
        for (OutAlert u : alerts) {
            if (alertRepository.findById(u.getSchoolId()).size() > 0) {
                return "很抱歉，单个学校最多只能添加一个气泡，是否要清理之前所有学校数据？";
            } else {
                Date d = new Date();//获取时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");//转换格式
                Alert alert = new Alert();
                alert.setImageUrl(u.getImageUrl());
                alert.setSchoolId(u.getSchoolId());
                alert.setWebUrl(u.getWebUrl());
                alert.setSchoolName(Utils.getName(u.getSchoolId()));
                alert.setTime(sdf.format(d));
                alertJpaRepository.save(alert);
            }
        }
        putRedis();
        return "success";
    }

    @Override
    public List<OutAlert> findOutList() {
        List<Alert> list = alertJpaRepository.findAll();
        List<OutAlert> customs1 = new ArrayList<>();
        for (Alert alert : list) {
            OutAlert alert1 = new OutAlert();
            alert1.setImageUrl(alert.getImageUrl());
            alert1.setSchoolId(alert.getSchoolId());
            alert1.setWebUrl(alert.getWebUrl());
            customs1.add(alert1);
        }
        return customs1;
    }

    @Override
    public List<Alert> getBodyByOu(String ou) {
        if (ou.equals("all")) {
            return findAll();
        } else {
            return findByOu(ou);
        }
    }

    @Override
    public String addAlertInfo(Alert alert) {
        if (alertRepository.findById(alert.getSchoolId()).size() > 0) {
            return "很抱歉，单个学校最多只能添加一个气泡信息";
        } else {
            saveBody(alert);
            return "success";
        }
    }

    @Override
    public String writeBodyInfoToServer() {
        try {
            List<OutAlert> outList = findOutList();
            if (outList.size() != 0) {
                String jsonString = FastJsonUtils.toJSONString(outList);
                File copyFile = new File(Config.writePath + "/" + Config.Alertkey1 + ".json");
                boolean b = LoginServer.writeTxtFile(jsonString, copyFile);
                if (b) {
                    LoginServer.login(LoginServer.getProperties(Config.Alertkey1 + ".json"), false);
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

    @Override
    public String getBodyInfoFromServer() {
        try {
            LoginServer.login(LoginServer.getProperties(Config.Alertkey1 + ".json"), false);
            File copyFile = new File(Config.writePath + "/" + Config.Alertkey1 + ".json");
            String s = LoginServer.readTxtFile(copyFile);
            if (s.contains("[") && s.contains("]")) {
                List<OutAlert> outAlerts = FastJsonUtils.toList(s, OutAlert.class);
                return addAllAlert(outAlerts);
            } else {
                return "导入文件数据格式不正确，请尝试手动导入";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    /**
     * 添加redis缓存
     */
    private List<Alert> putRedis() {
        List<Alert> all = alertJpaRepository.findAll();
        String s = FastJsonUtils.toJSONString(all);
        redisHandler.getLocalRedisTemplate().cacheStringValue(Config.prefix, Config.Alertkey, s);
        return all;
    }

    /**
     * 添加redis缓存
     */
    private List<Alert> getRedis(String app) {
        String stringValue = redisHandler.getLocalRedisTemplate().getStringValue(Config.prefix, Config.Alertkey);
        if (stringValue != null) {
            alerts = FastJsonUtils.toList(stringValue, Alert.class);
            if (app.equals("all")) {
                return alerts;
            } else {
                List<Alert> alerts1 = new ArrayList<>();
                for (Alert c : alerts) {
                    if (c.getSchoolId().equals(app)) {
                        alerts1.add(c);
                    }
                }
                return alerts1;
            }
        }
        return alerts;

    }

}
