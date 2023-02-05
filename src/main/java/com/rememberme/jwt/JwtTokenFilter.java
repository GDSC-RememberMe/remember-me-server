package com.rememberme.jwt;

import com.rememberme.jwt.entity.EnumType.JwtStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    public static final String BEARER_HEADER = "Bearer-";

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveToken(request, AUTHORIZATION_HEADER);
        JwtStatus accessTokenStatus = jwtTokenProvider.validateToken(accessToken);

        // 1. accessToken이 유효한 토큰일 경우, SecurityContext에 Authentication으로 저장
        if (accessToken != null && accessTokenStatus == JwtStatus.ACCESS) {
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 2. 만료된 토큰일 경우, refreshToken으로 추가 검증
        else if(accessToken != null && accessTokenStatus == JwtStatus.EXPIRED){
            String refreshToken = resolveToken(request, REFRESH_HEADER);
            JwtStatus refreshTokenStatus = jwtTokenProvider.validateToken(accessToken);
            
            // 2-a) refreshToken이 유효할 경우 -> accessToken와 refreshToken 갱신하여 authentication에 저장하기
            if(refreshToken != null && refreshTokenStatus == JwtStatus.ACCESS){
                String newRefreshToken = jwtTokenProvider.reissueRefreshToken(refreshToken);

                if(newRefreshToken!= null){
                    Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);

                    response.setHeader(AUTHORIZATION_HEADER, BEARER_HEADER + jwtTokenProvider.createAccessToken(authentication));
                    response.setHeader(REFRESH_HEADER, BEARER_HEADER + newRefreshToken);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request, String header) {
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith(BEARER_HEADER)) {
            return bearerToken.substring(BEARER_HEADER.length());
        }
        return null;
    }
}
