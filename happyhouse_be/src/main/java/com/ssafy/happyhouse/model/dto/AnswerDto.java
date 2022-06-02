package com.ssafy.happyhouse.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AnswerDto : 답 정보", description = "answer table 내용")
public class AnswerDto {
	@ApiModelProperty(value = "답변번호")
	private int anum;
	@ApiModelProperty(value = "질문번호")
	private int qnum;
	@ApiModelProperty(value = "답변자 아이디")
	private String amid;
	@ApiModelProperty(value = "답변내용")
	private String acontent;
	@ApiModelProperty(value = "답변 작성시간")
	private String createtime;
	@ApiModelProperty(value = "답변 변경자 아이디")
	private String amodifywriter;
	@ApiModelProperty(value = "답변 변경 시간")
	private String amodifytime;
	
	
	public AnswerDto(int anum, int qnum, String amid, String acontent, String createtime, String amodifywriter,
			String amodifytime) {
		super();
		this.anum = anum;
		this.qnum = qnum;
		this.amid = amid;
		this.acontent = acontent;
		this.createtime = createtime;
		this.amodifywriter = amodifywriter;
		this.amodifytime = amodifytime;
	}
	public AnswerDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getAnum() {
		return anum;
	}
	public void setAnum(int anum) {
		this.anum = anum;
	}
	public int getQnum() {
		return qnum;
	}
	public void setQnum(int qnum) {
		this.qnum = qnum;
	}
	public String getAmid() {
		return amid;
	}
	public void setAmid(String amid) {
		this.amid = amid;
	}
	public String getAcontent() {
		return acontent;
	}
	public void setAcontent(String acontent) {
		this.acontent = acontent;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getAmodifywriter() {
		return amodifywriter;
	}
	public void setAmodifywriter(String amodifywriter) {
		this.amodifywriter = amodifywriter;
	}
	public String getAmodifytime() {
		return amodifytime;
	}
	public void setAmodifytime(String amodifytime) {
		this.amodifytime = amodifytime;
	}
	@Override
	public String toString() {
		return "AnswerDto [anum=" + anum + ", qnum=" + qnum + ", amid=" + amid + ", acontent=" + acontent
				+ ", createtime=" + createtime + ", amodifywriter=" + amodifywriter + ", amodifytime=" + amodifytime
				+ "]";
	}
	
	
	
}
