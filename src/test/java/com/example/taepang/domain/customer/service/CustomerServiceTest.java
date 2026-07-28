package com.example.taepang.domain.customer.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.taepang.domain.customer.dto.reqDto.ModifyCustomerReqDto;
import com.example.taepang.domain.customer.dto.resDto.FindCustomerResDto;
import com.example.taepang.domain.customer.dto.resDto.UpdateCustomerResDto;
import com.example.taepang.domain.customer.entity.Customer;
import com.example.taepang.domain.customer.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private Customer mockCustomer; // 조회를 위함

	// ==================== createUser ====================

	@Test
	@DisplayName("성공 : 새로운 회원 생성")
	void 유저_생성_성공() {

		// given
		// CreateCustomerReqDto reqDto = CreateCustomerReqDto.builder()
		// 	.username("taewoo")
		// 	.email("wootaepark@naver.com")
		// 	.phoneNumber("010-1234-5678")
		// 	.build();
		//
		// // 가독성을 높이기위해 BDDMockito 를 활용하였다.
		// given(customerRepository.existsByEmail(reqDto.getEmail())).willReturn(false);
		//
		// // when
		// CreateCustomerResDto response = customerService.createUser(reqDto);
		//
		// // then
		// assertThat(response).isNotNull();
		// then(customerRepository).should(times(1)).existsByEmail(reqDto.getEmail());
		// then(customerRepository).should(times(1)).save(any(Customer.class));
	}

	@Test
	@DisplayName("예외 : 이미 존재하는 이메일이면 IllegalArgumentException 이 발생한다.")
	void 유저_중복_생성_예외처리() {

		// // given
		// CreateCustomerReqDto reqDto = CreateCustomerReqDto.builder() // 중복 코드 제거 가능 (@Nested)
		// 	.username("taewoo")
		// 	.email("wootaepark@naver.com")
		// 	.phoneNumber("010-1234-5678")
		// 	.build();
		//
		// given(customerRepository.existsByEmail(reqDto.getEmail())).willReturn(true);
		//
		// // when & then
		// assertThatThrownBy(() -> customerService.createUser(reqDto))
		// 	.isInstanceOf(IllegalArgumentException.class)
		// 	.hasMessage("이미 존재하는 이메일 입니다.");
		//
		// then(customerRepository).should(never()).save(any(Customer.class));

	}

	// ==================== findUser ====================

	@Test
	@DisplayName("성공 : ID로 회원 조회가 성공한다.")
	void 유저_조회_성공() {

		// given
		Long customerId = 1L;
		given(customerRepository.findByIdAndDeletedAtIsNull(customerId)).willReturn(Optional.of(mockCustomer));

		// when
		FindCustomerResDto response = customerService.findUser(customerId);

		// then
		assertThat(response).isNotNull();
		then(customerRepository).should(times(1)).findByIdAndDeletedAtIsNull(customerId);
	}

	@Test
	@DisplayName("예외: 존재하지 않거나 삭제된 회원이면 IllegalArgumentException 이 발생한다.")
	void 유저_조회_예외처리() {

		// given
		Long customer = 999L;
		given(customerRepository.findByIdAndDeletedAtIsNull(customer)).willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> customerService.findUser(customer))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("사용자를 찾을 수 없습니다. ID : " + customer);

	}

	// ==================== modifyUser ====================

	@Test
	@DisplayName("성공 : 회원 정보 수정 정상 수행")
	void 유저_정보_수정() {

		// given
		Long customer = 1L;
		Customer realCustomer = Customer.builder()
			.username("박태우")
			.phoneNumber("010-1234-5678")
			.email("wootaepark@naver.com")
			.build();

		given(customerRepository.findByIdAndDeletedAtIsNull(customer)).willReturn(Optional.of(realCustomer));

		ModifyCustomerReqDto reqDto = ModifyCustomerReqDto.builder()
			.phoneNumber("010-1111-2222")
			.build();

		// when
		UpdateCustomerResDto response = customerService.modifyUser(customer, reqDto);

		// then
		assertThat(realCustomer.getPhoneNumber()).isEqualTo("010-1111-2222");
		assertThat(response).isNotNull();
	}

	// ==================== deleteUser ====================

	@Test
	@DisplayName("성공 : 소프트 delete 메서드가 실행된다.")
	void 유저_삭제() {

		// given
		Long customer = 1L;
		Customer realCustomer = Customer.builder()
			.username("박태우")
			.phoneNumber("010-1234-5678")
			.email("wootaepark@naver.com")
			.build();
		given(customerRepository.findByIdAndDeletedAtIsNull(customer)).willReturn(Optional.of(realCustomer));

		// when
		customerService.removeUser(customer);

		// then
		assertThat(realCustomer.getDeletedAt()).isNotNull(); // deletedAt 이 업데이트 되었는지

		// 아래는 mock 객체만 사용가능한 BDDMock
		// then(mockUser).should(times(1)).delete();
	}
}
