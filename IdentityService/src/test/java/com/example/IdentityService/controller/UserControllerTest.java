//package com.example.IdentityService.controller;
//
//import java.time.LocalDate;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import com.example.IdentityService.dto.request.UserCreationRequest;
//import com.example.IdentityService.dto.response.UserResponse;
//import com.example.IdentityService.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private UserService userService;
//
//    private UserCreationRequest request;
//    private UserCreationRequest request2;
//    private UserResponse response;
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
//                .id("8f8e233c00e3")
//                .firstName("John")
//                .lastName("Doe")
//                .username("somedieyoung333")
//                .password("admin333")
//                .birthDate(dob)
//                .build();
//    }
//
//    @Test
//    void createUser_validRequest_success() throws Exception {
//        // GIVEN
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        String content = mapper.writeValueAsString(request);
//
//        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(response);
//        // WHEN
//
//        mvc.perform(MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(content))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"))
//                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value(response.getId()));
//
//        ;
//        // THEN
//    }
//    @Test
//    void createUser_invalidUsername_fail() throws Exception {
//        // GIVEN
//        request.setUsername("di");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        String content = mapper.writeValueAsString(request);
//
//        // WHEN
//
//        mvc.perform(MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(content))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1003"))
//                .andExpect(MockMvcResultMatchers.jsonPath("message").value("username must be at least 5 characters"));
//
//        ;
//        // THEN
//    }
//    @Test
//    void createUser_invalidPassword_fail() throws Exception {
//        request.setPassword("admin");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        String content = mapper.writeValueAsString(request);
//
//        // WHEN
//
//        mvc.perform(MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(content))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1004"))
//                .andExpect(MockMvcResultMatchers.jsonPath("message").value("password must be at least 8 characters"));
//
//        ;
//    }
//}
