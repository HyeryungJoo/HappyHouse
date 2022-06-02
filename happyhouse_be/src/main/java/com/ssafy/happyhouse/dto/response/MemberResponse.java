package com.ssafy.happyhouse.dto.response;

import com.ssafy.happyhouse.model.dto.MemberDto;

public class MemberResponse {

    private Long memberId;

    private String appId;

    private String name;

    private String email;

    private String mobile;

    public MemberResponse(MemberDto member) {
        this.memberId = member.getMno();
        this.appId = member.getMid();
        this.name = member.getMname();
        this.email = member.getMemail();
        this.mobile = member.getMphone();
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getAppId() {
        return appId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}
