package com.xinlu.model;

import java.io.Serializable;


public class OutUpdateInfo implements Serializable {
    private String phoneWare;
    private String newVersion;
    private String description;
    private String download_url;
    private String app;
    private String plan_alert;

    public String getPlan_alert() {
        return plan_alert;
    }

    public void setPlan_alert(String plan_alert) {
        this.plan_alert = plan_alert;
    }

    private int type;
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


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
