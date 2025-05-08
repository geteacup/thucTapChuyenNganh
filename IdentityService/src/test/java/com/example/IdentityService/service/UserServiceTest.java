//package com.example.IdentityService.service;
//
//import java.time.LocalDate;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import com.example.IdentityService.dto.request.UserCreationRequest;
//import com.example.IdentityService.dto.response.UserResponse;
//import com.example.IdentityService.entity.User;
//import com.example.IdentityService.repository.UserRepository;
//
//import lombok.extern.slf4j.Slf4j;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//@Slf4j
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserServiceTest {
//    @Autowired
//    private UserService userService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    private UserCreationRequest request;
//    private UserResponse response;
//    private User user;
//    private LocalDate dob;
//
//    @BeforeEach
//    public void initData() {
//        dob = LocalDate.of(2000, 1, 1);
//        request = UserCreationRequest.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .username("somedieyoung333")
//                .password("admin333")
//                .birthDate(dob)
//                .build();
//        response = UserResponse.builder()
//                .id("f79d6b5c47cc")
//                .firstName("John")
//                .lastName("Doe")
//                .username("somedieyoung333")
//                .password("admin333")
//                .birthDate(dob)
//                .build();
//
//        user = User.builder()
//                .id("f79d6b5c47cc")
//                .firstName("John")
//                .lastName("Doe")
//                .username("somedieyoung333")
//                .birthDate(dob)
//                .build();
//    }
//
//    @Test
//    void createUser_validRequest_Success() {
//        // Given
//        when(userRepository.existsByUsername(anyString()))
//                .thenReturn(false);
//        when(userRepository.save(any())).thenReturn(user);
//
//        // When
//        var response = userService.createUser(request);
//
//        // then
//        Assertions.assertThat(response.getId()).isEqualTo("f79d6b5c47cc");
//        Assertions.assertThat(response.getUsername()).isEqualTo("somedieyoung333");
//    }
//    @Test
//    void createUser_validRequest_Success() {
//        // Given
//        when(userRepository.existsByUsername(anyString()))
//                .thenReturn(false);
//        when(userRepository.save(any())).thenReturn(user);
//
//        // When
//        var response = userService.createUser(request);
//
//        // then
//        Assertions.assertThat(response.getId()).isEqualTo("f79d6b5c47cc");
//        Assertions.assertThat(response.getUsername()).isEqualTo("somedieyoung333");
//    }
//}
