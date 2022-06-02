package com.ssafy.happyhouse.dto.request;

public class MemberPasswordChangeRequest {

    private String appId;

    private String password;

    private String checkPassword;

    public String getAppId() {
        return appId;
    }

    public String getPassword() {
        return password;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public MemberPasswordChangeRequest(String appId, String password, String checkPassword) {
        this.appId = appId;
        this.password = password;
        this.checkPassword = checkPassword;
    }

    public MemberPasswordChangeRequest() {
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }
}
