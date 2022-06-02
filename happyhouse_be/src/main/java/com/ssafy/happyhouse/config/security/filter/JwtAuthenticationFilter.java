package com.ssafy.happyhouse.config.security.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssafy.happyhouse.config.security.service.AuthService;
import com.ssafy.happyhouse.config.security.util.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, AuthService authService) {
		super();
		this.jwtUtil = jwtUtil;
		this.authService = authService;
	}

	@Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        String jwtToken = extractToken((HttpServletRequest) request);

        // jwttoken이 만료가 됐는지 안됐는지 확인
        if (StringUtils.hasText(jwtToken) && jwtUtil.isValidToken(jwtToken)) {
            // 추출한 유효한 토큰안에 id값으로 회원을 조회
            UserDetails userDetails = authService.loadUserByUsername(jwtUtil.getSubject(jwtToken));

            //조회한 다음에 db에서 조회
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                    // 구현체
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        // 헤더에서 값을 추출
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}