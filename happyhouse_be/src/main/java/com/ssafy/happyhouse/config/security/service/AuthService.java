package com.ssafy.happyhouse.config.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.MemberDto;
import com.ssafy.happyhouse.model.mapper.MemberMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {

    private final MemberMapper memberMapper;

    public AuthService(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}

	@Override
    public UserDetails loadUserByUsername(String mno) throws UsernameNotFoundException {
        MemberDto findMember = memberMapper.selectOne(Long.valueOf(mno));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

        //jwt에서 mno를 토큰에 넣음 -> thread 로컬에 인증 객체를 넣음 -> 다음 필터를 통과하기 위해
        return new org.springframework.security.core.userdetails.User(String.valueOf(findMember.getMno()), "", authorities);
    }
}