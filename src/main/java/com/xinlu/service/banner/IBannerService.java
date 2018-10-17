package com.xinlu.service.banner;

import com.xinlu.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBannerService {
    /**
     * 获取全部的轮播图信息
     *
     * @return
     */
    List<Banner> findAll();

    /**
     * 保存banner对象
     *
     * @param banner
     */
    void saveBody(Banner banner);

    /**
     * 获取一个banner对象
     *
     * @param id
     * @return
     */
    Banner findOne(long id);

    /**
     * 删除一个banner
     *
     * @param id
     */
    void delete(long id);

    /**
     * 条件查询
     *
     * @param ou 学校
     * @return 学校集合
     */

    List<Banner> findByOu(String ou);

    /**
     * 条件查询
     *
     * @param ou 学校
     * @return 学校集合
     * 分页
     */
    Page<Banner> find(String ou, Pageable pageable);

    /**
     * 修改
     *
     * @param banner 对象
     */
    void updateBody(Banner banner);

    List<String> findOu();


    /**
     * 删除全部
     */
    void deleteAllBanner();

    /**
     * 批量添加
     */
    String addAllBanner(List<ImageBanner> imageBanners);

    /**
     * @return 数据导出
     */
    List<ImageBanner> findOutList();

    /**
     * @return 根据学校查询集合
     */
    List<Banner> getBodyByOu(String ou);

    /**
     * 添加对象检查
     */
    String addBannerInfo(Banner banner);
    /**
     * 部署本地文件到远程服务器
     */
    String writeBodyInfoToServer();


    /**
     * 获取远程服务器数据到本地
     */
    String getBodyInfoFromServer();
}
