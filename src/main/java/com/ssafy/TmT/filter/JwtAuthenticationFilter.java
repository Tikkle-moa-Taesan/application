package com.ssafy.TmT.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.TmT.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	
    	System.out.println("필터 작동");
        
    	// 리프레시 토큰을 처리하는 로직은 좀 나중에 만들어도 될것같은데.. 일단은 킵해두자.
        String accessToken = extractTokenFromCookie(request, "accessToken");

        if (accessToken != null && jwtUtil.validateToken(accessToken)) {
            Long memberId = jwtUtil.getMemberIdFromJwt(accessToken); 
            UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(memberId, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("유효한 accessToken으로 SecurityContext에 인증 설정 완료");
            System.out.println("현재 accessToken 값 : " + accessToken);
        } else {
            log.warn("유효하지 않은 JWT token");
        }

        chain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}



 