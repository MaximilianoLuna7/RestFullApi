package com.maxiluna.studentmanagement.presentation.dtos.auth;

import com.maxiluna.studentmanagement.presentation.dtos.user.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String message;
    private String userStatus;
    private LocalDateTime expirationToken;
    private UserInfoDto userInfo;
}
