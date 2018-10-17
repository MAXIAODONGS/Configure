package com.maxd.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by SEELE on 2017/11/18.
 */
@Entity
public class UpdateInfo implements Serializable {
    /**
     * phoneWare : ios
     * newVersion : 1.0.5
     * description : 客户端有新版本，请更新
     * download_url : https://itunes.apple.com/cn/app/wenet%E6%A0%A1%E5%9B%AD/id1271674548?mt=8
     * app : WeNet校园
     * plan_alert : true
     * type : 1
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String phoneWare;
    @Column
    private String newVersion;
    @Column
    private String description;
    @Column
    private String download_url;
    @Column
    private String app;
    @Column
    private String plan_alert;
    @Column
    private String type;

    public String getPlan_alert() {
        return plan_alert;
    }

    public void setPlan_alert(String plan_alert) {
        this.plan_alert = plan_alert;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column
    private String time;
    @Column
    private String schoolName;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneWare() {
        return phoneWare;
    }

    public void setPhoneWare(String phoneWare) {
        this.phoneWare = phoneWare;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }


}
