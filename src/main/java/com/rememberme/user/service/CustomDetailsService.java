package com.rememberme.user.service;

import com.rememberme.user.entity.User;
import com.rememberme.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 로그인용 - username 으로 검색
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " 아이디의 사용자가 존재하지 않습니다."));

        return createUserDetails(user);
    }

    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        Long findUserId = Long.parseLong(userId);
        User user = userRepository.findById(findUserId)
                .orElseThrow(() -> new UsernameNotFoundException(userId + "해당 사용자가 존재하지 않습니다."));

        return createUserDetails(user);
    }

    private UserDetails createUserDetails(User user) {

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(
                user.getId().toString(), // User 테이블 PK user_id
                user.getPassword(),
                grantedAuthorities);
    }
}