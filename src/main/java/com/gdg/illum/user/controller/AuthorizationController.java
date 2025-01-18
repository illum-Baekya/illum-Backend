package com.gdg.illum.user.controller;

import com.gdg.illum.user.dto.AccessTokenDto;
import com.gdg.illum.user.dto.TokenDto;
import com.gdg.illum.user.dto.UserSignInRequestDto;
import com.gdg.illum.user.dto.UserSignUpRequestDto;
import com.gdg.illum.user.jwt.TokenProvider;
import com.gdg.illum.user.jwt.TokenType;
import com.gdg.illum.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/authorization")
    public ResponseEntity<TokenDto> signUp(@RequestBody UserSignUpRequestDto requestDto) {
        TokenDto responseDto = userService.signUp(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/authorization")
    public ResponseEntity<TokenDto> signIn(@RequestBody UserSignInRequestDto requestDto) {
        TokenDto responseDto = userService.signIn(requestDto);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(Principal principal) {
        Long id = findIdByPrincipal(principal);
        userService.logout(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdraw(Principal principal) {
        Long id = findIdByPrincipal(principal);
        userService.withdraw(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/authorization/reissue")
    public ResponseEntity<AccessTokenDto> reissueAccessToken(HttpServletRequest request) {
        String refreshToken = tokenProvider.resolveToken(request);

        AccessTokenDto responseDto = userService.reissueAccessToken(refreshToken);

        return ResponseEntity.ok(responseDto);
    }

    private Long findIdByPrincipal(Principal principal) {
        return Long.parseLong(principal.getName());
    }
}
