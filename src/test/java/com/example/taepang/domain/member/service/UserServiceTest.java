package com.example.taepang.domain.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.taepang.domain.member.dto.reqDto.CreateUserReqDto;
import com.example.taepang.domain.member.dto.reqDto.ModifyUserReqDto;
import com.example.taepang.domain.member.dto.resDto.CreateUserResDto;
import com.example.taepang.domain.member.dto.resDto.FindUserResDto;
import com.example.taepang.domain.member.dto.resDto.UpdateUserResDto;
import com.example.taepang.domain.member.entity.User;
import com.example.taepang.domain.member.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private User mockUser; // 조회를 위함

	// ==================== createUser ====================

	@Test
	@DisplayName("성공 : 새로운 회원 생성")
	void 유저_생성_성공() {

		// given
		CreateUserReqDto reqDto = CreateUserReqDto.builder()
			.username("taewoo")
			.email("wootaepark@naver.com")
			.phoneNumber("010-1234-5678")
			.build();

		// 가독성을 높이기위해 BDDMockito 를 활용하였다.
		given(userRepository.existsByEmail(reqDto.getEmail())).willReturn(false);

		// when
		CreateUserResDto response = userService.createUser(reqDto);

		// then
		assertThat(response).isNotNull();
		then(userRepository).should(times(1)).existsByEmail(reqDto.getEmail());
		then(userRepository).should(times(1)).save(any(User.class));
	}

	@Test
	@DisplayName("예외 : 이미 존재하는 이메일이면 IllegalArgumentException 이 발생한다.")
	void 유저_중복_생성_예외처리() {

		// given
		CreateUserReqDto reqDto = CreateUserReqDto.builder() // 중복 코드 제거 가능 (@Nested)
			.username("taewoo")
			.email("wootaepark@naver.com")
			.phoneNumber("010-1234-5678")
			.build();

		given(userRepository.existsByEmail(reqDto.getEmail())).willReturn(true);

		// when & then
		assertThatThrownBy(() -> userService.createUser(reqDto))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("이미 존재하는 이메일 입니다.");

		then(userRepository).should(never()).save(any(User.class));

	}

	// ==================== findUser ====================

	@Test
	@DisplayName("성공 : ID로 회원 조회가 성공한다.")
	void 유저_조회_성공() {

		// given
		Long userId = 1L;
		given(userRepository.findByIdAndDeletedAtIsNull(userId)).willReturn(Optional.of(mockUser));

		// when
		FindUserResDto response = userService.findUser(userId);

		// then
		assertThat(response).isNotNull();
		then(userRepository).should(times(1)).findByIdAndDeletedAtIsNull(userId);
	}

	@Test
	@DisplayName("예외: 존재하지 않거나 삭제된 회원이면 IllegalArgumentException 이 발생한다.")
	void 유저_조회_예외처리() {

		// given
		Long userId = 999L;
		given(userRepository.findByIdAndDeletedAtIsNull(userId)).willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> userService.findUser(userId))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("사용자를 찾을 수 없습니다. ID : " + userId);

	}

	// ==================== modifyUser ====================

	@Test
	@DisplayName("성공 : 회원 정보 수정 정상 수행")
	void 유저_정보_수정() {

		// given
		Long userId = 1L;
		User realUser = User.builder()
			.username("박태우")
			.phoneNumber("010-1234-5678")
			.email("wootaepark@naver.com")
			.build();

		given(userRepository.findByIdAndDeletedAtIsNull(userId)).willReturn(Optional.of(realUser));

		ModifyUserReqDto reqDto = ModifyUserReqDto.builder()
			.phoneNumber("010-1111-2222")
			.build();

		// when
		UpdateUserResDto response = userService.modifyUser(userId, reqDto);

		// then
		assertThat(realUser.getPhoneNumber()).isEqualTo("010-1111-2222");
		assertThat(response).isNotNull();
	}

	@Test
	@DisplayName("성공 : 소프트 delete 메서드가 실행된다.")
	void 유저_삭제() {

		// given
		Long userId = 1L;
		User realUser = User.builder()
			.username("박태우")
			.phoneNumber("010-1234-5678")
			.email("wootaepark@naver.com")
			.build();
		given(userRepository.findByIdAndDeletedAtIsNull(userId)).willReturn(Optional.of(realUser));

		// when
		userService.removeUser(userId);

		// then
		assertThat(realUser.getDeletedAt()).isNotNull(); // deletedAt 이 업데이트 되었는지

		// 아래는 mock 객체만 사용가능한 BDDMock
		// then(mockUser).should(times(1)).delete();
	}
}
