package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.client.KakaoClient;
import com.ssafy.happyhouse.client.KakaoProfile;
import com.ssafy.happyhouse.dto.request.MemberSnsLoginRequest;
import com.ssafy.happyhouse.dto.request.*;
import com.ssafy.happyhouse.dto.response.MemberResponse;
import com.ssafy.happyhouse.exception.DuplicateException;
import com.ssafy.happyhouse.exception.NotFoundException;
import com.ssafy.happyhouse.exception.NotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.config.security.util.JwtUtil;
import com.ssafy.happyhouse.model.dto.MemberDto;
import com.ssafy.happyhouse.model.dto.MemberTokenDto;
import com.ssafy.happyhouse.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private KakaoClient kakaoClient;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void create(MemberCreateRequest request) {
        // 회원아이디 중복 검사
        MemberDto findMember = memberMapper.findByAppId(request.getAppId());

        // 이미 가입한 적이 있는지 확인
        if (findMember != null) {
            throw new DuplicateException("이미 존재하는 아이디입니다.");
        }

        // 회원가입
        MemberDto member = new MemberDto();
        member.setMid(request.getAppId());
        member.setMpw(passwordEncoder.encode(request.getPassword()));
        member.setMname(request.getName());
        member.setMemail(request.getEmail());
        member.setMphone(request.getMobile());
        memberMapper.create(member);
    }

    @Override
    public MemberResponse selectOne(Long memberId) {
        MemberDto findMember = memberMapper.selectOne(memberId);

        if (findMember == null) {
            throw new NotFoundException("존재하지 않는 회원입니다.");
        }

        return new MemberResponse(findMember);
    }

    @Override
    public MemberTokenDto login(MemberLoginRequest request) {
        MemberDto findMember = memberMapper.findByAppId(request.getAppId());

        if (findMember == null) {
            throw new NotFoundException("존재하지 않는 회원입니다.");
        }

        if (!passwordEncoder.matches(request.getPassword(), findMember.getMpw())) {
            throw new NotMatchException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.createToken(findMember.getMno());

        return new MemberTokenDto(accessToken);
    }

    @Override
    public void delete(Long memberId) {
        int result = memberMapper.delete(memberId);

        if (result != 1) {
            throw new NotFoundException("존재하지 않는 회원입니다");
        }
    }

    @Override
    public void update(Long memberId, MemberUpdateRequest request) {
        MemberDto updateMember = new MemberDto();
        updateMember.setMno(memberId);
        updateMember.setMemail(request.getEmail());
        updateMember.setMname(request.getName());
        updateMember.setMphone(request.getMobile());

        memberMapper.update(updateMember);
    }

    @Override
    public void updatePassword(Long memberId, MemberUpdatePasswordRequest request) {
        // 비밀번호 일치 확인
        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new NotMatchException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        MemberDto updateMember = new MemberDto();
        updateMember.setMno(memberId);
        updateMember.setMpw(passwordEncoder.encode(request.getPassword()));

        memberMapper.updatePassword(updateMember);
    }

    @Override
    public void findPassword(MemberPasswordFindRequest request) {

        MemberDto findMember = memberMapper.findByAppId(request.getAppId());

        if (findMember == null) {
            throw new NotFoundException("존재하지 않는 회원입니다.");
        }

        if (!findMember.getMname().equals(request.getName())) {
            throw new NotMatchException("회원 이름이 일치하지 않습니다.");
        }

        if (!findMember.getMphone().equals(request.getMobile())) {
            throw new NotMatchException("회원 전화번호가 일치하지 않습니다.");
        }
    }

    @Override
    public void updatePassword(MemberPasswordChangeRequest request) {

        System.out.println(request.getAppId());
        MemberDto findMember = memberMapper.findByAppId(request.getAppId());

        if (findMember == null) {
            throw new NotFoundException("존재하지 않는 회원입니다.");
        }

        // 비밀번호 일치 확인
        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new NotMatchException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        MemberDto updateMember = new MemberDto();
        updateMember.setMno(findMember.getMno());
        updateMember.setMpw(passwordEncoder.encode(request.getPassword()));

        memberMapper.updatePassword(updateMember);
    }

    @Override
    public MemberTokenDto kakaoLogin(MemberSnsLoginRequest request) {

        KakaoProfile kakaoProfile = kakaoClient.getKakaoProfile(request.getKakaoToken());

        MemberDto findMember = memberMapper.findBySnsId(kakaoProfile.getId());

        if (findMember == null) {
            // 회원가입
            MemberDto member = new MemberDto();
            member.setmSnsId(kakaoProfile.getId());
            member.setMname(kakaoProfile.getProperties().getNickname());
            memberMapper.createSns(member);

            findMember = memberMapper.findBySnsId(kakaoProfile.getId());
        }

        String token = jwtUtil.createToken(findMember.getMno());
        return new MemberTokenDto(token);
    }

    @Override
    public MemberDto findLikeArea(Long mno) {
        return memberMapper.findLikeArea(mno);
    }

    @Override
    public boolean insertLikeArea(MemberDto memberDto) {
        return memberMapper.insertLikeArea(memberDto) == 1;
    }

}
