package com.example.taepang.util;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	// 헤더 KEY 값 설정
	public static final String AUTHORIZATION_HEADER = "Authorization";
	// 사용자 권한 값의 KEY
	public static final String AUTHORIZATION_KEY = "auth";
	// Token 식별자
	public static final String BEARER_PREFIX = "Bearer ";
	// 토큰 만료 시간
	private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분
	@Value("${jwt.secret.key}")
	private String secretKey;
	private SecretKey key;

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
			.signWith(key)
			.compact();

	}

	private SecretKey getSigningKey() {
		return this.key;
	}

	// 토큰 검증 (요청과 함께 헤더에서 온 값을 검증할 때 사용)
	public void validateToken(String token) {
		try {
			Jwts.parser().verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();

		} catch (SecurityException | MalformedJwtException e) {
			log.error("Invalid JWT signature, 유효하지 않는 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token, + 만료된 JWT 토큰 입니다.");
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Expired JWT token, 만료된 JWT token 입니다.");
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
		} catch (IllegalArgumentException e) {
			log.error("JWT claims is empty, 잘못된 JWT 토큰입니다.");
		}
	}

	public String substringToken(String tokenValue) {
		if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
			return tokenValue.substring(BEARER_PREFIX.length());
		}
		throw new IllegalArgumentException("토큰이 존재하지 않거나 유효하지 않은 형식입니다.");
	}

	public Claims getMemberClaims(String token) {
		return Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
