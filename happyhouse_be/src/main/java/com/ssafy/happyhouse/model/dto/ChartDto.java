package com.ssafy.happyhouse.model.dto;

public class ChartDto {

	private String dongName;
	private String gugunName;
	private int count;
	private int max;
	private int min;
	private double avg;
	private String area;
	private int bmax;
	private int bmin;
	private double avgarea;

	public ChartDto() {
	}

	public ChartDto(String dongName, String gugunName, int count, int max, int min, double avg, String area, int bmax,
			int bmin, double avgarea) {
		super();
		this.dongName = dongName;
		this.gugunName = gugunName;
		this.count = count;
		this.max = max;
		this.min = min;
		this.avg = avg;
		this.area = area;
		this.bmax = bmax;
		this.bmin = bmin;
		this.avgarea = avgarea;
	}

	public String getDongName() {
		return dongName;
	}

	public void setDongName(String dongName) {
		this.dongName = dongName;
	}

	public String getGugunName() {
		return gugunName;
	}

	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getBmax() {
		return bmax;
	}

	public void setBmax(int bmax) {
		this.bmax = bmax;
	}

	public int getBmin() {
		return bmin;
	}

	public void setBmin(int bmin) {
		this.bmin = bmin;
	}

	public double getAvgarea() {
		return avgarea;
	}

	public void setAvgarea(double avgarea) {
		this.avgarea = avgarea;
	}

}
