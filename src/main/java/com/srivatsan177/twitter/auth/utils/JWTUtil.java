package com.srivatsan177.twitter.auth.utils;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.srivatsan177.twitter.auth.exceptions.AuthorizationException;
import com.srivatsan177.twitter.auth.exceptions.RestException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JWTUtil {
    private final String SECRET;
    private final String REFRESH;

    private final int JWT_EXPIRY = 300000;
    private final int REFRESH_EXPIRY = 28800000;

    public JWTUtil(@Value("${jwt.secret}") String secret,
            @Value("${jwt.refresh}") String refresh) {
        this.SECRET = secret;
        this.REFRESH = refresh;
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    private SecretKey getRefreshKey() {
        return Keys.hmacShaKeyFor(REFRESH.getBytes());
    }

    public String generateJWT(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .setExpiration(new java.util.Date(System.currentTimeMillis() + JWT_EXPIRY))
                .compact();
    }

    public String generateRefreshJWT(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(getRefreshKey(), SignatureAlgorithm.HS256)
                .setExpiration(new java.util.Date(System.currentTimeMillis() + REFRESH_EXPIRY))
                .compact();
    }

    public Jws<Claims> parseJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token);
    }

    public Jws<Claims> parseRefreshJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getRefreshKey())
                .build()
                .parseClaimsJws(token);
    }

    public String getUsernameFromJWT(String token) throws RestException {
        try {
            String username = parseJWT(token).getBody().getSubject();
            log.info(String.format("JWT - %s, Username - %s", token, username));
            return username;
        } catch (ExpiredJwtException e) {
            log.error(token, e);
            throw new AuthorizationException("Token Expired");
        } catch (JwtException e) {
            log.error(token, e);
            throw new AuthorizationException("Invalid Token");
        } catch (Exception e) {
            log.error(token, e);
            throw new RestException("Request cannot be processed at the moment");
        }
    }

    public String getUsernameFromRefreshJWT(String token) throws RestException {
        try {
            String username = parseRefreshJWT(token).getBody().getSubject();
            log.info(String.format("JWT - %s, Username - %s", token, username));
            return username;
        } catch (ExpiredJwtException e) {
            log.error(token, e);
            throw new AuthorizationException("Token Expired");
        } catch (JwtException e) {
            log.error(token, e);
            throw new AuthorizationException("Invalid Token");
        } catch (Exception e) {
            log.error(token, e);
            throw new RestException("Request cannot be processed at the moment");
        }
    }
}
