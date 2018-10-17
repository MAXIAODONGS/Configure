package com.xinlu.model;


public class OutVpnInfo {
    private String schoolId;
    private String port;
    private String serverAddress;
    private String updateAddress;
    private String vpnPsk;
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
