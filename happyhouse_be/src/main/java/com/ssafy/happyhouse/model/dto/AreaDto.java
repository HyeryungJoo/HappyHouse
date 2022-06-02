package com.ssafy.happyhouse.model.dto;

public class AreaDto {

	private String gugunName;
	private String dongName;
	private String x;
	private String y;
	
	public AreaDto() {}

	public AreaDto(String gugunName, String dongName, String x, String y) {
		super();
		this.gugunName = gugunName;
		this.dongName = dongName;
		this.x = x;
		this.y = y;
	}

	public String getGugunName() {
		return gugunName;
	}

	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}

	public String getDongName() {
		return dongName;
	}

	public void setDongName(String dongName) {
		this.dongName = dongName;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
	
}
