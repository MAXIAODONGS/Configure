package com.xinlu.service.alert;

import com.xinlu.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAlertService {
    /**
     * 获取全部的弹框信息
     *
     * @return
     */
    List<Alert> findAll();

    /**
     * 保存弹框对象
     *
     * @param alert
     */
    void saveBody(Alert alert);

    /**
     * 获取一个b弹框对象
     *
     * @param id
     * @return
     */
    Alert findOne(long id);

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

    List<Alert> findByOu(String ou);


    /**
     * 条件查询
     *
     * @param ou 学校
     * @return 学校集合
     * 分页
     */
    Page<Alert> find(String ou, Pageable pageable);

    /**
     * 修改
     */
    void updateBody(Alert alert);

    /**
     * 删除全部
     */
    void deleteAllAlert();

    /**
     * 批量添加
     */
    String addAllAlert(List<OutAlert> alerts);

    /**
     * @return 数据导出
     */
    List<OutAlert> findOutList();

    /**
     * @return 根据学校查询集合
     */
    List<Alert> getBodyByOu(String ou);

    /**
     * 添加对象检查
     */
    String addAlertInfo(Alert alert);


    /**
     * 部署本地文件到远程服务器
     */
    String writeBodyInfoToServer();


    /**
     * 获取远程服务器数据到本地
     */
    String getBodyInfoFromServer();

}
