package com.srivatsan177.twitter.auth.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRefreshRequestDTO {
    @NotBlank(message = "Refresh token cannot be blank")
    private String refreshToken;
}
