package com.rememberme.member.service;

import com.rememberme.family.FamilyRepository;
import com.rememberme.family.entity.Family;
import com.rememberme.jwt.JwtTokenProvider;
import com.rememberme.jwt.entity.RefreshToken;
import com.rememberme.jwt.repository.RefreshTokenRepository;
import com.rememberme.member.dto.*;
import com.rememberme.member.entity.Member;
import com.rememberme.member.entity.enumType.Gender;
import com.rememberme.member.entity.enumType.Role;
import com.rememberme.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    public static final String BEARER_HEADER = "Bearer-";
    private final MemberRepository memberRepository;
    private final FamilyRepository familyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomDetailsService customDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenDto join(JoinRequestDto joinRequestDto) throws ParseException {
        String username = joinRequestDto.getUsername();
        String password = joinRequestDto.getPassword();
        String nickname = joinRequestDto.getNickname();
        String phone = joinRequestDto.getPhone();
        String role = joinRequestDto.getRole();
        String birth = joinRequestDto.getBirth();
        String genderStr = joinRequestDto.getGender();
        String profileImg = joinRequestDto.getProfileImg();
        String address = joinRequestDto.getAddress();
        boolean isPushAgree = joinRequestDto.isPushAgree();
        int pushCnt = joinRequestDto.getPushCnt();
        boolean activated = joinRequestDto.isActivated();

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .phone(phone)
                .role(Role.valueOf(role))
                .profileImg(profileImg)
                .birth(parseLocalDateByBirth(birth))
                .gender(Gender.valueOf(genderStr))
                .address(address)
                .isPushAgree(isPushAgree)
                .pushCnt(pushCnt)
                .activated(activated)
                .build();

        log.info("Member : {} {}", member.getGender(), member.getRole());

        Member savedMember = memberRepository.save(member);
        // 환자일 경우, Family 객체 추가 생성
        if (role.equals(Role.PATIENT.toString())) {
            Family family = createFamilyByPatientUser(savedMember.getId());
            member.saveFamily(family);
        }
        return createToken(username, password);
    }

    private LocalDate parseLocalDateByBirth (String birth) throws ParseException {
        LocalDate date = LocalDate.parse(birth, DateTimeFormatter.ISO_DATE);
        return date;
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

        Optional<RefreshToken> refreshToken  = refreshTokenRepository.findByUserId(authentication.getName());
        if(refreshToken.isPresent()) {
            String updateRefreshToken = tokenDto.getRefreshToken();
            refreshToken.get().updateToken(updateRefreshToken);
        } else {
            RefreshToken newToken = RefreshToken.builder()
                    .userId(authentication.getName())
                    .token(tokenDto.getRefreshToken())
                    .build();
            refreshTokenRepository.save(newToken);
        }
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
        Member member = memberRepository.findById(parseLongUserId)
                .orElseThrow(() -> new RuntimeException("해당 사용자 정보가 잘못된 토큰입니다."));
        RefreshToken refreshToken  = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자의 refreshToken이 존재하지 않습니다."));

        // 사용자의 리프레시 토큰이랑 디비의 리프레시 토큰이랑 다를 경우
        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken())) {
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
        Member member =  memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 없습니다."));

        member.saveProfileImg(profileImg);
    }

    public MemberResponseDto getUserInfoByUserId(Long userId) {
        Member member =  memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 없습니다."));

        return new MemberResponseDto(member);
    }

    public void validateUsername(UsernameRequestDto usernameRequestDto) {
        String username = usernameRequestDto.getUsername();
        boolean isPresent = memberRepository.findByUsername(username).isPresent();
        if (isPresent) {
            throw new IllegalArgumentException("해당 아이디가 이미 존재합니다.");
        }
    }

    public void validatePhone(PhoneRequestDto phoneRequestDto) {
        String phone = phoneRequestDto.getPhone();
        boolean isPresent = memberRepository.findByPhone(phone).isPresent();
        if (isPresent) {
            throw new IllegalArgumentException("해당 핸드폰 번호로 가입한 회원이 이미 존재합니다.");
        }
    }
}