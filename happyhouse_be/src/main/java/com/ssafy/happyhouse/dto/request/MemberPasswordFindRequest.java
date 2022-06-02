package com.ssafy.happyhouse.dto.request;

public class MemberPasswordFindRequest {

    private String appId;

    private String name;

    private String mobile;

    public MemberPasswordFindRequest(String appId, String name, String mobile) {
        this.appId = appId;
        this.name = name;
        this.mobile = mobile;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAppId() {
        return appId;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }
}
