package com.maxd.service.banner;

import com.maxd.model.*;
import com.maxd.redis.RedisHandler;
import com.maxd.respository.banner.BannerJpaRepository;
import com.maxd.respository.banner.BannerRepository;
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
public class BannerServiceImpl implements IBannerService {
    @Autowired
    RedisHandler redisHandler;
    @Autowired
    private BannerJpaRepository bannerJpaRepository;
    @Autowired
    private BannerRepository bannerRepository;
    private List<Banner> banners=new ArrayList<>();
    private List<ImageBanner> imagebanners;

    @Override
    public List<Banner> findAll() {
        List<Banner> redis = getRedis("all");
        if (redis.size() == 0) {
            return putRedis();
        } else {
            return redis;
        }
    }

    @Override
    public void saveBody(Banner banner) {
        try {
            bannerJpaRepository.save(banner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        putRedis();
    }


    @Override
    public Banner findOne(long id) {
        return bannerJpaRepository.findOne(id);
    }

    @Override
    public void delete(long id) {
        try {
            bannerJpaRepository.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        putRedis();
    }


    @Override
    public List<Banner> findByOu(String ou) {
        List<Banner> redis = getRedis(ou);
        if (redis.size() == 0) {
            return bannerRepository.findById(ou);
        } else {
            return redis;
        }
    }

    @Override
    public Page<Banner> find(String app, Pageable pageable) {
        return bannerRepository.find(app, pageable);
    }


    @Override
    @Transactional
    public void updateBody(Banner banner) {
        Banner banner1 = bannerJpaRepository.findOne(banner.getId());
        BeanUtils.copyProperties(banner, banner1);
    }

    @Override
    public List<String> findOu() {
        return bannerRepository.findOu();
    }

    @Override
    public void deleteAllBanner() {
        try {
            List<Banner> all = bannerJpaRepository.findAll();
            for (Banner anAll : all) {
                bannerJpaRepository.delete(anAll.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        putRedis();
    }

    @Override
    public String addAllBanner(List<ImageBanner> imageBanners) {
        for (ImageBanner u : imageBanners) {
            Date d = new Date();//获取时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");//转换格式
            List<ImageBanner.ImageBannerBean> image_banner1 = u.getImage_banner();
            for (ImageBanner.ImageBannerBean imageBannerBean : image_banner1) {
                if (bannerRepository.findById(u.getOu()).size() > 5) {
                    return "单个学校最多只能添加五张轮播图，是否要清理之前所有学校数据？";
                } else {
                    Banner banner = new Banner();
                    banner.setOu(u.getOu());
                    banner.setImage_url(imageBannerBean.getImage_url());
                    banner.setPid(imageBannerBean.getPid());
                    banner.setSchoolName(Utils.getName(u.getOu()));
                    banner.setToken(imageBannerBean.getToken());
                    banner.setUrl(imageBannerBean.getUrl());
                    banner.setTime(sdf.format(d));
                    bannerJpaRepository.save(banner);
                }
            }

        }
        putRedis();
        return "success";
    }

    private List<ImageBanner> getOu() {
        imagebanners = new ArrayList<>();
        List<String> ou = findOu();
        System.out.println(ou.size());
        if (ou.size() == 0) {
            return imagebanners;
        }
        for (String s : ou) {
            ImageBanner alert1 = new ImageBanner();
            List<Banner> byOu = bannerRepository.findById(s);
            List<ImageBanner.ImageBannerBean> imageBannerBeans = new ArrayList<>();
            for (Banner b : byOu) {
                ImageBanner.ImageBannerBean imageBannerBean = new ImageBanner.ImageBannerBean();
                imageBannerBean.setImage_url(b.getImage_url());
                imageBannerBean.setToken(b.getToken());
                imageBannerBean.setUrl(b.getUrl());
                imageBannerBean.setPid(b.getPid());
                imageBannerBeans.add(imageBannerBean);
            }
            alert1.setOu(s);
            alert1.setImage_banner(imageBannerBeans);
            imagebanners.add(alert1);
        }
        return imagebanners;

    }

    @Override
    public List<ImageBanner> findOutList() {
        return getOu();
    }

    @Override
    public List<Banner> getBodyByOu(String ou) {
        if (ou.equals("all")) {
            return findAll();
        } else {
            return findByOu(ou);
        }
    }

    @Override
    public String addBannerInfo(Banner banner) {
        if (bannerRepository.findById(banner.getOu()).size() > 4) {
            return "单个学校最多只能添加五张轮播图片，" + banner.getSchoolName() + "已有" + bannerRepository.findById(banner.getOu()).size() + "张";
        } else {
           saveBody(banner);
            return "success";
        }
    }

    @Override
    public String writeBodyInfoToServer() {
        try {
            List<ImageBanner> outList = findOutList();
            if (outList.size() != 0) {
                String jsonString = FastJsonUtils.toJSONString(outList);
                File copyFile = new File(Config.writePath + "/" + Config.Bannerkey1 + ".json");
                boolean b = LoginServer.writeTxtFile(jsonString, copyFile);
                if (b) {
                    LoginServer.login(LoginServer.getProperties(Config.Bannerkey1 + ".json"), false);
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
            LoginServer.login(LoginServer.getProperties(Config.Bannerkey1 + ".json"), false);
            File copyFile = new File(Config.writePath + "/" + Config.Bannerkey1 + ".json");
            String s = LoginServer.readTxtFile(copyFile);
            if (s.contains("[") && s.contains("]")) {
                List<ImageBanner> imageBanners = FastJsonUtils.toList(s, ImageBanner.class);
                return addAllBanner(imageBanners);
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
    private List<Banner> putRedis() {
        List<Banner> all = bannerJpaRepository.findAll();
        String s = FastJsonUtils.toJSONString(all);
        redisHandler.getLocalRedisTemplate().cacheStringValue(Config.prefix, Config.Bannerkey, s);
        return all;
    }

    /**
     * 添加redis缓存
     */
    private List<Banner> getRedis(String app) {
        String stringValue = redisHandler.getLocalRedisTemplate().getStringValue(Config.prefix, Config.Bannerkey);
        if (stringValue != null) {
            banners = FastJsonUtils.toList(stringValue, Banner.class);
            if (app.equals("all")) {
                return banners;
            } else {
                List<Banner> banners1 = new ArrayList<>();
                for (Banner c : banners) {
                    if (c.getOu().equals(app)) {
                        banners1.add(c);
                    }
                }
                return banners1;
            }
        }
        return banners;

    }

}
