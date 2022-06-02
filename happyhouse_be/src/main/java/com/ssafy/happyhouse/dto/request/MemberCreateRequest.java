package com.ssafy.happyhouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

public class MemberCreateRequest {

    @NotBlank(message = "회원아이디는 필수입니다.")
    private String appId;

    private String password;

    private String name;

    private String email;

    private String mobile;

    public String getAppId() {
        return appId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

}
