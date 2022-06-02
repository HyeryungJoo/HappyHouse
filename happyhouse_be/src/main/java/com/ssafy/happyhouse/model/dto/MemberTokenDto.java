package com.ssafy.happyhouse.model.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "MemberTokenDto : 답 정보", description = "member token 내용")
public class MemberTokenDto {

	private String accessToken;

	public MemberTokenDto(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
