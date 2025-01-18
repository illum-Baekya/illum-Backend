package com.gdg.illum.user.controller;

import com.gdg.illum.user.domain.User;
import com.gdg.illum.user.dto.TokenDto;
import com.gdg.illum.user.dto.UserLoginReqDto;
import com.gdg.illum.user.repository.UserRepository;
import com.gdg.illum.user.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginReqDto userLoginReqDto) {
        TokenDto token = authService.login(userLoginReqDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteAccount(Principal principal) {
        String username = principal.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        userRepository.delete(user);

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("회원탈퇴 성공");
    }

}
