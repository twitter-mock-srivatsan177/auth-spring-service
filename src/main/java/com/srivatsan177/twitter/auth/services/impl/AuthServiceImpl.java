package com.srivatsan177.twitter.auth.services.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.srivatsan177.twitter.auth.exceptions.BadRequestRestException;
import com.srivatsan177.twitter.auth.exceptions.RestException;
import com.srivatsan177.twitter.auth.mappers.AuthMapper;
import com.srivatsan177.twitter.auth.models.dto.AuthDTO;
import com.srivatsan177.twitter.auth.models.dto.AuthTokensDTO;
import com.srivatsan177.twitter.auth.models.entity.Auth;
import com.srivatsan177.twitter.auth.repositories.AuthRepository;
import com.srivatsan177.twitter.auth.services.AuthService;
import com.srivatsan177.twitter.auth.utils.HashUtil;
import com.srivatsan177.twitter.auth.utils.JWTUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final HashUtil hashUtil;

    private final AuthRepository authRepository;

    private final JWTUtil jwtUtil;

    @Override
    public AuthTokensDTO login(AuthDTO authDTO) throws RestException {
        Auth auth = authRepository.findByUsername(authDTO.getUsername()).orElseThrow(
                () -> new BadRequestRestException("Invalid username or password"));
        if (!hashUtil.checkPassword(authDTO.getPassword(), auth.getPassword())) {
            throw new BadRequestRestException("Invalid username or password");
        }

        return AuthTokensDTO.builder()
                .accessToken(getAccessToken(authDTO.getUsername()))
                .refreshToken(getRefreshToken(authDTO.getUsername()))
                .build();
    }

    @Override
    public AuthTokensDTO refresh(String refreshToken) throws RestException {
        String username = jwtUtil.getUsernameFromRefreshJWT(refreshToken);

        return AuthTokensDTO.builder()
                .accessToken(getAccessToken(username))
                .refreshToken(getRefreshToken(username))
                .build();
    }

    @Override
    @Transactional
    public AuthTokensDTO register(AuthDTO authDTO) {
        this.hashAuthCredentials(authDTO);
        Auth auth = AuthMapper.toAuthEntity(authDTO);
        Auth savedAuth = authRepository.save(auth);
        AuthDTO saveAuthDTO = AuthMapper.toAuthDTO(savedAuth);

        return AuthTokensDTO.builder()
                .accessToken(getAccessToken(saveAuthDTO.getUsername()))
                .refreshToken(getRefreshToken(saveAuthDTO.getUsername()))
                .build();
    }

    private void hashAuthCredentials(AuthDTO authDTO) {
        authDTO.setPassword(hashUtil.hashPassword(authDTO.getPassword()));
    }

    private String getAccessToken(String username) {
        return jwtUtil.generateJWT(username);
    }

    private String getRefreshToken(String username) {
        return jwtUtil.generateRefreshJWT(username);
    }

}
