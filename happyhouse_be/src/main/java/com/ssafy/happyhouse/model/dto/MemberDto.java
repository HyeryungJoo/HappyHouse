package com.ssafy.happyhouse.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "MemberDto : 답 정보", description = "member table 내용")
public class MemberDto {
    @ApiModelProperty(value = "회원번호")
    private Long mno;
    @ApiModelProperty(value = "회원 아이디")
    private String mid;
    @ApiModelProperty(value = "회원 비밀번호")
    private String mpw;
    @ApiModelProperty(value = "회원이름")
    private String mname;
    @ApiModelProperty(value = "회원 이메일")
    private String memail;
    @ApiModelProperty(value = "회원 전화번호")
    private String mphone;
    @ApiModelProperty(value = "회원 역할")
    private String mrole;
    @ApiModelProperty(value = "회원 관심지역 구")
    private String mLikeGu;
    @ApiModelProperty(value = "회원 관심지역 동")
    private String mLikeDong;

    private Long mSnsId;

    public MemberDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Long getMno() {
        return mno;
    }

    public void setMno(Long mno) {
        this.mno = mno;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMpw() {
        return mpw;
    }

    public void setMpw(String mpw) {
        this.mpw = mpw;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMemail() {
        return memail;
    }

    public void setMemail(String memail) {
        this.memail = memail;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getMrole() {
        return mrole;
    }

    public void setMrole(String mrole) {
        this.mrole = mrole;
    }

    public Long getmSnsId() {
        return mSnsId;
    }

    public void setmSnsId(Long mSnsId) {
        this.mSnsId = mSnsId;
    }

    public String getmLikeGu() {
        return mLikeGu;
    }

    public void setmLikeGu(String mLikeGu) {
        this.mLikeGu = mLikeGu;
    }

    public String getmLikeDong() {
        return mLikeDong;
    }

    public void setmLikeDong(String mLikeDong) {
        this.mLikeDong = mLikeDong;
    }

    @Override
    public String toString() {
        return "MemberDto [mno=" + mno + ", mid=" + mid + ", mpw=" + mpw + ", mname=" + mname + ", memail=" + memail
                + ", mphone=" + mphone + ", mrole=" + mrole + " + ]";
    }
}
