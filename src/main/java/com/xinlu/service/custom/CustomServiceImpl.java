package com.xinlu.service.custom;

import com.xinlu.model.Custom;
import com.xinlu.model.ImageBanner;
import com.xinlu.model.OutCustom;
import com.xinlu.redis.GlobalRedisConfig;
import com.xinlu.redis.RedisHandler;
import com.xinlu.respository.custom.CustomJpaRepository;
import com.xinlu.respository.custom.CustomRepository;
import com.xinlu.upload.LoginServer;
import com.xinlu.utils.Config;
import com.xinlu.utils.FastJsonUtils;
import com.xinlu.utils.Utils;
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
public class CustomServiceImpl implements ICustomService {
    @Autowired
    RedisHandler redisHandler;

    @Autowired
    private CustomJpaRepository customJpaRepository;
    @Autowired
    private CustomRepository customRepository;
    private List<Custom> customs = new ArrayList<>();

    @Override
    public List<Custom> findAll() {
        List<Custom> redis = getRedis("all");
        if (redis.size() == 0) {
            return putRedis();
        } else {
            return redis;
        }
    }

    @Override
    public void saveBody(Custom custom) {
        try {
            customJpaRepository.save(custom);
        } catch (Exception e) {
            e.printStackTrace();
        }
        putRedis();

    }


    @Override
    public Custom findOne(long id) {
        return customJpaRepository.findOne(id);
    }

    @Override
    public void delete(long id) {
        try {
            customJpaRepository.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        putRedis();
    }

    @Override
    public List<Custom> findByOu(String ou) {
        List<Custom> redis = getRedis(ou);
        if (redis.size() == 0) {
            return customRepository.findById(ou);
        } else {
            return redis;
        }
    }

    @Override
    public Page<Custom> find(String app, Pageable pageable) {
        return customRepository.find(app, pageable);
    }


    @Override
    @Transactional
    public void updateBody(Custom custom) {
        Custom custom1 = customJpaRepository.findOne(custom.getId());
        BeanUtils.copyProperties(custom, custom1);
    }

    @Override
    public void deleteAllCustom() {
        try {
            List<Custom> all = customJpaRepository.findAll();
            for (Custom anAll : all) {
                customJpaRepository.delete(anAll.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        putRedis();
    }

    @Override
    public String addAllCustom(List<OutCustom> customs) {
        for (OutCustom u : customs) {
            if (customRepository.findById(u.getSchoolId()).size() > 0) {
                return "很抱歉，单个学校最多只能添加一个公众号信息，是否要清理之前所有学校数据？";
            } else {
                Date d = new Date();//获取时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");//转换格式
                Custom alert = new Custom();
                alert.setImageUrl(u.getImageUrl());
                alert.setSchoolId(u.getSchoolId());
                alert.setName(u.getName());
                alert.setSchoolName(Utils.getName(u.getSchoolId()));
                alert.setTime(sdf.format(d));
                customJpaRepository.save(alert);
            }
        }
        putRedis();
        return "success";
    }

    @Override
    public List<OutCustom> findOutList() {
        List<Custom> list = customJpaRepository.findAll();
        List<OutCustom> customs1 = new ArrayList<>();
        for (Custom custom : list) {
            OutCustom alert1 = new OutCustom();
            alert1.setImageUrl(custom.getImageUrl());
            alert1.setSchoolId(custom.getSchoolId());
            alert1.setName(custom.getName());
            customs1.add(alert1);
        }
        return customs1;
    }

    @Override
    public List<Custom> getBodyByOu(String ou) {
        if (ou.equals("all")) {
            return findAll();
        } else {
            return findByOu(ou);
        }

    }

    @Override
    public String addCustomInfo(Custom custom) {
        if (customRepository.findById(custom.getSchoolId()).size() > 0) {
            return "很抱歉，单个学校最多只能添加一个公众号信息";
        } else {
            saveBody(custom);
            return "success";
        }
    }


    @Override
    public String writeBodyInfoToServer() {
        try {
            List<OutCustom> outList = findOutList();
            if (outList.size() != 0) {
                String jsonString = FastJsonUtils.toJSONString(outList);
                File copyFile = new File(Config.writePath + "/" + Config.Customkey1 + ".json");
                boolean b = LoginServer.writeTxtFile(jsonString, copyFile);
                if (b) {
                    LoginServer.login(LoginServer.getProperties(Config.Customkey1 + ".json"), false);
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
            LoginServer.login(LoginServer.getProperties(Config.Customkey1 + ".json"), false);
            File copyFile = new File(Config.writePath + "/" + Config.Customkey1 + ".json");
            String s = LoginServer.readTxtFile(copyFile);
            if (s.contains("[") && s.contains("]")) {
                List<OutCustom> imageBanners = FastJsonUtils.toList(s, OutCustom.class);
                return addAllCustom(imageBanners);
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
    private List<Custom> putRedis() {
        List<Custom> all = customJpaRepository.findAll();
        String s = FastJsonUtils.toJSONString(all);
        redisHandler.getLocalRedisTemplate().cacheStringValue(Config.prefix, Config.Customkey, s);
        return all;
    }

    /**
     * 添加redis缓存
     */
    private List<Custom> getRedis(String app) {
        String stringValue = redisHandler.getLocalRedisTemplate().getStringValue(Config.prefix, Config.Customkey);
        if (stringValue != null) {
            customs = FastJsonUtils.toList(stringValue, Custom.class);
            if (app.equals("all")) {
                return customs;
            } else {
                List<Custom> custom = new ArrayList<>();
                for (Custom c : customs) {
                    if (c.getSchoolId().equals(app)) {
                        custom.add(c);
                    }
                }
                return custom;
            }
        }
        return customs;

    }
}
