//package com.ssafy.TmT.filter;
//
//import java.io.IOException;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.ssafy.TmT.util.JwtUtil;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//
//    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        
//        // 쿠키에서 accessToken 추출
//        String accessToken = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("accessToken".equals(cookie.getName())) {
//                    accessToken = cookie.getValue();
//                    break;
//                }
//            }
//        }
//
//        // JWT 검증
//        // Spring Security 도입..?
//        if (accessToken != null && jwtUtil.validateToken(accessToken)) {
//            Long memberId = jwtUtil.getMemberIdFromJwt(accessToken);
//            
//            // 유효한 토큰일 경우 SecurityContext에 사용자 정보 저장
//            UserDetails userDetails = User.withUsername(memberId.toString())
//                                          .password("")  // 비밀번호는 여기서 필요하지 않으므로 공란
//                                          .authorities("ROLE_USER")  // 기본 권한 설정
//                                          .build();
//            
//            UsernamePasswordAuthenticationToken authentication = 
//                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
