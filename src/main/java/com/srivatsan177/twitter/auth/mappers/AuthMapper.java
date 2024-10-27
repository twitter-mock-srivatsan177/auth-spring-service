package com.srivatsan177.twitter.auth.mappers;

import com.srivatsan177.twitter.auth.models.dto.AuthDTO;
import com.srivatsan177.twitter.auth.models.entity.Auth;

/**
 * AuthMapper
 */
public class AuthMapper {
    public static AuthDTO toAuthDTO(Auth auth) {
        return AuthDTO.builder()
                .id(auth.getId())
                .username(auth.getUsername())
                .password(auth.getPassword())
                .email(auth.getEmail())
                .build();
    }

    public static Auth toAuthEntity(AuthDTO authDTO) {
        return Auth.builder()
                .id(authDTO.getId())
                .username(authDTO.getUsername())
                .password(authDTO.getPassword())
                .email(authDTO.getEmail())
                .build();
    }
}
