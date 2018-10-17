package com.xinlu.service.custom;

import com.xinlu.model.Custom;
import com.xinlu.model.OutCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ICustomService {
    /**
     * 获取全部的弹框信息
     *
     * @return
     */
    List<Custom> findAll();

    /**
     * 保存弹框对象
     */
    void saveBody(Custom custom);

    /**
     * 获取一个b弹框对象
     *
     * @param id
     * @return
     */
    Custom findOne(long id);

    /**
     * 删除一个弹框
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

    List<Custom> findByOu(String ou);

    /**
     * 条件查询
     *
     * @param ou 学校
     * @return 学校集合
     * 分页
     */
    Page<Custom> find(String ou, Pageable pageable);

    /**
     * 修改
     */
    void updateBody(Custom custom);


    /**
     * 删除全部
     */
    void deleteAllCustom();

    /**
     * 批量添加
     */
    String addAllCustom(List<OutCustom> customs);

    /**
     * @return 数据导出
     */
    List<OutCustom> findOutList();

    /**
     * @return 根据学校查询集合
     */
    List<Custom> getBodyByOu(String ou);

    /**
     * 添加对象检查
     */
    String addCustomInfo(Custom custom);

    /**
     * 部署本地文件到远程服务器
     */
    String writeBodyInfoToServer();


    /**
     * 获取远程服务器数据到本地
     */
    String getBodyInfoFromServer();

}


