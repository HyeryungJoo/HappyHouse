package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.dto.request.MemberSnsLoginRequest;
import com.ssafy.happyhouse.dto.request.*;
import com.ssafy.happyhouse.dto.response.MemberResponse;
import com.ssafy.happyhouse.model.dto.MemberDto;
import com.ssafy.happyhouse.model.dto.MemberTokenDto;
import com.ssafy.happyhouse.model.dto.QuestionDto;

public interface MemberService {
	// 회원가입 -> 회원 등록
	void create(MemberCreateRequest memberDto);
		
	// 회원 정보 조회
	MemberResponse selectOne(Long memberId);
		
	// 로그인
	MemberTokenDto login(MemberLoginRequest request);
		
	// 회원탈퇴 -> 삭제
	void delete(Long memberId);
		
	// 회원정보 수정
    void update(Long memberId, MemberUpdateRequest request);

	// 회원 비밀번호 수정
	void updatePassword(Long memberId, MemberUpdatePasswordRequest request);

	// 회원 비밀번호 찾기
	void findPassword(MemberPasswordFindRequest request);

	// 회원 비밀번호 찾기 후처리
	void updatePassword(MemberPasswordChangeRequest request);

	// SNS 로그인
	MemberTokenDto kakaoLogin(MemberSnsLoginRequest request);

	MemberDto findLikeArea(Long mno);

	boolean insertLikeArea(MemberDto memberDto);
}
