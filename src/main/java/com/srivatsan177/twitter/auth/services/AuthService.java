package com.srivatsan177.twitter.auth.services;

import org.springframework.stereotype.Service;

import com.srivatsan177.twitter.auth.exceptions.RestException;
import com.srivatsan177.twitter.auth.models.dto.AuthDTO;
import com.srivatsan177.twitter.auth.models.dto.AuthTokensDTO;

@Service
public interface AuthService {
    AuthTokensDTO login(AuthDTO authDTO) throws RestException;

    AuthTokensDTO refresh(String refreshToken) throws RestException;

    AuthTokensDTO register(AuthDTO authDTO);

    // TODO: Add reset password
}
