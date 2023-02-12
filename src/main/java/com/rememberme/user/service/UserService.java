package com.rememberme.user.service;

import com.rememberme.jwt.JwtTokenProvider;
import com.rememberme.user.dto.JoinRequestDto;
import com.rememberme.user.dto.LoginRequestDto;
import com.rememberme.user.dto.TokenDto;
import com.rememberme.user.dto.UserResponseDto;
import com.rememberme.user.entity.User;
import com.rememberme.user.entity.enumType.Role;
import com.rememberme.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service

@RequiredArgsConstructor
public class UserService {

    public static final String BEARER_HEADER = "Bearer-";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomDetailsService customDetailsService;

    @Transactional
    public UserResponseDto join(JoinRequestDto joinRequestDto){
        String phone = joinRequestDto.getPhone();
        String username = joinRequestDto.getUsername();
        String password = joinRequestDto.getPassword();
        String role = joinRequestDto.getRole();
        String profileImg = joinRequestDto.getProfileImg();
        LocalDate birth = joinRequestDto.getBirth();
        String address = joinRequestDto.getAddress();
        int pushCnt = joinRequestDto.getPushCnt();
        boolean activated = joinRequestDto.isActivated();

        validateDuplicateUser(phone);

        User user = User.builder()
                .phone(phone)
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.valueOf(role))
                .profileImg(profileImg)
                .birth(birth)
                .address(address)
                .pushCnt(pushCnt)
                .activated(activated)
                .build();

        userRepository.save(user);
        return new UserResponseDto(user);

    }

    @Transactional()
    public TokenDto login(LoginRequestDto loginRequestDto) {

        String phone = loginRequestDto.getPhone();
        String password = loginRequestDto.getPassword();

        UserDetails userDetails = customDetailsService.loadUserByUsername(phone);

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
        }

        Authentication authentication =  new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        return new TokenDto(
                BEARER_HEADER + jwtTokenProvider.createAccessToken(authentication),
                BEARER_HEADER + jwtTokenProvider.issueRefreshToken(authentication));
    }

    private void validateDuplicateUser(String phone){
        userRepository.findByPhone(phone)
                .ifPresent(user -> {
                    throw new RuntimeException(user.getPhone() + "번호의 사용자가 이미 존재합니다.");
                });
    }

    public UserResponseDto getUserInfoByPhone(String phone) {
        User user =  userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 없습니다."));

        return new UserResponseDto(user);
    }

    @Transactional
    public void addProfileImage(Long userId, String profileImg) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 없습니다."));

        user.saveProfileImg(profileImg);
    }
}