package com.gdg.illum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignupReqDto {
    private String username;
    private String password;
    private String email;
}
