package com.eastsoft.android.esbic.jni;

import com.eastsoft.android.esbic.util.JsonUtil;

public class IpAddressInfo {
    private String ip;
    private String subnetMask;
    private String gateway;
    private String impAdress;
    private String centerAddress;

    public IpAddressInfo(String ip, String subnetMask, String gateway, String impAdress, String centerAddress) {
        this.ip = ip;
        this.subnetMask = subnetMask;
        this.gateway = gateway;
        this.impAdress = impAdress;
        this.centerAddress = centerAddress;
    }

    public String getIp() {
        return ip;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public String getGateway() {
        return gateway;
    }

    public String getImpAdress() {
        return impAdress;
    }

    public String getCenterAddress() {
        return centerAddress;
    }

    @Override
    public String toString() {
        return JsonUtil.toString(this);
    }
}
