package com.ssafy.TmT.global.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import com.ssafy.TmT.global.security.jwt.exception.InvalidTokenException;
import com.ssafy.TmT.global.security.jwt.exception.TokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

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
    public String generateAccessToken(Long userId) {
        SecretKey secretKey = createSecretKey(); // SecretKey를 즉석에서 생성
        long now = System.currentTimeMillis();
        
        return Jwts.builder()
                .claim("userId", userId) // 사용자 ID를 클레임으로 추가
                .setIssuedAt(new Date(now)) // 토큰 발행 시간
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRY)) // 토큰 만료 시간
                .signWith(secretKey,SignatureAlgorithm.HS256) // 시그니처 알고리즘과 SecretKey 지정
                .compact();
    }
	
	
    // RefreshToken 생성 메서드
    public String generateRefreshToken(Long userId) {
        SecretKey secretKey = createSecretKey();
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .claim("userId", userId)
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

            Long userId = claims.get("userId", Long.class);
            return generateAccessToken(userId);
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
    private Long getUserIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);
    }
    
    // JWT의 모든 클레임 추출
    private Claims extractAllClaims(String token) {
        SecretKey secretKey = createSecretKey();
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public Long getMemberIdFromHeaders(HttpHeaders headers) {
    	String accessToken = extractAccessTokenFromHeaders(headers);
    	Long userId = getUserIdFromToken(accessToken);
    	return userId;
    }
    
}
