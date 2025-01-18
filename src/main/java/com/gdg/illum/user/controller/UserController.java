package com.gdg.illum.user.controller;

import com.gdg.illum.user.dto.UpdatedUserDto;
import com.gdg.illum.user.dto.UserInfoDto;
import com.gdg.illum.user.dto.UserSignupReqDto;
import com.gdg.illum.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserInfoDto> signup(@Valid @RequestBody UserSignupReqDto userSignupReqDto) {
        UserInfoDto savedUser = userService.signUp(userSignupReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserInfoDto> getProfile(Principal principal) {
        UserInfoDto userInfo = userService.getUserInfo(principal.getName());
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/profile")
    public ResponseEntity<UserInfoDto> updateUser(Principal principal, @RequestBody UpdatedUserDto updatedUserDto) {
        UserInfoDto updatedUserInfo = userService.updateUser(principal, updatedUserDto);
        return ResponseEntity.ok(updatedUserInfo);
    }

    @GetMapping("/profile/username/{username}")
    public ResponseEntity<UserInfoDto> findByUsername(@PathVariable String username) {
        UserInfoDto userInfo = userService.getUserInfoByUsername(username);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/profile/email/{email}")
    public ResponseEntity<UserInfoDto> findByEmail(@PathVariable String email) {
        UserInfoDto userInfo = userService.getUserInfoByEmail(email);
        return ResponseEntity.ok(userInfo);
    }
}
