package com.ssafy.happyhouse.model.mapper;

import com.ssafy.happyhouse.model.dto.MemberDto;

public interface MemberMapper {
	// 회원가입 -> 회원 등록
	int create(MemberDto memberDto);

	// SNS 회원가입
	int createSns(MemberDto memberDto);
	
	// 회원 정보 조회
	MemberDto selectOne(Long mno);

	// 회원 아이디로 조회
	MemberDto findByAppId(String appId);
	
	// 로그인
	MemberDto selectLogin(String mid);
	
	// 회원탈퇴 -> 삭제
	int delete(Long mno);
	
	// 회원정보 수정
	int update(MemberDto memberDto);

	// 회원 비밀번호 수정
	int updatePassword(MemberDto memberDto);

	// SNS ID 로 조회
	MemberDto findBySnsId(Long snsId);

	// SNS ID 로 조회
	MemberDto findLikeArea(Long mno);

	// 회원 관심지역 등록
	int insertLikeArea(MemberDto memberDto);

}
