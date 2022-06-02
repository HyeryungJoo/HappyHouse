package com.ssafy.happyhouse.model.dto;

public class NoticeDto {

	private int nnum;
	private String aid;
	private String ntitle;
	private String ncontent;
	private String ncreatetime;
	private String nmodifywriter;
	private String nmodifytime;

	public NoticeDto() {
	}

	public NoticeDto(int nnum, String aid, String ntitle, String ncontent, String ncreatetime, String nmodifywriter,
			String nmodifytime) {
		super();
		this.nnum = nnum;
		this.aid = aid;
		this.ntitle = ntitle;
		this.ncontent = ncontent;
		this.ncreatetime = ncreatetime;
		this.nmodifywriter = nmodifywriter;
		this.nmodifytime = nmodifytime;
	}

	public int getNnum() {
		return nnum;
	}

	public void setNnum(int nnum) {
		this.nnum = nnum;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getNtitle() {
		return ntitle;
	}

	public void setNtitle(String ntitle) {
		this.ntitle = ntitle;
	}

	public String getNcontent() {
		return ncontent;
	}

	public void setNcontent(String ncontent) {
		this.ncontent = ncontent;
	}

	public String getNcreatetime() {
		return ncreatetime;
	}

	public void setNcreatetime(String ncreatetime) {
		this.ncreatetime = ncreatetime;
	}

	public String getNmodifywriter() {
		return nmodifywriter;
	}

	public void setNmodifywriter(String nmodifywriter) {
		this.nmodifywriter = nmodifywriter;
	}

	public String getNmodifytime() {
		return nmodifytime;
	}

	public void setNmodifytime(String nmodifytime) {
		this.nmodifytime = nmodifytime;
	}

}
