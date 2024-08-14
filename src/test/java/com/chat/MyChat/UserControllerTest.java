package com.chat.MyChat;

import com.chat.MyChat.controller.UserController;
import com.chat.MyChat.dto.RefreshTokenRequest;
import com.chat.MyChat.entity.UserEntity;
import com.chat.MyChat.service.RefreshTokenService;
import com.chat.MyChat.service.UserService;
import com.chat.MyChat.util.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private JwtTokenUtils jwtTokenUtils;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RefreshTokenService refreshTokenService;
    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUserTest() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
        System.out.println(userJson);
    }

    @Test
    void refreshTokenTest() throws Exception {
        String refreshTokenJson = "{\"token\":\"refreshToken\"}";

        when(refreshTokenService.refreshToken("refreshToken")).thenReturn("newToken");

        mockMvc.perform(post("/refreshToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(refreshTokenJson))
                .andExpect(result -> {
                    System.out.println(result.getResponse().getContentAsString());
                });

        verify(refreshTokenService, times(1)).refreshToken("refreshToken");

    }

    @Test
    void logoutTest() throws Exception {
        String refreshTokenJson = "{\"token\":\"refreshToken\"}";

        when(refreshTokenService.deleteRefreshToken("refreshToken")).thenReturn(true);

        mockMvc.perform(post("/customLogout")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(refreshTokenJson))
                .andExpect(status().isOk());

        verify(refreshTokenService, times(1)).deleteRefreshToken("refreshToken");

    }



}
