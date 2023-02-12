package com.rememberme.jwt;

import com.rememberme.jwt.entity.EnumType.JwtStatus;
import com.rememberme.jwt.entity.RefreshToken;
import com.rememberme.jwt.repository.RefreshTokenRepository;
import com.rememberme.user.service.CustomDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider implements InitializingBean {

    private final CustomDetailsService customDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final String secretKey;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    private Key key;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey,
                            @Value("${jwt.access-token-validity-in-ms}") long accessTokenValidity,
                            @Value("${jwt.refresh-token-validity-in-ms}") long refreshTokenValidity,
                            CustomDetailsService customDetailsService,
                            RefreshTokenRepository refreshTokenRepository){
        this.secretKey = secretKey;
        this.accessTokenValidity = accessTokenValidity * 1000;
        this.refreshTokenValidity = refreshTokenValidity * 1000;
        this.customDetailsService = customDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        UserDetails userDetails = customDetailsService.loadUserByUsername(claims.getSubject()); // username
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    public String createAccessToken(Authentication authentication) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public JwtStatus validateToken(String token) {
        try { Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return JwtStatus.ACCESS;

        } catch (ExpiredJwtException e){
            return JwtStatus.EXPIRED;

        } catch (JwtException | IllegalArgumentException e) {
            return JwtStatus.DENIED;
        }
    }

    @Transactional
    public String issueRefreshToken(Authentication authentication){
        String newToken = createRefreshToken(authentication);

        refreshTokenRepository.findByUserId(authentication.getName())
                .ifPresentOrElse(t -> { t.updateToken(newToken);},
                        () -> {
                            String userId = authentication.getName();
                            RefreshToken token = RefreshToken.createToken(userId, newToken);
                            refreshTokenRepository.save(token);
                        });

        return newToken;
    }

    private String createRefreshToken(Authentication authentication){
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidity);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    @Transactional
    public String reissueRefreshToken(String refreshToken) throws RuntimeException{

        Authentication authentication = getAuthentication(refreshToken);
        RefreshToken findRefreshToken = refreshTokenRepository.findByUserId(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(authentication.getName() + "번호의 사용자가 없습니다."));

        if(findRefreshToken.getRefreshToken().equals(refreshToken)){

            String newRefreshToken = createRefreshToken(authentication);
            findRefreshToken.updateToken(newRefreshToken);
            return newRefreshToken;
        }
        return null;
    }
}