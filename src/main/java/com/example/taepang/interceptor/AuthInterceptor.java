package com.example.taepang.interceptor;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.taepang.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private final JwtUtil jwtUtil;

	@Override
	public boolean preHandle(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull Object handler) throws
		RuntimeException {
		try {
			String headerToken = request.getHeader(AUTHORIZATION_HEADER);
			String accessToken = Optional.ofNullable(jwtUtil.substringToken(headerToken))
				.orElseThrow();
			this.jwtUtil.validateToken(accessToken);
			return true;
		} catch (RuntimeException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 없습니다.");
		}

	}
}
