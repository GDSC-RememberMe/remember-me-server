package com.rememberme.user.service;

import com.rememberme.family.FamilyRepository;
import com.rememberme.family.entity.Family;
import com.rememberme.jwt.JwtTokenProvider;
import com.rememberme.jwt.entity.RefreshToken;
import com.rememberme.jwt.repository.RefreshTokenRepository;
import com.rememberme.user.dto.*;
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
    private final FamilyRepository familyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomDetailsService customDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenDto join(JoinRequestDto joinRequestDto){
        String phone = joinRequestDto.getPhone();
        String username = joinRequestDto.getUsername();
        String nickname = joinRequestDto.getNickname();
        String password = joinRequestDto.getPassword();
        String role = joinRequestDto.getRole();
        String profileImg = joinRequestDto.getProfileImg();
        LocalDate birth = joinRequestDto.getBirth();
        String address = joinRequestDto.getAddress();
        int pushCnt = joinRequestDto.getPushCnt();
        boolean activated = joinRequestDto.isActivated();

        validateDuplicateUser(phone);

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .phone(phone)
                .role(Role.valueOf(role))
                .profileImg(profileImg)
                .birth(birth)
                .address(address)
                .pushCnt(pushCnt)
                .activated(activated)
                .build();

        User savedUser = userRepository.save(user);
        // 환자일 경우, Family 객체 추가 생성
        if (role.equals(Role.PATIENT.toString())) {
            Family family = createFamilyByPatientUser(savedUser.getId());
            user.saveFamily(family);
        }
        return createToken(username, password);
    }

    private void validateDuplicateUser(String username){
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new RuntimeException(user.getUsername() + "해당 아이디의 사용자가 이미 존재합니다.");
                });
    }

    @Transactional
    private Family createFamilyByPatientUser(Long patientId){
        Family family = Family.builder().patientId(patientId).build();
        return familyRepository.save(family);
    }

    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) {

        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        return createToken(username, password);
    }

    private TokenDto createToken(String username, String password) {

        UserDetails userDetails = customDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException(userDetails.getUsername() + "비밀 번호가 잘못 입력되었습니다.");
        }

        Authentication authentication =  new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        // 새로 발급한 RefreshToken을 RefreshTokenRepository에 저장하기
        TokenDto tokenDto = jwtTokenProvider.createTokenDto(authentication);
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(authentication.getName())
                .refreshToken(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }

    // AccessToken 만료 -> 사용자의 RefreshToken 검증 후 재발급하기
    @Transactional
    public TokenDto reissue(TokenDto tokenRequestDto) {
        String accessToken = tokenRequestDto.getAccessToken();
        String resolveAccessToken = resolveToken(accessToken);

        Authentication authentication = jwtTokenProvider.getAuthentication(resolveAccessToken);

        String userId = authentication.getName();
        Long parseLongUserId = Long.parseLong(userId);
        User user = userRepository.findById(parseLongUserId)
                .orElseThrow(() -> new RuntimeException("해당 사용자 정보가 잘못된 토큰입니다."));
        RefreshToken refreshToken  = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자의 refreshToken이 존재하지 않습니다."));

        // 사용자의 리프레시 토큰이랑 디비의 리프레시 토큰이랑 다를 경우
        if (!refreshToken.getRefreshToken().equals(tokenRequestDto.getRefreshToken())) {
            new RuntimeException("잘못된 refreshToken 입니다.");
        }

        // 토큰 재발급, refreshToken은 DB에 갱신해주기
        TokenDto tokenResponseDto = jwtTokenProvider.createTokenDto(authentication);
        refreshToken.updateToken(tokenResponseDto.getRefreshToken());

        return tokenResponseDto;
    }

    private String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(BEARER_HEADER)) {
            return bearerToken.substring(BEARER_HEADER.length());
        }
        return null;
    }

    @Transactional
    public void addProfileImage(Long userId, String profileImg) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 없습니다."));

        user.saveProfileImg(profileImg);
    }

    public UserResponseDto getUserInfoByUserId(Long userId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 없습니다."));

        return new UserResponseDto(user);
    }

    public void validateUsername(UsernameRequestDto usernameRequestDto) {
        String username = usernameRequestDto.getUsername();
        boolean isPresent = userRepository.findByUsername(username).isPresent();
        if (isPresent) {
            throw new RuntimeException("해당 아이디가 이미 존재합니다.");
        }
    }

    public void validatePhone(PhoneRequestDto phoneRequestDto) {
        String phone = phoneRequestDto.getPhone();
        boolean isPresent = userRepository.findByPhone(phone).isPresent();
        if (isPresent) {
            throw new RuntimeException("해당 핸드폰 번호로 가입한 회원이 이미 존재합니다.");
        }
    }
}