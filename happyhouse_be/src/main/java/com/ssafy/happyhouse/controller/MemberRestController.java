package com.ssafy.happyhouse.controller;

import javax.validation.Valid;

import com.ssafy.happyhouse.model.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.happyhouse.config.web.LoginMember;
import com.ssafy.happyhouse.dto.request.*;
import com.ssafy.happyhouse.dto.response.MemberResponse;
import com.ssafy.happyhouse.model.dto.MemberDto;
import com.ssafy.happyhouse.model.dto.MemberTokenDto;
import com.ssafy.happyhouse.model.service.MemberService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/member")
public class MemberRestController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private MemberService memberService;
	
	@ApiOperation(value = "회원가입", response = String.class)
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody @Valid MemberCreateRequest request){
		memberService.create(request);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "회원로그인", response = MemberTokenDto.class)
	@PostMapping("/login")
	public ResponseEntity<MemberTokenDto> login(@RequestBody @Valid MemberLoginRequest request) {
		MemberTokenDto response = memberService.login(request);
		System.out.println("controller");
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "회원 카카오 로그인", response = MemberTokenDto.class)
	@PostMapping("/login/kakao")
	public ResponseEntity<MemberTokenDto> kakaoLogin(@RequestBody @Valid MemberSnsLoginRequest request) {
		MemberTokenDto response = memberService.kakaoLogin(request);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "회원수정", response = String.class)
	@PutMapping
	//jwt안에 숨겨져 있는 id를 여기에 넣어줌
	public ResponseEntity<Void> update(@LoginMember Long memberId,
										 @RequestBody @Valid MemberUpdateRequest request) {
		memberService.update(memberId, request);
		return ResponseEntity.noContent().build();
	}

	//비밀번호만 변경
	@PatchMapping("/password")
	public ResponseEntity<Void> updatePassword(@LoginMember Long memberId,
											   @RequestBody @Valid MemberUpdatePasswordRequest request){
		memberService.updatePassword(memberId, request);
		return ResponseEntity.noContent().build();
	}

	//비밀번호 잃어버렸을 시 비밀번호 변경
	@PostMapping("/password")
	public ResponseEntity<Void> updatePassword(@RequestBody @Valid MemberPasswordChangeRequest request){
		System.out.println("updatePassword");
		memberService.updatePassword(request);
		return ResponseEntity.noContent().build();
	}

	//비밀번호 찾기(회원 인증)
	@GetMapping("/password")
	public ResponseEntity<Void> findPassword(@Valid MemberPasswordFindRequest request){
		memberService.findPassword(request);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "회원삭제", response = String.class)
	@DeleteMapping
	public ResponseEntity<Void> delete(@LoginMember Long memberId) {
		memberService.delete(memberId);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "회원조회", response = MemberDto.class)
	@GetMapping()
	public ResponseEntity<MemberResponse> selectOne(@LoginMember Long memberId) {
		MemberResponse response = memberService.selectOne(memberId);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "관심지역 조회", response = String.class)
	@GetMapping("/{mno}")
	public ResponseEntity<MemberDto> insertLikeArea(@PathVariable("mno") Long mno){
		return new ResponseEntity<>(memberService.findLikeArea(mno), HttpStatus.OK);
	}

	@ApiOperation(value = "관심지역 등록", response = String.class)
	@PostMapping("/registerlikearea")
	public ResponseEntity<String> insertLikeArea(@RequestBody MemberDto memberDto){
		memberService.insertLikeArea(memberDto);
		return ResponseEntity.noContent().build();
	}


}
