package com.srivatsan177.twitter.auth.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srivatsan177.twitter.auth.exceptions.RestException;
import com.srivatsan177.twitter.auth.models.dto.AuthDTO;
import com.srivatsan177.twitter.auth.models.dto.AuthRefreshRequestDTO;
import com.srivatsan177.twitter.auth.models.dto.AuthTokensDTO;
import com.srivatsan177.twitter.auth.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("create")
    public AuthTokensDTO create(@Valid @RequestBody AuthDTO authDTO) {
        return authService.register(authDTO);
    }

    @PostMapping("login")
    public AuthTokensDTO login(@Valid @RequestBody AuthDTO authDTO) throws RestException {
        return authService.login(authDTO);
    }

    @PostMapping("refresh")
    public AuthTokensDTO refresh(@Valid @RequestBody AuthRefreshRequestDTO authRefreshRequestDTO)
            throws RestException {
        return authService.refresh(authRefreshRequestDTO.getRefreshToken());
    }

}
