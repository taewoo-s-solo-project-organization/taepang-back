package com.example.taepang.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.example.taepang.domain.auth.dto.VerifiedMember;
import com.example.taepang.util.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private final JwtUtil jwtUtil;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(VerifiedMember.class);
	}

	@Override // 나중에 VerifiedMember 의 정보를 가져와서 db 조회와 같이 사용할 수 있다.
	public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		assert request != null; // 아래 getHeader 의 NullPointerException 처리 위함
		String headerToken = request.getHeader(AUTHORIZATION_HEADER);
		String token = jwtUtil.substringToken(headerToken);
		Claims memberClaims = jwtUtil.getMemberClaims(token);
		Long memberId = Long.parseLong(memberClaims.getSubject());
		String email = memberClaims.get("email").toString();
		return VerifiedMember.builder()
			.id(memberId)
			.email(email)
			.build();
	}
}
