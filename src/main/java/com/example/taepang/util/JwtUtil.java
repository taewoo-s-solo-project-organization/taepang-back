package com.example.taepang.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	// 헤더 KEY 값 설정
	public static final String AUTHORIZATION_HEADER = "Authorization";
	// 사용자 권한 값의 KEY
	public static final String AUTHORIZATION_KEY = "auth";
	// Token 식별자
	public static final String BEARER_PREFIX = "Bearer ";
	// 로그 설정
	public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");
	// 토큰 만료 시간
	private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분
	@Value("${jwt.secret.key}")
	private String secretKey;
	private Key key;

	@PostConstruct
	public void init() {
		// base64 디코딩 후 키 생성
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
	}

	// 토큰 생성
	public String createToken(Long userId, String email) { // 일단 권한은 생략
		return BEARER_PREFIX + Jwts.builder()
			.subject(String.valueOf(userId)) // subject 는 식별자 느낌으로 1개씩만 존재
			// .claim(AUTHORIZATION_KEY, role) // claim 은 토큰에 담긴 정보
			.claim("email", email)
			.issuedAt(new Date()) // 발급일
			.expiration(new Date(System.currentTimeMillis() + TOKEN_TIME))
			.signWith(key) // 암호화 알고리즘 자동으로 HS256/HS384/HS512 중 맞춰서 적용 (0.12 버전 이후)
			.compact();

	}

	private SecretKey getSigningKey() {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// 토큰 검증 (요청과 함께 헤더에서 온 값을 검증할 때 사용)
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();

			return true;

		} catch (SecurityException | MalformedJwtException e) {
			logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token, + 만료된 JWT 토큰 입니다.");
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims is empty, 잘못된 JWT 토큰입니다.");
		}

		return false;
	}

}
