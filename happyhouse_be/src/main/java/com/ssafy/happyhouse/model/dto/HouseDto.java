package com.ssafy.happyhouse.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HouseDto : 집매물 정보", description = "house table 내용")
public class HouseDto {
    @ApiModelProperty(value = "매물번호")
    private int no;
    @ApiModelProperty(value = "아파트코드")
    private int aptCode;

    @ApiModelProperty(value = "거래가격")
    private String dealAmount;

    @ApiModelProperty(value = "매물연도")
    private int dealYear;
    @ApiModelProperty(value = "매물월")
    private int dealMonth;
    @ApiModelProperty(value = "매물일")
    private int dealDay;

    @ApiModelProperty(value = "평수")
    private String area;
    @ApiModelProperty(value = "층수")
    private String floor;
    @ApiModelProperty(value = "매물종류")
    private String type;
    @ApiModelProperty(value = "대출금")
    private String rentMoney;
    @ApiModelProperty(value = "아파트 이름")
    private String aptName;
    @ApiModelProperty(value = "동 코드")
    private String dongCode;
    @ApiModelProperty(value = "동 이름")
    private String dongName;

    @ApiModelProperty(value = "건설연도")
    private int buildYear;

    @ApiModelProperty(value = "지번")
    private String jibun;
    @ApiModelProperty(value = "경도")
    private String lat;
    @ApiModelProperty(value = "위도")
    private String lng;
    @ApiModelProperty(value = "사진")
    private String img;
    @ApiModelProperty(value = "사업자")
    private String agent;
    @ApiModelProperty(value = "수정날짜")
    private String modifyDate;
    @ApiModelProperty(value = "수정자")
    private String modifyWriter;
    @ApiModelProperty(value = "찜한 수")
    private int likeCount;

    public HouseDto(int no, int aptCode, String dealAmount, int dealYear, int dealMonth, int dealDay, String area, String floor, String type, String rentMoney, String aptName, String dongCode, String dongName, int buildYear, String jibun, String lat, String lng, String img, String agent, String modifyDate, String modifyWriter, int likeCount) {
        this.no = no;
        this.aptCode = aptCode;
        this.dealAmount = dealAmount;
        this.dealYear = dealYear;
        this.dealMonth = dealMonth;
        this.dealDay = dealDay;
        this.area = area;
        this.floor = floor;
        this.type = type;
        this.rentMoney = rentMoney;
        this.aptName = aptName;
        this.dongCode = dongCode;
        this.dongName = dongName;
        this.buildYear = buildYear;
        this.jibun = jibun;
        this.lat = lat;
        this.lng = lng;
        this.img = img;
        this.agent = agent;
        this.modifyDate = modifyDate;
        this.modifyWriter = modifyWriter;
        this.likeCount = likeCount;
    }

    public HouseDto() {
        super();
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getAptCode() {
        return aptCode;
    }

    public void setAptCode(int aptCode) {
        this.aptCode = aptCode;
    }

    public String getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }

    public int getDealYear() {
        return dealYear;
    }

    public void setDealYear(int dealYear) {
        this.dealYear = dealYear;
    }

    public int getDealMonth() {
        return dealMonth;
    }

    public void setDealMonth(int dealMonth) {
        this.dealMonth = dealMonth;
    }

    public int getDealDay() {
        return dealDay;
    }

    public void setDealDay(int dealDay) {
        this.dealDay = dealDay;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRentMoney() {
        return rentMoney;
    }

    public void setRentMoney(String rentMoney) {
        this.rentMoney = rentMoney;
    }

    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    public String getDongCode() {
        return dongCode;
    }

    public void setDongCode(String dongCode) {
        this.dongCode = dongCode;
    }

    public String getDongName() {
        return dongName;
    }

    public void setDongName(String dongName) {
        this.dongName = dongName;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    public String getJibun() {
        return jibun;
    }

    public void setJibun(String jibun) {
        this.jibun = jibun;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyWriter() {
        return modifyWriter;
    }

    public void setModifyWriter(String modifyWriter) {
        this.modifyWriter = modifyWriter;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public String toString() {
        return "HouseDto{" +
                "no=" + no +
                ", aptCode=" + aptCode +
                ", dealAmount='" + dealAmount + '\'' +
                ", dealYear=" + dealYear +
                ", dealMonth=" + dealMonth +
                ", dealDay=" + dealDay +
                ", area='" + area + '\'' +
                ", floor='" + floor + '\'' +
                ", type='" + type + '\'' +
                ", rentMoney='" + rentMoney + '\'' +
                ", aptName='" + aptName + '\'' +
                ", dongCode='" + dongCode + '\'' +
                ", dongName='" + dongName + '\'' +
                ", buildYear=" + buildYear +
                ", jibun='" + jibun + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", img='" + img + '\'' +
                ", agent='" + agent + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", modifyWriter='" + modifyWriter + '\'' +
                ", likeCount='" + likeCount + '\'' +
                '}';
    }
}
