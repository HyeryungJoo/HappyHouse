package com.ssafy.happyhouse.config.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ssafy.happyhouse.config.security.filter.JwtAuthenticationFilter;
import com.ssafy.happyhouse.config.security.handler.CustomAccessDeniedHandler;
import com.ssafy.happyhouse.config.security.handler.CustomAuthenticationEntryPoint;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
			CustomAccessDeniedHandler accessDeniedHandler, CustomAuthenticationEntryPoint authenticationEntryPoint) {
		super();
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.accessDeniedHandler = accessDeniedHandler;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


	@Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs","/swagger-resources/**","/swagger-ui.html"
        ,"/webjars/**","/swagger/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                // Session과 쿠키를 사용하면 데이터가 계속 저장됨 -> 세션이 만료 될때 까지
                // jwt는 session을 사용하면 안됨 회원의 인증 정보가 들어가 있음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/member", "/member/login", "/member/login/kakao", "/member/password","/house/search/like/{mno}/{hno}").permitAll()
                .antMatchers(HttpMethod.GET, "/member/password","/house/list","/house/area/{dongCode}","/house/apt/{aptCode}","/house/search/like/{mno}/{hno}").permitAll()
                .antMatchers(HttpMethod.POST
                        , "/gudong/","/gudong","/gudong/{gugunName}"
                        ,"/question","/question/", "/question/{qnum}","/question/search/{qdong}", "/question/search/list/{keyword}","/question/search/list/{qdong}/{keyword}"
                        ,"/answer/","/answer","/answer/search/{anum}","/answer/{anum}"
                        ,"/notice","/notice/","/notice/{nnum}"
                        ,"/house/search/like/{mno}/{hno}"
                        ,"/member","/member/", "/member/login","/member/{mno}","/member/one{mno}").permitAll()
                .antMatchers(HttpMethod.GET
                        , "/gudong/","/gudong","/gudong/{gugunName}"
                        ,"/question","/question/", "/question/{qnum}","/question/search/{qdong}", "/question/search/list/{keyword}","/question/search/list/{qdong}/{keyword}","/question/search/mylist/{mid}"
                        ,"/house","/house/search/{no}","/house/list","/house/area/{dongCode}","/house/apt/{aptCode}","/house/current","/house/countByMonth","/house/maxAmountByDong","/house/maxAmountByGu"
                        ,"/house/seoul","/house/seoul40","/house/dongper","/house/dongper40","/house/guper","/house/guper40"
                        ,"/answer/","/answer","/answer/search/{anum}","/answer/{anum}"
                        ,"/notice","/notice/","/notice/{nnum}"
                        ,"/house/search/like/{mno}"
                        ,"/news","/news/"
                        ,"/member","/member/", "/member/login","/member/{mno}","/member/one{mno}").permitAll()
                .antMatchers(HttpMethod.PUT
                        , "/gudong/","/gudong","/gudong/{gugunName}"
                        ,"/question","/question/", "/question/{qnum}","/question/search/{qdong}", "/question/search/list/{keyword}","/question/search/list/{qdong}/{keyword}"
                        ,"/answer/","/answer","/answer/search/{anum}","/answer/{anum}"
                        ,"/notice","/notice/","/notice/{nnum}"
                        ,"/member","/member/", "/member/login","/member/{mno}","/member/one{mno}").permitAll()
                .antMatchers(HttpMethod.PATCH
                        ,"/member","/member/password", "/member/login","/member/{mno}","/member/one{mno}").permitAll()
                .antMatchers(HttpMethod.DELETE
                        , "/gudong/","/gudong","/gudong/{gugunName}"
                        ,"/question","/question/", "/question/{qnum}","/question/search/{qdong}", "/question/search/list/{keyword}","/question/search/list/{qdong}/{keyword}"
                        ,"/answer/","/answer","/answer/search/{anum}","/answer/{anum}"
                        ,"/notice","/notice/","/notice/{nnum}"
                        ,"/house/search/like/{mno}/{hno}"
                        ,"/member","/member/", "/member/login","/member/{mno}","/member/one{mno}").permitAll()
                .anyRequest().hasAnyRole("MEMBER", "ADMIN") //회원의 권한을 확인
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                //jwtAuthenticationFilter를 넣어줌 !!필수!! -> 필터를 생성해서 인증과 인가 실행 (securityConfig의 설정)

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}