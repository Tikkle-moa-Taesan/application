package com.ssafy.TmT.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import com.ssafy.TmT.dto.Profile;
import com.ssafy.TmT.exception.InvalidTokenException;
import com.ssafy.TmT.exception.TokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;

@Configuration
public class JwtUtil {

	@Value("${TnT.secret-key}")
	private String secretKeyString;
	// secret key 가 인식이 안될수도 있음
	
    // AccessToken 유효 시간: 1시간 (ms 단위)
    private static final long ACCESS_TOKEN_EXPIRY = 1000L * 60 * 60;
    
    // RefreshToken 유효 시간: 7일 (ms 단위)
    private static final long REFRESH_TOKEN_EXPIRY = 1000L * 60 * 60 * 24 * 7;
    // SecretKey 즉시 생성 메서드
    private SecretKey createSecretKey() {
        byte[] keyBytes = secretKeyString.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
	
    // AccessToken 생성 메서드
    public String generateAccessToken(Long memberId) {
        SecretKey secretKey = createSecretKey(); // SecretKey를 즉석에서 생성
        long now = System.currentTimeMillis();
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("memberId", member.getMemberId());
//        claims.put("email", member.getEmail());
        // 더 넣을거 있으면 더 넣어야 함
        
        return Jwts.builder()
        		.claim("memberId", memberId)
//        		.setClaims(claims)			// 사용자 정보 저장
                .setIssuedAt(new Date(now)) // 토큰 발행 시간
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRY)) // 토큰 만료 시간
                .signWith(secretKey,SignatureAlgorithm.HS256) // 시그니처 알고리즘과 SecretKey 지정
                .compact();
    }
	
	
    // RefreshToken 생성 메서드
    public String generateRefreshToken(Long memberId) {
        SecretKey secretKey = createSecretKey();
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .claim("memberId", memberId)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRY))
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .compact();
    }
    
    // RefreshToken 유효성 검증 및 새 AccessToken 발급 메서드
    public String refreshAccessToken(String refreshToken) {
        try {
            SecretKey secretKey = createSecretKey();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            Long memberId = claims.get("memberId", Long.class);
            return generateAccessToken(memberId);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Refresh Token has expired", e);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid Refresh Token", e);
        }
    }
	
    // HttpHeaders에서 AccessToken 추출
    public String extractAccessTokenFromHeaders(HttpHeaders headers) {
        String bearerToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
        	return null;
        }
    }

    // JWT에서 userId 추출
    private Long getMemberIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("memberId", Long.class);
    }
    
    // JWT의 모든 클레임 추출
    private Claims extractAllClaims(String token) {
        SecretKey secretKey = createSecretKey();
        String pureToken = token.startsWith("Bearer ") ? token.substring(7).trim() : token;

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(pureToken)
                .getBody();
    }
    
    public Long getMemberIdFromJwt(String jwt) {
    	Long memberId = getMemberIdFromToken(jwt);
    	return memberId;
    }

	public Long getAccessTokenExpiry() {
		return ACCESS_TOKEN_EXPIRY;
	}

	public Long getRefreshTokenExpiry() {
		return REFRESH_TOKEN_EXPIRY;
	}


	public boolean validateAccountId(String jwt, Long memberId) {
		Long tokenMemberId = getMemberIdFromJwt(jwt);
		return tokenMemberId == memberId;
	}

	// JWT 토큰의 유효성 검증 메서드
	public boolean validateToken(String token) {
		SecretKey secretKey = createSecretKey(); // SecretKey를 즉석에서 생성
		try {
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
//	public Cookie createAccessTokenCookie(String customAccessToken) {
//	    Cookie accessTokenCookie = new Cookie("accessCookie", customAccessToken);
//        accessTokenCookie.setHttpOnly(true);
//        accessTokenCookie.setSecure(false);	// true : https only 
//        accessTokenCookie.setPath("/");
//        accessTokenCookie.setMaxAge((int) ACCESS_TOKEN_EXPIRY); 
//		return accessTokenCookie;
//	}
//
//	public Cookie createRefreshTokenCookie(String customRefreshToken) {
//        Cookie refreshTokenCookie = new Cookie("refreshCookie", customRefreshToken);
//        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setSecure(false); 
//        refreshTokenCookie.setPath("/");
//        refreshTokenCookie.setMaxAge((int) REFRESH_TOKEN_EXPIRY);
//		return refreshTokenCookie;
//	}
	
	  public void setAccessToken(HttpHeaders headers, String accessToken) {
	  ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
	          .httpOnly(true)
	          .secure(true)  // HTTPS 환경에서는 true로 설정
	          .path("/")
	          .maxAge(ACCESS_TOKEN_EXPIRY) // 1시간
	          .sameSite("None") // CSRF protection
	          .build();
	  headers.add("Set-Cookie", accessTokenCookie.toString());
	}

	public void setRefreshToken(HttpHeaders headers, String jwtRefreshToken) {
	  ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", jwtRefreshToken)
	          .httpOnly(true)
	          .secure(true)  // HTTPS 환경에서는 true로 설정
	          .path("/")
	          .maxAge(REFRESH_TOKEN_EXPIRY) // 쿠키의 유효기간을 refreshToken과 동일하게 설정
	          .sameSite("None")
	//          .sameSite("Lax")
	          .build();
	  headers.add("Set-Cookie", refreshTokenCookie.toString());
	}
	
	  public HttpHeaders expireCookies() {
		  HttpHeaders headers = new HttpHeaders();
		
		  // accessToken 쿠키를 만료시키기
		  ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", "")
		          .httpOnly(true)
		          .secure(true)  // HTTPS 환경에서는 true로 설정
		          .path("/")
		          .maxAge(0)  // 쿠키의 만료 시간을 0으로 설정하여 즉시 만료
		          .sameSite("None")  // 모든 요청에서 쿠키를 전송하도록 설정
		          .build();
		  headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
		
		  // refreshToken 쿠키를 만료시키기
		  ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "")
		          .httpOnly(true)
		          .secure(true)  // HTTPS 환경에서는 true로 설정
		          .path("/")
		          .maxAge(0)  // 쿠키의 만료 시간을 0으로 설정하여 즉시 만료
		          .sameSite("None")  // 모든 요청에서 쿠키를 전송하도록 설정
		          .build();
		  headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
		
		  return headers;
		}
    
}
