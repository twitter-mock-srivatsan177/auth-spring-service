package com.srivatsan177.twitter.auth.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDTO {
    private String id;

    @NotNull(message = "username cannot be blank")
    private String username;

    @NotNull(message = "password cannot be blank")
    private String password;

    private String email;
}
