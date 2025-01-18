package com.gdg.illum.user.service;

import com.gdg.illum.user.domain.User;
import com.gdg.illum.user.dto.UpdatedUserDto;
import com.gdg.illum.user.dto.UserInfoDto;
import com.gdg.illum.user.dto.UserSignupReqDto;
import com.gdg.illum.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserInfoDto signUp(UserSignupReqDto userSignupReqDto) {
        if (userRepository.findByEmail(userSignupReqDto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .username(userSignupReqDto.getUsername())
                .password(passwordEncoder.encode(userSignupReqDto.getPassword()))
                .email(userSignupReqDto.getEmail())
                .build();

        userRepository.save(user);

        return UserInfoDto.from(user);
    }

    @Transactional
    public UserInfoDto updateUser(Principal principal, UpdatedUserDto updatedUserDto) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        if (updatedUserDto.getUsername() != null && !updatedUserDto.getUsername().isEmpty()) {
            user.setUsername(updatedUserDto.getUsername());
        }

        if (updatedUserDto.getPassword() != null && !updatedUserDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUserDto.getPassword()));
        }

        if (updatedUserDto.getEmail() != null && !updatedUserDto.getEmail().isEmpty()) {
            user.setEmail(updatedUserDto.getEmail());
        }

        userRepository.save(user);

        return UserInfoDto.from(user);
    }

    public UserInfoDto getUserInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));
        return UserInfoDto.from(user);
    }

    public UserInfoDto getUserInfoByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));
        return UserInfoDto.from(user);
    }

    public UserInfoDto getUserInfoByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));
        return UserInfoDto.from(user);
    }
}
