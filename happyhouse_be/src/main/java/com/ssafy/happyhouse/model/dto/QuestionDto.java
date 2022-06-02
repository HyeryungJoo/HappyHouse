package com.ssafy.happyhouse.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "QuestionDto : 질문 정보", description = "question table 내용")
public class QuestionDto {

	@ApiModelProperty(value = "질문번호")
	private int qnum;
	@ApiModelProperty(value = "질문자 아이디")
	private String qmid;
	@ApiModelProperty(value = "질문제목")
	private String qtitle;
	@ApiModelProperty(value = "질문내용")
	private String qcontent;
	@ApiModelProperty(value = "질문 작성 시간")
	private String qcreatetime;
	@ApiModelProperty(value = "질문 변경자 아이디")
	private String qmodifywriter;
	@ApiModelProperty(value = "질문 변경시간")
	private String qmodifytime;
	@ApiModelProperty(value = "질문 등록된 위치 - 위도")
	private String qLat;
	@ApiModelProperty(value = "질문 등록된 위치 - 경도")
	private String qLng;
	@ApiModelProperty(value = "질문 등록된 상세주소")
	private String qDetailAddress;
	private String keyword;

	public QuestionDto() {
	}

	public QuestionDto(int qnum, String qmid, String qtitle, String qcontent, String qcreatetime, String qmodifywriter,
			String qmodifytime, String qLat, String qLng, String qDetailAddress, String keyword) {
		super();
		this.qnum = qnum;
		this.qmid = qmid;
		this.qtitle = qtitle;
		this.qcontent = qcontent;
		this.qcreatetime = qcreatetime;
		this.qmodifywriter = qmodifywriter;
		this.qmodifytime = qmodifytime;
		this.qLat = qLat;
		this.qLng = qLng;
		this.qDetailAddress = qDetailAddress;
		this.keyword = keyword;
	}

	public int getQnum() {
		return qnum;
	}

	public void setQnum(int qnum) {
		this.qnum = qnum;
	}

	public String getQmid() {
		return qmid;
	}

	public void setQmid(String qmid) {
		this.qmid = qmid;
	}

	public String getQtitle() {
		return qtitle;
	}

	public void setQtitle(String qtitle) {
		this.qtitle = qtitle;
	}

	public String getQcontent() {
		return qcontent;
	}

	public void setQcontent(String qcontent) {
		this.qcontent = qcontent;
	}

	public String getQcreatetime() {
		return qcreatetime;
	}

	public void setQcreatetime(String qcreatetime) {
		this.qcreatetime = qcreatetime;
	}

	public String getQmodifywriter() {
		return qmodifywriter;
	}

	public void setQmodifywriter(String qmodifywriter) {
		this.qmodifywriter = qmodifywriter;
	}

	public String getQmodifytime() {
		return qmodifytime;
	}

	public void setQmodifytime(String qmodifytime) {
		this.qmodifytime = qmodifytime;
	}

	public String getqLat() {
		return qLat;
	}

	public void setqLat(String qLat) {
		this.qLat = qLat;
	}

	public String getqLng() {
		return qLng;
	}

	public void setqLng(String qLng) {
		this.qLng = qLng;
	}

	public String getqDetailAddress() {
		return qDetailAddress;
	}

	public void setqDetailAddress(String qDetailAddress) {
		this.qDetailAddress = qDetailAddress;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
