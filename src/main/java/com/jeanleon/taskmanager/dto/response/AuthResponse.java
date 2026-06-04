// AuthResponse.java
package com.jeanleon.taskmanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private String userId;
    private String fullName;
    private String email;
}