package com.xinlu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class VpnInfo implements Serializable {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String schoolId;
    @Column
    private String port;
    @Column
    private String serverAddress;
    @Column
    private String updateAddress;
    @Column
    private String vpnPsk;
    @Column
    private String schoolName;
    @Column
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getUpdateAddress() {
        return updateAddress;
    }

    public void setUpdateAddress(String updateAddress) {
        this.updateAddress = updateAddress;
    }

    public String getVpnPsk() {
        return vpnPsk;
    }

    public void setVpnPsk(String vpnPsk) {
        this.vpnPsk = vpnPsk;
    }
}
