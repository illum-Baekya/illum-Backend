package com.gdg.illum.user.service;

import com.gdg.illum.user.domain.User;
import com.gdg.illum.user.dto.AccessTokenDto;
import com.gdg.illum.user.dto.TokenDto;
import com.gdg.illum.user.dto.UserSignInRequestDto;
import com.gdg.illum.user.dto.UserSignUpRequestDto;
import com.gdg.illum.user.jwt.TokenProvider;
import com.gdg.illum.user.jwt.TokenType;
import com.gdg.illum.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public TokenDto signUp(UserSignUpRequestDto requestDto) {
        User user = userRepository.save(User.builder()
                .loginId(requestDto.getLoginId())
                .password(requestDto.getPassword())
                .build()
        );

        String accessToken = tokenProvider.createToken(user, TokenType.ACCESS);
        String refreshToken = tokenProvider.createToken(user, TokenType.REFRESH);
        user.getRefreshToken().setTokenValue(refreshToken);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenDto signIn(UserSignInRequestDto requestDto) {
        User user = userRepository.findUserByLoginIdAndPassword(requestDto.getLoginId(), requestDto.getPassword())
                .orElseThrow(() -> new RuntimeException("유저 조회 실패"));

        String accessToken = tokenProvider.createToken(user, TokenType.ACCESS);
        String refreshToken = tokenProvider.createToken(user, TokenType.REFRESH);
        user.getRefreshToken().setTokenValue(refreshToken);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 조회 실패"));
        user.getRefreshToken().setTokenValue(null);
    }

    public void withdraw(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 조회 실패"));
        userRepository.delete(user);
    }

    public AccessTokenDto reissueAccessToken(String refreshToken) {
        long userId = Long.parseLong(tokenProvider.parseClaims(refreshToken).getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 조회 실패"));

        if (!user.getRefreshToken().getTokenValue().equals(refreshToken)) {
            throw new RuntimeException("부적절한 토큰");
        }

        return AccessTokenDto.builder()
                .accessToken(tokenProvider.createToken(user, TokenType.ACCESS))
                .build();
    }
}
